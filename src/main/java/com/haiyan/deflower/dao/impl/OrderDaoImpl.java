package com.haiyan.deflower.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haiyan.deflower.dao.OrderDao;
import com.haiyan.deflower.mapper.OrderMapper;
import com.haiyan.deflower.pojo.Order;
import org.springframework.stereotype.Repository;

/**
 * @author haiyan
 */
@Repository
public class OrderDaoImpl extends ServiceImpl<OrderMapper, Order> implements OrderDao {
}
