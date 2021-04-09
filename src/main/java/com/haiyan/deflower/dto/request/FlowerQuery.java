package com.haiyan.deflower.dto.request;

import com.haiyan.deflower.pojo.PageDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 花
 * @author haiyan
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("查询花")
public class FlowerQuery extends PageDomain {
    @ApiModelProperty("分类id")
    private Long cid;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("发布人")
    private Long createId;

    @ApiModelProperty("审核状态")
    private String auditStatus;

    @ApiModelProperty("入库状态")
    private String inventoryStatus;

    @ApiModelProperty("类型")
    private int type;
}