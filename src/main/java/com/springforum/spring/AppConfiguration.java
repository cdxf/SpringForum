package com.springforum.spring;

import org.jooq.DSLContext;
import org.jooq.conf.RenderKeywordStyle;
import org.jooq.conf.RenderNameStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.validation.ConstraintViolationProblemModule;

import javax.annotation.PostConstruct;

@Configuration
public class AppConfiguration {

    @Autowired DSLContext context;

    @PostConstruct
    public void config() {
        context.settings().setRenderKeywordStyle(RenderKeywordStyle.AS_IS);
        context.settings().setRenderNameStyle(RenderNameStyle.AS_IS);
    }

    @Bean
    TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setQueueCapacity(0);
        threadPoolTaskExecutor.setMaxPoolSize(4);
        return threadPoolTaskExecutor;
    }

    @Bean
    public ProblemModule problemModule() {
        return new ProblemModule();
    }

    @Bean
    public ConstraintViolationProblemModule constraintViolationProblemModule() {
        return new ConstraintViolationProblemModule();
    }

    @Bean
    PasswordEncoder encoder() {
        PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return delegatingPasswordEncoder;
    }

}
