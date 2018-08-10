package com.springforum.generic;

import java.time.Instant;

public interface IBaseEntity {
    Integer getId();

    Instant getCreatedTime();

    Instant getUpdatedTime();
}
