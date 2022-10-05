package com.inn.store.rest;

import com.inn.store.request.UserLoginResquest;
import com.inn.store.request.UserRequest;
import com.inn.store.request.UserUpdateStatusRequest;
import com.inn.store.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "*")
@RequestMapping(path = "/user")
public interface UserRest {

    @PostMapping(path = "/signup")
    public ResponseEntity<String> signUp(@RequestBody(required = true) @Valid UserRequest userRequest) ;

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody(required = true) @Valid UserLoginResquest userLoginResquest) ;

    @GetMapping
   ResponseEntity<List<UserResponse>> getAllUser(@RequestParam(value="page" , defaultValue = "1")int page,
                                                 @RequestParam(value="limit", defaultValue = "7")int limit,
                                                 @RequestParam(value="search" , defaultValue = "") String search);

    @PutMapping(path = "/update")
    ResponseEntity<String> update(@RequestBody(required = true) @Valid UserUpdateStatusRequest userUpdateStatusRequest);

    @GetMapping(path="/checkToken")
    ResponseEntity<String> checkToken();

    @PostMapping(path = "/changePassword")
    ResponseEntity<String> changePassword(@RequestBody Map<String,String> requestMap);

    @PostMapping(path = "/forgotPassword")
    ResponseEntity<String> forgotPassword(@RequestBody Map<String,String> requestMap);

}

