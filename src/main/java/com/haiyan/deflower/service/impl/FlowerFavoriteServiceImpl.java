package com.haiyan.deflower.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiyan.deflower.dao.FlowerDao;
import com.haiyan.deflower.dao.FlowerFavoriteDao;
import com.haiyan.deflower.dto.response.FlowerRowVo;
import com.haiyan.deflower.exception.ExceptionResult;
import com.haiyan.deflower.mapper.FlowerFavoriteMapper;
import com.haiyan.deflower.pojo.*;
import com.haiyan.deflower.service.FlowerFavoriteService;
import com.haiyan.deflower.utils.ServletUtils;
import com.haiyan.deflower.utils.UserUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author haiyan
 */
@Service
public class FlowerFavoriteServiceImpl implements FlowerFavoriteService {

    private final FlowerFavoriteDao flowerFavoriteDao;
    private final FlowerFavoriteMapper flowerFavoriteMapper;
    private final FlowerDao flowerDao;
    private final ModelMapper modelMapper;

    @Autowired
    private UserUtils userUtils;

    public FlowerFavoriteServiceImpl(FlowerFavoriteDao flowerFavoriteDao, FlowerFavoriteMapper flowerFavoriteMapper, FlowerDao flowerDao, ModelMapper modelMapper) {
        this.flowerFavoriteDao = flowerFavoriteDao;
        this.flowerFavoriteMapper = flowerFavoriteMapper;
        this.flowerDao = flowerDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public Long addFlowerFavorite(FlowerFavorite flowerFavorite) {
        // 获取登录用户
        User user = userUtils.getUser(ServletUtils.getRequest());
        if (Objects.isNull(user)) {
            throw new ExceptionResult("user","false",null,"请先登陆");
        }
        flowerFavorite.setUid(user.getId());
        if (!flowerFavoriteDao.save(flowerFavorite)) {
            throw new ExceptionResult("flowerFavorite","false",null,"收藏失败");
        }
        return flowerFavorite.getId();
    }

    @Override
    public Boolean deletedFlowerFavorite(Long id) {
        // 获取登录用户
        User user = userUtils.getUser(ServletUtils.getRequest());
        if (Objects.isNull(user)) {
            throw new ExceptionResult("user","false",null,"请先登陆");
        }
        if (flowerFavoriteMapper.deleteById(id)==0) {
            throw new ExceptionResult("flowerFavorite","false",null,"取消收藏失败");
        }
        return true;
    }

    @Override
    public Boolean verify(Long favoriteId) {
        // 获取登录用户
        User user = userUtils.getUser(ServletUtils.getRequest());
        if (Objects.isNull(user)) {
            throw new ExceptionResult("user","false",null,"请先登陆");
        }
        if(flowerFavoriteDao.lambdaQuery().eq(FlowerFavorite::getUid,user.getId()).eq(FlowerFavorite::getFavoriteId,favoriteId).count()==0) {
            return false;
        }
        return true;
    }

    @Override
    public PageList<FlowerRowVo> listCollection(PageDomain pageDomain) {
        // 获取登录用户
        User user = userUtils.getUser(ServletUtils.getRequest());
        if (Objects.isNull(user)) {
            throw new ExceptionResult("user","false",null,"请先登陆");
        }
        Page<FlowerFavorite> page = flowerFavoriteDao
                .lambdaQuery()
                .eq(FlowerFavorite::getUid, user.getId())
                .orderByDesc(FlowerFavorite::getCreateTime)
                .page(new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize()));
        List<FlowerRowVo> flowerRowVos = new ArrayList<>();
        page.getRecords().forEach(flowerFavorite -> {
            Flower flower = flowerDao.getById(flowerFavorite.getFavoriteId());
            if (!Objects.isNull(flower)) {
                FlowerRowVo flowerRowVo = modelMapper.map(flower,FlowerRowVo.class);
                flowerRowVo.setCategoryName("");
                flowerRowVos.add(flowerRowVo);
            }
        });
        return PageList.of(flowerRowVos, page);
    }

    @Override
    public Integer getFlowerFavoriteCunt() {
        User user = userUtils.getUser(ServletUtils.getRequest());
        if (Objects.isNull(user)) {
            throw new ExceptionResult("user","false",null,"请先登陆");
        }
        return flowerFavoriteDao.lambdaQuery().eq(FlowerFavorite::getUid,user.getId()).count();
    }
}
