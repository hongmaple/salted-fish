package com.haiyan.deflower.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiyan.deflower.dao.CartDao;
import com.haiyan.deflower.dao.FlowerDao;
import com.haiyan.deflower.dao.UserDao;
import com.haiyan.deflower.dto.response.CartRowVo;
import com.haiyan.deflower.exception.ExceptionResult;
import com.haiyan.deflower.mapper.CartMapper;
import com.haiyan.deflower.pojo.*;
import com.haiyan.deflower.service.CartService;
import com.haiyan.deflower.utils.ServletUtils;
import com.haiyan.deflower.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author haiyan
 */
@Service
public class CartServiceImpl implements CartService {

    private final CartDao cartDao;
    private final CartMapper cartMapper;
    private final FlowerDao flowerDao;
    private final UserDao userDao;

    @Autowired
    private UserUtils userUtils;

    public CartServiceImpl(CartDao cartDao, CartMapper cartMapper, FlowerDao flowerDao, UserDao userDao) {
        this.cartDao = cartDao;
        this.cartMapper = cartMapper;
        this.flowerDao = flowerDao;
        this.userDao = userDao;
    }

    @Override
    public Long addCart(Cart cart) {
        User user = userUtils.getUser(ServletUtils.getRequest());
        if (Objects.isNull(user)) {
            throw new ExceptionResult("user","false",null,"请先登陆");
        }
        cart.setUserId(user.getId());
        Integer count1 = flowerDao.lambdaQuery().eq(Flower::getId, cart.getSkuId()).eq(Flower::getCreateId, user.getId()).count();
        if (count1>0) {
            throw new ExceptionResult("cart","false",null,"不能添加自己发布的商品");
        }
        Integer count = cartDao.lambdaQuery().eq(Cart::getSkuId, cart.getSkuId()).eq(Cart::getUserId,user.getId()).count();
        if (count==0) {
            if(!cartDao.save(cart)) {
                throw new ExceptionResult("cart","false",null,"添加失败");
            }
        }else {
            if(!cartDao.lambdaUpdate().eq(Cart::getSkuId,cart.getSkuId()).eq(Cart::getUserId,user.getId()).update(cart)) {
                throw new ExceptionResult("cart","false",null,"添加失败");
            }
        }
        return cart.getId();
    }

    @Override
    public Boolean updateCart(Cart cart) {
        User user = userUtils.getUser(ServletUtils.getRequest());
        if (Objects.isNull(user)) {
            throw new ExceptionResult("user","false",null,"请先登陆");
        }
        if (!cartDao.lambdaUpdate().eq(Cart::getId,cart.getId()).eq(Cart::getUserId,user.getId()).update(cart)) {
            throw new ExceptionResult("cart","false",null,"修改失败");
        }
        return true;
    }

    @Override
    public Boolean deletedCart(Long id) {
        User user = userUtils.getUser(ServletUtils.getRequest());
        if (Objects.isNull(user)) {
            throw new ExceptionResult("user","false",null,"请先登陆");
        }
        if(cartMapper.deleteById(id)==0) {
            throw new ExceptionResult("cart","false",null,"删除失败");
        }
        return true;
    }

    @Override
    public PageList<CartRowVo> listCart(PageDomain pageDomain) {
        User user = userUtils.getUser(ServletUtils.getRequest());
        if (Objects.isNull(user)) {
            throw new ExceptionResult("user","false",null,"请先登陆");
        }
        Page<Cart> page = cartDao.lambdaQuery().eq(Cart::getUserId,user.getId()).page(new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize()));
        List<Cart> carts = page.getRecords();
        List<CartRowVo> cartRowVos = new ArrayList<>();
        Map<Long, List<Cart>> collect = carts.parallelStream().collect(Collectors.groupingBy(Cart::getSellerId));
        Iterator<Map.Entry<Long, List<Cart>>> entries = collect.entrySet().iterator();
        while(entries.hasNext()){
            Map.Entry<Long, List<Cart>> entry = entries.next();
            Map<Long, List<Cart>> collect2 = entry.getValue().parallelStream().collect(Collectors.groupingBy(Cart::getBackgroundAgentId));
            Iterator<Map.Entry<Long, List<Cart>>> entries2 = collect2.entrySet().iterator();
            while(entries2.hasNext()){
                Map.Entry<Long, List<Cart>> entry2 = entries2.next();
                User user1 = userDao.getById(entry.getKey());
                User user2 = new User();
                CartRowVo cartRowVo = new CartRowVo();
                if (Objects.isNull(user1)) {
                    user2.setUsername("该卖家已注销");
                }
                if (entry2.getKey()>0) {
                    cartRowVo.setIsAgent(true);
                    cartRowVo.setBackgroundAgentId(entry2.getKey());
                }else {
                    cartRowVo.setIsAgent(false);
                }
                user2.setUsername(user1.getUsername());
                user2.setId(user1.getId());
                user2.setAvatarImage(user1.getAvatarImage());
                cartRowVo.setUser(user2);
                cartRowVo.setCarts(entry2.getValue());
                cartRowVos.add(cartRowVo);
            }
        }

        return PageList.of(cartRowVos, page);
    }

    @Override
    public Cart getCartDetail(Long id) {
        return cartDao.getById(id);
    }

    @Override
    public Cart getCartDetailBySkuId(Long skuId) {
        return cartDao.lambdaQuery().eq(Cart::getSkuId,skuId).one();
    }

    @Override
    public Integer count() {
        User user = userUtils.getUser(ServletUtils.getRequest());
        if (Objects.isNull(user)) {
            throw new ExceptionResult("user","false",null,"请先登陆");
        }
        return cartDao.lambdaQuery().eq(Cart::getUserId,user.getId()).count();
    }
}
