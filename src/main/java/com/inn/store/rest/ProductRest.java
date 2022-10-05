package com.inn.store.rest;

import com.inn.store.entities.Product;
import com.inn.store.request.ProductRequest;
import com.inn.store.response.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RequestMapping(path = "/product")
public interface ProductRest {

    @PostMapping
    ResponseEntity<String> addProduct(@RequestBody @Valid  ProductRequest productRequest);

    @GetMapping
    ResponseEntity<List<ProductResponse>> getAllProducts(@RequestParam(value="page" , defaultValue = "1")int page,
                                                         @RequestParam(value="limit", defaultValue = "7")int limit,
                                                         @RequestParam(value="search" , defaultValue = "") String search);

    @GetMapping(path = "/{id}")
    ResponseEntity<?> getOneProduct(@PathVariable(name = "id") Integer id);

    @GetMapping(path = "/paductof")
    ResponseEntity<?> getProductByCategory(@RequestParam(value="page" , defaultValue = "1")int page,
                                           @RequestParam(value="limit", defaultValue = "7")int limit,
                                           @RequestParam(value = "category",defaultValue = "") String categoryName);
    @PutMapping
    ResponseEntity<String> updateProduct(@RequestBody @Valid ProductRequest productRequest);

    @DeleteMapping(path="/{id}")
    ResponseEntity<String> deleteProduct(@PathVariable(name = "id") Integer id);


    @PutMapping(path = "/{id}")
    ResponseEntity<String> changeStatus(@PathVariable(name = "id")Integer id,@RequestBody Map<String,String> requestMap);

}
