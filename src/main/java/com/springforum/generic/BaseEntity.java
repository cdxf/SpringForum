package com.springforum.generic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class BaseEntity implements IBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "sequence")
    private Integer id;
    @CreationTimestamp
    @Column(name = "created_time")
    private Instant createdTime;
    @UpdateTimestamp
    @Column(name = "updated_time")
    private Instant updatedTime;
}
