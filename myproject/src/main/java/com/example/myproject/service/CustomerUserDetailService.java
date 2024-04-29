package com.example.myproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.myproject.entity.ShoppingUser;
import com.example.myproject.repository.UserRepository;

@Service
public class CustomerUserDetailService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        @SuppressWarnings("null")
        ShoppingUser user = userRepository.findById(userEmail).get();
        if(user != null){
            return new User(user.getUserEmail(),user.getUserPassword(),List.of(user.getUserRole()));
        } else {
            throw new UsernameNotFoundException("Could not found user with user Id: " + userEmail);
        }
    }
    

}
