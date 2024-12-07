package com.quanh1524.bookshop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("classpath:/resources/");

        registry.addResourceHandler("/templates/**")
                .addResourceLocations("classpath:/templates/");

        // Specific handler for CSS in admin.dashboard
        registry.addResourceHandler("/admin/dashboard/**")
                .addResourceLocations("classpath:/templates/admin/dashboard/");

        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/js/**").addResourceLocations("/resources/admin/dashboard/js");
        registry.addResourceHandler("/css/**").addResourceLocations("/resources/admin/dashboard/css");
    }
}