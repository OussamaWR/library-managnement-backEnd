package com.inn.store.dao;

import com.inn.store.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserDao extends JpaRepository<User,Integer> {
    User findUserByEmail(String email);
    Page<User> findAllByRole(Pageable pageable,String role);
    List<User> findAllByRole(String role);


  @Query(value = "select u from User u where u.role='user' and u.name=?1")
    Page<User> getAllUser(Pageable page,  String search);



    Page<User> findAll(Pageable page);


   // List<User> findAll();

    @Transactional
    @Modifying
    @Query(value = "update user u set u.status=?1 where u.id=?2",nativeQuery = true)
    Integer updateStatus(String status,Integer id);
}
