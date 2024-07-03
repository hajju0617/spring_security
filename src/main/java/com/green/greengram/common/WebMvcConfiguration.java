package com.green.greengram.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Slf4j
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final String uploadPath;

    public WebMvcConfiguration(@Value("${file.directory}") String uploadPath) {
        this.uploadPath = uploadPath;
    }

    //CORS 오픈
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedHeaders("Authorization", "Content-Type")
                .allowCredentials(true)     // 쿠키 요청을 허용
                .maxAge(3600);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) { // 외부파일과 연결
        registry.addResourceHandler("/pic/**").addResourceLocations("file:" + uploadPath);
        //  /pic/ 뒤에 어떤 내용이 온다면 addResourceLocations("file:" + uploadPath) 를 붙여줌

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/**")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                   @Override
                   protected Resource getResource(String resourcePath, Resource location) throws IOException {
                                                            // classpath:/static/** ----> location
                       Resource requestedResource = location.createRelative(resourcePath);

                       if(requestedResource.exists() && requestedResource.isReadable()) {
                           return requestedResource;
                       }

                       return new ClassPathResource("/static/index.html");
                   }
                });
    }
}
