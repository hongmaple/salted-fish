package com.haiyan.deflower.service;


import com.haiyan.deflower.dto.request.FlowerQuery;
import com.haiyan.deflower.dto.response.FlowerRowVo;
import com.haiyan.deflower.dto.response.TagRowVo;
import com.haiyan.deflower.pojo.Flower;
import com.haiyan.deflower.pojo.PageList;

import java.util.List;

/**
 * @author haiyan
 */
public interface FlowerService {
    /**
     * 新增花
     * @param flower 花
     * @return 花id
     */
    Long addFlower(Flower flower);

    /**
     * 修改花
     * @param flower 花
     * @return 结果
     */
    Boolean updateFlower(Flower flower);

    /**
     * 分页查询
     * @param query 参数
     * @return 分页数据
     */
    PageList<FlowerRowVo> listFlower(FlowerQuery query);

    /**
     * 加载后台商品列表
     * @param query 参数
     * @return 商品列表
     */
    PageList<FlowerRowVo> backstageListFlower(FlowerQuery query);

    /**
     * 加载前台首页商品列表
     * @return 商品列表
     */
    List<TagRowVo> listTagRowVo();

    /**
     * 查询商品详情
     * @param id id
     * @return 详情
     */
    Flower getFlowerDetail(Long id);

    /**
     * 修改审核状态
     * @param id id
     * @param auditStatus 审核状态
     * @return 结果
     */
    Boolean updateAuditStatus(Long id,String auditStatus);


    /**
     * 修改上架下架
     * @param id id
     * @param saleable 上架下架
     * @return 结果
     */
    Boolean updateSaleable(Long id,Boolean saleable);

    /**
     * 代理商品
     * @param id
     * @param auditStatus
     * @return
     */
    Boolean agency(Long id,String auditStatus);

    /**
     * 修改上架下架
     * @param id id
     * @param saleable 上架下架
     * @return 结果
     */
    Boolean agencyUpdateSaleable(Long id,Boolean saleable);
}
