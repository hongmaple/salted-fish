package com.haiyan.deflower.controller;

import com.haiyan.deflower.dto.request.FlowerQuery;
import com.haiyan.deflower.dto.response.TagRowVo;
import com.haiyan.deflower.pojo.AjaxResult;
import com.haiyan.deflower.pojo.Flower;
import com.haiyan.deflower.service.FlowerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 花请求处理
 *
 * @author haiyan
 */
@Slf4j
@Api(tags = "花相关")
@RestController
@RequestMapping("/flower")
public class FlowerController {

    private final FlowerService flowerService;

    public FlowerController(FlowerService flowerService) {
        this.flowerService = flowerService;
    }

    /**
     * 新增花
     * @param flower 花
     * @return 花id
     */
    @ApiOperation("新增花")
    @PostMapping()
    public AjaxResult addFlower(@RequestBody Flower flower) {
        AjaxResult ajaxResult = AjaxResult.success("新增花成功",flowerService.addFlower(flower));
        return ajaxResult;
    }

    /**
     * 修改花
     * @param flower 花
     * @return 结果
     */
    @ApiOperation("修改花")
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
    @ApiOperation("分页查询花")
    @PostMapping("/list")
    public AjaxResult listFlower(@RequestBody FlowerQuery query) {
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
}
