package com.inn.store.serviceImp;

import com.inn.store.JWT.CustomtomerUsersDetailsService;
import com.inn.store.JWT.JwtFilter;
import com.inn.store.JWT.JwtUtil;
import com.inn.store.constants.StoreConstants;
import com.inn.store.dao.UserDao;
import com.inn.store.entities.User;
import com.inn.store.request.UserLoginResquest;
import com.inn.store.request.UserRequest;
import com.inn.store.request.UserUpdateStatusRequest;
import com.inn.store.response.UserResponse;
import com.inn.store.service.UserService;
import com.inn.store.utils.EmailUtils;
import com.inn.store.utils.StoreUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Slf4j //Spring Logging Facade for Java => (Error , warn , info , debug) managment
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomtomerUsersDetailsService customtomerUsersDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    EmailUtils emailUtils;
    @Override
    public ResponseEntity<String> signUp(UserRequest userRequest){
        log.info("Inside signup {}",userRequest);
        User user = userDao.findUserByEmail(userRequest.getEmail());
        if(!Objects.isNull(user)) return  StoreUtils.getResponseEntity("Email already existe",HttpStatus.BAD_REQUEST); //throw new UserExceptionHandler(UserErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
        try{
            // verefié si le email est utilisé par un autre personne
           ModelMapper modelMapper = new ModelMapper();
                User userRes=modelMapper.map(userRequest,User.class);
                userDao.save(userRes);
                return StoreUtils.getResponseEntity("Successfully Registered.",HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
        }

        return StoreUtils.getResponseEntity(StoreConstants.INVALID_DATA,HttpStatus.BAD_REQUEST);


    }

    @Override
    public ResponseEntity<String> login(UserLoginResquest userLoginResquest) {
       try{
           Authentication  authentication =authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginResquest.getEmail(),userLoginResquest.getPassword())
           );
           if(authentication.isAuthenticated()){
               // status=true is  mean the admin aaccept  the request of user  for  join the  plateforme , so can login normal and the jwt will created
               if(customtomerUsersDetailsService.getUserDetails().getStatus().equalsIgnoreCase("true") ){
                   Integer id = customtomerUsersDetailsService.getUserDetails().getId();
                   String email =customtomerUsersDetailsService.getUserDetails().getEmail();
                   String role = customtomerUsersDetailsService.getUserDetails().getRole();
                   String name = customtomerUsersDetailsService.getUserDetails().getName();
                   String token=jwtUtil.genereteToken(email,role,name,id);

                   return new ResponseEntity<String>("{\"token\": \""+token+"\" , \"id\":\""+id+"\"}",HttpStatus.OK);

               }else{
                   // if false  => the user can't login and  , jwt isn't created yet
                   return new ResponseEntity<String>("{\"message\": \"Wait for admin approval.\"}",HttpStatus.BAD_REQUEST);
               }
           }
       }catch (Exception e){
           log.error("{}",e);
       }
        return StoreUtils.getResponseEntity("Bad Credentials.",HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserResponse>> getUsers(int page, int limit,String search) {

      try{
          if(jwtFilter.isAdmin()){
              if(page>0) page -=1;

              List<UserResponse> userResponses = new ArrayList<>();
              Pageable pageable = PageRequest.of(page,limit);
              Page<User> userPage;

              if(search.isEmpty()){
                  userPage = userDao.findAllByRole(pageable,"user");
              }else{
                  System.out.println("====> "+search);

                  userPage=userDao.getAllUser(pageable,search);
                  System.out.println("====> "+userPage);
              }
              //List<User> users = userDao.findAllByRole("user");
              ModelMapper modelMapper = new ModelMapper();
              for(User user :userPage){
                  UserResponse userResponse =modelMapper.map(user,UserResponse.class);
                  userResponses.add(userResponse);
              }
              return new ResponseEntity<>(userResponses,HttpStatus.OK);
          }else {
              return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
          }



      }catch (Exception e){

      }
      return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> update(UserUpdateStatusRequest userUpdateStatusRequest) {
        try{
            if(jwtFilter.isAdmin()){
                Optional<User> optionalUser = userDao.findById(userUpdateStatusRequest.getId()); // if exist ok else exception

                if(!optionalUser.isEmpty()){
                    userDao.updateStatus(userUpdateStatusRequest.getStatus(), userUpdateStatusRequest.getId());

                    
                    List<String> emailsOfAdmins=new ArrayList<>();
                    List<User> users = userDao.findAllByRole("admin");
                    ModelMapper modelMapper = new ModelMapper();
                    for(User user :users){
                        String email = user.getEmail();
                        emailsOfAdmins.add(email);

                    }


                    sendMailToAllAdmin(userUpdateStatusRequest.getStatus()
                                        ,optionalUser.get().getEmail()
                                        ,emailsOfAdmins) ;

                    return StoreUtils.getResponseEntity("User Status Updated Successfully",HttpStatus.OK);
                }else{
                    return StoreUtils.getResponseEntity("User id doesn't exist",HttpStatus.OK);

                }
            }else{
                return StoreUtils.getResponseEntity(StoreConstants.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
                e.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private void sendMailToAllAdmin(String status, String user, List<String> emailsOfAdmins) {
        emailsOfAdmins.remove(jwtFilter.getCurrentUser());
        if(status!=null&&status.equalsIgnoreCase("true")){
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(),"Account Approved",
                    "USER"+user+"\n is approved by \nADMIN:"+jwtFilter.getCurrentUser(), emailsOfAdmins );
        }else{
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(),"Account Disabled",
                    "USER"+user+"\n is disabled by \nADMIN:"+jwtFilter.getCurrentUser(), emailsOfAdmins );
        }
    }

    @Override
    public ResponseEntity<String> checkToken() {
        return StoreUtils.getResponseEntity("true",HttpStatus.OK) ;
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> mapRequest) {
      try{
        User user = userDao.findUserByEmail(jwtFilter.getCurrentUser());
        if(!user.equals(null)){

            if(user.getPassword().equals(mapRequest.get("oldPassword"))){
                user.setPassword(mapRequest.get("newPassword"));
                userDao.save(user);
                return StoreUtils.getResponseEntity("Your Password Changed Successfully",HttpStatus.OK);
            }
            else return StoreUtils.getResponseEntity("Incorrect Old Password",HttpStatus.BAD_REQUEST);
        }
        else return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
      }catch (Exception e){
            e.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> mapRequest) {
        try{
            User user = userDao.findUserByEmail(mapRequest.get("email"));
            if(!Objects.isNull(user)){
                emailUtils.forgotMail(user.getEmail(),"Credentials by Libriry Management System",user.getPassword());
                return StoreUtils.getResponseEntity("Check your  mail for Credentials",HttpStatus.OK);
            }else{
                return StoreUtils.getResponseEntity("this email doesn't exist !! ",HttpStatus.BAD_REQUEST);

            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
