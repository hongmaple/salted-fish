package com.haiyan.deflower.controller;

import com.haiyan.deflower.pojo.AjaxResult;
import com.haiyan.deflower.pojo.FlowerFavorite;
import com.haiyan.deflower.pojo.PageDomain;
import com.haiyan.deflower.service.FlowerFavoriteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收藏夹
 *
 * @author haiyan
 */
@Api(tags = "收藏夹")
@RestController
@RequestMapping("/flower/favorite")
public class FlowerFavoriteController {

    private final FlowerFavoriteService flowerFavoriteService;

    public FlowerFavoriteController(FlowerFavoriteService flowerFavoriteService) {
        this.flowerFavoriteService = flowerFavoriteService;
    }

    /**
     * 添加收藏
     * @param flowerFavorite 参数
     * @return id
     */
    @ApiOperation("添加收藏")
    @PostMapping()
    public AjaxResult addFlowerFavorite(@RequestBody FlowerFavorite flowerFavorite) {
        AjaxResult ajaxResult = AjaxResult.success("添加收藏成功",flowerFavoriteService.addFlowerFavorite(flowerFavorite));
        return ajaxResult;
    }

    /**
     * 删除收藏
     * @param id id
     * @return 结果
     */
    @ApiOperation("取消收藏")
    @DeleteMapping("/{id}")
    public AjaxResult deletedFlowerFavorite(@PathVariable Long id) {
        AjaxResult ajaxResult = AjaxResult.success("删除收藏成功",flowerFavoriteService.deletedFlowerFavorite(id));
        return ajaxResult;
    }

    /**
     * 验证是否收藏
     * @param favoriteId 花id
     * @return 结果
     */
    @ApiOperation("验证是否收藏")
    @GetMapping("/{favoriteId}")
    public AjaxResult verify(@PathVariable Long favoriteId) {
        AjaxResult ajaxResult = AjaxResult.success("验证是否收藏成功",flowerFavoriteService.verify(favoriteId));
        return ajaxResult;
    }

    /**
     * 加载所有收藏
     * @return 收藏
     */
    @ApiOperation("加载所有收藏")
    @PostMapping("/list")
    public AjaxResult listCollection(@RequestBody PageDomain pageDomain) {
        AjaxResult ajaxResult = AjaxResult.success("加载所有收藏",flowerFavoriteService.listCollection(pageDomain));
        return ajaxResult;
    }

    /**
     * 获取收藏数
     * @return 结果
     */
    @ApiOperation("获取收藏数")
    @GetMapping("/count")
    public Integer getFlowerFavoriteCunt() {
       return flowerFavoriteService.getFlowerFavoriteCunt();
    }
}
