package com.haiyan.deflower.service;

import com.haiyan.deflower.dto.request.OrderBody;
import com.haiyan.deflower.dto.request.OrderQuery;
import com.haiyan.deflower.dto.response.OrderDetailsVo;
import com.haiyan.deflower.dto.response.OrderRowVo;
import com.haiyan.deflower.pojo.PageList;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author haiyan
 */
public interface OrderService {

    /**
     * 创建订单
     *
     * @param order
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    Long createOrder(OrderBody order);

    /**
     * 根据订单id查询订单
     *
     * @param id
     * @return
     */
    OrderDetailsVo queryById(String id);

    /**
     * 分页查询登录用户订单
     *
     * @param page
     * @param rows
     * @param status
     * @return
     */
    PageList<OrderRowVo> queryUserOrderList(Integer page, Integer rows, Integer status);

    /**
     * 后台加载订单数据
     * @param query 参数
     * @return 订单数据
     */
    PageList<OrderRowVo> queryOrderList(OrderQuery query);

    /**
     * 更新订单状态
     *
     * @param id
     * @param status
     * @return
     */
    Boolean updateStatus(String id, Integer status);

    /**
     * 获取订单数
     * @return 订单数
     */
    Integer getOrderCount();

    /**
     * 删除订单
     * @param id 订单id
     * @return 结果
     */
    Boolean deletedOrder(String id);

}
