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
