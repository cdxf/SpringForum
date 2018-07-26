package com.springforum.user;


import com.springforum.user.dto.UserSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    Optional<User> getByUsername(@Param("username") String username);

    Optional<UserSummary> findByUsername(@Param("username") String username);

    Page<UserSummary> findAllByUsername(@Param("username") String username, Pageable page);

    boolean existsByEmail(String email);

    @Query("select new com.springforum.user.dto.UserSummary(u.id,u.username,u.avatar) from User u where u.id in :id")
    List<UserSummary> findAllByIdIn(@Param("id") Set<Integer> id);

    @Override
    Iterable<User> findAll();
}