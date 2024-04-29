package com.example.myproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myproject.entity.Product;
import com.example.myproject.entity.UserRole;
import com.example.myproject.service.ProductDAO;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductDAO productDAO;

    @GetMapping
    public ResponseEntity<List<Product>> fetchAllProduct() {
        return ResponseEntity.ok(productDAO.fetchAllProductWithoutPhoto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> fetchProductById(@PathVariable("id") Integer id) {
        Product p = productDAO.fetchProductByIdWithoutPhoto(id);
        if (p != null) {
            return ResponseEntity.ok(p);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/image")
    public void fetchProductPhoto(@PathParam("id") Integer id, HttpServletResponse response) {
        byte[] photo = null;
        if (id != null) {
            photo = productDAO.fetchPhotoById(id);
        }
        if (photo != null) {
            response.setContentType("mage/jpeg, image/jpg, image/png, image/gif");
            try {
                response.getOutputStream().write(photo);
                response.getOutputStream().close();
            } catch (Exception ex) {
                System.out.println("Fetch Photo Error " + ex.getMessage());
            }
        }
    }

    @PostMapping("/addnewproduct")
    public ResponseEntity<Product> createNewProduct(@RequestBody Product product,
            @RequestHeader("Authorization") String userRole) {
        boolean isAuthenticated = UserRole.isAuthenticatedEmployee(userRole);
        Product p = productDAO.saveProduct(product);
        if (isAuthenticated && p != null) {
            return ResponseEntity.ok(p);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Integer id, @RequestBody Product product,
            @RequestHeader("Authorization") String userRole) {
        Product p = productDAO.updateProduct(id, product);
        System.out.println("id : " + id);
        System.out.println("product" + product);
        boolean isAuthenticated = UserRole.isAuthenticatedEmployee(userRole);
        if (isAuthenticated && p != null) {
            return ResponseEntity.ok(p);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Integer id,
            @RequestHeader("Authorization") String userRole) {
        System.out.println("productId:" + id);
        boolean isAuthenticated = UserRole.isAuthenticatedAdmin(userRole);
        if (isAuthenticated) {
            boolean isDelete = productDAO.deleteProduct(id);
            if (isDelete) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.noContent().build();
            }
        } else {
            return ResponseEntity.noContent().build();
        }
    }

}
