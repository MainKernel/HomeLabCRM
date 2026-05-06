package com.homelab.suit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${app.item.documents.upload.dir}")
    private String uploadDocumentsDir;
    @Value("${app.item.preview.upload.dir}")
    private String uploadImagePreviewDir;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173", "http://localhost:6006") // Дозволяємо Vite
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/api/inventory/resources/preview/**")
                        .addResourceLocations("file:"+uploadImagePreviewDir+"/");

        registry.addResourceHandler("/api/inventory/resources/documents/**")
                .addResourceLocations("file:"+uploadDocumentsDir+"/" );
    }
}