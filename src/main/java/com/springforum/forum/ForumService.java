package com.springforum.forum;

import com.springforum.forum.dto.ForumDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@NoArgsConstructor
@AllArgsConstructor
@Accessors
public class ForumService {
    @Autowired
    ForumDao forumDao;

    public List<ForumDTO> getForums() {
        return forumDao.getAllForum();
    }


    public ForumDTO getForumDTO(Integer forumID) {
        return forumDao.getForumById(forumID);
    }


}
