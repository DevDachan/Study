# 11. ORM, JPA, Spring Data JPA

### ORM(Object Relational Mapping)이란?

- 애플리케이션의 객체와 관계형 DB의 데이터를 자동으로 매핑해주는 것을 의미
    - Java의 데이터 클래스와 DB의 테이블을 매핑
- 객체지향 프로그래밍과 관계형 DB의 차이로 발생하는 제약사항을 해결해주는 역할을 수행
- 대표적으로 JPA, Hibernate등이 있음 (Persistent API)

![Untitled](11%20ORM,%20JPA,%20Spring%20Data%20JPA%207ac48a456e5e480798c20fe421d44295/Untitled.png)

### ORM의 장점

- SQL 쿼리가 아닌 직관적인 코드로 데이터를 조작할 수 있음
    - 개발자가 보다 비즈니스 로직에 집중할 수 있음
- 재사용 및 유지보수 편리
    - ORM은 독립적으로 작성되어 있어 재사용이 가능
    - 매핑정보를 명확하게 설계하기 때문에 따로 DB를 볼 필요가 없음
- DBMS에 대한 종속성이 줄어듬
    - DBMS를 교체하는 작업을 비교적 적은 리스크로 수행 가능

### ORM의 단점

- 복잡성이 커질 경우 ORM만으로 구현하기 어려움
    - 직접 쿼리를 구현하지 않아 복잡한 설계가 어려움
- 잘못 구현할 경우 속도 저하 발생
    - 코드를 작성한 것을 쿼리로 변환하기 때문에 코드 작성이 중요함
- 대형 쿼리는 별도의 튜닝이 필요할 수 있음

### JPA(Java Persistance API)란?

- ORM과 관련된 인터페이스의 모음
- Java 진영에서 표준 ORM으로 채택되어 있음
- ORM이 큰 개념이라고 하면 JPA는 더 구체화 시킨 스펙을 포함하고 있다.

### Hibernate

- ORM Framework 중 하나
- JPA의 실제 구현체 중 하나이며 현재 JPA 구현체 중 가장 많이 사용됨

![Untitled](11%20ORM,%20JPA,%20Spring%20Data%20JPA%207ac48a456e5e480798c20fe421d44295/Untitled%201.png)

### Spring Data JPA

- Spring Framework에서 JPA를 편리하게 사용할 수 있게 지원하는 라이브러리
    - CRUD 처리용 인터페이스 제공
    - Repository 개발 시 인터페이스만 작성하면 구현 객체를 동적으로 생성해서 주입해준다.
    - 데이터 접근 계층 개발시 인터페이스만 작성해도 됨
- Hibernate에서 자주 사용되는 기능을 조금 더 쉽게 사용할 수 있게 구현

![Untitled](11%20ORM,%20JPA,%20Spring%20Data%20JPA%207ac48a456e5e480798c20fe421d44295/Untitled%202.png)

## Code

**application.propertices**

```jsx
## JPA 설정
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
```

- ddl-auto: 애플리케이션을 실행할 때 DB를 자동으로 생성. (만약 존재할 경우 drop후 생성)
- show-sql:  요청을 받았을 때 repository가 동작하는데 그때 관련된 SQL을 표시할 것 인지를 말함. (test할 때만 true로 하고 실무에서는 false)

### Controller

- Controller는 client와 직접적으로 소통하는 부분으로 client에게 받은 data를 DTO 형태로 Service측에 넘기는 역할을 한다.

**ProductContoller**

```jsx
package com.example.demo.controller;

import com.example.demo.data.dto.ProductDTO;
import com.example.demo.data.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devdachan/product-api")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/{productId}")
    public ProductDTO getProduct(@PathVariable String productId) {
        return productService.getProduct(productId);
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
```

## Service

- Controller에게 DTO 형식으로 받은 데이터를 이용해 비즈니스 로직을 처리하고 DAO에게 Entity를 보내는 역할을 한다.
- 이때 DAO에게 Entity를 보내는 과정에서 DataHandler를 사용하게 된다.

**ProductService**

```jsx
package com.example.demo.data.service;

import com.example.demo.data.dto.ProductDTO;

public interface ProductService {
    ProductDTO saveProduct(String productId, String productName, int productPrice, int productStock);

    ProductDTO getProduct(String productId);
}
```

**ProductServiceImpl**

```jsx
package com.example.demo.data.service.Impl;

import com.example.demo.data.dto.ProductDTO;
import com.example.demo.data.entity.ProductEntity;
import com.example.demo.data.handler.ProductDataHandler;
import com.example.demo.data.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
```

### H**andler**

- Handler는 DAO와 Service를 연결해주는 역할을 하고 입력 받은 data를 entity형식으로 만들어서 DAO에게 전달해준다.

**ProductDataHandler**

```jsx
package com.example.demo.data.handler;

import com.example.demo.data.entity.ProductEntity;

public interface ProductDataHandler {
    ProductEntity saveProductEntity(String productId, String productName, int productPrice, int productStock);

    ProductEntity getProductEntity(String productId);

}
```

**ProductDataHandlerImpl**

```jsx
package com.example.demo.data.handler.Impl;

import com.example.demo.data.dao.ProductDAO;
import com.example.demo.data.entity.ProductEntity;
import com.example.demo.data.handler.ProductDataHandler;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductDataHandlerImpl implements ProductDataHandler {
    ProductDAO productDAO;

    @Autowired
    public ProductDataHandlerImpl(ProductDAO productDAO){
        this.productDAO = productDAO;
    }

    @Override
    public ProductEntity saveProductEntity(String productId, String productName, int productPrice, int productStock){
        ProductEntity productEntity = new ProductEntity(productId, productName, productPrice, productStock);
        return productDAO.saveProduct(productEntity);
    }

    @Override
    public ProductEntity getProductEntity(String productId){
        return productDAO.getProduct(productId);
    }
}
```

(이전 내용)

### DAO

- DAO는 최종적으로 Service 측(Handler)에게 받은 Entity data를 Repository를 사용해서 DB에 저장하거나 조회하는 역할을 한다.

**DAO**

```jsx
package com.example.demo.data.dao;

import com.example.demo.data.entity.ProductEntity;
public interface ProductDAO {
    ProductEntity saveProduct(ProductEntity productEntity);
    ProductEntity getProduct (String productId);
}
```

**DAOImpl**

```jsx
package com.example.demo.data.dao.Impl;

import com.example.demo.data.dao.ProductDAO;
import com.example.demo.data.entity.ProductEntity;
import com.example.demo.data.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDAOImpl  implements ProductDAO {
    ProductRepository productRepository;

    @Autowired
    public ProductDAOImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public ProductEntity saveProduct(ProductEntity productEntity){
        productRepository.save(productEntity);
        return productEntity;
    }

    @Override
    public ProductEntity getProduct(String productId){
        ProductEntity productEntity = productRepository.getById(productId);
        return productEntity;
    }
}
```

### Repository

```jsx
package com.example.demo.data.repository;

import com.example.demo.data.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {
// 실제로 ProductRepository에서는 정의되는 method가 없지만 jpa에서 상속받게 되면 기본적으로 사용 가능한
// method가 있다.
}
```