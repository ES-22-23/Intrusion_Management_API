package es.module2.imapi.config;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import javax.servlet.MultipartConfigElement;

@Configuration
public class MultiparConfig {



    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(50000000);
        return multipartResolver;
    }
}
