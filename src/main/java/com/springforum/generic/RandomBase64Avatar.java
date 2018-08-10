package com.springforum.generic;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Random;

public abstract class RandomBase64Avatar {
    public static String get() {
        try {
            ClassPathResource classPathResource = new ClassPathResource("static/avatar");

            File[] files = classPathResource.getFile().listFiles();
            var random = new Random();
            var randomFile = files[random.nextInt(files.length)];
            var fileInputStream = Files.readAllBytes(randomFile.toPath());
            byte[] decode = Base64.getEncoder().encode(fileInputStream);
            return new String(decode);
        } catch (Exception e) {
            return "";
        }
    }
}
