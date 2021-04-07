package com.haiyan.deflower.controller;

import com.haiyan.deflower.pojo.AjaxResult;
import com.haiyan.deflower.pojo.Category;
import com.haiyan.deflower.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 分类相关
 *
 * @author haiyan
 */
@Slf4j
@Api(tags = "分类相关")
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 新增分类
     * @param category 参数
     * @return 结果
     */
    @ApiOperation("新增分类")
    @PostMapping()
    public AjaxResult addCategory(@RequestBody Category category) {
        AjaxResult ajaxResult = AjaxResult.success("新增分类成功",categoryService.addCategory(category));
        return ajaxResult;
    }

    /**
     * 修改分类
     * @param category 分类
     * @return 结果
     */
    @ApiOperation("修改分类")
    @PutMapping()
    public AjaxResult updateCategory(@RequestBody Category category) {
        AjaxResult ajaxResult = AjaxResult.success("修改分类成功",categoryService.updateCategory(category));
        return ajaxResult;
    }

    /**
     * 查询所有分类
     * @return
     */
    @ApiOperation("查询所有分类")
    @GetMapping
    public AjaxResult queryCategory() {
        AjaxResult ajaxResult = AjaxResult.success("查询所有分类成功",categoryService.queryCategory());
        return ajaxResult;
    }
}
