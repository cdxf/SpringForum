package com.example.springforum.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.ZonedDateTime;

@MappedSuperclass
public class BaseEntity {
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    @Id
    @GeneratedValue
    Integer id;
    @Getter
    @CreationTimestamp
    ZonedDateTime createdTime;
    @UpdateTimestamp
    @Getter
    ZonedDateTime updatedTime;

}
