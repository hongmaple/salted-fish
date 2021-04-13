package com.haiyan.deflower.controller;

import com.haiyan.deflower.dto.request.OrderBody;
import com.haiyan.deflower.dto.request.OrderListBody;
import com.haiyan.deflower.dto.request.OrderQuery;
import com.haiyan.deflower.dto.response.OrderRowVo;
import com.haiyan.deflower.pojo.AjaxResult;
import com.haiyan.deflower.pojo.Order;
import com.haiyan.deflower.pojo.PageList;
import com.haiyan.deflower.service.OrderService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author haiyan
 */
@RestController
@Api("订单服务接口")
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     *
     * @param orderListBody 订单对象
     * @return 订单编号
     */
    @PostMapping
    @ApiOperation(value = "创建订单接口，返回订单编号", notes = "创建订单")
    @ApiImplicitParam(name = "order", required = true, value = "订单的json对象,包含订单条目和物流信息")
    public AjaxResult createOrder(@RequestBody @Valid OrderListBody orderListBody) {
        List<Long> ids = this.orderService.createOrder(orderListBody);
        AjaxResult ajaxResult = AjaxResult.success("创建订单成功",ids);
        return ajaxResult;
    }

    /**
     * 删除订单
     * @param id 订单id
     * @return 结果
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除订单", notes = "删除订单")
    @ApiImplicitParam(name = "id", required = true, value = "订单的编号")
    public Boolean deletedOrder(@PathVariable String id) {
        return this.orderService.deletedOrder(id);
    }

    /**
     * 根据订单编号查询订单
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "根据订单编号查询订单，返回订单对象", notes = "查询订单")
    @ApiImplicitParam(name = "id", required = true, value = "订单的编号")
    public AjaxResult queryOrderById(@PathVariable("id") String id) {
        Order order = this.orderService.queryById(id);
        AjaxResult ajaxResult = AjaxResult.success("查询订单成功",order);
        return ajaxResult;
    }

    /**
     * 分页查询当前用户订单
     *
     * @param status 订单状态
     * @return 分页订单数据
     */
    @GetMapping("list")
    @ApiOperation(value = "分页查询当前用户订单，并且可以根据订单状态过滤", notes = "分页查询当前用户订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", defaultValue = "1", type = "Integer"),
            @ApiImplicitParam(name = "rows", value = "每页大小", defaultValue = "5", type = "Integer"),
            @ApiImplicitParam(name = "status", value = "订单状态：1未付款，2已付款未发货，3已发货未确认，4.交易成功，5交易关闭", type = "Integer"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "订单的分页结果"),
            @ApiResponse(code = 404, message = "没有查询到结果"),
            @ApiResponse(code = 500, message = "查询失败"),
    })
    public AjaxResult queryUserOrderList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "status", required = false) Integer status) {
        PageList<OrderRowVo> result = this.orderService.queryUserOrderList(page, rows, status);
        AjaxResult ajaxResult = AjaxResult.success("查询订单成功",result);
        return ajaxResult;
    }

    /**
     * 后台加载订单数据
     * @param query 参数
     * @return 订单数据
     */
    @PostMapping("/user/list")
    @ApiOperation(value = "分页查询用户订单，并且可以根据订单状态过滤", notes = "后台加载订单数据")
    public AjaxResult queryOrderList(@RequestBody OrderQuery query) {
        PageList<com.haiyan.deflower.dto.response.OrderRowVo> result = this.orderService.queryOrderList(query);
        AjaxResult ajaxResult = AjaxResult.success("查询订单成功",result);
        return ajaxResult;
    }

    /**
     * 更新订单状态
     *
     * @param id
     * @param status
     * @return
     */
    @PutMapping("{id}/{status}")
    @ApiOperation(value = "更新订单状态", notes = "更新订单状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单编号", type = "Long"),
            @ApiImplicitParam(name = "status", value = "订单状态：1未付款，2已付款未发货，3已发货未确认，4已确认未评价，5交易关闭，6交易成功，已评价", type = "Integer"),
    })

    @ApiResponses({
            @ApiResponse(code = 204, message = "true：修改状态成功；false：修改状态失败"),
            @ApiResponse(code = 400, message = "请求参数有误"),
            @ApiResponse(code = 500, message = "查询失败")
    })
    public AjaxResult updateStatus(@PathVariable("id") String id, @PathVariable("status") Integer status) {
        Boolean boo = this.orderService.updateStatus(id, status);
        AjaxResult ajaxResult = AjaxResult.success("查询订单成功",boo);
        return ajaxResult;
    }
    @GetMapping("count")
    @ApiOperation(value = "获取订单数")
    public Integer getOrderCount() {
        return orderService.getOrderCount();
    }
}
