package com.springforum.user;

import com.springforum.generic.BindingErrorDTO;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

class RegisterErrorsDTO {
    @Getter
    private Map<String, BindingErrorDTO> errors = new HashMap<>();

    public RegisterErrorsDTO(Stream<BindingErrorDTO> errorsStream) {
        errorsStream.forEach(error -> errors.put(error.field, error));
    }

}
