package com.haiyan.deflower.controller;

import com.alibaba.fastjson.JSON;
import com.haiyan.deflower.pojo.*;
import com.haiyan.deflower.service.BackgroundUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author haiyan
 */
@Api(tags = "后台用户")
@RestController
@RequestMapping("/background/user")
public class BackgroundUserController {
    private final BackgroundUserService backgroundUserService;

    public BackgroundUserController(BackgroundUserService backgroundUserService) {
        this.backgroundUserService = backgroundUserService;
    }

    @PostMapping("/register")
    @ApiOperation("注册")
    public AjaxResult register(@Valid @RequestBody BackgroundUser user) {
        AjaxResult ajaxResult = AjaxResult.success(backgroundUserService.register(user));
        return ajaxResult;
    }

    /**
     * 登陆
     * @param user 参数
     * @return 结果
     */
    @PostMapping("/login")
    @ApiOperation("登陆")
    public AjaxResult login(@Valid @RequestBody BackgroundUser user) {
        AjaxResult ajaxResult = AjaxResult.success(JSON.toJSON(backgroundUserService.login(user)));
        return ajaxResult;
    }

    /**
     * 修改用户信息，密码，手机号，头像
     * @param user 用户
     * @return 结果
     */
    @PutMapping
    @ApiOperation("修改用户信息")
    public AjaxResult updateUser(@Valid @RequestBody BackgroundUser user) {
        AjaxResult ajaxResult = AjaxResult.success(backgroundUserService.updateUser(user));
        return ajaxResult;
    }


    /**
     * 修改用户信息，密码，手机号，头像
     * @param user 用户
     * @return 结果
     */
    @PutMapping("/admin")
    @ApiOperation("修改用户信息")
    public AjaxResult adminUpdateUser(@Valid @RequestBody BackgroundUser user) {
        AjaxResult ajaxResult = AjaxResult.success(backgroundUserService.adminUpdateUser(user));
        return ajaxResult;
    }



    /**
     * 加载后台用户
     * @param pageDomain 参数
     * @return 结果
     */
    @PostMapping("/list")
    @ApiOperation("加载后台用户")
    public AjaxResult ListUser(@RequestBody PageDomain pageDomain) {
        AjaxResult ajaxResult = AjaxResult.success(backgroundUserService.ListUser(pageDomain));
        return ajaxResult;
    }

    /**
     * 获取当前用户登陆的信息
     * @return 结果
     */
    @PostMapping("/info")
    @ApiOperation("获取当前用户登陆的信息")
    public AjaxResult getUserInfo() {
        AjaxResult ajaxResult = AjaxResult.success(backgroundUserService.getUserInfo());
        return ajaxResult;
    }

    /**
     * 删除用户
     * @param id 用户id
     * @return 结果
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除用户")
    public AjaxResult deletedUser(@PathVariable Long id) {
        AjaxResult ajaxResult = AjaxResult.success(backgroundUserService.deletedUser(id));
        return ajaxResult;
    }
}
