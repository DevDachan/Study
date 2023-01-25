package com.example.demo.controller;

import com.example.demo.data.dto.ProductDTO;
import com.example.demo.data.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/devdachan/product-api")
public class ProductController {
    private ProductService productService;
    private final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/{productId}")
    public ProductDTO getProduct(@PathVariable String productId) {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[ProductController] perform {} of DEMO API","getProduct");

        ProductDTO productDTO = productService.getProduct(productId);
        LOGGER.info("[ProductController] Response :: productId = {}, productName ={} , productPrice = {} , productStock = {}",
                productDTO.getProductId(),productDTO.getProductName(),productDTO.getProductPrice(),productDTO.getProductStock());

        return productDTO;
    }

    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO productDto) {
        String productId = productDto.getProductId();
        String productName = productDto.getProductName();
        int productPrice = productDto.getProductPrice();
        int productStock = productDto.getProductStock();
        return productService.saveProduct(productId, productName, productPrice, productStock);
    }

    @DeleteMapping(value = "/product.{productId}")
    public ProductDTO deleteProduct(@PathVariable String productId)
    {
        return null;
    }
}
