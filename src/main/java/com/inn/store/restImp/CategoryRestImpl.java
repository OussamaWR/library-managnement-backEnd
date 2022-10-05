package com.inn.store.restImp;

import com.inn.store.constants.StoreConstants;
import com.inn.store.entities.Category;
import com.inn.store.rest.CategoryRest;
import com.inn.store.service.CategoryService;
import com.inn.store.utils.StoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CategoryRestImpl implements CategoryRest{

    @Autowired
    CategoryService categoryService;

    @Override
    public ResponseEntity<String> addNewCategory(Category category) {

        try{

            return categoryService.addNewCategory(category);
        }catch (Exception e){
            e.printStackTrace();

        }

        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory() {
        try{
            return categoryService.getAllCategory();
        }catch (Exception e ){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCaegory(Category category) {
        try{
            return categoryService.updateCaegory(category);
        }catch (Exception e ){
            e.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
