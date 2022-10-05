package com.inn.store.JWT;

import com.inn.store.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@Service
public class CustomtomerUsersDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    private  com.inn.store.entities.User userDetails;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("inside loadUserByUsername {}",username);
        userDetails = userDao.findUserByEmail(username);
        if(!Objects.isNull(userDetails)){
            return new User(userDetails.getEmail(),userDetails.getPassword(),new ArrayList<>());

        }else throw new UsernameNotFoundException("User not found");

    }

    public   com.inn.store.entities.User getUserDetails(){
        return this.userDetails;
    }

}
