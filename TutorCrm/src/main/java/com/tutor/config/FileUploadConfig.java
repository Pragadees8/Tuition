package com.tutor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileUploadConfig implements WebMvcConfigurer {

    @Value("${file.upload.dir:uploads/homework}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // Homework files
        registry.addResourceHandler("/uploads/homework/**")
                .addResourceLocations("file:" + uploadDir + "/");

        // ✅ Admin profile pictures
        registry.addResourceHandler("/uploads/admins/**")
                .addResourceLocations("file:uploads/admins/");   
        // ✅ SuperAdmin profile pictures
        registry.addResourceHandler("/uploads/superadmins/**")
        .addResourceLocations("file:uploads/superadmins/");
        // ✅ SubAdmin profile pictures
        registry.addResourceHandler("/uploads/subadmins/**")
        .addResourceLocations("file:uploads/subadmins/");
     // ✅ Teachers profile pictures
        registry.addResourceHandler("/uploads/teachers/**")
        .addResourceLocations("file:uploads/teachers/");
     // ✅ Students profile pictures
        registry.addResourceHandler("/uploads/students/**")
        .addResourceLocations("file:uploads/students/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}