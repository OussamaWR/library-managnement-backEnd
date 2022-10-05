package com.inn.store.service;

import com.inn.store.entities.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface CategoryService {
    ResponseEntity<String> addNewCategory(Category category);
    ResponseEntity<List<Category>> getAllCategory();
    ResponseEntity<String> updateCaegory(Category category);
}
