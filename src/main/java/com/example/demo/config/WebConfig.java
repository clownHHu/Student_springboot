package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
            /**
             * 设置资源映射路径，后端D:/upload/->/image/
           * 资源映射路径
           * addResourceHandler：访问映射路径
           * addResourceLocations：资源绝对路径
           */      registry.addResourceHandler("/image/**").addResourceLocations("file:D:/upload/");
       }

 }
