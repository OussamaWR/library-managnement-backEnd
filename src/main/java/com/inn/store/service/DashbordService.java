package com.inn.store.service;

import com.inn.store.response.DashbordStatistic;
import org.springframework.http.ResponseEntity;

public interface DashbordService {
    ResponseEntity<DashbordStatistic> getStatistic();
}
