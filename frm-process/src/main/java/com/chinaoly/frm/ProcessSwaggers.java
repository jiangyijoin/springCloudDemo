package com.chinaoly.frm;

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
 * edos-service的swagger2
 * @author jiangyi
 * @date 2018年1月12日
 *
 */

@Configuration
@EnableSwagger2
public class ProcessSwaggers{

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .tags(new Tag("PROCESS", "工作流管理"), getTags())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.chinaoly.frm.process.controller"))
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
                .title("FRM-process")
                .description("工作流管理")
                .termsOfServiceUrl("#")
                .contact(new Contact("jiangyi", "#", "jiangy@chinaoly.com"))
                .version("1.0")
                .build();
    }
}
