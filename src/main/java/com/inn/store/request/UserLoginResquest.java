package com.inn.store.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserLoginResquest {

    @NotNull(message = "This field must not be null!")
    @NotBlank
    private String email;

    @NotNull(message = "This field must not be null!")
    @NotBlank
    private String password;

}
