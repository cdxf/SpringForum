package com.springforum;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableAsync
@EnableCaching
public class SpringforumApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringforumApplication.class, args);
    }

    @Bean
    PolicyFactory sanitizer() {
        return Sanitizers.BLOCKS
                .and(Sanitizers.FORMATTING)
                .and(Sanitizers.IMAGES)
                .and(Sanitizers.LINKS)
                .and(Sanitizers.TABLES);
    }

    @Bean
    PasswordEncoder encoder() {
        PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return delegatingPasswordEncoder;
    }

}
