package com.example.springforum.repository;


import com.example.springforum.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User,Integer> {
    Optional<User> getByUsername(String name);
}