package com.example.myproject.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table(name = "orderList")
@Entity
@Getter
@Setter
@ToString
public class OrderList implements Serializable{
    @Id
    private String orderId;
    private Date orderDate;
    private double orderTotalPrice;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String customeraddress;
    private boolean isDelivered;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userEmail")
    private ShoppingUser user;
}
