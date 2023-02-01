package com.example.demo.controller;


import com.example.demo.data.dto.ProductDTO;
import com.example.demo.data.service.Impl.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.web.servlet.function.ServerResponse.status;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@WebMvcTest(ProductController.class)
//@AutoConfigureWebMvc 해당 어노테이션 사용시 MockMvc를 Builder없이 주입 가능
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    // ProductController에서 잡고 있는 Bean 객체에 대해 Mock 형태희 객체를 생성해줌
    @MockBean
    ProductServiceImpl productService;

    @Test
    @DisplayName("Product 데이터 가져오기 테스트")
    void getProductTest() throws Exception{
        
        // given: Mock 객체가 특정 상황에서 해야 하는 행위를 정의하는 메소드
        // 아래 예시는 DB에서 DTO를 생성하면서 이러한 값으로 DTO가 생성 될 것이다라고 하면서 TestCase를 반환해주는 것
        given(productService.getProduct("12315")).willReturn(
                new ProductDTO("1212", "pen", 4500, 5500)
        );
        String productId = "12315";

        // andExpect: 기대하는 값이 나왔는지 체크하는 메소드
        mockMvc.perform(
                get("/devdachan/product-api/product/" + productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").exists())
                .andExpect(jsonPath("$.productName").exists())
                .andExpect(jsonPath("$.productPrice").exists())
                .andExpect(jsonPath("$.productStock").exists())
                .andDo(print());

        // verify: 해당 객체의 메소드가 실행되었는지 체크해줌
        verify(productService).getProduct("12315");
    }

    @Test
    @DisplayName("Product 데이터 생성 테스트")
    void createProductTest() throws  Exception{
        given(productService.saveProduct("1212","pen", 4500, 5500)).willReturn(
                new ProductDTO("1212", "pen", 4500, 5500));

        ProductDTO productDTO = ProductDTO.builder().productId("1212").productName("pen").productPrice(4500).productStock(5500).build();
        Gson gson = new Gson();
        String content = gson.toJson(productDTO);

        // 위 gson은 아래와 같이 ObjectMapper를 통해 json을 만드는 것으로 대체가 가능하다.
        // String json = new ObjectMapper().writeValueAsString(productDTO);

        mockMvc.perform(
                post("/devdachan/product-api/product")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.productId").exists())
                .andExpect(jsonPath("$.productName").exists())
                .andExpect(jsonPath("$.productPrice").exists())
                .andExpect(jsonPath("$.productStock").exists())
                .andDo(print());
        verify(productService).saveProduct("1212", "pen", 4500, 5500);

    }
}
