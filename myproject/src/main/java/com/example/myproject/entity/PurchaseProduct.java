package com.example.myproject.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PurchaseProduct {
    private Integer productId;
    private String productName;
    private String productPrice;
    private int quantity;
    private double totalPricePerProduct;

    public void setTotalPricePerProduct(Product product, int quantity){
        this.totalPricePerProduct = product.getProductPrice()*quantity;
    }
}
