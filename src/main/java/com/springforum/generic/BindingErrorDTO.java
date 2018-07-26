package com.springforum.generic;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BindingErrorDTO {
    public String field;
    public String message;
}
