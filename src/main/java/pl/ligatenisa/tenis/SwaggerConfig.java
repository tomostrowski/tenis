package pl.ligatenisa.tenis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Object docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/**"))
                .build().apiInfo(createApiInfo());
    }
    
    private ApiInfo createApiInfo() {
        return new ApiInfo(
                "Tennis API",
                "",
                "1.0",
                "",
                new Contact("", "", "t.z.ostrowski@gmail.com"),
                "licencja własna",
                "",
                Collections.emptyList()
        );
    }

}
