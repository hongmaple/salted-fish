package com.haiyan.deflower.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 花 收藏
 * @author haiyan
 */
@Data
@TableName("tb_flower_favorite")
public class FlowerFavorite {
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long uid;
    /**
     * 花的id
     */
    private Long favoriteId;
    /**
     * 创建时间
     */
    private Date createTime;
}
