package com.haiyan.deflower.dto.request;

import com.haiyan.deflower.pojo.PageDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author haiyan
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("查询订单")
public class OrderQuery extends PageDomain {
    @ApiModelProperty("订单id")
    private String orderId;
    @ApiModelProperty("订单状态")
    private Integer status;
}
