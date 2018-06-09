package com.chinaoly.frm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//@EnableDiscoveryClient
//@ComponentScan(basePackages = { "com.chinaoly.frm" })
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SecurityApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SecurityApplication.class, args);
    }

}
