package com.haiyan.deflower.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * 收货地址
 * @author haiyan
 */
@Data
@TableName("tb_address")
public class Address {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    private Long uid;
    /**
     * 收件人
     */
    private String consignee;
    /**
     * 收件人手机
     */
    private String consigneePhone;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String district;
    /**
     * 详细地址
     */
    private String detail;

    public Address() {
    }
}
