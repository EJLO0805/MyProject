package com.example.myproject.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ShoppingCart {
    private List<PurchaseProduct> purchaseProducts;
    private double orderTotalPrice;
    private String userEmail;
    private String customerName;
    private String customerPhone;
    private String customerAddress;
    private String customerEmail;
}
