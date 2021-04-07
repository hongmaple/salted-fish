package com.haiyan.deflower.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haiyan.deflower.dao.CartDao;
import com.haiyan.deflower.mapper.CartMapper;
import com.haiyan.deflower.pojo.Cart;
import org.springframework.stereotype.Repository;

/**
 * @author haiyan
 */
@Repository
public class CartDaoImpl extends ServiceImpl<CartMapper, Cart> implements CartDao {
}
