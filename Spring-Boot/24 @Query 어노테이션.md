# 24. @Query 어노테이션

## 사용 배경

- 쿼리 메소드를 통해 쿼리를 생성하는 방식은 조건이 많아질 경우 메소드의 이름이 길어져 가독성이 떨어지게 된다.
- 비교적 복잡한 쿼리를 작성하기 위해 사용됨

## @Query 어노테이션

- Spring Data JPA에서 제공하는 기능으로 JPQL을 사용하여 쿼리를 작성하는 방법
- JPQL은 SQL과 문법이 거의 비슷함
- JPQL은 엔티티 객체를 대상으로 쿼리를 수행함

## @Query 문법 - 기본 쿼리 작성

- 직접 쿼리를 사용하는 방법
    
    ```jsx
    @Query(”SELECT p FROM Product p WHERE p.price > 2000”)
    List<Product> findByPriceBasis();
    ```
    
- DB의 Native Query를 사용하는 방법
    
    ```jsx
    @Query(value=”SELECT * FROM product p WHERE p.price > 2000”, nativeQuery = true)
    List<Product> fundByPriceBasisNativeQuery();
    ```
    

## @Query 문법 - 파라미터 주입

- 파라미터를 쿼리에 주입하는 방법
    
    ```jsx
    @Query(”SELECT p FROM Product p WHERE p.price > ?1”)
    List<Product> findByPriceWithParameter(Integer price);
    ```
    
    - 위에서 ? 뒤에 나오는 번호의 경우 몇 번째 파라미터를 받을 것 인지를 말함
- :parameter 방식으로 주입하는 방법
    
    ```jsx
    @Query("SELECT p FROM Product p WHERE p.price > :price")
    List<Product> findByPriceWithParameterNaming(Integer price);
    
    @Query("SELECT p FROM Product p WHERE p.price > :pn")
    List<Product> findByPriceWithParameterNaming2(@Param("pri") Integer price);
    ```
    

## 공식 레퍼런스

[https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.at-query](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.at-query)

## Code

- 파라미터와 :parameter의 이름이 같다면 그냥 사용하면 되고 만약 이름이 다를 경우에는 Object 생성 시 @Param을 추가해서 파라미터 이름을 선언해줘야 한다.
- nativeQuery는 말 그대로 SQL문을 그대로 사용하는 것을 말한다.

**ProductRepository**

```jsx
@Query("SELECT p FROM ProductEntity p WHERE p.productPrice > 2000")
List<ProductEntity> findByProductPriceBasis();

@Query(value = "SELECT * FROM product WHERE product_price > 2000", nativeQuery = true)
List<ProductEntity> findByProductPriceBasisNativeQuery();

@Query("SELECT p FROM ProductEntity p WHERE p.productPrice > ?1")
List<ProductEntity> findByProductPriceWithParameter(Integer price);

@Query("SELECT p FROM ProductEntity p WHERE p.productPrice > :price")
List<ProductEntity> findByProductPriceWithParameterNaming(Integer price);

@Query("SELECT p FROM ProductEntity p WHERE p.productPrice > :pri")
List<ProductEntity> findByProductPriceWithParameterNaming2(@Param("pri") Integer price);

@Query(value = "SELECT * FROM product WHERE product_price > :price",
    countQuery = "SELECT count(*) FROM product WHERE product_price > ?1",
    nativeQuery = true)
List<ProductEntity> findByProductPriceWithParameterPaging(Integer price, Pageable pageable);
```

- 쿼리문을 입력하는 것은 직접 객체를 이용해 쿼리를 작성 하는 것과 Native 쿼리를 작성하는 것 2가지로 나뉜다.
- 어쨌든 쿼리문을 만들어 어노테이션의 value로 추가해주고 method를 지정해줘야 한다.

**ProductRepositoryTest**

```jsx
@Test
  public void queryTest() {
    List<ProductEntity> foundAll = productRepository.findAll();
    System.out.println("====↓↓ Test Data ↓↓====");
    for (ProductEntity product : foundAll) {
      System.out.println(product.toString());
    }
    System.out.println("====↑↑ Test Data ↑↑====");

    List<ProductEntity> foundProducts = productRepository.findByProductPriceBasis();
    for (ProductEntity product : foundProducts) {
      System.out.println(product);
    }
  }

  @Test
  public void nativeQueryTest() {
    List<ProductEntity> foundAll = productRepository.findAll();
    System.out.println("====↓↓ Test Data ↓↓====");
    for (ProductEntity product : foundAll) {
      System.out.println(product.toString());
    }
    System.out.println("====↑↑ Test Data ↑↑====");

    List<ProductEntity> foundProducts = productRepository.findByProductPriceBasisNativeQuery();
    for (ProductEntity product : foundProducts) {
      System.out.println(product);
    }
  }

  @Test
  public void parameterQueryTest() {
    List<ProductEntity> foundAll = productRepository.findAll();
    System.out.println("====↓↓ Test Data ↓↓====");
    for (ProductEntity product : foundAll) {
      System.out.println(product.toString());
    }
    System.out.println("====↑↑ Test Data ↑↑====");

    List<ProductEntity> foundProducts = productRepository.findByProductPriceWithParameter(2000);
    for (ProductEntity product : foundProducts) {
      System.out.println(product);
    }
  }

  @Test
  public void parameterNamingQueryTest() {
    List<ProductEntity> foundAll = productRepository.findAll();
    System.out.println("====↓↓ Test Data ↓↓====");
    for (ProductEntity product : foundAll) {
      System.out.println(product.toString());
    }
    System.out.println("====↑↑ Test Data ↑↑====");

    List<ProductEntity> foundProducts = productRepository.findByProductPriceWithParameterNaming(2000);
    for (ProductEntity product : foundProducts) {
      System.out.println(product);
    }
  }

  @Test
  public void parameterNamingQueryTest2() {
    List<ProductEntity> foundAll = productRepository.findAll();
    System.out.println("====↓↓ Test Data ↓↓====");
    for (ProductEntity product : foundAll) {
      System.out.println(product.toString());
    }
    System.out.println("====↑↑ Test Data ↑↑====");

    List<ProductEntity> foundProducts = productRepository.findByProductPriceWithParameterNaming2(2000);
    for (ProductEntity product : foundProducts) {
      System.out.println(product);
    }
  }

  @Test
  public void nativeQueryPagingTest() {
    List<ProductEntity> foundAll = productRepository.findAll();
    System.out.println("====↓↓ Test Data ↓↓====");
    for (ProductEntity product : foundAll) {
      System.out.println(product.toString());
    }
    System.out.println("====↑↑ Test Data ↑↑====");

    List<ProductEntity> foundProducts = productRepository.findByProductPriceWithParameterPaging(2000,
        PageRequest.of(0, 2));
    for (ProductEntity product : foundProducts) {
      System.out.println(product);
    }
  }'
```

**Test Data**

<img src="https://user-images.githubusercontent.com/111109411/215967618-b065707e-0d2e-44c4-bbb5-60da46ecf54d.png" width=100%>


**nativeQueryTest**


<img src="https://user-images.githubusercontent.com/111109411/215967672-7eaee50d-a746-4b8d-bbe3-d7b0d7010534.png" width=100%>


**parameterQueryTest**


<img src="https://user-images.githubusercontent.com/111109411/215967697-b57295e8-c834-4758-b294-47b80768c1a7.png" width=100%>


- nativeQuery와 parameterQuery를 Hibernate가 만들 때 만들어지는 쿼리문을 보면 훨씬 간단하게 만들어 진 것을 볼 수 있다.
- 이러한 방식이 Secure coding의 한 부분이 될 수도 있다.
