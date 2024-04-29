package com.example.myproject.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myproject.entity.OrderDetail;
import com.example.myproject.entity.OrderList;
import com.example.myproject.entity.PurchaseProduct;
import com.example.myproject.entity.ShoppingCart;
import com.example.myproject.repository.OrderDetailRepository;
import com.example.myproject.repository.OrderRepository;

@Service
public class OrderDAO {

   @Autowired
   OrderRepository orderRepository;

   @Autowired
   OrderDetailRepository orderDetailRepository;

   @Autowired
   UserDAO userDAO;

   @Autowired
   ProductDAO productDAO;

   public OrderList saveOrder(ShoppingCart shoppingCart) {
      System.out.println(shoppingCart.getPurchaseProducts());
      OrderList orderList = new OrderList();
      Date date = new Date();
      SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmmssSSS");
      String dateStr = ft.format(date);
      String strings = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
      char randomStr1 = strings.charAt((int) (Math.random() * 52));
      char randomStr2 = strings.charAt((int) (Math.random() * 52));
      orderList.setOrderId(randomStr1 + dateStr + randomStr2);
      orderList.setCustomerName(shoppingCart.getCustomerName());
      orderList.setCustomerEmail(shoppingCart.getCustomerEmail());
      orderList.setCustomerPhone(shoppingCart.getCustomerPhone());
      orderList.setCustomeraddress(shoppingCart.getCustomerAddress());
      orderList.setOrderDate(date);
      orderList.setUser(userDAO.fetchUserByEmail(shoppingCart.getUserEmail()));
      orderList.setDelivered(false);
      orderList.setOrderTotalPrice(shoppingCart.getOrderTotalPrice());
      try {
         orderRepository.save(orderList);
         List<PurchaseProduct> products = shoppingCart.getPurchaseProducts();
         for (PurchaseProduct product : products) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderList(orderList);
            orderDetail.setProduct(productDAO.fetchProductById(product.getProductId()));
            orderDetail.setQtyPerProduct(product.getQuantity());
            orderDetail.setTotalPricePerProducts(product.getTotalPricePerProduct());
            try {
               orderDetailRepository.save(orderDetail);
            } catch (Exception e) {
               System.out.println("orderDetail Create Failed");
               return null;
            }
         }
         return orderList;
      } catch (Exception e) {
         System.out.println("Order Create Failed");
         return null;
      }
   }

   public List<OrderList> fetchAllOrder() {
      return orderRepository.findAll();
   }

   public List<OrderList> fetchOrderByUser(String userEmail) {
      System.out.println("My email:" + userEmail);
      return orderRepository.findAll().stream().filter(o -> o.getUser().getUserEmail().equals(userEmail)).toList();
   }

   public OrderList fetchOrderById(String id) {
      return orderRepository.findById(id).orElse(null);
   }

   public OrderList updateOrderList(String orderId) {
      OrderList orderList = fetchOrderById(orderId);
      if (orderList != null) {
         orderList.setDelivered(true);
         return orderRepository.save(orderList);
      } else {
         System.out.println("Could not find order");
         return null;
      }
   }

   public List<OrderDetail> fetchOrderDetailByOrderId(String orderId) {
      List<OrderDetail> details = orderDetailRepository.findAll().stream()
            .filter(o -> o.getOrderList().getOrderId().equals(orderId)).toList();
      return details;
   }

}
