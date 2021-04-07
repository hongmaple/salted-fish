package com.haiyan.deflower.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haiyan.deflower.dao.BackgroundUserDao;
import com.haiyan.deflower.mapper.BackgroundUserMapper;
import com.haiyan.deflower.pojo.BackgroundUser;
import org.springframework.stereotype.Repository;

/**
 * @author haiyan
 */
@Repository
public class BackgroundUserDaoImpl extends ServiceImpl<BackgroundUserMapper, BackgroundUser> implements BackgroundUserDao {
}
