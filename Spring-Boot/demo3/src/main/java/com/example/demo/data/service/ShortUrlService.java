package com.example.demo.data.service;

import com.example.demo.data.dto.ShortUrlResponseDTO;

public interface ShortUrlService {
  ShortUrlResponseDTO getShortUrl(String clientId, String clientSecret, String originalUrl);

  ShortUrlResponseDTO generateShortUrl(String clientId, String clientSecret, String originalUrl);

  ShortUrlResponseDTO updateShortUrl(String clientId, String clientSecret, String originalUrl);

  void deleteByShortUrl(String shortUrl);

  void deleteShortUrl(String url);
  void deleteByOriginalUrl(String originalUrl);

}
