package com.inn.store.service;

import com.inn.store.entities.Product;
import com.inn.store.request.ProductRequest;
import com.inn.store.response.ProductResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ProductService {
    ResponseEntity<String> addProduct(ProductRequest productRequest);

    ResponseEntity<List<ProductResponse>> getAllProducts(int page, int limit, String search);

    ResponseEntity<String> updateProduct(ProductRequest productRequest);

    ResponseEntity<?> getOneProduct(Integer id);

    ResponseEntity<?> getProductByCategory(int page, int limit,String categoryName);

    ResponseEntity<String> deleteProduct(Integer id);

    ResponseEntity<String> changeStatus(Integer id, Map<String,String> requestMap);
}
