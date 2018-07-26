package com.springforum.generic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity implements IBaseEntity {
    @Id
    @GeneratedValue
    private Integer id = -1;
    @CreationTimestamp
    @Column(name = "created_time")
    private Instant createdTime = Instant.EPOCH;
    @UpdateTimestamp
    @Column(name = "updated_time")
    private Instant updatedTime = Instant.EPOCH;
}
