package com.example.myproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myproject.entity.ShoppingUser;


@Repository
public interface UserRepository extends JpaRepository<ShoppingUser,String>{
    
}
