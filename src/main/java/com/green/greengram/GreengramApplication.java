package com.green.greengram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan    // AppProperties 에서 @ConfigurationProperties 를 사용하기 위해서
public class GreengramApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreengramApplication.class, args);
    }

}
