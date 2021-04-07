package com.haiyan.deflower.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiyan.deflower.dao.CartDao;
import com.haiyan.deflower.exception.ExceptionResult;
import com.haiyan.deflower.mapper.CartMapper;
import com.haiyan.deflower.pojo.*;
import com.haiyan.deflower.service.CartService;
import com.haiyan.deflower.utils.ServletUtils;
import com.haiyan.deflower.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author haiyan
 */
@Service
public class CartServiceImpl implements CartService {

    private final CartDao cartDao;
    private final CartMapper cartMapper;

    @Autowired
    private UserUtils userUtils;

    public CartServiceImpl(CartDao cartDao, CartMapper cartMapper) {
        this.cartDao = cartDao;
        this.cartMapper = cartMapper;
    }

    @Override
    public Long addCart(Cart cart) {
        User user = userUtils.getUser(ServletUtils.getRequest());
        if (Objects.isNull(user)) {
            throw new ExceptionResult("user","false",null,"请先登陆");
        }
        cart.setUserId(user.getId());
        Integer count = cartDao.lambdaQuery().eq(Cart::getSkuId, cart.getSkuId()).count();
        if (count==0) {
            if(!cartDao.save(cart)) {
                throw new ExceptionResult("cart","false",null,"添加失败");
            }
        }else {
            if(!cartDao.lambdaUpdate().eq(Cart::getSkuId,cart.getSkuId()).update(cart)) {
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
        if (!cartDao.lambdaUpdate().eq(Cart::getId,cart.getId()).update(cart)) {
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
    public PageList<Cart> listCart(PageDomain pageDomain) {
        Page<Cart> page = cartDao.lambdaQuery().page(new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize()));
        List<Cart> carts = page.getRecords();
        return PageList.of(carts, page);
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
