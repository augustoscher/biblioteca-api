package br.com.objetive.biblioteca.docs;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket apiDoc( ) {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
//				.apis(RequestHandlerSelectors.basePackage("br.com.objective.biblioteca")) //serve para filtar o pacote de endpoints
				.paths(regex("/v1.*"))
				.build()
				.globalOperationParameters(Collections.singletonList(new ParameterBuilder()
																	.name("Authorization")
																	.description("Bearer token")
																	.modelRef(new ModelRef("string"))
																	.parameterType("header")
																	.required(true)
																	.build()))
				.apiInfo(metaData());
	}
	
	private ApiInfo metaData() {
		return new ApiInfoBuilder()
				.title("Biblioteca_API")
				.description("Projeto Biblicoteca API das escolas")
				.version("1.0")
				.contact(new Contact("Augusto Scher", "http://projeto-biblioteca.com.br", "augustoscher@gmail.com"))
				.license("")
				.build();
	}
}
