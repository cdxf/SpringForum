package com.springforum.generic;

public interface IBaseEntity {
    Integer getId();

    java.time.Instant getCreatedTime();

    java.time.Instant getUpdatedTime();
}
