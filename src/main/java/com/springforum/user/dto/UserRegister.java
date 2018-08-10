package com.springforum.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springforum.user.validation.UniqueEmail;
import com.springforum.user.validation.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserRegister {
    private Integer id;
    @Email
    @NotBlank
    @UniqueEmail
    private String email;
    @NotBlank
    @UniqueUsername
    private String username;
    @NotBlank()
    @Size(min = 4, max = 128)
    @NotBlank
    @JsonIgnore
    private String password;
    private Integer avatar;
}
