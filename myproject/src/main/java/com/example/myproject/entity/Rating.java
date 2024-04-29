package com.example.myproject.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table(name = "rating")
@Entity
@Getter
@Setter
@ToString
public class Rating implements Serializable{
    @Id
    private int id;
    private double rate;
    private int count;

    public void setId(Product product){
        this.id = product.getProductId();
    }
}
