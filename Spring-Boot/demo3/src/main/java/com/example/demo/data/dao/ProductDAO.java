package com.example.demo.data.dao;

import com.example.demo.data.entity.ProductEntity;
public interface ProductDAO {
    ProductEntity saveProduct(ProductEntity productEntity);
    ProductEntity getProduct (String productId);
}
