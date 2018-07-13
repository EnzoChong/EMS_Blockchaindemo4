package com.sap.summit.demo.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sap.summit.DemoApplication;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {

		ParameterBuilder tenantPar = new ParameterBuilder();
		List<Parameter> pars = new ArrayList<Parameter>();
		tenantPar.name("tenant").description("user tenant").modelRef(new ModelRef("string")).parameterType("header")
				.required(false).build(); 
		pars.add(tenantPar.build()); 
		
		ParameterBuilder userIdPar = new ParameterBuilder();
		userIdPar.name("userId").description("userId").modelRef(new ModelRef("string")).parameterType("header")
		.required(false).build(); 

		pars.add(userIdPar.build()); 
		
		return new Docket(DocumentationType.SWAGGER_2).select()
				// Only API under Project Root Package
				.apis(RequestHandlerSelectors.basePackage(DemoApplication.class.getPackage().getName()))
				// All Paths
				.paths(PathSelectors.any())
				// Build
				.build().apiInfo(apiInfo()).globalOperationParameters(pars);

	}

	ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("api swagger document").description("swagger api document for frontend debug")
				.version("2.1.5.5").build();
	}
}
