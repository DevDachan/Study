# 21. @MappedSuperclass, JPA Audit

엔티티 = DB Table과 1:1 매칭되는 객체로써 하나의 Row라고 생각하면 된다. 

## @MappedSuperclass

- 여러 엔티티 객체에서 사용되는 공통 속성이 존재할 경우가 많다.
- 공통적으로 사용되는 속성은 대표적으로 id, createdAt, updatedAt등이 있음
- 공통되는 속성을 별도의 클래스로 구분하여 @MappedSuperclass를 선언한 후 사용
- 코드 상 분리되어 있는 것이며 DB의 테이블 개념에서는 분리되어 있지 않음

**Mapping 이전**


<img src="https://user-images.githubusercontent.com/111109411/215454469-ea76b573-dfe1-4b3c-a452-6f0a8e3be3e5.png" width=60%>


**Mapping 이후**

<img src="https://user-images.githubusercontent.com/111109411/215454475-ba25d8eb-66ce-4bd3-a98f-077ae1f5e9d9.png" width=60%>


## @Auditing

- JPA에서의 Auditing은 각 엔티티 별로 누가, 언제 접근했는지 기록하여 감시 체계를 꾸리는 것을 의미 (history 추적을 위한 data)
- Spring Data JPA에서 이 기능을 사용하기 위해서는 @EnavleJpaAuditing을 사용해야 함

## @EntityListener

- 엔티티 객체를 데이터베이스에 적용하기 전/후에 콜백을 요청하는 어노테이션
- @EntityListener의 파라미터로 콜백을 요청할 클래스를 지정하여 사용
- @EntityListener의 요청 시점은 아래와 같다
    - @PostLoad
    - @PrePersist
    - @PostPersist
    - @PreUpdate
    - @PostUpdate
    - @PreRemove
    - @PostRemove

## JPA Auditing Annotation

- @CreatedDate: 엔티티가 저장되는 시점에 자동으로 시간을 주입
- @CreatedBy: 엔티티가 저장되는 시점에 저장 주체가 누구인지 주입
- @LastModifiedDate: 엔티티가 수정되는 시점에 자동으로 시간을 주입
- @LastModifiedBy: 엔티티가 수정되는 시점에 수정 주체가 누구인지 주입

## Code

### CustomListener

```jsx
package com.example.demo.data.entity.listener;

import ...

public class CustomListener {

  private final Logger LOGGER = LoggerFactory.getLogger(CustomListener.class);

  @PostLoad
  public void postLoad(ListenerEntity entity) {
    LOGGER.info("[postLoad] called!!");
  }

  @PrePersist
  public void prePersist(ListenerEntity entity) {
    LOGGER.info("[prePersist] called!!");
  }

  @PostPersist
  public void postPersist(ListenerEntity entity) {
    LOGGER.info("[postPersist] called!!");
  }

  @PreUpdate
  public void preUpdate(ListenerEntity entity) {
    LOGGER.info("[preUpdate] called!!");
  }

  @PostUpdate
  public void postUpdate(ListenerEntity entity) {
    LOGGER.info("[postUpdate] called!!");
  }

  @PreRemove
  public void preRemove(ListenerEntity entity) {
    LOGGER.info("[preRemove] called!!");
  }

  @PostRemove
  public void postRemove(ListenerEntity entity) {
    LOGGER.info("[postRemove] called!!");
  }
}
```

- Listener는 일단 단순하게 log를 찍어주는 역할만 하도록 한다.

### ListenerEntity

```jsx
package com.example.demo.data.entity;

import ...

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "listener")
@EntityListeners(CustomListener.class)
public class ListenerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

}
```

ListenerServiceImpl

```jsx
package com.example.demo.data.service.Impl;

import c...

@Service
public class ListenerServiceImpl implements ListenerService {

  private ListenerRepository listenerRepository;

  @Autowired
  public ListenerServiceImpl(ListenerRepository listenerRepository) {
    this.listenerRepository = listenerRepository;
  }

  @Override
  public ListenerEntity getEntity(Long id) {
    return listenerRepository.findById(id).get();
  }

  @Override
  public void saveEntity(ListenerEntity listener) {
    listenerRepository.save(listener);
  }

  @Override
  public void updateEntity(ListenerEntity listener) {
    ListenerEntity foundListener = listenerRepository.findById(listener.getId()).get();
    foundListener.setName(listener.getName());

    listenerRepository.save(foundListener);
  }

  @Override
  public void removeEntity(ListenerEntity listener) {
    listenerRepository.delete(listener);
  }
}
```

- Service측에서는 CRUD기능을 담당하고 Conroller측에서 넘어온 값을 Repository로 넘겨주는 작업을 한다.

### ListenerConroller

```jsx
package com.example.demo.controller;

import ...

@RestController
@RequestMapping("/listener")
public class ListenerController {

  private ListenerService listenerService;

  @Autowired
  public ListenerController(ListenerService listenerService){
    this.listenerService = listenerService;
  }

  @GetMapping
  public String getListener(Long id){
    listenerService.getEntity(id);

    return "OK";
  }

  @PostMapping
  public void saveListener(String name){
    ListenerEntity listener = new ListenerEntity();
    listener.setName(name);

    listenerService.saveEntity(listener);
  }

  @PutMapping
  public void updateListener(Long id, String name){
    ListenerEntity listener = new ListenerEntity();
    listener.setId(id);
    listener.setName(name);

    listenerService.updateEntity(listener);
  }

  @DeleteMapping
  public void deleteListener(Long id){
    ListenerEntity listener = listenerService.getEntity(id);

    listenerService.removeEntity(listener);
  }

}
```

- ListenerController측에서는 Client측에서 받은 값을 Service단에 Entity형식으로 넘겨준다.

### 실행 결과

**saveListener**

<img src="https://user-images.githubusercontent.com/111109411/215454593-b9521009-4bbc-44a6-a182-cf004bc2ee0b.png" width=80%>


<img src="https://user-images.githubusercontent.com/111109411/215454600-671569c0-0356-43e3-806c-c7d8d8a509f7.png" width=100%>


- Log를 확인해보면 보이는 것과 같이 쿼리문이 실행되는 상황에서 앞 뒤로 listener가 동작한 것을 볼 수 있다.

**getListener**


<img src="https://user-images.githubusercontent.com/111109411/215454696-723b104f-0538-4898-8a34-a9ee17139a93.png" width=80%>


<img src="https://user-images.githubusercontent.com/111109411/215454787-8ccbc704-ea25-4afa-8890-c3e12f3f1cbd.png" width=100%>

**updateListener**


<img src="https://user-images.githubusercontent.com/111109411/215454835-7ee426a4-0145-410f-9e2b-bf11baa0c5c2.png" width=80%>

<img src="https://user-images.githubusercontent.com/111109411/215454885-b910d58e-c20f-4c07-bf63-a424aca9de37.png" width=100%>


- 이러한 Listener를 사용하게 되면 특정 쿼리 메소드을 실행하는 상황에서 앞 뒤로 listener를 사용해 처리가 가능하기 때문에 코드 생산성이 좋아진다.

### @MappedSuperclass

**BaseEntity**

```jsx
package com.example.demo.data.entity;

import .

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

  //@CreatedDate와 @UpdatedDate는 자동으로 값을 생성해준다.
  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdAt;

  /*
  @CreatedBy
  @Column(updatable = false)
  private String createdBy;
  */

  @LastModifiedDate
  private LocalDateTime updatedAt;

  /*
  @LastModifiedBy
  private String updatedBy;
  */

}
```

**ListenerEntity**

```jsx
public class ListenerEntity extends BaseEntity{
```

- 이렇게 BaseEntity를 extends해서 상속 받아 그 안에 있는 Auditing을 사용한다.

### Configuration

- Bean을 등록할 때 싱글톤이 되도록 보장
- 한마디로 프로젝트에 Bean을 수동으로 등록하는 동작을 말한다.

**1. JpaAuditingConfiguration**

```jsx
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration {

}
```

**2. DemoApllication**

```jsx
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
```

- 두 가지 방법 다 올바르게 동작하지만 configuration 파일을 직접 추가해서 사용하는 것을 권장한다. → 테스트 할 때 DemoApplication에 추가를 하게 되면 실행이 되지 않을 수 있다.
