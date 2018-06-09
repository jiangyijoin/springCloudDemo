import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * edos-service的swagger2
 * @author jiangyi
 * @date 2018年1月12日
 *
 */

@Configuration
@EnableSwagger2
public class SecuritySwaggers {

    @Bean
    public Docket createRestApi() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("token")
                .defaultValue("user-eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbjAifQ.EzOtp4tB1dD7xTGWIc5Dlceoi7undj9ikhDdkuz23N_te3iLoE61nqSd-X-9hmC_ERIdKMXu62ZHbuV4vqWzhQ")
                .description("令牌")
                .modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .tags(new Tag("FRM-security", "框架安全管理"), getTags())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.chinaoly.frm.security.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
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
                .title("FRM-security")
                .description("框架安全管理")
                .termsOfServiceUrl("#")
                .contact(new Contact("jiangyi", "#", "jiangy@chinaoly.com"))
                .version("1.0")
                .build();
    }
}
