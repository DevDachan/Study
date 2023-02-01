# 20. 단축 URL

### 네이버 애플리케이션 등록 (API 신청)

[애플리케이션 - NAVER Developers](https://developers.naver.com/apps/#/register)

<img src="https://user-images.githubusercontent.com/111109411/215320436-37a8ef16-8c1b-4bfe-8682-922f52ef5bdb.png" width=60%>



- 개발을 하면서 내가 만드는 이 프로그램이 어떤 생명주기를 가지고 있나를 정하는 것이 중요하다.

### 단축 URL 가이드

[단축 URL API 적용 가이드](https://developers.naver.com/docs/utils/shortenurl/)

## 설계

### 전체 프로젝트 구조

<img src="https://user-images.githubusercontent.com/111109411/215320446-1394660c-0d73-43f7-b3dc-11e33b682924.png" width=60%>



- Naver API가 존재하고 기본 Project와 연결이 된다. Client에 해당하는 User와도 HTTP 통신을 하며 DB와는 CRUD를 위한 통신을 하게 된다.

### 코드 구조

<img src="https://user-images.githubusercontent.com/111109411/215320457-e667459c-77bb-4db0-8a5e-72296c171616.png" width=60%>



- Controller
- Service-Impl
- Handeler: DB자체(mariaDB)가 바뀐 것이 없기 때문에 기존 Handler 그대로 사용
- DAO-Impl
- Repository

### Controller 설계

<img src="https://user-images.githubusercontent.com/111109411/215320462-9a8c7812-acf0-4401-91fb-43aab049a4f0.png" width=60%>


### Service 설계

<img src="https://user-images.githubusercontent.com/111109411/215320472-f3fe0918-1599-41a3-bcce-3f252e9c7169.png" width=60%>


### DAO 설계


<img src="https://user-images.githubusercontent.com/111109411/215320482-3ebb2532-3528-48e5-9b28-29a860e05e89.png" width=60%>

### Entity 설계

<img src="https://user-images.githubusercontent.com/111109411/215320520-71d6b1e7-dc0d-4cbe-b9d1-1f1fc168c173.png" width=60%>


- 생성 일을 자동으로 생성해주는 JPA
- DB를 사용하면서 만약 잘못됐을 경우 해당 내용을 롤백할 때 어떤 시점으로 돌아갈지, 누가 잘못 했는지를 확인하게 해주기 때문에 이러한 생성일, 수정 일을 기본적으로 추가 한다.

[JPA Auditing으로 생성일/수정일 자동화하기](https://velog.io/@conatuseus/2019-12-06-2212-%EC%9E%91%EC%84%B1%EB%90%A8-1sk3u75zo9)


## Code

### ShortUrlController

```jsx
package com.example.demo.controller;

import ...

@RestController
@RequestMapping("short-url")
//http://localhost:8080/short-url
public class ShortUrlController {
  private final Logger LOGGER = LoggerFactory.getLogger(ShortUrlController.class);

  //이런 식으로 Value라는 어노테이션을 사용하면 application.propertices에서 선언된 변수 값을
  //불러와 String 객체에 넣어줄 수 있다.
  @Value("${demo.short.url.id}")
  private String CLIENT_ID;

  @Value("${demo.short.url.secret}")
  private String CLIENT_SECRET;

  ShortUrlService shortUrlService;

  @Autowired
  public ShortUrlController(ShortUrlService shortUrlService){
    this.shortUrlService = shortUrlService;
  }

  @PostMapping()
  public ShortUrlResponseDTO generateShortUrl(@RequestBody String originalUrl){
    LOGGER.info("[generateShortUrl] perform API CLIENT_ID : {}, CLIENT_SECRET : {}, originalUrl: {}", CLIENT_ID, CLIENT_SECRET,originalUrl);

    return shortUrlService.generateShortUrl(CLIENT_ID,CLIENT_SECRET,originalUrl);
  }

  @GetMapping()
  public ShortUrlResponseDTO getShortUrl( String originalUrl){
    LOGGER.info("[generateShortUrl] perform API CLIENT_ID : {}, CLIENT_SECRET : {}, originalUrl: {}", CLIENT_ID, CLIENT_SECRET,originalUrl);

    ShortUrlResponseDTO shortUrlResponseDTO = new ShortUrlResponseDTO("ss", "ss");
    return shortUrlService.getShortUrl(CLIENT_ID, CLIENT_SECRET, originalUrl);
  }

  @PutMapping
  public ShortUrlResponseDTO updateShortUrl(@RequestBody String originalUrl){ return null; }

  @DeleteMapping
  public String deleteShortUrl(@RequestBody String url){
    //Conrtoller는 단순히 요청된 정보를 service측에 전달만 하는 것

    try{
      shortUrlService.deleteShortUrl(url);
    }catch(RuntimeException e){
      e.printStackTrace();
    }
    return "정상 삭제 됐습니다.";
  }

}
```

- **generateShortUrl**: 만약 shortUrl이 DB에 존재하지 않을 경우에는 생성을 해줘야 한다. generate할 때는 정보를 받아야 하기 때문에 PostMapping을 해주고 service측에 generate 호출해야 한다.
    - PostMapping이기 때문에 @RequestBody를 통해서 값을 받고 위 메소드에서는 단순히 origianl url값만 받으면 되기 때문에 body측에 json형태로 작업할 필요 없이 그냥 바로 입력 받으면 됨 (왜 POST에서는 안 읽어질까?)
- **getShortUrl**: DB에 존재하는 데이터를 불러와 original url과 연결된 객체를 받아오는 역할을 하는 메소드이다.
    - GetMapping이기 때문에 @PathVariable을 사용해야 하지만 위와 같이 같은 이름의 변수를 사용할 경우에는 따로 어노테이션 없이 사용해도 무관하다.

### ShortUrlEntity

```jsx
package com.example.demo.data.entity;

import ...

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "short_url")
public class ShortUrlEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  // GenerationType.IDENTITY: 알아서 테이블을 읽어서 인덱스 번호를 가져옴
  private Long id;

  @Column(nullable = false, unique = true)
  private String hash;

  @Column(nullable = false, unique = true)
  private String url;

  @Column(nullable = false, unique = true)
  private String orgUrl;

}
```

### ShortUrlRepository

```jsx
package com.example.demo.data.repository;

import com.example.demo.data.entity.ShortUrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortUrlRepository extends JpaRepository<ShortUrlEntity, Long> {
  ShortUrlEntity findByUrl(String url);

  ShortUrlEntity findByOrgUrl(String originalUrl);
}
```

- 이렇게 naming을 통해서 findBy를 앞에 붙여줌으로써  원하는 쿼리 메소드를 만드는 것이 가능해진다.
- 따로 구현체를 만들지 않아도 Jpa측에서 알아서 메소드를 만들어준다. 해당 메소드를 통해서 DB와 연결되어 data를 관리(CRUD)한다.

### ShortUrlService

```jsx
package com.example.demo.data.service;

import com.example.demo.data.dto.ShortUrlResponseDTO;

public interface ShortUrlService {
  ShortUrlResponseDTO getShortUrl(String clientId, String clientSecret, String originalUrl);

  ShortUrlResponseDTO generateShortUrl(String clientId, String clientSecret, String originalUrl);

  ShortUrlResponseDTO updateShortUrl(String clientId, String clientSecret, String originalUrl);

  void deleteByShortUrl(String shortUrl);

  void deleteByOriginalUrl(String originalUrl);

}
```

### ShortUrlServiceImpl

```jsx
package com.example.demo.data.service.Impl;

import ...

@Service
public class ShortUrlServiceImpl implements ShortUrlService {

  private final Logger LOGGER = LoggerFactory.getLogger(ShortUrlServiceImpl.class);

  private final ShortUrlDAO shortUrlDAO;

  @Autowired
  public ShortUrlServiceImpl(ShortUrlDAO shortUrlDAO) {
    this.shortUrlDAO = shortUrlDAO;
  }

  @Override
  public ShortUrlResponseDTO getShortUrl(String clientId, String clientSecret,
      String originalUrl) {
    LOGGER.info("[getShortUrl] request data : {}", originalUrl);
    ShortUrlEntity getShortUrlEntity = shortUrlDAO.getShortUrl(originalUrl);

    String orgUrl;
    String shortUrl;

    if (getShortUrlEntity == null) {
      LOGGER.info("[getShortUrl] No Entity in Database.");
      ResponseEntity<NaverUriDTO> responseEntity = requestShortUrl(clientId, clientSecret,
          originalUrl);

      orgUrl = responseEntity.getBody().getResult().getOrgUrl();
      shortUrl = responseEntity.getBody().getResult().getUrl();
      String hash = responseEntity.getBody().getResult().getHash();

    } else {
      orgUrl = getShortUrlEntity.getOrgUrl();
      shortUrl = getShortUrlEntity.getUrl();
    }

    ShortUrlResponseDTO shortUrlResponseDto = new ShortUrlResponseDTO(orgUrl, shortUrl);

    LOGGER.info("[getShortUrl] Response DTO : {}", shortUrlResponseDto);
    return shortUrlResponseDto;
  }

  @Override
  public ShortUrlResponseDTO generateShortUrl(String clientId, String clientSecret,
      String originalUrl) {

    LOGGER.info("[generateShortUrl] request data : {}", originalUrl);

    ResponseEntity<NaverUriDTO> responseEntity = requestShortUrl(clientId, clientSecret,
        originalUrl);

    String orgUrl = responseEntity.getBody().getResult().getOrgUrl();
    String shortUrl = responseEntity.getBody().getResult().getUrl();
    String hash = responseEntity.getBody().getResult().getHash();

    ShortUrlEntity shortUrlEntity = new ShortUrlEntity();
    shortUrlEntity.setOrgUrl(orgUrl);
    shortUrlEntity.setUrl(shortUrl);
    shortUrlEntity.setHash(hash);
    //BaseEntity에 있는 createdAt과 UpdatedAt이 자동으로 생성이 된다.

    shortUrlDAO.saveShortUrl(shortUrlEntity);

    ShortUrlResponseDTO shortUrlResponseDto = new ShortUrlResponseDTO(orgUrl, shortUrl);

    LOGGER.info("[generateShortUrl] Response DTO : {}", shortUrlResponseDto);
    return shortUrlResponseDto;
  }

  @Override
  public ShortUrlResponseDTO updateShortUrl(String clientId, String clientSecret,
      String originalUrl) {
    return null;
  }

  @Override
  public void deleteShortUrl(String url) {
    if(url.contains("me2.do")){
      LOGGER.info("[deleteShortUrl] Request Url is 'ShortUrl'");
      deleteByShortUrl(url);
    }else{
      LOGGER.info("[deleteShortUrl] Request Url is 'OriginalUrl'");
      deleteByOriginalUrl(url);
    }
  }
  @Override
  public void deleteByShortUrl(String url) {
    LOGGER.info("[deleteByShortUrl] delete record");
    shortUrlDAO.deleteByShortUrl(url);
  }

  @Override
  public  void deleteByOriginalUrl(String url) {
    LOGGER.info("[deleteByOriginalUrl] delete record");
    shortUrlDAO.deleteByOriginalUrl(url);
  }

  private ResponseEntity<NaverUriDTO> requestShortUrl(String clientId, String clientSecret,
      String originalUrl) {
    LOGGER.info("[requestShortUrl] client ID : ***, client Secret : ***, original URL : {}", originalUrl);

    URI uri = UriComponentsBuilder
        .fromUriString("https://openapi.naver.com")
        .path("/v1/util/shorturl")
        .queryParam("url", originalUrl)
        .encode()
        .build()
        .toUri();

    LOGGER.info("[requestShortUrl] set HTTP Request Header");
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON}));
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("X-Naver-Client-Id", clientId);
    headers.set("X-Naver-Client-Secret", clientSecret);

    HttpEntity<String> entity = new HttpEntity<>("", headers);

    RestTemplate restTemplate = new RestTemplate();

    LOGGER.info("[requestShortUrl] request by restTemplate");
    ResponseEntity<NaverUriDTO> responseEntity = restTemplate.exchange(uri, HttpMethod.GET,
        entity, NaverUriDTO.class);

    LOGGER.info("[requestShortUrl] request has been successfully complete.");

    return responseEntity;
  }

}
```

- Service측에서는 직접적인 기능을 구현하고 있는데 DB와 연결해서 DB에 있는 내용을 client측에 전달해주거나 Naver API측에 단축 URL을 전달해 주소를 받는 역할을 한다.
- **generateShortUrl**: client에서 들어온 요청을 requestShortUrl과 연결해주며 requestShortUrl에서 네이버 측에서 보낸 Entity값을 DB에 저장하는 역할까지 담당한다.
- **requestShortUrl**: Naver API와 직접적으로 연결을 해주는 역할을 담당한다. 이전에 배웠던 RestTemplate을 사용해서 입력 값을 다른 path로 연결해 data를 전송한다.
- **나머지 get, delete, update:** 단순한 CRUD기능을 담당

### ShortUrlDAOImpl

```jsx
package com.example.demo.data.dao.Impl;

import ...

@Component
public class ShortUrlDAOImpl implements ShortUrlDAO {
  private final ShortUrlRepository shortUrlRepository;

  @Autowired
  public ShortUrlDAOImpl(ShortUrlRepository shortUrlRepository){
    this.shortUrlRepository = shortUrlRepository;
  }

  @Override
  public ShortUrlEntity saveShortUrl(ShortUrlEntity shortUrlEntity) {
    ShortUrlEntity foundShortUrlEntity = shortUrlRepository.save(shortUrlEntity);
    return foundShortUrlEntity;
  }

  @Override
  public ShortUrlEntity getShortUrl(String originalUrl) {
    ShortUrlEntity foundShortUrlEntity = shortUrlRepository.findByOrgUrl(originalUrl);
    return foundShortUrlEntity;
  }

  @Override
  public ShortUrlEntity getOriginalUrl(String shortUrl) {
    ShortUrlEntity foundShortUrlEntity = shortUrlRepository.findByUrl(shortUrl);
    return foundShortUrlEntity;
  }

  @Override
  public ShortUrlEntity updateShortUrl(ShortUrlEntity newShortUrlEntity) {
    ShortUrlEntity foundShortUrlEntity = shortUrlRepository.findByOrgUrl(newShortUrlEntity.getOrgUrl());
    foundShortUrlEntity.setUrl(newShortUrlEntity.getUrl());
    ShortUrlEntity savedShortUrlEntity = shortUrlRepository.save(foundShortUrlEntity);
    return foundShortUrlEntity;
  }

  @Override
  public void deleteByShortUrl(String shortUrl) {
    ShortUrlEntity foundShortUrlEntity = shortUrlRepository.findByUrl(shortUrl);
    shortUrlRepository.delete(foundShortUrlEntity);
  }

  @Override
  public void deleteByOriginalUrl(String originalUrl) {
    ShortUrlEntity foundShortUrlEntity = shortUrlRepository.findByUrl(originalUrl);
    shortUrlRepository.delete(foundShortUrlEntity);
  }
}
```

- DAO측에서는 특별한 기능을 가지고 있지 않고 단순히 Entity를 생성해서 Repository에 전달하는 역할만을 한다.

### ShortUrlResponseDTO

```jsx
package com.example.demo.data.dto;

import ...

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ShortUrlResponseDTO implements Serializable {

  private static final long serialVersionUID = -214490344996507077L;

  @Id
  private String orgUrl;

  private String shortUrl;

}
```

### ShortUrlEntity

```jsx
package com.example.demo.data.entity;

import ...
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "short_url")
public class ShortUrlEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  // GenerationType.IDENTITY: 알아서 테이블을 읽어서 인덱스 번호를 가져옴
  private Long id;

  @Column(nullable = false, unique = true)
  private String hash;

  @Column(nullable = false, unique = true)
  private String url;

  @Column(nullable = false, unique = true)
  private String orgUrl;

}
```

- **MappedSuperClass:** BaseEntity를 상속 받음으로써 BaseEntity가 가지고 있는 필드 값을 가져오게 된다.
- **EntityListeners**: Entity가 사용되는 시점에서 저장되는 시점의 이전인지 이후인지를 정하는 것

### BaseEntity

```jsx
package com.example.demo.data.entity;

import ...

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

- 해당 Entity를 상속 받아 사용하게 되면 수정&삽입을 할 때 date를 넣어줄 수 있다.

 

```jsx
@SpringBootApplication
@EnableJpaAuditing
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
```

- Entity 설정만 한다고 Entity가 추가가 되는 것이 아니고 Application단에서 @EnableJpaAuditing을 추가해줘야 한다.
