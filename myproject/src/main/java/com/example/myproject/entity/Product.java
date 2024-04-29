package com.example.myproject.entity;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table(name = "product")
@Entity
@Getter
@Setter
@ToString
public class Product implements Serializable{
    @Id
    private int productId;
    private String productName;
    private double productPrice;
    private String category;
    @Column(columnDefinition = "Text")
    private String description;
    @Column(length = Integer.MAX_VALUE)
    private byte[] image;
    @JoinColumn(name = "ratingId", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL)
    private Rating rating;
}
