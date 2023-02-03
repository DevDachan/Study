package com.example.demo.service.Impl;

import static org.mockito.Mockito.verify;

import com.example.demo.data.dto.ProductDTO;
import com.example.demo.data.entity.ProductEntity;
import com.example.demo.data.handler.Impl.ProductDataHandlerImpl;
import com.example.demo.data.service.Impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

// 어떤 객체를 가져올지 모르겠을 때는 SpringBootTest를 사용, 매개변수에 대한 Bean을 Load
@SpringBootTest(classes = {ProductDataHandlerImpl.class, ProductServiceImpl.class})

// 만약 내가 필요한 부분만을 가져와 사용한다고 했을 때는 ExtendWith을 통해서 사용
//@ExtendWith(SpringExtension.class)
//@Import({ProductDataHandlerImpl.class, ProductServiceImpl.class})
public class ProductServiceImplTest {


    @MockBean
    ProductDataHandlerImpl productDataHandler;
    // 서비스 측에서 ProductDataHandler라는 객체를 상속받아 사용하기 때문에 동일하게 구현체 Mock 생성

    @Autowired
    ProductServiceImpl productService;

    @Test
    public void getProductTest() {
        //given
        Mockito.when(productDataHandler.getProductEntity("123"))
                .thenReturn(new ProductEntity("123", "pen", 2000, 3000));

        ProductDTO productDto = productService.getProduct("123");

        Assertions.assertEquals(productDto.getProductId(), "123");
        Assertions.assertEquals(productDto.getProductName(), "pen");
        Assertions.assertEquals(productDto.getProductPrice(), 2000);
        Assertions.assertEquals(productDto.getProductStock(), 3000);

        verify(productDataHandler).getProductEntity("123");
    }

    @Test
    public void saveProductTest() {
        //given
        Mockito.when(productDataHandler.saveProductEntity("123", "pen", 2000, 3000))
                .thenReturn(new ProductEntity("123", "pen", 2000, 3000));

        ProductDTO productDto = productService.saveProduct("123", "pen", 2000, 3000);

        Assertions.assertEquals(productDto.getProductId(), "123");
        Assertions.assertEquals(productDto.getProductName(), "pen");
        Assertions.assertEquals(productDto.getProductPrice(), 2000);
        Assertions.assertEquals(productDto.getProductStock(), 3000);

        verify(productDataHandler).saveProductEntity("123", "pen", 2000, 3000);
    }
}