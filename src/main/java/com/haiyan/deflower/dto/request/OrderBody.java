package com.haiyan.deflower.dto.request;

import com.haiyan.deflower.pojo.Order;
import com.haiyan.deflower.pojo.OrderDetail;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
/**
 * @author haiyan
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("新增订单")
public class OrderBody extends Order {
    private List<OrderDetail> orderDetails;
}
