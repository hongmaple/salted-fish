package com.haiyan.deflower.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haiyan.deflower.dao.CategoryDao;
import com.haiyan.deflower.mapper.CategoryMapper;
import com.haiyan.deflower.pojo.Category;
import org.springframework.stereotype.Repository;


/**
 * @author maple
 */
@Repository
public class CategoryDaoImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryDao {
}
