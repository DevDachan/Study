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
