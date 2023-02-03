package com.example.demo.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class NaverUriDTO {

  private String message;

  private String code;

  private Result result;


  // 결과 값으로 아래와 같이 json형태를 받기 위함
  /*
  * {
  *  "message":"ok",
  *  "result": {
  *      "hash":"GyvykVAu",
  *      "url":"https://me2.do/GyvykVAu",
  *      "orgUrl":"http://d2.naver.com/helloworld/4874130"
  *  }
  *  ,"code":"200"
  *  }
  *
  */
  @Getter
  @Setter
  public static class Result {

    private String hash;

    private String url;

    private String orgUrl;

  }

}