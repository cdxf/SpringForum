package com.springforum.page_hits;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@Component
@WebFilter("/*")
@Slf4j
public class PageHitsFilter implements Filter {

    private static final Logger LOGGER = log;
    @Autowired PageHitsDao pageHitsDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // empty
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        pageHitsDao.increasePageHit(req.getRemoteAddr());
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        // empty
    }
}