package com.haiyan.deflower.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.haiyan.deflower.handel.EntityAutoFilledHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author haiyan
 */
@Configuration
public class MybatisPlusAutoConfiguration {

    /**
     * mybatis-plus分页插件
     * 文档：http://mp.baomidou.com
     */
    @Bean
    @ConditionalOnMissingBean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        //取消单页限制数量
        paginationInterceptor.setLimit(-1);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize());
        return paginationInterceptor;
    }

    @Bean
    @ConditionalOnMissingBean
    public EntityAutoFilledHandler entityAutoFilledHandler() {
        return new EntityAutoFilledHandler();
    }
}