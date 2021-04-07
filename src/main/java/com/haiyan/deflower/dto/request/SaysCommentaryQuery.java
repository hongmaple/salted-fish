package com.haiyan.deflower.dto.request;

import com.haiyan.deflower.pojo.PageDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 花语解说查询
 * @author haiyan
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("查询花语解说")
public class SaysCommentaryQuery extends PageDomain {
    @ApiModelProperty("花名")
    private String flowerName;
}
