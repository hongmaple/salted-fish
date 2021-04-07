package com.haiyan.deflower.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 订单状态
 * @author haiyan
 */
@TableName("tb_order_status")
@Data
public class OrderStatus {

    @TableId(type = IdType.INPUT)
    private String orderId;
    /**
     * 初始阶段：1、未付款、未发货；初始化所有数据
     * 付款阶段：2、已付款、未发货；更改付款时间
     * 发货阶段：3、已发货，未确认；更改发货时间、物流名称、物流单号
     * 成功阶段：4、已确认；更改交易结束时间
     * 关闭阶段：5、关闭； 更改更新时间，交易关闭时间。
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 付款时间
     */
    private Date paymentTime;

    /**
     * 发货时间
     */
    private Date consignTime;

    /**
     * 交易结束时间
     */
    private Date endTime;

    /**
     * 交易关闭时间
     */
    private Date closeTime;
}
