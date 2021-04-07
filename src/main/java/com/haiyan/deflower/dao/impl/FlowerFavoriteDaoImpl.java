package com.haiyan.deflower.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haiyan.deflower.dao.FlowerFavoriteDao;
import com.haiyan.deflower.mapper.FlowerFavoriteMapper;
import com.haiyan.deflower.pojo.FlowerFavorite;
import org.springframework.stereotype.Repository;

/**
 * @author haiyan
 */
@Repository
public class FlowerFavoriteDaoImpl extends ServiceImpl<FlowerFavoriteMapper, FlowerFavorite> implements FlowerFavoriteDao {
}
