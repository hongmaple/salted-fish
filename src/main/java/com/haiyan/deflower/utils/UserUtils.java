package com.haiyan.deflower.utils;

import com.alibaba.fastjson.JSON;
import com.haiyan.deflower.exception.ExceptionResult;
import com.haiyan.deflower.pojo.User;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;


import javax.net.ssl.SSLSession;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

/**
 * @author haiyan
 */
@Component
public class UserUtils {
    private static final String TOKEN = "token";


    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    public User getUser(HttpServletRequest request)
    {
        String token = request.getHeader(TOKEN);
        if (!StringUtils.hasText(token)) {
            throw new ExceptionResult("user","403",null,"请先登陆");
        }
        User user = null;
        try {
            user = JSON.parseObject(java.net.URLDecoder.decode(token,"UTF-8"), User.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return user;
    }
}
