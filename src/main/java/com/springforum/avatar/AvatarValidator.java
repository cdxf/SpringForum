package com.springforum.avatar;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class AvatarValidator {
    public static void validate(MultipartFile file) {
        if (!file.getContentType().contains("image")) throw new IllegalArgumentException("File is not an image");
        if (file.getSize() > 100000) throw new IllegalArgumentException("Image file size is too large");
        try {
            BufferedImage read = ImageIO.read(file.getInputStream());
            if (read.getWidth() > 500)
                throw new IllegalArgumentException("Image file height is larger than 1000 pixel");
            if (read.getHeight() > 500)
                throw new IllegalArgumentException("Image file width  is larger than 1000 pixel");
            if (read.getHeight() / read.getWidth() > 2)
                throw new IllegalArgumentException("Image Height / Image Width > 2");
            if (read.getWidth() / read.getHeight() > 2)
                throw new IllegalArgumentException("Image Width / Image Height > 2");
        } catch (IOException e) {
            throw new IllegalArgumentException("Unknow Erro");
        }

    }
}
