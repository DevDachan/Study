package com.example.demo.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

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