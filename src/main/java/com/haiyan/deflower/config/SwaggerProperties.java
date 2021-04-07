package com.haiyan.deflower.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author DFFuture
 * @date 2019/12/7
 */
@ConfigurationProperties("deflower-swagger")
@Data
public class SwaggerProperties {

    private Boolean enable = true;

    private ApiInfoProperties apiInfo = new ApiInfoProperties();

    @Data
    public static class ApiInfoProperties {
        /**
         * 标题
         */
        private String title;
        /**
         * 描述
         */
        private String description;
        /**
         * 版本
         */
        private String version;
    }
}
