package com.haiyan.deflower.dto.response;

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
@ApiModel("订单一行数据")
public class OrderRowVo extends Order {
    private List<OrderDetail> orderDetails;
    private String sellerName;
}
