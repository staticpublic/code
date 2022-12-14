package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;

import java.io.IOException;

@Configuration
@ComponentScan({"com.controller","com.config"})
@EnableWebMvc
public class SpringMvcConfig {

    /*protected void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/", ".jsp");
    }*/

    @Bean
    public MultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }
/*    @Bean
    public MultipartResolver multipartResolver() throws IOException {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setUploadTempDir(new FileSystemResource("/tmp/spittr/uploads")); //设置临时路径
        multipartResolver.setMaxUploadSize(2097152); //设置上传文件的最大大小
        multipartResolver.setMaxInMemorySize(0); //设置内存大小
        return multipartResolver;
    }*/


}
