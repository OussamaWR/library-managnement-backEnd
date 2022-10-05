package com.inn.store.dao;

import com.inn.store.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BillDao extends JpaRepository<Bill,Integer> {



    List<Bill> findAllByCreatedBy(String username);
}
