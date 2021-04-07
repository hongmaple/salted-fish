package com.haiyan.deflower.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haiyan.deflower.dao.OrderDetailDao;
import com.haiyan.deflower.mapper.OrderDetailMapper;
import com.haiyan.deflower.pojo.OrderDetail;
import org.springframework.stereotype.Repository;

/**
 * @author haiyan
 */
@Repository
public class OrderDetailDaoImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailDao {
}
