package hcmute.edu.vn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**") // URL truy cập ảnh
                .addResourceLocations("file:uploads/"); // thư mục gốc lưu ảnh (tuyệt đối hoặc tương đối)
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://booking-website-three-lovat.vercel.app")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}
