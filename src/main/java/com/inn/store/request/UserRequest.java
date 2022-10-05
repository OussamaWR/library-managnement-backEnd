package com.inn.store.request;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.Empty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private Integer id;

    @NotNull(message = "This field must not be null!")
    @NotBlank(message = "This field must not be blank!")
    private String name ;

    @NotNull(message = "This field must not be null!")
    @NotBlank
    private String contactNumber;

    @NotNull(message = "This field must not be null!")
    @NotBlank
    private String email;

    @NotNull(message = "This field must not be null!")
    @NotBlank
    private String password;


    private String status="false";


    private String role="user";
}
