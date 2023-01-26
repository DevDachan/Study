package com.example.demo.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



public class SwaggerConfiguration {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()) //아래에서 설정한 api의 정보를 담는 것
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo"))
                //basePackage는 swagger가 REST controller를 스캔할 때 어디를 범위로 스캔할지 정해주는 것
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages( false);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Dachan Hub Open API Test with Swagger")
                .description("설명 부분")
                .version("1.0.0") //maven(pom.xml)에서 설정한 프로젝트 version
                .build();
    }
}
