package com.inn.store.response;

import com.inn.store.entities.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class ProductResponse {

    private String name;

    private String categoryName;


    private String description;


    private Integer price;

}
