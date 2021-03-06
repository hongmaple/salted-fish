package com.haiyan.deflower.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiyan.deflower.dao.*;
import com.haiyan.deflower.dto.request.OrderListBody;
import com.haiyan.deflower.dto.request.OrderQuery;
import com.haiyan.deflower.dto.response.OrderDetailsVo;
import com.haiyan.deflower.dto.response.OrderRowVo;
import com.haiyan.deflower.exception.ExceptionResult;
import com.haiyan.deflower.mapper.OrderDetailMapper;
import com.haiyan.deflower.mapper.OrderMapper;
import com.haiyan.deflower.mapper.OrderStatusMapper;
import com.haiyan.deflower.pojo.*;
import com.haiyan.deflower.service.OrderService;
import com.haiyan.deflower.utils.IdWorker;
import com.haiyan.deflower.utils.ServletUtils;
import com.haiyan.deflower.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author haiyan
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderDao orderDao;
    private final OrderStatusMapper orderStatusMapper;
    private final OrderDetailMapper orderDetailMapper;
    private final OrderDetailDao orderDetailDao;
    private final IdWorker idWorker;
    private final ModelMapper modelMapper;
    private final FlowerDao flowerDao;
    private final BackgroundUserDao backgroundUserDao;
    private final UserDao userDao;

    @Autowired
    private UserUtils userUtils;

    public OrderServiceImpl(OrderMapper orderMapper, OrderDao orderDao, OrderStatusMapper orderStatusMapper, OrderDetailMapper orderDetailMapper, OrderDetailDao orderDetailDao, IdWorker idWorker, ModelMapper modelMapper, FlowerDao flowerDao, BackgroundUserDao backgroundUserDao, UserDao userDao) {
        this.orderMapper = orderMapper;
        this.orderDao = orderDao;
        this.orderStatusMapper = orderStatusMapper;
        this.orderDetailMapper = orderDetailMapper;
        this.orderDetailDao = orderDetailDao;
        this.idWorker = idWorker;
        this.modelMapper = modelMapper;
        this.flowerDao = flowerDao;
        this.backgroundUserDao = backgroundUserDao;
        this.userDao = userDao;
    }

    @Override
    public List<String> createOrder(OrderListBody orderListBody) {
        // ??????????????????
        User user = userUtils.getUser(ServletUtils.getRequest());
        if (Objects.isNull(user)) {
            throw new ExceptionResult("user","false",null,"????????????");
        }
        List<String> orderIds = new ArrayList<>();
        orderListBody.getOrderBodies().forEach(orderBody -> {
            // ??????orderId
            long orderId = idWorker.nextId();
            // ???????????????
            Order order = modelMapper.map(orderBody,Order.class);
            order.setBuyerNick(user.getUsername());
            order.setCreateTime(new Date());
            order.setOrderId(String.valueOf(orderId));
            order.setUserId(user.getId());
            order.setStatus(1);
            // ????????????
            this.orderMapper.insert(order);

            // ??????????????????
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setOrderId(String.valueOf(orderId));
            orderStatus.setCreateTime(order.getCreateTime());
            // ????????????????????????
            orderStatus.setStatus(1);

            this.orderStatusMapper.insert(orderStatus);

            // ?????????????????????orderId
            orderBody.getOrderDetails().forEach(od -> {
                Integer count1 = flowerDao.lambdaQuery().eq(Flower::getId, od.getSkuId()).eq(Flower::getCreateId, user.getId()).count();
                if (count1>0) {
                    throw new ExceptionResult("cart","false",null,"?????????????????????????????????");
                }
                od.setOrderId(String.valueOf(orderId));
                this.orderDetailMapper.insert(od);
            });
            // ??????????????????,????????????????????????

            log.debug("??????????????????????????????{}?????????id???{}", orderId, user.getId());

            orderIds.add(String.valueOf(orderId));
        });

        return orderIds;
    }

    @Override
    public OrderDetailsVo queryById(String id) {
        // ??????????????????
        User user = userUtils.getUser(ServletUtils.getRequest());
        if (Objects.isNull(user)) {
            throw new ExceptionResult("user","false",null,"????????????");
        }
        // ????????????
        Order order = this.orderMapper.selectById(id);
        if (Objects.isNull(order)) {
            throw new ExceptionResult("user","000001",null,"???????????????");
        }
        OrderDetailsVo orderDetailsVo = modelMapper.map(order,OrderDetailsVo.class);
        if (order.getBackgroundAgentId()==0) {
            BackgroundUser backgroundUser = backgroundUserDao.getById(order.getBackgroundAgentId());
            orderDetailsVo.setSellerName(Objects.isNull(backgroundUser)? "???????????????":backgroundUser.getUsername());
        }else {
            User SellerUser = userDao.getById(order.getSellerId());
            orderDetailsVo.setSellerName(Objects.isNull(SellerUser)? "???????????????":SellerUser.getUsername());
        }
        // ??????????????????
        OrderDetail detail = new OrderDetail();
        detail.setOrderId(id);
        List<OrderDetail> details = this.orderDetailDao
                .lambdaQuery()
                .eq(OrderDetail::getOrderId,id)
                .list();
        orderDetailsVo.setOrderDetails(details);

        // ??????????????????
        OrderStatus status = this.orderStatusMapper.selectById(order.getOrderId());
        orderDetailsVo.setStatus(status.getStatus());
        return orderDetailsVo;
    }

    @Override
    public PageList<OrderRowVo> queryUserOrderList(Integer page, Integer rows, Integer status) {
        // ??????????????????
        User user = userUtils.getUser(ServletUtils.getRequest());
        if (Objects.isNull(user)) {
            throw new ExceptionResult("user","false",null,"????????????");
        }
        LambdaQueryChainWrapper<Order> lambdaQuery = orderDao.lambdaQuery();
        if (status>0) {
            lambdaQuery.eq(Order::getStatus,status);
        }
        lambdaQuery.orderByDesc(Order::getUpdateTime);
        Page<Order> orderPage = lambdaQuery
                .eq(Order::getUserId, userUtils.getUser(ServletUtils.getRequest()).getId())
                .orderByDesc(Order::getCreateTime)
                .page(new Page<>(page, rows));
        List<OrderRowVo> orderRowVos = new LinkedList<>();
        orderPage.getRecords().forEach(order -> {
            // ??????????????????
            OrderRowVo orderRowVo = modelMapper.map(order,OrderRowVo.class);
            List<OrderDetail> details = this.orderDetailDao
                    .lambdaQuery()
                    .eq(OrderDetail::getOrderId,order.getOrderId())
                    .list();
            if (order.getBackgroundAgentId()==0) {
                BackgroundUser backgroundUser = backgroundUserDao.getById(order.getBackgroundAgentId());
                orderRowVo.setSellerName(Objects.isNull(backgroundUser)? "???????????????":backgroundUser.getUsername());
            }else {
                User SellerUser = userDao.getById(order.getSellerId());
                orderRowVo.setSellerName(Objects.isNull(SellerUser)? "???????????????":SellerUser.getUsername());
            }
            orderRowVo.setOrderDetails(details);
            orderRowVos.add(orderRowVo);
        });
        return PageList.of(orderRowVos, orderPage);
    }

    @Override
    public PageList<OrderRowVo> queryOrderList(OrderQuery query) {
        // ??????????????????
        User user = userUtils.getUser(ServletUtils.getRequest());
        if (Objects.isNull(user)) {
            throw new ExceptionResult("user","false",null,"????????????");
        }
        LambdaQueryChainWrapper<Order> lambdaQuery = orderDao.lambdaQuery();
        if (StringUtils.hasText(query.getOrderId())) {
            lambdaQuery.like(Order::getOrderId,query.getOrderId());
        }
        if (query.getStatus()>0) {
            lambdaQuery.eq(Order::getStatus,query.getStatus());
        }
        if (query.getType()!=null) {
            if (query.getType()==0) {
                lambdaQuery.eq(Order::getSellerId,user.getId());
            }else {
                lambdaQuery.eq(Order::getBackgroundAgentId,user.getId());
            }
        }
        lambdaQuery.orderByDesc(Order::getUpdateTime);
        Page<Order> orderPage = lambdaQuery
                .orderByDesc(Order::getCreateTime)
                .page(new Page<>(query.getPageNum(), query.getPageSize()));
        List<OrderRowVo> orderRowVos = new ArrayList<>();
        orderPage.getRecords().forEach(order -> {
            // ??????????????????
            OrderRowVo orderRowVo = modelMapper.map(order,OrderRowVo.class);
            List<OrderDetail> details = this.orderDetailDao
                    .lambdaQuery()
                    .eq(OrderDetail::getOrderId,order.getOrderId())
                    .list();
            orderRowVo.setOrderDetails(details);
            orderRowVos.add(orderRowVo);
        });
        return PageList.of(orderRowVos, orderPage);
    }

    @Override
    public Boolean updateStatus(String id, Integer status) {
        // ??????????????????
        User user = userUtils.getUser(ServletUtils.getRequest());
        if (Objects.isNull(user)) {
            throw new ExceptionResult("user","false",null,"????????????");
        }
        OrderStatus record = new OrderStatus();
        record.setOrderId(id);
        record.setStatus(status);
        // ????????????????????????????????????
        switch (status) {
            case 2:
                // ??????
                record.setPaymentTime(new Date());
                break;
            case 3:
                // ??????
                record.setConsignTime(new Date());
                break;
            case 4:
                // ???????????????????????????
                record.setEndTime(new Date());
                break;
            case 5:
                // ???????????????????????????
                record.setCloseTime(new Date());
                break;
            default:
                return null;
        }
        int count = this.orderStatusMapper.updateById(record);
        Order Order = new Order();
        Order.setOrderId(id);
        Order.setStatus(status);
        Order.setUpdateTime(new Date());
        orderMapper.updateById(Order);
        return count == 1;
    }

    @Override
    public Integer getOrderCount() {
        // ??????????????????
        User user = userUtils.getUser(ServletUtils.getRequest());
        if (Objects.isNull(user)) {
            throw new ExceptionResult("user","false",null,"????????????");
        }
        return orderDao.lambdaQuery().eq(Order::getUserId,user.getId()).count();
    }

    @Override
    public Boolean deletedOrder(String id) {
        // ??????????????????
        User user = userUtils.getUser(ServletUtils.getRequest());
        if (Objects.isNull(user)) {
            throw new ExceptionResult("user","false",null,"????????????");
        }
        return orderDao.removeById(id);
    }
}
