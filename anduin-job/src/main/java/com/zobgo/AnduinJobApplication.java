package com.zobgo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by zongbo.zhang on 8/16/18.
 */

@SpringBootApplication
@Slf4j
@ComponentScan(basePackages = {"com.zobgo"})
public class AnduinJobApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnduinJobApplication.class,args);
    }

}
