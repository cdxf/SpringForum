package com.springforum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@AllArgsConstructor
@ConfigurationProperties("ggg")
@NoArgsConstructor
class ggg {
    private String gggg = "gsdgsdgsd ";
    private Long hehe = 233l;
    private String dsadsadas = " d  ";
    private Integer age;

    public long getAge() {
        return hehe * 4;
    }
}
