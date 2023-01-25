package com.example.demo.controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
