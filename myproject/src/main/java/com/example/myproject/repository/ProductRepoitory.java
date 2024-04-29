package com.example.myproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myproject.entity.Product;

@Repository
public interface ProductRepoitory extends JpaRepository<Product,Integer>{

}
