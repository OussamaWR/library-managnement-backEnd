package com.inn.store.restImp;

import com.inn.store.constants.StoreConstants;
import com.inn.store.entities.Product;
import com.inn.store.request.ProductRequest;
import com.inn.store.response.ProductResponse;
import com.inn.store.rest.ProductRest;
import com.inn.store.service.ProductService;
import com.inn.store.utils.StoreUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class ProdcutRestImpl implements ProductRest {

    @Autowired
    ProductService productService ;

    @Override
    public ResponseEntity<String> addProduct(ProductRequest productRequest) {
       try{
           return  productService.addProduct(productRequest);
       }catch (Exception e){
           e.printStackTrace();
       }

        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductResponse>> getAllProducts(int page, int limit, String search) {
        try{
            return productService.getAllProducts(page, limit , search);
        }catch (Exception e ){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>() , HttpStatus.INTERNAL_SERVER_ERROR );
    }

    @Override
    public ResponseEntity<?> getOneProduct(Integer id) {
        try{
            return  productService.getOneProduct(id);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(StoreConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getProductByCategory(int page,int limit,String categoryName) {
        log.info("I m in Rest Imp : {}",categoryName);
        try{

            return  productService.getProductByCategory(page,limit,categoryName);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(StoreConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateProduct(ProductRequest productRequest) {
        try{
            return  productService.updateProduct(productRequest);
        }catch (Exception e){
            e.printStackTrace();
        }

        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try{
            return  productService.deleteProduct(id);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(StoreConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> changeStatus(Integer id, Map<String,String> requestMap) {
        try{
            return  productService.changeStatus(id,requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(StoreConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
