package com.springforum.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Import(SecurityProblemSupport.class)
class WebSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    private SecurityProblemSupport problemSupport;
    @Autowired
    private UserDetailsService userDetailsServiceImpl;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().
                configurationSource(corsConfigurationSource()).and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository
                        .withHttpOnlyFalse())
                .and()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .anonymous()
                .and()
                .exceptionHandling()
                //.defaultAuthenticationEntryPointFor(new LoginUrlAuthenticationEntryPoint("/login"),new AntPathRequestMatcher("/**"))
                .defaultAuthenticationEntryPointFor(problemSupport, new AntPathRequestMatcher("/**"))
                .accessDeniedHandler(problemSupport)
                .and()
                .formLogin()
                .defaultSuccessUrl("/api/users/me")
                .failureHandler(authenticationFailureHandler())
                .and()
                .httpBasic()
                .authenticationEntryPoint(problemSupport)
                .and()
                .rememberMe().key("HelloWorld")
                .and()
                .logout();
    }
}
