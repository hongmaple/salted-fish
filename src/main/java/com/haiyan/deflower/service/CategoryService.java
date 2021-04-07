package com.haiyan.deflower.service;

import com.haiyan.deflower.pojo.Category;

import java.util.List;

/**
 * @author haiyan
 */
public interface CategoryService {

    /**
     * 新增分类
     * @param category 参数
     * @return 结果
     */
    Long addCategory(Category category);

    /**
     * 修改分类
     * @param category 分类
     * @return 结果
     */
    Boolean updateCategory(Category category);

    /**
     * 查询所有分类
     * @return
     */
    List<Category> queryCategory();
}
