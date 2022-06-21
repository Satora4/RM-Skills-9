package ch.ergon.lernende.wmtippspiel.backend;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class BackendWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // The frontend Angular app is in a separate embedded JAR. Resources from there are not directly recognized.
        // We need to add them as resources manually.
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/public/");
    }
}
