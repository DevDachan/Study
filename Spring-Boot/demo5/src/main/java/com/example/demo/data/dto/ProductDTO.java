package com.example.demo.data.dto;

import com.example.demo.data.entity.ProductEntity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@RedisHash(value="product", timeToLive = 60)
public class ProductDTO {
    @NotNull
    private String productId;
    @NotNull
    private String productName;
    @NotNull
    @Min(value = 500)
    @Max(value = 30000)
    private int productPrice;
    @NotNull
    @Min(value = 0)
    @Max(value = 9999)
    private int productStock;

    public ProductEntity toEntity(){
        return ProductEntity.builder()
                .productId(productId)
                .productName(productName)
                .productPrice(productPrice)
                .productStocks(productStock)
                .build();

    }
}
