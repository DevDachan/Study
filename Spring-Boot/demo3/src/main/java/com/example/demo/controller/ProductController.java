package com.example.demo.controller;

import com.example.demo.common.Constants;
import com.example.demo.common.exception.DemoException;
import com.example.demo.data.dto.ProductDTO;
import com.example.demo.data.entity.ProductEntity;
import com.example.demo.data.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@RequestMapping("/devdachan/product-api")
@EnableWebMvc
public class ProductController {
    private ProductService productService;
    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/product/{productId}")
    public ProductDTO getProduct(@PathVariable String productId) {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[ProductController] perform {} of DEMO API","getProduct");

        ProductDTO productDTO = productService.getProduct(productId);
        LOGGER.info("[ProductController] Response :: productId = {}, productName ={} , productPrice = {} , productStock = {}",
                productDTO.getProductId(),productDTO.getProductName(),productDTO.getProductPrice(),productDTO.getProductStock());

        return productDTO;
    }

    /* Validation 적용 이전
    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO productDto) {
        String productId = productDto.getProductId();
        String productName = productDto.getProductName();
        int productPrice = productDto.getProductPrice();
        int productStock = productDto.getProductStock();
        return productService.saveProduct(productId, productName, productPrice, productStock);
    }*/

    @PostMapping(value="/product")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDto) {
        String productId = productDto.getProductId();
        String productName = productDto.getProductName();
        int productPrice = productDto.getProductPrice();
        int productStock = productDto.getProductStock();
        ProductDTO response = productService.saveProduct(productId,productName,productPrice,productStock);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @DeleteMapping(value = "/product/{productId}")
    public ProductDTO deleteProduct(@PathVariable String productId) {
        return null;
    }

    @PostMapping(value = "/product/exception")
    public void exceptionTest() throws DemoException {
        throw new DemoException(Constants.ExceptionClass.PRODUCT, HttpStatus.FORBIDDEN, "접근이 금지되었습니다.");
    }
}
