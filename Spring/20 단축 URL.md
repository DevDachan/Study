# 20. 단축 URL

### 네이버 애플리케이션 등록 (API 신청)

[애플리케이션 - NAVER Developers](https://developers.naver.com/apps/#/register)

![Untitled](20%20%E1%84%83%E1%85%A1%E1%86%AB%E1%84%8E%E1%85%AE%E1%86%A8%20URL%206298767f25f3448b9f2f2305aca0839a/Untitled.png)

- 개발을 하면서 내가 만드는 이 프로그램이 어떤 생명주기를 가지고 있나를 정하는 것이 중요하다.

### 단축 URL 가이드

[단축 URL API 적용 가이드](https://developers.naver.com/docs/utils/shortenurl/)

## 설계

### 전체 프로젝트 구조

![제목 없음.png](20%20%E1%84%83%E1%85%A1%E1%86%AB%E1%84%8E%E1%85%AE%E1%86%A8%20URL%206298767f25f3448b9f2f2305aca0839a/%25EC%25A0%259C%25EB%25AA%25A9_%25EC%2597%2586%25EC%259D%258C.png)

- Naver API가 존재하고 기본 Project와 연결이 된다. Client에 해당하는 User와도 HTTP 통신을 하며 DB와는 CRUD를 위한 통신을 하게 된다.

### 코드 구조

![Untitled](20%20%E1%84%83%E1%85%A1%E1%86%AB%E1%84%8E%E1%85%AE%E1%86%A8%20URL%206298767f25f3448b9f2f2305aca0839a/Untitled%201.png)

- Controller
- Service-Impl
- Handeler: DB자체(mariaDB)가 바뀐 것이 없기 때문에 기존 Handler 그대로 사용
- DAO-Impl
- Repository

### Controller 설계

![Untitled](20%20%E1%84%83%E1%85%A1%E1%86%AB%E1%84%8E%E1%85%AE%E1%86%A8%20URL%206298767f25f3448b9f2f2305aca0839a/Untitled%202.png)

### Service 설계

![Untitled](20%20%E1%84%83%E1%85%A1%E1%86%AB%E1%84%8E%E1%85%AE%E1%86%A8%20URL%206298767f25f3448b9f2f2305aca0839a/Untitled%203.png)

### DAO 설계

![Untitled](20%20%E1%84%83%E1%85%A1%E1%86%AB%E1%84%8E%E1%85%AE%E1%86%A8%20URL%206298767f25f3448b9f2f2305aca0839a/Untitled%204.png)

### Entity 설계

![Untitled](20%20%E1%84%83%E1%85%A1%E1%86%AB%E1%84%8E%E1%85%AE%E1%86%A8%20URL%206298767f25f3448b9f2f2305aca0839a/Untitled%205.png)

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

  @PostMapping
  public ShortUrlResponseDTO generateShortUrl(String originalUrl){
    LOGGER.info("[generateShortUrl] perform API CLIENT_ID : {}, CLIENT_SECRET : {}", CLIENT_ID, CLIENT_SECRET);

    return shortUrlService.generateShortUrl(CLIENT_ID,CLIENT_SECRET,originalUrl);
  }

  @GetMapping()
  public ShortUrlResponseDTO getSHortUrl(String originalUrl){
    ShortUrlResponseDTO shortUrlResponseDTO = new ShortUrlResponseDTO("ss", "ss");
    return shortUrlService.getShortUrl(CLIENT_ID, CLIENT_SECRET, originalUrl);
  }

  @PutMapping
  public ShortUrlResponseDTO updateShortUrl(String originalUrl){ return null; }

  @DeleteMapping
  public ShortUrlResponseDTO deleteShortUrl(String url){ return null;}

}
```

- generate: 만약 shortUrl이 DB에 존재하지 않을 경우에는 생성을 해줘야 한다. generate할 때는 정보를 받아야 하기 때문에 PostMapping을 해주고 service측에 generate 호출해야 한다.

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

### ShortUrlResponseDTO

```jsx
package com.example.demo.data.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

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