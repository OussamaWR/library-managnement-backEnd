package com.inn.store.rest;

import com.inn.store.response.DashbordStatistic;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/dashbord")
public interface Dashbord {
    @GetMapping
    ResponseEntity<DashbordStatistic> getStatistic();
}
