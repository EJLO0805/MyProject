package com.example.myproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myproject.entity.Product;
import com.example.myproject.entity.Rating;
import com.example.myproject.repository.ProductRepoitory;

@Service
public class ProductDAO {
    @Autowired
    ProductRepoitory productRepoitory;

    public Product saveProduct(Product product) {
        boolean isExisted = fetchProductById(product.getProductId()) == null ? false : true;
        if (!isExisted) {
            Rating rate = new Rating();
            rate.setId(product);
            product.setRating(rate);
            return productRepoitory.save(product);
        }else{
            System.out.println("Product Id is existed, please change a id code");
            return null;
        }
    }

    public List<Product> fetchAllProduct() {
        return productRepoitory.findAll();
    }

    public List<Product> fetchAllProductWithoutPhoto() {
        List<Product> products = productRepoitory.findAll();
        products.forEach(p -> p.setImage(null));
        return products;
    }

    @SuppressWarnings("null")
    public Product fetchProductById(Integer id) {
        return productRepoitory.findById(id).orElse(null);
    }

    public Product fetchProductByIdWithoutPhoto(Integer id) {
        Product p = fetchProductById(id);
        p.setImage(null);
        return p;
    }

    public byte[] fetchPhotoById(Integer id){
        Product product = fetchProductById(id);
        if(product != null){
            return product.getImage();
        }else{
            return null;
        }
    }

    public Product updateProduct(Integer id, Product product){
        Product updateProduct = fetchProductById(id);
        if(updateProduct != null && id == product.getProductId()){
            updateProduct.setProductName(product.getProductName());
            updateProduct.setCategory(product.getCategory());
            updateProduct.setDescription(product.getDescription());
            updateProduct.setProductPrice(product.getProductPrice());
            if(product.getImage() != null){
                updateProduct.setImage(product.getImage());
            }
            return productRepoitory.save(updateProduct);
        }else{
            System.out.println("Product is not existed");
            return null;
        }
    }

    public boolean deleteProduct(Integer id){
        Product product = fetchProductById(id);
        if(product != null){
            productRepoitory.delete(product);
            return true;
        }else{
            System.out.println("Product is not existed");
            return false;
        }
    }

}
