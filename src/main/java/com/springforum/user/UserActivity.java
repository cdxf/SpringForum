package com.springforum.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springforum.generic.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Data
public class UserActivity extends BaseEntity implements Serializable {
    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;
    private Instant lastThreadCreation;
}
