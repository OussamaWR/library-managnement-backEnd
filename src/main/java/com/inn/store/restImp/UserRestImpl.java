package com.inn.store.restImp;

import com.inn.store.constants.StoreConstants;
import com.inn.store.request.UserLoginResquest;
import com.inn.store.request.UserRequest;
import com.inn.store.request.UserUpdateStatusRequest;
import com.inn.store.response.UserResponse;
import com.inn.store.rest.UserRest;
import com.inn.store.service.UserService;
import com.inn.store.utils.StoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserRestImpl implements UserRest {

    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<String> signUp(UserRequest userRequest){
       try {
            return userService.signUp(userRequest);
       }catch (Exception e){
            e.printStackTrace();
       }

       return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @Override
    public ResponseEntity<String> login(UserLoginResquest userLoginResquest) {
        try{
            return userService.login(userLoginResquest);
        }catch (Exception e){
            e.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserResponse>> getAllUser(int page,
                                                         int limit,
                                                         String search) {
        try{
            return userService.getUsers(page,limit,search);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<UserResponse>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(UserUpdateStatusRequest userUpdateStatusRequest) {
        try{
            return userService.update(userUpdateStatusRequest);
        }catch (Exception e){
            e.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checkToken() {
        try{
            return userService.checkToken();
        }catch (Exception e){
            e.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try{
            return userService.changePassword(requestMap );
        }catch (Exception e){
            e.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try{
            return userService.forgotPassword(requestMap );
        }catch (Exception e){
            e.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
