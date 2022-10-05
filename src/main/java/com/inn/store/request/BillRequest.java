package com.inn.store.request;


import com.inn.store.entities.Product;
import com.inn.store.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Data @AllArgsConstructor @NoArgsConstructor
public class BillRequest {


    private Integer id;

    @NotNull(message = "This field must not be null!")
    @NotBlank(message = "This field must not be blank!")
    private String uuid;


    @NotNull(message = "This field must not be null!")
    @NotBlank(message = "This field must not be blank!")
    private String paymentMethod;

    @NotNull(message = "This field must not be null!")
    private Integer total;

    @NotNull(message = "This field must not be null!")
    private List<Map<String,String>> productdetails;


    @NotNull(message = "This field must not be null!")
    private User createdBy;
}
