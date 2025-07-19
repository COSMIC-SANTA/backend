package SANTA.backend.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//React에서 업로드 된 파일을 불러올 때 필요한 config파일입니다.
@Configuration
public class PostConfig implements WebMvcConfigurer {
    private String resourcePath = "/upload/**"; //view에서 접근할 경로
    private String savePath = "file:///C:/Users/User/Desktop/springtemp_img/"; //실제 파일 저장 경로
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler(resourcePath)
                .addResourceLocations(savePath);
    }
}
