package com.springforum.user;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    Optional<User> getByUsername(@Param("username") String username);
    boolean existsByEmail(String email);
    @Override
    Iterable<User> findAll();
}