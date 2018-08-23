package com.springforum.avatar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class AvatarController {
    @Autowired AvatarService avatarService;

    @PostMapping("/api/avatars")
    public Integer postAvatar(@RequestPart("file") MultipartFile avatar) throws Exception {
        AvatarValidator.validate(avatar);
        return avatarService.addAvatar(avatar);
    }

    @GetMapping(value = "/api/avatars/random")
    public List<Integer> getAvatar() {
        return avatarService.getRandomAvatarID();
    }

    @GetMapping(value = "/api/avatars/{id}")
    public ResponseEntity<Resource> getAvatar(@PathVariable(value = "id") Integer avatar_id) throws Exception {
        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/png");
        return ResponseEntity.ok()
                .headers(headers)
                .body(avatarService.getAvatar(avatar_id));
    }
}
