package com.haiyan.deflower;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author haiyan
 */
@SpringBootApplication
@MapperScan(basePackages = {
        "com.haiyan.deflower.mapper"
})
public class DeflowerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeflowerApplication.class, args);
    }

}
