package com.chinaoly.frm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.chinaoly.frm.pocess.mapper")
public class ProcessApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ProcessApp.class, args);
    }

}
