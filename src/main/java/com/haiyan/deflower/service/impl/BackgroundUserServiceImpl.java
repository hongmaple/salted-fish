package com.haiyan.deflower.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haiyan.deflower.dao.BackgroundUserDao;
import com.haiyan.deflower.exception.ExceptionResult;
import com.haiyan.deflower.mapper.BackgroundUserMapper;
import com.haiyan.deflower.pojo.BackgroundUser;
import com.haiyan.deflower.pojo.PageDomain;
import com.haiyan.deflower.pojo.PageList;
import com.haiyan.deflower.pojo.User;
import com.haiyan.deflower.service.BackgroundUserService;
import com.haiyan.deflower.utils.CookieUtils;
import com.haiyan.deflower.utils.ServletUtils;
import com.haiyan.deflower.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Objects;

/**
 * @author haiyan
 */
@Service
public class BackgroundUserServiceImpl implements BackgroundUserService {

    private final BackgroundUserMapper userMapper;
    private final BackgroundUserDao userDao;

    @Autowired
    private UserUtils userUtils;

    public BackgroundUserServiceImpl(BackgroundUserMapper userMapper, BackgroundUserDao userDao) {
        this.userMapper = userMapper;
        this.userDao = userDao;
    }

    @Override
    public Boolean register(BackgroundUser user) {
        if(userDao.lambdaQuery().eq(BackgroundUser::getPhone,user.getPhone()).count()>0) {
            throw new ExceptionResult("user","false",null,"注册失败，手机号码已存在");
        }
        user.setCreateTime(new Date());
        if (userMapper.insert(user)==0) {
            throw new ExceptionResult("user","false",null,"注册失败");
        }
        return true;
    }

    @Override
    public BackgroundUser login(BackgroundUser user) {
        if (userDao.lambdaQuery()
                .eq(BackgroundUser::getPhone,user.getPhone())
                .count()==0) {
            throw new ExceptionResult("user","false",null,"登陆失败，手机号码不存在");
        }
        BackgroundUser loginUser = userDao.lambdaQuery()
                .eq(BackgroundUser::getPhone, user.getPhone())
                .eq(BackgroundUser::getPassword, user.getPassword())
                .one();
        if(Objects.isNull(loginUser)) {
            throw new ExceptionResult("user","false",null,"登陆失败，密码不正确");
        }
        HttpSession session = ServletUtils.getSession();
        session.setAttribute("token", JSON.toJSONString(loginUser));
        //session过期时间设置，以秒为单位，即在没有活动120分钟后，session将失效
        session.setMaxInactiveInterval(120 * 60);
        //CookieUtils.setCookie(ServletUtils.getRequest(),ServletUtils.getResponse(),"token", JSON.toJSONString(loginUser),1);
        return loginUser;
    }

    @Override
    public Boolean updateUser(BackgroundUser user) {
        User loginUser = userUtils.getUser(ServletUtils.getRequest());
        if (userDao.lambdaQuery().eq(BackgroundUser::getId,loginUser.getId()).count()==0) {
            throw new ExceptionResult("user","false",null,"当前用户不存在");
        }
        if(userDao.lambdaQuery().eq(BackgroundUser::getPhone,user.getPhone()).ne(BackgroundUser::getId,loginUser.getId()).count()>0) {
            throw new ExceptionResult("user","false",null,"修改失败，手机号码已存在");
        }
        if(!userDao.lambdaUpdate().eq(BackgroundUser::getId,user.getId()).update(user)) {
            throw new ExceptionResult("user","false",null,"修改失败");
        }
        return true;
    }

    @Override
    public Boolean adminUpdateUser(BackgroundUser user) {
        User loginUser = userUtils.getUser(ServletUtils.getRequest());
        if (!"admin".equals(loginUser.getRole())) {
            throw new ExceptionResult("user","false",null,"你不够格");
        }
        if (userDao.lambdaQuery().eq(BackgroundUser::getId,loginUser.getId()).count()==0) {
            throw new ExceptionResult("user","false",null,"当前用户不存在");
        }
        if(userDao.lambdaQuery().eq(BackgroundUser::getPhone,user.getPhone()).ne(BackgroundUser::getId,user.getId()).count()>0) {
            throw new ExceptionResult("user","false",null,"修改失败，手机号码已存在");
        }
        if(!userDao.lambdaUpdate().eq(BackgroundUser::getId,user.getId()).update(user)) {
            throw new ExceptionResult("user","false",null,"修改失败");
        }
        return true;
    }

    @Override
    public PageList<BackgroundUser> ListUser(PageDomain pageDomain) {
        Page<BackgroundUser> page = userDao.lambdaQuery().page(new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize()));
        return PageList.of(page.getRecords(),page);
    }

    @Override
    public BackgroundUser getUserInfo() {
        User loginUser = userUtils.getUser(ServletUtils.getRequest());
        return userDao.getById(loginUser.getId());
    }

    @Override
    public Boolean deletedUser(Long id) {
        User loginUser = userUtils.getUser(ServletUtils.getRequest());
        if (!"admin".equals(loginUser.getRole())) {
            throw new ExceptionResult("user","false",null,"你不够格");
        }
        if (userDao.lambdaQuery().eq(BackgroundUser::getId,loginUser.getId()).count()==0) {
            throw new ExceptionResult("user","false",null,"当前用户不存在");
        }
        if(userMapper.deleteById(id)==0) {
            throw new ExceptionResult("user","false",null,"删除失败");
        }
        return true;
    }
}
