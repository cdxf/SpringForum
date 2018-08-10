package com.springforum.page_hits;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(indexes = {@Index(name = "createdTimeIndex", columnList = "created_time")})
@Setter
@Getter
@RequiredArgsConstructor
public class PageHits {
    final private String ip;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "sequence")
    private Integer id;
    @CreationTimestamp
    @Column(name = "created_time")
    private Instant createdTime;
}
