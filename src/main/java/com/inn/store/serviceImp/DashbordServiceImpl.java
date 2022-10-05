package com.inn.store.serviceImp;

import com.inn.store.dao.BillDao;
import com.inn.store.dao.CategoryDao;
import com.inn.store.dao.ProductDao;
import com.inn.store.response.DashbordStatistic;
import com.inn.store.service.DashbordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class DashbordServiceImpl implements DashbordService {
    @Autowired
    ProductDao productDao;
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    BillDao billDao;
    @Override
    public ResponseEntity<DashbordStatistic> getStatistic() {
        try {
            DashbordStatistic dashbordStatistic = new DashbordStatistic();
            dashbordStatistic.setBill(billDao.count());
            dashbordStatistic.setProduct(productDao.count());
            dashbordStatistic.setCategory(categoryDao.count());
            log.info("statistic : {}",dashbordStatistic);
           return new ResponseEntity<>(dashbordStatistic, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
