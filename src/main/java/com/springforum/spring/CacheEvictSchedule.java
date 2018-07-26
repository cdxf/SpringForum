package com.springforum.spring;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class CacheEvictSchedule {
    @CacheEvict(allEntries = true, cacheNames = {"forum", "forum_path"})
    @Scheduled(fixedDelay = 5000)
    public void cacheEvict() {
    }
}

