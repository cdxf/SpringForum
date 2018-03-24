package com.example.springforum.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Tag extends BaseEntity {
    @Getter
    @Setter
    @NotNull
    String name;
}
