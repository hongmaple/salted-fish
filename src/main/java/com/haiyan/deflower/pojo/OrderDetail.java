package com.haiyan.deflower.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 订单详情
 * @author haiyan
 */
@TableName("tb_order_detail")
@Data
public class OrderDetail {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 商品id
     */
    private Long skuId;

    /**
     * 商品购买数量
     */
    private Integer num;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品单价
     */
    private Long price;

    /**
     * 图片
     */
    private String image;
}
