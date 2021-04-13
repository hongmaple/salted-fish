package com.haiyan.deflower.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author haiyan
 */
@Data
@ApiModel("新增-订单列表")
public class OrderListBody {
    private List<OrderBody> orderBodies;
    private Long total;
    private Long actualTotal;
}
