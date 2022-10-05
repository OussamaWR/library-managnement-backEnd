package com.inn.store.restImp;

import com.inn.store.response.DashbordStatistic;
import com.inn.store.rest.Dashbord;
import com.inn.store.service.DashbordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashbordRest implements Dashbord {
    @Autowired
    DashbordService dashbordService;
    @Override
    public ResponseEntity<DashbordStatistic> getStatistic() {
        try {
            return  dashbordService.getStatistic();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
