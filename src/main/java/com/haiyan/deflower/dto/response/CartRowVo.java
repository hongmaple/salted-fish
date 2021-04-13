package com.haiyan.deflower.dto.response;

import com.haiyan.deflower.pojo.Cart;
import com.haiyan.deflower.pojo.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author haiyan
 */
@Data
@ApiModel("购物车一行数据")
public class CartRowVo {
    private User user;
    private List<Cart> carts;
}
