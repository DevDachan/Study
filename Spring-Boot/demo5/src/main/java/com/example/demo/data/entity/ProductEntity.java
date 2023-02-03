package com.example.demo.data.entity;

import com.example.demo.data.dto.ProductDTO;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "product")
public class ProductEntity extends BaseEntity{
    @Id
    String productId;
    String productName;
    Integer productPrice;
    Integer productStocks;

    public ProductDTO toDto(){
        return ProductDTO.builder()
                .productId(productId)
                .productName(productName)
                .productPrice(productPrice)
                .productStock(productStocks)
                .build();
    }
}
