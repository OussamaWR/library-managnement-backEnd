package com.inn.store.request;

import com.inn.store.entities.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data @NoArgsConstructor
public class ProductRequest {



    private Integer id;
    @NotNull(message = "This field must not be null!")
    @NotBlank(message = "This field must not be blank!")
    private String name;


    @NotNull(message = "This field must not be null!")

    private Category category;

    @NotNull(message = "This field must not be null!")
    @NotBlank(message = "This field must not be blank!")
    private String description;

    private String status="true";

    @NotNull(message = "This field must not be null!")
    private Integer price;


    private Integer quantity;

  //  private Integer total = price*quantity ;


}
