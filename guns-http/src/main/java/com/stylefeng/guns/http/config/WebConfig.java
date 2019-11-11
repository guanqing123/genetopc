package com.stylefeng.guns.http.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
* create by guanqing
* 2019年8月18日 下午9:13:51
*/
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	
    /**
     * 增加swagger的支持
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    
    /**
     * 支持ajax跨域请求
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
    	// TODO Auto-generated method stub
		registry
		.addMapping("/**")
		.allowedMethods("*")
		.allowedOrigins("*")
		.allowedHeaders("*");
    }
}
