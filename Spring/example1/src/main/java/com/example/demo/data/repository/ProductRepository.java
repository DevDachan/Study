package com.example.demo.data.repository;

import com.example.demo.data.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {
// 실제로 ProductRepository에서는 정의되는 method가 없지만 jpa에서 상속받게 되면 기본적으로 사용 가능한
// method가 있다.
}
