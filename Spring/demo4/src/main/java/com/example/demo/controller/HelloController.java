package com.example.demo.controller;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashMap;
import java.util.Map;


@RestController
public class HelloController {

    private final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);
   // @RequestMapping(value="/hello", method= RequestMethod.GET)
    @GetMapping("/hello")
    public String hello(){
        return "Hello world\n";
    }
    @PostMapping("log-test")
    public void logTest(){
        LOGGER.trace("Trace log");
        LOGGER.debug("Debug log");
        LOGGER.info("Info log");
        LOGGER.warn("Warn log");
        LOGGER.error("Error log");
    }

    @PostMapping("/exception")
    public void exceptionTest() throws Exception{
        throw new Exception();
    }
/*
    @ExceptionHandler(value= Exception.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(Exception e){
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        LOGGER.info(e.getLocalizedMessage());
        LOGGER.info("Controller 내 ExceptionHandler 호출");

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", "에러 발생");

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }*/
}
