package com.example.demo.data.service.Impl;

import com.example.demo.data.dto.ProductDTO;
import com.example.demo.data.entity.ProductEntity;
import com.example.demo.data.handler.ProductDataHandler;
import com.example.demo.data.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Service
@EnableWebMvc
public class ProductServiceImpl implements ProductService {
    ProductDataHandler productDataHandeler;

    @Autowired
    public ProductServiceImpl(ProductDataHandler productDataHandler){
        this.productDataHandeler = productDataHandler;
    }

    // Service(Client) <-> Controller : DTO
    // Service <-> DAO(DB) : Entity
    @Override
    public ProductDTO saveProduct(String productId, String productName, int productPrice, int productStock){
        ProductEntity productEntity = productDataHandeler.saveProductEntity(productId, productName, productPrice, productStock);

        ProductDTO productDTO = new ProductDTO(productEntity.getProductId(), productEntity.getProductName(),
                productEntity.getProductPrice(), productEntity.getProductStocks());
        return productDTO;
    }

    @Override
    public ProductDTO getProduct(String productId){
        ProductEntity productEntity = productDataHandeler.getProductEntity(productId);

        ProductDTO productDTO = new ProductDTO(productEntity.getProductId(), productEntity.getProductName(),
                productEntity.getProductPrice(), productEntity.getProductStocks());
        return productDTO;
    }

}
