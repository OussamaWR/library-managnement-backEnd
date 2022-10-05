package com.inn.store.serviceImp;

import com.inn.store.JWT.JwtFilter;
import com.inn.store.constants.StoreConstants;
import com.inn.store.dao.CategoryDao;
import com.inn.store.entities.Category;
import com.inn.store.service.CategoryService;
import com.inn.store.utils.StoreUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    JwtFilter jwtFilter;
    @Override
    public ResponseEntity<String> addNewCategory(Category category) {

        try{
            if(jwtFilter.isAdmin()){

                categoryDao.save(category);
                return StoreUtils.getResponseEntity("Category  Added Successfully ",HttpStatus.OK);
            }else {
                return StoreUtils.getResponseEntity(StoreConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);


    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory() {
       try {
           if(jwtFilter.isAdmin())
               return new ResponseEntity<>(categoryDao.findAll(),HttpStatus.OK);
           else return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
       }catch (Exception e){
           e.printStackTrace();
       }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public ResponseEntity<String> updateCaegory(Category category) {
        try{
          if(jwtFilter.isAdmin()){
              Optional upCategory=categoryDao.findById(category.getId());
              if(!upCategory.isEmpty()){
                  ModelMapper modelMapper =new ModelMapper();
                  Category category1 = modelMapper.map(upCategory,Category.class);

                  categoryDao.save(category1);
                  return StoreUtils.getResponseEntity("Category updated successeflly",HttpStatus.OK);
              }
              else{
                  return StoreUtils.getResponseEntity("Category id does not exist",HttpStatus.OK);
              }

          }else {
            return StoreUtils.getResponseEntity(StoreConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
          }
        }catch (Exception e ){
            e.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
