package com.example.myproject.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table(name ="user")
@Entity
@Getter
@Setter
@ToString
public class ShoppingUser implements Serializable{
    @Id
    private String userEmail;
    private String userName;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userPassword;
    private String userPhone;
    @Column(columnDefinition = "Enum('ADMIN','EMPLOYEE','NORMALUSER')")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

}
