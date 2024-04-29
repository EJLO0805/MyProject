package com.example.myproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myproject.entity.ShoppingUser;
import com.example.myproject.repository.UserRepository;

@Service
public class UserDAO {
    @Autowired
    UserRepository userRepository;

    public List<ShoppingUser> fetchAllUser() {
        return userRepository.findAll();
    }

    public ShoppingUser fetchUserByEmail(String userEmail) {
        try {
            ShoppingUser user = userRepository.findById(userEmail).get();
            return user;
        } catch (Exception ex) {
            System.out.println("No User");
            return null;
        }
    }

    public ShoppingUser createNewUser(ShoppingUser user) {
        try {
            userRepository.save(user);
            return user;
        } catch (Exception e) {
            System.out.println("Create User failed " + e.getMessage());
            return null;
        }
    }

}
