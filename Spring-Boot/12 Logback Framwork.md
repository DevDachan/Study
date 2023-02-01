# 12. Logback Framwork

## Logback이란?

- Log4J를 기반으로 개발된 로깅(Logging)라이브러리
- log4j에 비해 약 10배 정도 빠른 퍼포먼스, 메모리 효율성 증대
    - 출시 순서: log4j → logback→log4j2
    

## Logback 특징

- 로그에 특정 레벨을 설정 할 수 있음 (Trace→ Debug→Info→Warn→Error)
- 실 운영과 테스트 상황에서 각각 다른 출력 레벨을 설정하여 로그를 확인할 수 있음
- 출력 방식에 대해 설정 할 수 있음
    - Console, Mail, DB등
- 설정 파일을 일정 시간마다 스캔하여 애플리케이션 중단 없이 설정 변경 가능
- 별도의 프로그램 없이 자체적으로 로그 압축을 지원
- 로그 보관 기간 설정 가능

## Logback 구조

<img src="https://user-images.githubusercontent.com/111109411/214507438-3f82dceb-a9ad-47b6-b163-fafad9684a24.png" width=60%>




## Logback 설정

- 일반적으로 Classpath에 있는 logback설정을 참조하게 됨
    - Java Legacy, Spring의 경우에는 logback.xml 파일을 참조
    - Spring Boot의 경우에는 logback-spring.xml 파일을 참조

## Logback 설정 파일 형식

<img src="https://user-images.githubusercontent.com/111109411/214507471-e735d322-b241-45b5-b31c-56a26b71bbc0.png" width=80%>


## Appender

- Log의 형태 및 어디에 출력할지 설정하기 위한 영역
    - ConsoleAppender: 콘솔에 로그 출력
    - FileAppender: 파일에 로그를 저장
    - RollingFileAppender: 여러 개의 파일을 순회하며 로그를 저장
    - SMTPAppender: 로그를 메일로 보냄
    - DBAppender: DB에 로그를 저장

<img src="https://user-images.githubusercontent.com/111109411/214507499-d9af6954-7fc2-4c2c-9646-69eb4bd6680e.png" width=80%>



- RollingFileAppender의 예시로써 filter는 임계치를 설정, file은 저장되는 파일의 이름, append는 내용을 뒤에 붙인다는 의미, rollingPolicy는 여러 개 파일을 순회하게 될 때 몇몇 정책을 지정하는 것이다.

## Encoder

- Appender 내에 포함되는 항복이며 pattern을 사용하여 원하는 형식으로 로그를 표현할 수 있음

<img src="https://user-images.githubusercontent.com/111109411/214507535-089d6f2a-796e-443b-8172-01a3be182d5e.png" width=80%>



## Root

- 설정한 Appender를 참조하여 로그의 레벨을 설정할 수 있음
    - root는 전역 설정이며, 지역 설정을 하기 위해서는 logger를 사용

<img src="https://user-images.githubusercontent.com/111109411/214507587-a741f690-1209-46f6-860d-145eac0c5835.png" width=40%>




## Log Level

### TRACE → DEBUG → INFO → WARN → ERROR

**1) ERROR:** 로직 수행 중에 오류가 발생한 경우, 시스템적으로 심각한 문제가 발생하여 작동이 불가능 한 경우

**2) WARN:** 시스템 에러의 원인이 될 수 있는 경우

**3) INFO:** 상태 변경과 같은 정보성 메시지

**4) DEBUG:** 애플리케이션의 디버깅을 위한 메시지 레벨

**5) TRACE:** DEBUG 레벨보다 더 디테일한 메시지를 표현하기 위한 레벨

## Pattern

<img src="https://user-images.githubusercontent.com/111109411/214507612-cbb36b9e-b9a0-4845-b5ff-c019f2dfa4cf.png" width=100%>




```jsx
<pattern>[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%-5level][%thread] %logger %msg%n </pattern>
```

- %logger{30}과 같이 변경하면 글자 수 제한이 가능

# Code

### logback.spring.xml

```jsx
<?xml version="1.0" encoding="UTF-8"?>
<configuration scan="true" scanPeriod="30 seconds"> <!-- logback 구현체가 설정을 확인하는 주기 -->
    <property name="moduleId" value="demo" /> <!-- moduleId는 프로젝트가 설치된 폴더명 또는 구분 가능한 식별자 -->
    <property name="type" value="demo_example"/> <!-- 로그 파일명을 구성하는 인자-->
    <property name="logback" value="logback" /> <!-- log를 저장할 최종 디렉토리명-->

    <!-- Appenders -->
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <filter class="ch.qoslogback.classic.filter.ThresholdFilter">
            <level>DEBUG</level>
        </filter>
        <encoder>
            <pattern>[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%-5level][%thread] %logger %msg%n </pattern>
        </encoder>
    </appender>

    <root level="INFO">
        <appender-ref ref="console" />

    </root>

</configuration>
```

**Controller 기본 설정**

```jsx
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

private final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);
```

### HelloController

```jsx
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
```

- 현재 root의 Level이 Info로 설정되어 있기 때문에 출력 되는 내용을 봤을 때 info Log의 하위 log들만 출력이 되는 것을 볼 수 있다.
- 사실 logback의 기능이 단순히 console.log와 동일하다고 생각할 수 있다.

<img src="https://user-images.githubusercontent.com/111109411/214507658-00556b61-f5eb-4bfa-a068-8899fd7341bd.png" width=100%>




### ProductDTO 설정 변경 이후 출력해보기

```jsx
@GetMapping(value = "/{productId}")
    public ProductDTO getProduct(@PathVariable String productId) {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[ProductController] perform {} of DEMO API","getProduct");

        ProductDTO productDTO = productService.getProduct(productId);
        LOGGER.info("[ProductController] Response :: productId = {}, productName ={} , productPrice = {} , productStock = {}",
                productDTO.getProductId(),productDTO.getProductName(),productDTO.getProductPrice(),productDTO.getProductStock());

        return productDTO;
    }
```

<img src="https://user-images.githubusercontent.com/111109411/214507691-506b80a2-a44b-4fbc-8175-58a063a76798.png" width=100%>



