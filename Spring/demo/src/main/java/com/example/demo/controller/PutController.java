package com.example.demo.controller;


import com.example.demo.dto.MemberDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("devdachan/put-api")
public class PutController {
    @PutMapping(value = "/default")
    public String putMethod(){
        return "Hello world!";
    }

    @PutMapping(value = "/member")
    public String postMember(@RequestBody Map<String, Object> putData){
        StringBuilder sb = new StringBuilder();

        putData.entrySet().forEach(map ->{
            sb.append(map.getKey() + " : " + map.getValue() + "\n");
        });
        return sb.toString();
    }

    @PutMapping(value = "member1")
    public String postMemberDto1(@RequestBody MemberDTO memberDTO){
        return memberDTO.toString();
    }

    @PutMapping(value = "member2")
    public MemberDTO postMemberDto2(@RequestBody MemberDTO memberDTO){
        return memberDTO;
    }
    @PutMapping(value = "member3")
    public ResponseEntity<MemberDTO> postMemberDto3(@RequestBody MemberDTO memberDTO){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(memberDTO);
    }

}
