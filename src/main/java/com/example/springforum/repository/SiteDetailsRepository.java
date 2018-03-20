package com.example.springforum.repository;

import com.example.springforum.entity.SiteDetail;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;
@Transactional
public interface SiteDetailsRepository extends CrudRepository<SiteDetail,Integer> {
    Optional<SiteDetail> getByKey(String key);
    @Modifying
    @Query("update SiteDetail set value=:value where key=:key")
    void updateValue(@Param("key") String key, @Param("value") String value);
    @Modifying
    @Query("update SiteDetail set description=:description where key=:key")
    void updateDescription(@Param("key") String key, @Param("description") String description);
}
