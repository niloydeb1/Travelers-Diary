package com.projects.travelblog.config;

import com.projects.travelblog.util.AttributeNames;
import com.projects.travelblog.util.ViewNames;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry)
    {
        registry.addViewController("/"+ViewNames.LOGIN).setViewName(ViewNames.LOGIN);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path imgUploadDir = Paths.get("./src/main/resources/blog-images/");
        String imgUploadPath = imgUploadDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/src/main/resources/blog-images/**").addResourceLocations("file:/" + imgUploadPath + "/");
    }
}
