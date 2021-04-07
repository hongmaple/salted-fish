package com.haiyan.deflower.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haiyan.deflower.dao.EvaluationDao;
import com.haiyan.deflower.mapper.EvaluationMapper;
import com.haiyan.deflower.pojo.Evaluation;
import org.springframework.stereotype.Repository;

/**
 * @author maple
 */
@Repository
public class EvaluationDaoImpl extends ServiceImpl<EvaluationMapper, Evaluation> implements EvaluationDao {
}
