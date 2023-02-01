# 16. Test Code (JUnit)

## TDD에 대한 간단한 정리

- 테스트 주도 개발이라는 의미를 가진다.
- 단순히 표현하면 테스트를 먼저 설계 및 구축 후 테스트를 통과할 수 있는 코드를 짜는 것
- 코드 작성 후 테스트를 진행하는 지금까지 사용된 일반적인 방식과 다소 차이가 있음
- 애자일 개발 방식
    - 코드 설계 시 원하는 단계적 목표에 대해 설정하여 진행하고자 하는 것에 대한 결정 방향의 갭을 줄이고자 함
    - 최초 목표에 맞춘 테스트를 구축하여 그에 맞게 코드를 설계하기 때문에 보다 적은 의견 충돌을 기대할 수 있음

## 테스트 코드 작성 목적

- 코드의 안정성을 높일 수 있음
- 기능을 추가하거나 변경하는 과정에서 발생할 수 있는 Side-effect를 줄일 수 있음
    
    (특정 코드를 수정할 때 내가 원하는 기능 이외의 기능에 영향을 끼치는 것을 줄이는 것)
    
- 해당 코드가 작성된 목적을 명확하게 표현할 수 있음
    - 코드에 불필요한 내용이 들어가는 것을 비교적 줄일 수 있다.

## JUnit이란?

- Java진영의 대표적인 Test Framework
- 단위 테스트(Unit Test)를 위한 도구를 제공
    - 코드의 특정 모듈이 의도된 대로 동작하는지 테스트하는 절차를 의미
    - 모든 함수와 메소드에 대한 각각의 테스트 케이스를 작성하는 것
- 어노테이션을 기반으로 테스트를 지원
- 단정문(Assert)으로 테스트 케이스의 기대값에 대해 수행 결과를 확인 할 수 있음

## Jnut 모듈 설명

- **JUnit Jupiter**
    - TestEngine API 구현체로 JUnit 5를 구현하고 있음
    - 테스트의 실제 구현체는 별도 모듈 역할을 수행하는데 그 모듈 중 하나가 Jupitor-Engine임
    - 이 모듈은 Jupitor-API를 사용하여 작성한 테스트 코드를 발견하고 실행하는 역할을 수행
- **JUnit Platform**
    - Test를 실행하기 위한 뼈대
    - Test를 발견하고 테스트 계획을 생성하는 TestEngine 인터페이스를 가지고 있음
    - TestEngine을 통해 Test를 발견하고, 수행 및 결과를 보고함
    - 그리고 각종 IDE 연동을 보조하는 역할을 수행한다.
- **JUnit Vintage**
    - TestEngine API 구현체로 JUnit 3,4를 구현하고 있음
    - 기존 JUnit 3,4버전으로 작성된 테스트코드를 실행할 때 사용된다.


<img src="https://user-images.githubusercontent.com/111109411/215076619-4794cf95-c5fa-453a-88d2-a994f74e7ab2.png" width=40%>


## JUnit LifeCycle Annotation

<img src="https://user-images.githubusercontent.com/111109411/215076300-928e6d36-4a22-438f-b817-9fecb6510256.png" width=80%>



## JUnit Main Annotation

- **@SpringBootTest**
    - 통합 테스트 용도로 사용됨
    - @SpringBootApplication을 찾아가 하위의 모든 Bean을 스캔하여 로드함
    - 그 후 Test용 Application Context를 만들어 Bean을 추가하고 MockBean을 찾아 교체
- **@ExtendWith**
    - JUnit4에서 @RunWith로 사용되던 어노테이션이 ExtendWith로 변경됨
    - @ExtendWith는 메인으로 실행될 Class를 지정할 수 있음
    - @SpringBootTest는 기본적으로 @ExtendWith가 추가되어 있다.
- **@WebMvcTest(Class명.class)**
    - ()안에 작성된 클래스만 실제로 로드하여 테스트 진행
    - 매개변수를 지정해주지 않으면 @Controller, @RestController, @RestControllerAdvice등 컨트롤러와 연관된 Bean이 모두 로드됨
    - 스프링의 모든 Beandmf fhemgksms @SpringBootTest 대신 컨트롤러 관련 코드만 테스트할 경우 사용
- **@Autowired about Mockbean**
    - Controller의 API를 테스트하는 용도인 MockMvc 객체를 주입 받음
    - perform() 메소드를 활용하여 컨트롤러의 동작을 확인할 수 있음
        - .andExpect(), andDo(), andReturn()등의 메소드를 같이 활용 함
- **@MockBean**
    - 테스트할 클래스에서 주입 받고 있는 객체에 대해 가짜 객체를 생성해주는 어노테이션
    - given() 메소드를 활용하여 가짜 객체의 동작에 대해 정의하여 사용 할 수 있음
- **@AutoConfugureMockMvc**
    - spring.test.mockmvc의 설정을 로드하면서 MockMvc의 의존성을 자동으로 주입
    - MockMvc클래스는 REST API 테스트를 할 수 있는 클래스
- **@Import**
    - 필요한 Class들을 Configuration으로 만들어 사용이 가능하다
    - Configuration Component 클래스도 의존성 설정 할 수 있음
    - Import된 클래스는 주입으로 사용 가능

## 통합 테스트

- 통합 테스트는 여러 기능을 조합하여 전체 비즈니스 로직이 제대로 동작하는지 확인하는 것을 의미
- 통합 테스트의 경우 @SpringBootTest를 사용하여 진행
    - @SpringBootTest는 @SpringBootApplication을 찾아가서 모든 Bean을 로드하게 됨
    - 이 방법을 대규모 프로젝트에서 사용할 경우 테스트를 실행할 때마다 모든 빈을 스캔하고 로드 하는 작업이 반복되어 매번 무거운 작업을 수행해야 한다.
    

## 단위 테스트

- 프로젝트에 필요한 모든 기능에 대한 테스트를 각각 진행하는 것을 의미
    - 일반적으로 스프링 부트에서는 org.springframework.boot: spring-boot-starter-test dependency만으로도 모든 의존성을 가질 수 있음
- FIRST 원칙
    - Fast : 테스트 코드의 실행은 빠르게 진행되어야 함
    - Independent: 독립적인 테스트가 가능해야 함
    - Repeatable: 테스트는 매번 같은 결과를 만들어야 함
    - Self-Validating: 테스트는 그 자체로 실행하여 결과를 확인 할 수 있어야 함
    - Timely: 단위 테스트는 비즈니스 코드가 완성되기 전에 구성하고 테스트가 가능해야 한다.
        - 코드가 완성되기 전부터 테스트가 따라와야 한다는 TDD의 원칙을 담고 있음

## Code

- Test를 위해서 controller를 만들 때에는 원래 있는 코드와 같은 경로에 controller를 만들어야 한다.

```jsx
<dependency>
			<groupId>com.google.code.gson</groupId>
			<artifactId>gson</artifactId>
			<version>2.8.5</version>
		</dependency>
```

- gson: 구글에서 제공하는 라이브러리로써 Json을 자유롭게 다룰 수 있게 해줌

### TestLifeCycle

```jsx
package com.example.demo.test;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.*;

public class TestLifeCycle {

    @BeforeAll
    static void beforeAll(){
        System.out.println("## BeforeAll Annotation 호풀 ##");
        System.out.println();
    }

    @AfterAll
    static void afterAll(){
        System.out.println("## BeforeAll Annotation 호풀 ##");
        System.out.println();
    }

    @AfterEach
    void afterEach(){
        System.out.println("## afterEach Annotation 호출 ##");
        System.out.println();
    }

    @Test
    void test1(){
        System.out.println("## Test 1 시작");
        System.out.println();
    }
    @Test
    @DisplayName("Test Case 2!! ")
    void test2(){
        System.out.println("@@ Test 2 시작");
        System.out.println();
    }

    @Test
    @Disabled // 테스트를 실행하지 않게 설정하는 어노테이션
    void test3(){
        System.out.println("Test 3 시작");
        System.out.println();
    }
}
```

- 단순히 Test가 되는지를 확인하기 위한 파일로써 해당 클래스를 실행 했을 경우 Disabled된 test3을 제외한 나머지 test가 실행된다.

<img src="https://user-images.githubusercontent.com/111109411/215076306-a4a0b68e-7d72-4d8b-9e0b-a62b66b38f35.png" width=80%>


### ProductControllerTest

```jsx
package com.example.demo.controller;

import ...

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
        
      
        given(productService.getProduct("12315")).willReturn(
                new ProductDTO("1212", "pen", 4500, 5500)
        );
        String productId = "12315";

       
        mockMvc.perform(
                get("/devdachan/product-api/product/" + productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").exists())
                .andExpect(jsonPath("$.productName").exists())
                .andExpect(jsonPath("$.productPrice").exists())
                .andExpect(jsonPath("$.productStock").exists())
                .andDo(print());
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
```

- 전체적인 코드는 말 그대로 Test를 해주는 클래스이다. 우리가 만들어 놓은 특정 method들에 우리가 원하는 값들을 HTTP로 직접 넣어서 test하는 것이 아니라 Mock을 생성해서 자동으로 test를 해주는 것
- Mock이란?
    - 실제 객체를 만들어 사용하기에 시간, 비용등의 Cost가 높거나 혹은 객체간 의존성이 강해 구현하기 힘들 경우 가짜 객체를 만들어 사용하는 방법이다.
    - 통합 테스트 : 상향식(Driver: 가상의 서버), 하향식(Stub: 가상의 클라이언트)
- **given**: Mock 객체가 특정 상황에서 해야 하는 행위를 정의하는 메소드
- **andExpect**: 기대하는 값이 나왔는지 체크하는 메소드
- **verify**: 해당 객체의 메소드가 실행되었는지 체크해줌

### ProductServiceImplTest

```jsx
package com.example.demo.service.Impl;

import ...

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
```

- **Mockito**: given 대신 사용하는 method로써 어떤 상황에 어떤 값을 return할지를 설정 하는 것
    - when: 내부 핸들러가 어떤 method를 어떤 파라미터로 사용했을 경우
    - thenReturn: 해당 값을 return해줘라
- 실제 getProduct에서 productEntity는 productDataHandler.getProductEntity(productId)를 통해서 선언되는 구문이 있다. 그래서 test의 작업이 자동으로 호출 되는 것이다.
- **Assertions**: 단정문으로써 값이 같은지를 확인 할 수 있다.
