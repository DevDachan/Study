package com.example.demo.controller;

import com.example.demo.data.dto.ShortUrlResponseDTO;
import com.example.demo.data.service.ShortUrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
