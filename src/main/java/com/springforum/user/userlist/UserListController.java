package com.springforum.user.userlist;

import com.springforum.generic.TimestampKeyset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserListController {
    @Autowired UserListDAO userSQLQuery;

    @GetMapping("api/users")
    public Object users(TimestampKeyset keyset) {
        return userSQLQuery.query(keyset);
    }

}
