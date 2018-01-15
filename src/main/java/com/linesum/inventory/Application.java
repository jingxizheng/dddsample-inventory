package com.linesum.inventory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@SpringBootApplication
@MapperScan(basePackages = "com.linesum.inventory.infrastructure.persistence.mapper")
public class Application {

    static {
        //关闭fastjson 循环引用出现ref
        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
    }

    @Bean
    public PerformanceInterceptor getPerformanceInterceptor() {
        return new PerformanceInterceptor();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}