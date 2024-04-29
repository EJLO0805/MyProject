package com.example.myproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.myproject.entity.OrderDetail;
import com.example.myproject.entity.OrderList;
import com.example.myproject.entity.ShoppingCart;
import com.example.myproject.entity.UserRole;
import com.example.myproject.service.OrderDAO;

import jakarta.websocket.server.PathParam;

@CrossOrigin("http://localhost:3000")
@RestController
public class OrderListController {

    @Autowired
    OrderDAO orderDAO;

    @GetMapping("/allOrderList")
    public ResponseEntity<List<OrderList>> fetchAllOrderList() {
        List<OrderList> orderLists = orderDAO.fetchAllOrder();
        return ResponseEntity.ok(orderLists);
    }

    @GetMapping("/allOrderList/order")
    public ResponseEntity<List<OrderDetail>> fetchOrderDetailById(@PathParam("orderId") String orderId,
            @RequestHeader("Authorization") String userRole) {
        boolean isAuthenticated = UserRole.isAuthenticatedEmployee(userRole);
        List<OrderDetail> orderDetails = null;
        OrderList orderList = null;
        if (isAuthenticated && orderId != null && !orderId.isEmpty()) {
            orderList = orderDAO.fetchOrderById(orderId);
            if (orderList != null) {
                orderDetails = orderDAO.fetchOrderDetailByOrderId(orderId);
                return ResponseEntity.ok(orderDetails);
            } else {
                return ResponseEntity.noContent().build();
            }
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/purchaseProduct")
    public ResponseEntity<OrderList> purchaseProduct(@RequestBody ShoppingCart shoppingCart,
            @RequestHeader("Authorization") String userRole) {
        boolean isAuthenticated = UserRole.isAuthenticatedAllUser(userRole);
        if (isAuthenticated) {
            OrderList orderList = orderDAO.saveOrder(shoppingCart);
            if (orderList != null) {
                return ResponseEntity.ok(orderList);
            } else {
                return ResponseEntity.noContent().build();
            }
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/allOrderList")
    public ResponseEntity<OrderList> updateOrderList(@PathParam("orderId") String orderId,
            @RequestHeader("Authorization") String userRole) {
        System.out.println("userRole:" + userRole);
        boolean isAuthenticated = UserRole.isAuthenticatedEmployee(userRole);
        if (isAuthenticated && orderId != null && !orderId.isEmpty()) {
            OrderList orderList = orderDAO.updateOrderList(orderId);
            return ResponseEntity.ok(orderList);
        } else {
            System.out.println("Update failed or OrderList not exist");
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/myOrderList")
    public ResponseEntity<List<OrderList>> fetchMyOrderList(@RequestBody String userEmail,
            @RequestHeader("Authorization") String userRole) {
        boolean isAuthenticated = UserRole.isAuthenticatedAllUser(userRole);
        if (isAuthenticated && userEmail != null) {
            List<OrderList> orderLists = orderDAO.fetchOrderByUser(userEmail);
            orderLists.stream().forEach(o -> System.out.println(o.getOrderId()));
            System.out.println(orderLists);
            return ResponseEntity.ok(orderLists);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("myOrderList/order")
    public ResponseEntity<List<OrderDetail>> fetchMyOrderDetails(@PathParam("orderId") String orderId,
            @RequestHeader("Authorization") String userRole) {
        boolean isAuthenticated = UserRole.isAuthenticatedAllUser(userRole);
        if (isAuthenticated && orderId != null) {
            List<OrderDetail> orderDetails = orderDAO.fetchOrderDetailByOrderId(orderId);
            return ResponseEntity.ok(orderDetails);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("myOrderList/{orderId}")
    public ResponseEntity<OrderList> fetchOrderById(@PathVariable("orderId") String orderId,
            @RequestHeader("Authorization") String userRole) {
        boolean isAuthenticated = UserRole.isAuthenticatedAllUser(userRole);
        if (isAuthenticated && orderId != null) {
            OrderList orderList = orderDAO.fetchOrderById(orderId);
            return ResponseEntity.ok(orderList);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

}
