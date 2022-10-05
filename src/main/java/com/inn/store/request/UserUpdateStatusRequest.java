package com.inn.store.request;

import lombok.Data;

@Data
public class UserUpdateStatusRequest {
    private Integer id;
    private String status="false";
}
