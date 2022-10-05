package com.inn.store.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class UserResponse {

    private  Integer id;

    private String name ;

    private String email;

    private String contactNumber;

    private String status;

}
