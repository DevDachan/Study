package com.example.demo.data.repository;

import com.example.demo.data.dto.ShortUrlResponseDTO;
import org.springframework.data.repository.CrudRepository;

public interface ShortUrlRedisRepository extends CrudRepository<ShortUrlResponseDTO, String> {

}
