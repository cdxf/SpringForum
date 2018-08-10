package com.springforum.generic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

@Slf4j
public class Benchmark {

    public static <T> T call(Callable<T> task) throws Exception {
        T call = null;
            long startTime = System.currentTimeMillis();
            call = task.call();
            log.info((System.currentTimeMillis() - startTime) + "ms");
        return call;
    }
}
