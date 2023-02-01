package com.example.demo.data.service.Impl;


import com.example.demo.data.dao.ShortUrlDAO;
import com.example.demo.data.dto.NaverUriDTO;
import com.example.demo.data.dto.ShortUrlResponseDTO;
import com.example.demo.data.entity.ShortUrlEntity;
import com.example.demo.data.service.ShortUrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import java.util.Arrays;
import java.util.Optional;

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

