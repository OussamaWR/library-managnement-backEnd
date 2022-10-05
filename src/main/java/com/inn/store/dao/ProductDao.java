package com.inn.store.dao;

import com.inn.store.entities.Product;
import com.inn.store.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductDao extends JpaRepository<Product,Integer> {

    @Query(value = "select p from Product p where p.name=?1 and p.status='true'")
    Page<Product> getAllProducts(Pageable page, String search);

    @Query(value = "select p from Product p where p.category.name=?1 and p.status='true'")
    Page<Product> findAllByStatusAndCateAndCategoryName( Pageable page,String categoryName);

    Page<Product> findAllByStatus(Pageable page, String status);
}
