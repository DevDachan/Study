package com.example.demo.data.dao;

import com.example.demo.data.entity.ShortUrlEntity;

public interface ShortUrlDAO {

  ShortUrlEntity saveShortUrl(ShortUrlEntity shortUrl);

  ShortUrlEntity getShortUrl(String originalUrl);

  ShortUrlEntity getOriginalUrl(String shortUrl);

  ShortUrlEntity updateShortUrl(ShortUrlEntity newShortUrl);

  void deleteByShortUrl(String shortUrl);

  void deleteByOriginalUrl(String originalUrl);

}