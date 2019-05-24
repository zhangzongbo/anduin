package com.zobgo.web.demo;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author zhangzongbo
 * @date 18-11-21 下午7:38
 */

@SpringBootApplication
@Slf4j
@Configuration
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class,args);
    }
}
