package com.ssk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ssk
 * @date 2020/8/21
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.ssk"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
