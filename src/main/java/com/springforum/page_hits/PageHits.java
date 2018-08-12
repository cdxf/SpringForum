package com.springforum.page_hits;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(indexes = {@Index(name = "createdTimeIndex", columnList = "created_time")})
@Setter
@Getter
@NoArgsConstructor
public class PageHits {
    @NotNull private String ip;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "sequence")
    private Integer id;
    @CreationTimestamp
    @Column(name = "created_time")
    private Instant createdTime;
}
