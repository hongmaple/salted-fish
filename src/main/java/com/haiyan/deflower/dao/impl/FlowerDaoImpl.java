package com.haiyan.deflower.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haiyan.deflower.dao.FlowerDao;
import com.haiyan.deflower.mapper.FlowerMapper;
import com.haiyan.deflower.pojo.Flower;
import org.springframework.stereotype.Repository;

/**
 * @author haiyan
 */
@Repository
public class FlowerDaoImpl extends ServiceImpl<FlowerMapper, Flower> implements FlowerDao {

}
