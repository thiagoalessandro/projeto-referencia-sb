package br.com.packagebase.projetoreferenciasb.config;

import br.com.packagebase.projetoreferenciasb.component.PropertiesApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import br.com.packagebase.projetoreferenciasb.component.MessagesApp;

@Profile({"dev", "test"})
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    private final MessagesApp messagesApp;

    private final PropertiesApp propertiesApp;

    public SwaggerConfig(MessagesApp messagesApp, PropertiesApp propertiesApp) {
        this.messagesApp = messagesApp;
        this.propertiesApp = propertiesApp;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("br.com.packagebase.projetoreferenciasb.controller"))
                .paths(PathSelectors.any()).build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(messagesApp.get("swagger.title"))
                .description(messagesApp.get("swagger.description"))
                .version(propertiesApp.getAppBuildVersion())
                .build();
    }

}
