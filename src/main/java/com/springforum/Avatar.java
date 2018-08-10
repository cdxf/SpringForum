package com.springforum;

import com.springforum.generic.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(indexes = {@Index(name = "random", columnList = "random")})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Avatar extends BaseEntity {

    @Column(columnDefinition = "bytea")
    byte[] data;
    @NotNull
    Double random;
}
