package com.inn.store.rest;

import com.inn.store.entities.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RequestMapping(path = "/category")
public interface CategoryRest {


    @PostMapping
    ResponseEntity<String> addNewCategory(@RequestBody @Valid Category category);

    @GetMapping
    ResponseEntity<List<Category>> getAllCategory();

    @PutMapping
    ResponseEntity<String> updateCaegory(@RequestBody @Valid Category category);

}
