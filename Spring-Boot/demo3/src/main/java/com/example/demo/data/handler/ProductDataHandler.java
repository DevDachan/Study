package com.example.demo.data.handler;

import com.example.demo.data.entity.ProductEntity;

public interface ProductDataHandler {
    ProductEntity saveProductEntity(String productId, String productName, int productPrice, int productStock);

    ProductEntity getProductEntity(String productId);

}
