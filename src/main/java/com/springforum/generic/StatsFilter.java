package com.springforum.generic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@WebFilter("/*")
@Slf4j
public class StatsFilter implements Filter {

    private static final Logger LOGGER = log;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // empty
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        long time = System.currentTimeMillis();
        try {
            chain.doFilter(req, resp);
        } finally {
            var request = ((HttpServletRequest) req);
            if (request.getRequestURI().contains("favicon")) return;
            time = System.currentTimeMillis() - time;
            LOGGER.info("{}: {} ms ", request.getRequestURI(), time);
        }
    }

    @Override
    public void destroy() {
        // empty
    }
}