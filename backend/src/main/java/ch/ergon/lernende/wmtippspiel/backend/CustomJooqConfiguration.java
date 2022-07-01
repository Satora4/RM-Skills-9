package ch.ergon.lernende.wmtippspiel.backend;

import org.jooq.conf.Settings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomJooqConfiguration {

    @Bean
    public Settings settings() {
        return new Settings().withRenderSchema(false);
    }
}
