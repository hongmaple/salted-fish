package com.haiyan.deflower.controller;

import com.haiyan.deflower.pojo.Address;
import com.haiyan.deflower.pojo.AjaxResult;
import com.haiyan.deflower.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


/**
 * @author haiyan
 */
@Api(tags = "收货地址")
@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * 新增收货地址
     * @param address 地址
     * @return 地址id
     */
    @ApiOperation("新增收货地址")
    @PostMapping()
    public AjaxResult addAddress(@RequestBody Address address) {
        AjaxResult ajaxResult = AjaxResult.success("新增收货地址成功",addressService.addAddress(address));
        return ajaxResult;
    }

    /**
     * 修改收货地址
     * @param address 地址
     * @return 结果
     */
    @ApiOperation("修改收货地址")
    @PutMapping()
    public AjaxResult updateAddress(@RequestBody Address address) {
        AjaxResult ajaxResult = AjaxResult.success("修改收货地址成功",addressService.updateAddress(address));
        return ajaxResult;
    }

    /**
     * 删除收货地址
     * @param id 地址id
     * @return 结果
     */
    @ApiOperation("删除收货地址")
    @DeleteMapping("/{id}")
    public AjaxResult deletedAddress(@PathVariable Long id) {
        AjaxResult ajaxResult = AjaxResult.success("修改收货地址成功",addressService.deletedAddress(id));
        return ajaxResult;
    }

    @ApiOperation("加载所有收货地址")
    @GetMapping()
    public AjaxResult listAddress() {
        AjaxResult ajaxResult = AjaxResult.success("加载所有收货地址",addressService.listAddress());
        return ajaxResult;
    }

    /**
     * 获取地址详情
     * @param id 地址id
     * @return 详情
     */
    @ApiOperation("获取地址详情")
    @GetMapping("/{id}")
    public Address getDetail(@PathVariable Long id) {
        return addressService.getDetail(id);
    }
}
