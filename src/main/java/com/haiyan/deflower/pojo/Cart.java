package com.haiyan.deflower.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 购物车
 * @author haiyan
 */
@Data
@TableName("tb_cart")
public class Cart {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 卖家id
     */
    private Long sellerId;

    /**
     * 后台代理用户id
     */
    private Long backgroundAgentId;

    /**
     * 商品id
     */
    private Long skuId;
    /**
     * 标题
     */
    private String title;
    /**
     * 图片
     */
    private String image;
    /**
     * 加入购物车时的价格
     */
    private Long price;
    /**
     * 购买数量
     */
    private Integer num;

    public Cart() {
        this.num=0;
    }
}