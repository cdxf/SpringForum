package com.springforum.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springforum.user.UniqueEmail;
import com.springforum.user.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserRegister {
    private Integer id;
    @Email
    @NotNull
    @UniqueEmail
    private String email;
    @NotNull
    @UniqueUsername
    private String username;
    @NotBlank()
    @Size(min = 6, max = 128)
    @NotNull
    @JsonIgnore
    private String password;
    private String avatar;
}
