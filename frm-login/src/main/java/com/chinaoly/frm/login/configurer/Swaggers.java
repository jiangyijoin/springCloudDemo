package com.chinaoly.frm.login.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * login-统一认证登录模块的swagger2
 * @author jiangyi
 * @date 2018年5月16日
 *
 */

@Configuration
@EnableSwagger2
public class Swaggers {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .tags(new Tag("FRM", "统一认证"), getTags())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.chinaoly.frm.login.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private Tag[] getTags() {
        Tag[] tags = {
                new Tag("book", "书相关的API")
        };
        return tags;
    }

    @SuppressWarnings("deprecation")
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("YYCP-FRAME")
                .description("基础框架")
                .termsOfServiceUrl("#")
                .contact(new Contact("jiangyi", "#", "jiangy@chinaoly.com"))
                .version("1.0")
                .build();
    }
}
