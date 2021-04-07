package com.haiyan.deflower.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haiyan.deflower.dao.UserDao;
import com.haiyan.deflower.mapper.UserMapper;
import com.haiyan.deflower.pojo.User;
import org.springframework.stereotype.Repository;

/**
 * @author haiyan
 */
@Repository
public class UserDaoImpl extends ServiceImpl<UserMapper, User> implements UserDao {
}
