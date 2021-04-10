package com.haiyan.deflower.controller;

import com.haiyan.deflower.dto.request.FlowerQuery;
import com.haiyan.deflower.dto.response.FlowerRowVo;
import com.haiyan.deflower.dto.response.TagRowVo;
import com.haiyan.deflower.exception.ExceptionResult;
import com.haiyan.deflower.pojo.AjaxResult;
import com.haiyan.deflower.pojo.Flower;
import com.haiyan.deflower.pojo.PageList;
import com.haiyan.deflower.pojo.User;
import com.haiyan.deflower.service.FlowerService;
import com.haiyan.deflower.utils.ServletUtils;
import com.haiyan.deflower.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 商品相关请求处理
 *
 * @author haiyan
 */
@Slf4j
@Api(tags = "商品相关")
@RestController
@RequestMapping("/flower")
public class FlowerController {

    private final FlowerService flowerService;
    private final UserUtils userUtils;

    public FlowerController(FlowerService flowerService, UserUtils userUtils) {
        this.flowerService = flowerService;
        this.userUtils = userUtils;
    }

    /**
     * 新增商品
     * @param flower 商品
     * @return 商品id
     */
    @ApiOperation("新增商品")
    @PostMapping()
    public AjaxResult addFlower(@RequestBody Flower flower) {
        AjaxResult ajaxResult = AjaxResult.success("新增花成功",flowerService.addFlower(flower));
        return ajaxResult;
    }

    /**
     * 修改商品
     * @param flower 商品
     * @return 结果
     */
    @ApiOperation("修改商品")
    @PutMapping()
    public AjaxResult updateFlower(@RequestBody Flower flower) {
        AjaxResult ajaxResult = AjaxResult.success("修改花成功",flowerService.updateFlower(flower));
        return ajaxResult;
    }


    /**
     * 分页查询
     * @param query 参数
     * @return 分页数据
     */
    @ApiOperation("商品列表")
    @PostMapping("/list")
    public AjaxResult listFlower(@RequestBody FlowerQuery query) {
        query.setAuditStatus("1");
        AjaxResult ajaxResult = AjaxResult.success(flowerService.listFlower(query));
        return ajaxResult;
    }

    /**
     * 加载后台商品列表
     * @param query 参数
     * @return 商品列表
     */
    @ApiOperation("加载后台商品列表")
    @PostMapping("/backstage/list")
    public AjaxResult backstageListFlower(@RequestBody FlowerQuery query) {
        AjaxResult ajaxResult = AjaxResult.success(flowerService.listFlower(query));
        return ajaxResult;
    }

    /**
     * 我的宝贝
     * @param query 参数
     * @return 分页数据
     */
    @ApiOperation("我的宝贝")
    @PostMapping("/MyBabyList")
    public AjaxResult MyBabyList(@RequestBody  FlowerQuery query) {
        // 获取登录用户
        User user = userUtils.getUser(ServletUtils.getRequest());
        if (Objects.isNull(user)) {
            throw new ExceptionResult("user","false",null,"请先登陆");
        }
        query.setCreateId(user.getId());
        AjaxResult ajaxResult = AjaxResult.success(flowerService.listFlower(query));
        return ajaxResult;
    }

    /**
     * 加载前台首页商品列表
     * @return 商品列表
     */
    @ApiOperation("加载前台首页商品列表")
    @GetMapping("/prodTagList")
    public List<TagRowVo> listTagRowVo() {
           return flowerService.listTagRowVo();
    }

    /**
     * 查询商品详情
     * @param id id
     * @return 详情
     */
    @ApiOperation("查询商品详情")
    @GetMapping("/prodInfo/{id}")
    public Flower getFlowerDetail(@PathVariable Long id) {
        return flowerService.getFlowerDetail(id);
    }

    /**
     * 修改审核状态
     * @param id id
     * @param auditStatus 审核状态
     * @return 结果
     */
    @ApiOperation("修改审核状态")
    @PutMapping("/auditStatus/{id}/{auditStatus}")
    public Boolean updateAuditStatus(@PathVariable Long id,@PathVariable String auditStatus) {
        return  flowerService.updateAuditStatus(id,auditStatus);
    }
}
