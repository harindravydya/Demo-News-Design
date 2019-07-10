package com.upday.news.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket UpdayAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()                 .apis(RequestHandlerSelectors.basePackage("com.upday.news"))
                .paths(regex("/api/v1/articles.*"))
                .build()
                .apiInfo(metaData());
    }

	private ApiInfo metaData() {
		 ApiInfo apiInfo = new ApiInfo(
			      "Upday REST API",
			      "Upday REST API for news",
			      "Version 1.0",
			      "Terms & Conditions",
			      new Contact("Harindra", "https://github.com/harindravydya", "vydya.harindra@gmail.com"),
			      "",
			      "");
	        return apiInfo;
	}
}
