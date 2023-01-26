# 15. Rest Template

## RestTemplate이란?

- 스프링에서 제공하는 HTTP 통신 기능을 쉽게 사용할 수 있게 설계되어 있는 템플릿
- HTTP 서버와의 통신을 단순화하고 RESTful원칙을 지킴
- 동기 방식으로 처리되며 비동기 방식으로는 AsyncRestTemplate이 있음
- RestTemplate 클래스는 REST 서비스를 호출하도록 설계되어 HTTP 프로토콜의 메소드에 맞게 여러 메소드를 제공

![Untitled](15%20Rest%20Template%20033af974c904416490205f8ef2dc4679/Untitled.png)

- 가장 눈 여겨 봐야 하는 method는 exchange인데 type이 따로 정해져 있지 않고 그때그때 바꿔서 사용하는 것

## Code

- 전체적인 코드는 server(demo2) - client(demo) 구조로 실행이 될 것이다

- 만약 포트번호를 변경하고 싶다면 application.properties에 아래 구문 추가하기

```jsx
server.port = 9090
```

### Server Project (Demo2)

**Controller**

```jsx
package com.example.demo2.controller;

import com.example.demo2.dto.MemberDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/server")
public class TestController {
    private final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @GetMapping(value = "/demo")
    public String getTest1(){
        LOGGER.info("getTest1 호출");
        return "Hello Demo Program";
    }

    @GetMapping(value="/name")
    public String getTest2(@RequestParam String name){
        LOGGER.info("getTest2 호출!");
        return "Hello. " + name + "!";
    }

    @GetMapping(value="/path-variable/{name}")
    public String getTest3(@PathVariable String name){
        LOGGER.info("getTest3 호출!");
        return "Hello. " + name + "!";
    }

    @PostMapping(value = "/member")
    public ResponseEntity<MemberDTO> getMember(
            @RequestBody MemberDTO memberDTO,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String organization
    ){
        LOGGER.info("getMemeber 호출");

        return ResponseEntity.status(HttpStatus.OK).body(memberDTO);
    }
    @PostMapping(value="add-header")
    public ResponseEntity< MemberDTO > addHeader(@RequestHeader("demo-header") String header, @RequestBody MemberDTO memberDTO){
        LOGGER.info("add-header 호출");
        LOGGER.info("header 값 : {}", header);

        return ResponseEntity.status(HttpStatus.OK).body(memberDTO);
    }
}
```

- Server측 controller에서는 Client측에서 URI로 날린 Request에 대한 처리를 하는 부분이다.

### Client Project (Demo)

**Controller**

```jsx
package com.example.demo.controller;

import com.example.demo.data.service.RestTemplateService;
import com.example.demo.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rest-template")
public class RestTemplateController {
    RestTemplateService restTemplateService;

    @Autowired

    public RestTemplateController(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    @GetMapping(value = "/demo")
    public String getDemo() {
        return restTemplateService.getDemo();
    }

    @GetMapping(value = "/name")
    public String getName() {
        return restTemplateService.getName();
    }

    @GetMapping(value = "/name2")
    public String getName2() {
        return restTemplateService.getName2();
    }

    @PostMapping(value = "/dto")
    public ResponseEntity<MemberDTO> postDto() {
        return restTemplateService.postDto();
    }

    @PostMapping(value = "/add-header")
    public ResponseEntity<MemberDTO> addHeader() {
        return restTemplateService.addHeader();
    }

}
```

**RestTemplateService**

```jsx
package com.example.demo.data.service;

import com.example.demo.dto.MemberDTO;
import org.springframework.http.ResponseEntity;

public interface RestTemplateService {
    public String getDemo();

    public String getName();

    public String getName2();

    public ResponseEntity<MemberDTO> postDto();

    public ResponseEntity<MemberDTO> addHeader();
}
```

**RestTemplateServiceImpl**

```jsx
package com.example.demo.data.service.Impl;

import com.example.demo.data.service.RestTemplateService;
import com.example.demo.dto.MemberDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Member;
import java.net.URI;

@Service
public class RestTemplateServiceImpl implements RestTemplateService {
    private final Logger LOGGER = LoggerFactory.getLogger(RestTemplateServiceImpl.class);

    @Override
    public String getDemo(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("api/server/demo")
                .encode()
                .build()
                .toUri();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

        LOGGER.info("status code : {}", responseEntity.getStatusCode());
        LOGGER.info("body : {}", responseEntity.getBody());

        return responseEntity.getBody();
    }

    @Override
    public String getName(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("api/server/name")
                .queryParam("name" , "Flature")
                .encode()
                .build()
                .toUri();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

        LOGGER.info("status code : {}", responseEntity.getStatusCode());
        LOGGER.info("body : {}", responseEntity.getBody());

        return responseEntity.getBody();
    }

    @Override
    public String getName2(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("api/server/path-variable/{name}")
                .encode()
                .build()
                .expand("Flature") // 값을 pathvariable에 넣어줌, 여러개 값은 ,로 구분
                .toUri();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

        LOGGER.info("status code : {}", responseEntity.getStatusCode());
        LOGGER.info("body : {}", responseEntity.getBody());

        return responseEntity.getBody();
    }

    @Override
    public ResponseEntity<MemberDTO> postDto(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("api/server/member")
                .queryParam("name", "Flature")
                .queryParam("email", "ccc@cc.com")
                .queryParam("organization" , "Around Hub Studio")
                .encode()
                .build()
                .toUri();
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setName("flature!");
        memberDTO.setEmail("aaa@aa.com");
        memberDTO.setOrganization("Around Hub ");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<MemberDTO> responseEntity = restTemplate.postForEntity(uri, memberDTO, MemberDTO.class);

        LOGGER.info("status code : {}", responseEntity.getStatusCode());
        LOGGER.info("body : {}", responseEntity.getBody());

        return responseEntity;
    }

    @Override
    public ResponseEntity<MemberDTO> addHeader(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("api/server/add-header")
                .encode()
                .build()
                .toUri();
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setName("flature!");
        memberDTO.setEmail("aaa@aa.com");
        memberDTO.setOrganization("Around Hub ");

        RequestEntity<MemberDTO> requestEntity = RequestEntity
                .post(uri)
                .header("around-header", "Around Hub Studio")
                .body(memberDTO);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<MemberDTO> responseEntity = restTemplate.exchange(requestEntity, MemberDTO.class);
        // MemberDTO와는 다른 reqeustEntity를 넣어주기 때문에 형식 지정을 exchange를 해줌으로써 response type만 지정을 해준다.
        LOGGER.info("status code : {}", responseEntity.getStatusCode());
        LOGGER.info("body : {}", responseEntity.getBody());

        return responseEntity;
    }
}
```

- URI에서 받은 값을 바로 처리하는 것이 아니라 server측 path로 값을 넘겨주는 작업을 하는 것이 RestTemplate을 말한다.