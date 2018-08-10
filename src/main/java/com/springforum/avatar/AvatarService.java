package com.springforum.avatar;

import com.springforum.Avatar;
import com.springforum.Tables;
import com.springforum.user.User;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class AvatarService {
    final com.springforum.tables.Avatar avatarTable = Tables.AVATAR;
    final com.springforum.tables.Users users = Tables.USERS;
    @Autowired DSLContext dslContext;
    @Autowired EntityManager entityManager;

    public List<Integer> getRandomAvatarID() {
        var list = dslContext.select(avatarTable.ID)
                .from(avatarTable)
                .where(avatarTable.RANDOM.le(Math.random()))
                .limit(10)
                .fetchArray(avatarTable.ID);
        return Arrays.asList(list);
    }

    public void setAvatar(Integer userId, Integer avatarId) {
        User user = entityManager.getReference(User.class, userId);
        Avatar avatar = entityManager.getReference(Avatar.class, avatarId);
        user.setAvatar(avatar);
        entityManager.merge(user);
    }

    public Resource getAvatar(Integer id) {
        byte[] bytes = dslContext.select(avatarTable.DATA).from(avatarTable)
                .where(avatarTable.ID.eq(id))
                .fetchOne()
                .get(avatarTable.DATA);
        return new ByteArrayResource(bytes);
    }

    public Integer addAvatar(byte[] bytes) throws IOException {
        var avatar = new Avatar(bytes, Math.random());
        Avatar merge = entityManager.merge(avatar);
        return merge.getId();
    }

    public Integer addAvatar(MultipartFile file) throws IOException {
        return addAvatar(file.getBytes());
    }
}
