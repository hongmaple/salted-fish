package com.haiyan.deflower.service;

import com.haiyan.deflower.dto.request.EvaluationQuery;
import com.haiyan.deflower.dto.response.EvaluationRowVo;
import com.haiyan.deflower.pojo.Evaluation;
import com.haiyan.deflower.pojo.PageList;

/**
 * @author maple
 */
public interface EvaluationService {

    /**
     * 新增留言
     * @param evaluation 留言
     * @return 留言id
     */
    Long saveEvaluation(Evaluation evaluation);

    /**
     * 分页查询 留言
     * @param query 分页数据
     * @return 结果
     */
    PageList<EvaluationRowVo> queryEvaluationList(EvaluationQuery query);


    /**
     * 分页查询 子留言
     * @param query 分页数据
     * @return 结果
     */
    PageList<EvaluationRowVo> subQueryEvaluationList(EvaluationQuery query);

    /**
     * 根据商品id 查询 留言数
     * @param prodId 商品id
     * @return 留言数
     */
    Integer countEvaluationByProdId(Long prodId);

}
