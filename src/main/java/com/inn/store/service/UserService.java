package com.inn.store.service;

import com.inn.store.request.UserLoginResquest;
import com.inn.store.request.UserRequest;
import com.inn.store.request.UserUpdateStatusRequest;
import com.inn.store.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface UserService {

    ResponseEntity<String> signUp(UserRequest userRequest);
    ResponseEntity<String> login(UserLoginResquest userLoginResquest);
    ResponseEntity<List<UserResponse>>getUsers(int page, int limit, String search) ;
    ResponseEntity<String> update(UserUpdateStatusRequest userUpdateStatusRequest);
    ResponseEntity<String> checkToken();
    ResponseEntity<String> changePassword(Map<String,String> mapRequest);
    ResponseEntity<String> forgotPassword(Map<String,String> mapRequest);

}
