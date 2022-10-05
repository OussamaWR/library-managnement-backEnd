package com.inn.store.serviceImp;

import com.inn.store.JWT.JwtFilter;
import com.inn.store.constants.StoreConstants;
import com.inn.store.dao.CategoryDao;
import com.inn.store.dao.ProductDao;
import com.inn.store.entities.Category;
import com.inn.store.entities.Product;
import com.inn.store.entities.User;
import com.inn.store.request.ProductRequest;
import com.inn.store.response.ProductResponse;
import com.inn.store.response.UserResponse;
import com.inn.store.service.ProductService;
import com.inn.store.utils.StoreUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDao productDao;
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    JwtFilter jwtFilter;
    @Override
    public ResponseEntity<String> addProduct(ProductRequest productRequest) {

        try {
            Optional optional = categoryDao.findById(productRequest.getCategory().getId());
            if(optional.isEmpty()){
                return StoreUtils.getResponseEntity("this category is doesn't exist , check your informations", HttpStatus.BAD_REQUEST);
            }

            if(jwtFilter.isAdmin()){
                ModelMapper modelMapper =new ModelMapper();
                Product product =modelMapper.map(productRequest,Product.class);
                productDao.save(product);
                return StoreUtils.getResponseEntity("Product Added Successefully", HttpStatus.OK);
            }
           else {
               return  StoreUtils.getResponseEntity(StoreConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductResponse>> getAllProducts(int page, int limit, String search) {
        try {
            if(page>0) page -=1;

            List<ProductResponse> productsResponses = new ArrayList<>();
            Pageable pageable = PageRequest.of(page,limit);
            Page<Product> productPage;

            if(search.isEmpty()){
                productPage = productDao.findAllByStatus(pageable,"true");
            }else{

                productPage=productDao.getAllProducts(pageable,search);

            }
            //List<User> users = userDao.findAllByRole("user");
            ModelMapper modelMapper = new ModelMapper();
            for(Product product :productPage){
                ProductResponse productResponse =modelMapper.map(product,ProductResponse.class);
                productsResponses.add(productResponse);
            }
            return  new ResponseEntity<>(productsResponses,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ResponseEntity<String> updateProduct(ProductRequest productRequest) {
        try {

            Optional<Product> optional = productDao.findById(productRequest.getId());
            if(optional.isEmpty()){
                return StoreUtils.getResponseEntity("this products is doesn't exist , check your informations", HttpStatus.BAD_REQUEST);
            }

            if(jwtFilter.isAdmin()){

                Product product =productDao.findById(productRequest.getId()).get();
                product.setName(productRequest.getName());
                product.setCategory(productRequest.getCategory());
                product.setPrice(productRequest.getPrice());
                product.setStatus(productRequest.getStatus());
                product.setDescription(productRequest.getDescription());

                productDao.save(product);
                return StoreUtils.getResponseEntity("Product updated Successefully", HttpStatus.OK);
            }
            else {
                return  StoreUtils.getResponseEntity(StoreConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getOneProduct(Integer id) {
        try{
            Optional optional = productDao.findById(id);
            if(!optional.isEmpty()){
                System.out.println(optional);
                ModelMapper modelMapper = new ModelMapper();
                ProductResponse productResponse= modelMapper.map(optional.get(),ProductResponse.class);
                System.out.println(productResponse);
                return new ResponseEntity<>(productResponse,HttpStatus.OK);
            }else{
                return new ResponseEntity<>( "this Products doesn't exist , check the id maybe is  wrong !!" , HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(StoreConstants.SOMETHING_WENT_WRONG ,HttpStatus.BAD_REQUEST);
    }


    @Override
    public ResponseEntity<?> getProductByCategory(int page, int limit,String categoryName) {

        try{
            if(page>0) page -=1;

            Pageable pageable = PageRequest.of(page,limit);
            Page<Product> productPage;


            List<ProductResponse> productResponses = new ArrayList<>();

            if(categoryName.isEmpty()) {

                productPage = productDao.findAllByStatus(pageable, "true");
            }else {
             productPage = productDao.findAllByStatusAndCateAndCategoryName(pageable,categoryName);

            }

            if(!productPage.isEmpty()){

                for(Product product : productPage){
                    ModelMapper modelMapper = new ModelMapper();
                    ProductResponse productResponse= modelMapper.map(product,ProductResponse.class);
                    productResponses.add(productResponse);
                }

                return new ResponseEntity<>(productResponses,HttpStatus.OK);
            }else{
                return new ResponseEntity<>( "No Products in this Category" , HttpStatus.BAD_REQUEST);
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ProductResponse() ,HttpStatus.BAD_REQUEST);

    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try {

            if (jwtFilter.isAdmin()) {
                if(!productDao.findById(id).isEmpty()){
                    productDao.deleteById(id);
                    return  StoreUtils.getResponseEntity("Product deleted successeflly !! ", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("this Products doesn't exist , check the id maybe is  wrong !!", HttpStatus.BAD_REQUEST);
            }
        }else return StoreUtils.getResponseEntity(StoreConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(StoreConstants.SOMETHING_WENT_WRONG ,HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> changeStatus(Integer id, Map<String,String> requestMap) {

        try{
            if (jwtFilter.isAdmin()) {
                Optional<Product> optional=productDao.findById(id);
                if(!optional.isEmpty()){
                   Product product = optional.get();

                   product.setStatus(requestMap.get("status"));
                   productDao.save(product);
                    return  StoreUtils.getResponseEntity("Status updated successeflly !! ", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("this Products doesn't exist , check the id maybe is  wrong !!", HttpStatus.BAD_REQUEST);
                }
            }else return StoreUtils.getResponseEntity(StoreConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(StoreConstants.SOMETHING_WENT_WRONG , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
