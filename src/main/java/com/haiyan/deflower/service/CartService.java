package com.haiyan.deflower.service;

import com.haiyan.deflower.pojo.Cart;
import com.haiyan.deflower.pojo.PageDomain;
import com.haiyan.deflower.pojo.PageList;

/**
 * @author maple
 */
public interface CartService {

    /**
     * 添加购物车
     * @param cart 参数
     * @return id
     */
    Long addCart(Cart cart);

    /**
     * 修改购物车中的数据
     * @param cart 参数
     * @return 结果
     */
    Boolean updateCart(Cart cart);

    /**
     * 删除购物车数据
     * @param id 购物车id
     * @return 结果
     */
    Boolean deletedCart(Long id);

    /**
     * 分页查询购物车
     * @param pageDomain 分页数据
     * @return 购物车数据
     */
    PageList<Cart> listCart(PageDomain pageDomain);

    /**
     * 获取商品的购物详情
     * @param id
     * @return
     */
    Cart getCartDetail(Long id);

    /**
     * 获取商品的购物详情
     * @param skuId
     * @return
     */
    Cart getCartDetailBySkuId(Long skuId);

    /**
     * 获取购物车数量
     * @return 数量
     */
    Integer count();

}
