package com.haiyan.deflower.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 花
 * @author haiyan
 */
@Data
@TableName("tb_flower")
public class Flower {
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 分类id
     */
    private Long cid;
    /**
     * 标题
     */
    private String title;
    /**
     * 花封面地址
     */
    private String images;
    /**
     * 图片列表
     */
    private String imagesList;
    /**
     * 价格
     */
    private double price;
    /**
     * 规格
     */
    private String specification;

    /**
     * 新旧程度
     */
    private String oldNewLevel;

    /**
     * 简介
     */
    private String brief;

    /**
     * 类型
     */
    private int type;

    /**
     * 联系方式
     */
    private String contactWay;

    /**
     * 寄放时间
     */
    private Date depositingTime;

    /**
     * 状态
     */
    private String auditStatus;

    /**
     * 是否上架
     */
    private Boolean saleable;

    /**
     * 发布人
     */
    private Long createId;

    /**
     * 代理人
     */
    private Long backgroundAgentId;

    /**
     * 是否有效，逻辑删除用
     */
    private Boolean isDeleted;
    /**
     * 创建时间
     */
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date createTime;
    /**
     * 最后修改时间
     */
    private Date updateTime;
}