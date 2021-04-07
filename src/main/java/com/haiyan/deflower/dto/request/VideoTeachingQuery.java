package com.haiyan.deflower.dto.request;

import com.haiyan.deflower.pojo.PageDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 教学视频查询
 * @author haiyan
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("教学视频查询")
public class VideoTeachingQuery extends PageDomain {
    @ApiModelProperty("关键字")
    private String title;
}
