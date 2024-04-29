package com.example.myproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myproject.entity.OrderList;

@Repository
public interface OrderRepository extends JpaRepository<OrderList,String>{

}
