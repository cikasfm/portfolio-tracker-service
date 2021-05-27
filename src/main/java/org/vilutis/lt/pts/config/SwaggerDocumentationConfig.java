package org.vilutis.lt.pts.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-05-21T20:48:48.519Z")

@Configuration
public class SwaggerDocumentationConfig extends WebMvcConfigurerAdapter {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Portfolio Tracking Service")
            .description("Portfolio Tracking Service")
            .license("Apache 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
            .termsOfServiceUrl("")
            .version("1.0")
            .contact(new Contact("Zilvinas Vilutis","", "zilvinas@vilutis.lt"))
            .build();
    }

    @Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("org.vilutis.lt.pts"))
                    .build()
                .apiInfo(apiInfo());
    }

    @Bean
    public SecurityConfiguration swaggerSecurityConfig(
      @Value("${spring.security.oauth2.client.registration.github.clientId}") String githubClientId,
      @Value("${spring.security.oauth2.client.registration.github.clientSecret}") String githubSecret
    ) {
        return SecurityConfigurationBuilder.builder()
          .clientId(githubClientId)
          .clientSecret(githubSecret)
//          .scopeSeparator(" ")
//          .useBasicAuthenticationWithAccessCodeGrant(true)
          .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**").addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/3.49.0/");
    }


}
