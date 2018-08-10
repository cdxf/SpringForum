package com.springforum.spring;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SanitizerConfiguration {
    @Bean
    PolicyFactory sanitizer() {
        return Sanitizers.BLOCKS
                .and(Sanitizers.FORMATTING)
                .and(Sanitizers.IMAGES)
                .and(Sanitizers.LINKS)
                .and(Sanitizers.TABLES);
    }
}
