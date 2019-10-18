package com.archerzhang.cs.user.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 自动加载Swagger Api文档
 * @author archerzhang
 * @date 2019.10.18
 */
@Configuration
@EnableSwagger2
@Slf4j
public class AutoLoadSwaggerConfig implements CommandLineRunner {

    @Value("${server.port}")
    private String serverPort;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${swagger.show}")
    private boolean isShow;

    @Override
    public void run(String... args) {
        Properties props = System.getProperties();
        String property = props.getProperty("os.name");

        if(isShow && property.startsWith("Win")){
            String swaggerUrl = "http://127.0.0.1:" + serverPort + contextPath + "/swagger-ui.html";
            try{
                Runtime.getRuntime().exec("cmd /c start "+ swaggerUrl);
                log.debug("run command is: cmd /c start " + swaggerUrl);
            }catch (Exception e){
                log.error("failed to launch browser for swagger",e);
            }
        }
    }

    /**
     * 自定义文档
     * @return
     */
    @Bean
    public Docket customDocket() {
        if (isShow) {
            List<Parameter> pars = new ArrayList<>();
            return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiOfflineInfo()).select()
                    .apis(RequestHandlerSelectors.basePackage("com.archerzhang.cs.user.controller"))
                    .paths(PathSelectors.any()).build().globalOperationParameters(pars);
        } else {
            return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiOnlineInfo()).select()
                    .paths(PathSelectors.none()).build();
        }
    }

    private ApiInfo apiOfflineInfo() {
        return new ApiInfoBuilder().title("springcloud 学习 测试 user").description("springcloud 学习 测试 user")
                .license("archerzhang user").version("1.0.0").build();
    }

    private ApiInfo apiOnlineInfo() {
        return new ApiInfoBuilder().title("Api").description("Api").license("user")
                .version("1.0.0").build();
    }
}
