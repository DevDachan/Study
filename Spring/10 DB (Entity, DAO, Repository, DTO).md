# 10. DB (Entity, DAO, Repository, DTO)

### Spring Boot 서비스 구조

![Untitled](10%20DB%20(Entity,%20DAO,%20Repository,%20DTO)%209ec24b4a09974c43816f156c50bdf5c5/Untitled.png)

### Entity (Domain)

- DB에 쓰일 컬럼과 여러 엔티티간의 연관관계를 정의
- DB 테이블을 하나의 엔티티로 생각해도 되며 실제 DB의 테이블과 1:1 Mapping된다.
- 이 클래스의 필드는 각 테이블 내부의 컬럼을 의미한다.

### Repository

- Entity에 의해 생성된 DB에 접근하는 method를 사용하기 위한 인터페이스
- Service와 DB를 연결하는 고리의 역할을 수행
- DB에 적용하고자 하는 CRUD를 정의하는 영역

### DAO (Data Access Object)

- DB에 접근하는 객체를 의미
- Service가 DB에 연결할 수 있게 해주는 역할
- DB를 사용하여 데이터를 조회하거나 조작하는 기능을 전담
- DAO에서 Repository를 사용해서 코드를 작성하게 된다.

### DTO (Data Transfer Object)

- DTO는 VO(Value Object)로 불리기도 하며, 계층 간 데이터 교환을 위한 객체를 의미
- VO의 경우 REad Only의 개념을 가지고 있음
- DTO는 Entity와는 다르게 반드시 DB 테이블의 컬럼과 같을 필요가 없다.

## **Code**

### **DB 기본 설정**

**pom.xml**

```jsx
<!-- mariaDb -->
		<dependency>
			<groupId>org.mariadb.jdbc</groupId>
			<artifactId>mariadb-java-client</artifactId>
		</dependency>
```

**application.properties**

```jsx
## Maria DB
spring.datasource.driverClassName=org.mariadb.jdbc.Driver
spring.datasource.url=jdbc:mariadb://localhost:3307/spring_example
spring.datasource.username=root
spring.datasource.password=7894
```

### **DTO DAO Repository 사용 code**

```jsx
pom.xml

<dependency>
	<groupId>jakarta.persistence</groupId>
	<artifactId>jakarta.persistence-api</artifactId>
	<version>3.1.0</version>
</dependency>
```

- 기본적인 어노테이션 사용을 위해 jakarta.persistence 라이브러리를 dependency에 추가해줘야 한다.
- interface는 구체화된 코드 없이 단순히 어떤 method를 가지고 있는지를 선언만 해놓은 클래스이다.
- 해당 클래스를 상속 받아서 선언해 사용하게 된다.

**ProductDTO**

```jsx
package com.example.demo.dto;

import com.example.demo.entity.ProductEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductDTO {
    private String productId;
    private String productName;
    private int productPrice;
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
```

- product DTO의 경우에는 전체적으로 사용되는 DB에 저장되는 정보 필드를 가지고 있다.

**Entity**

```jsx
package com.example.demo.data.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product")
public class ProductEntity {
    @Id
    String productId;
    String productName;
    Integer productPrice;
    Integer productStocks;
}
```

- DB에서 Primary Key가 자바에서는 @Id에 해당하는 값이 된다. 전체적으로 데이터를 특정할 수 있는 값
- @Table은 해당 Entity값을 통해서 DB에 테이블을 생성해주게 될텐데 그때 생성될 테이블의 이름을 지정하는 것이다.

**Repository**

```jsx
package com.example.demo.data.repository;

import com.example.demo.data.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {
// 실제로 ProductRepository에서는 정의되는 method가 없지만 jpa에서 상속받게 되면 
// 기본적으로 사용 가능한 method가 있다.
}
```

- Repository는 interface로 생성되어야 한다.
- 파라미터의 경우 해당 DB에서 사용하게 될 Entity 클래스와 해당 클래스의 Id(PK)의 자료형이 mapping되어야 한다.

**DAO**

```jsx
package com.example.demo.data.dao;

import com.example.demo.data.entity.ProductEntity;
public interface ProductDAO {
    ProductEntity saveProduct(ProductEntity productEntity);
    ProductEntity getProduct (String productId);
}
```

- DAO의 경우는 DB에서 직접 우리가 사용하게 될 method를 선언해주는 작업을 한다.
- 실제로 선언된 method의 정의는 DAOImpl에서 정의하게 된다.

**DAOImpl**

```jsx
package com.example.demo.data.Impl;

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

- 기본적으로 해당 class의 constructor를 생성할 때 @Autowired를 사용해야 한다. 이때 Autowired는 자동으로 싱글톤 구조에 맞게 미리 생성되어 있는 Repository를 끌어와서 사용하게 된다. 이때 Repository가 Bean이 되는 것
- DAOImpl은 DAO가 interface이므로 해당 DAO를 상속 받아 선언되어 있는 method의 정의를 해준다.