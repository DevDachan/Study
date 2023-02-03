package com.example.demo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("devdachan/delete-api")
public class DeleteController {
    //http://localhost:8080/devDachan/delete-api
    @DeleteMapping(value = "/{variable}")
    public String DeleteVariable(@PathVariable String variable){
        return variable;
    }
    //원래라면 variable값을 DB에서 확인하고 해당 값을 삭제하는 것이 DeleteAPI의 기능
}
