package com.springforum.thread.get_latest_thread;

import com.springforum.generic.TimestampKeyset;
import com.springforum.thread.dto.ThreadDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/")
public class GetLatestThreadController {
    @Autowired
    private GetLatestThreadDao getLatestThreadDao;

    @GetMapping(value = "threads")
    public List<ThreadDTO> get(TimestampKeyset epoch) {
        return getLatestThreadDao.query(epoch);
    }

}


