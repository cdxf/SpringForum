package com.example.springforum.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
public class Tag extends BaseEntity {
    @Getter
    @Setter
    String name;
}
