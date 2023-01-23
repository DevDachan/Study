# 7. Swagger (협업 라이브러리) X

### **협업을 위해 필요한 라이브러리**

![Untitled](7%20Swagger%20(%E1%84%92%E1%85%A7%E1%86%B8%E1%84%8B%E1%85%A5%E1%86%B8%20%E1%84%85%E1%85%A1%E1%84%8B%E1%85%B5%E1%84%87%E1%85%B3%E1%84%85%E1%85%A5%E1%84%85%E1%85%B5)%20X%20860e6d81746044e798ae69cea411b060/Untitled.png)

- 이전에는 API를 소개할 때 Spec을 담고 있는 Document(Excel,word 등)를 전해주게 된다. 그런데 그때그때 해당 Document를 수정해서 보내주는 것에 문제가 될 수 있음

### **Swagger란?**

- 서버로 요청되는 API리스트를 HTML 화면으로 문서화하여 테스트 할 수 있는 라이브러리
    - 서버가 가동되면서 @RestController를 읽어 API를 분석하여 HTML 문서를 작성함

### **Swagger가 필요한 이유**

- REST API의 스펙을 문서화 하는 것은 매우 중요한데 API를 변경할 때마다 Reference문서를 계속 바꿔야 하는 불편함이 존재한다.

### **Swagger 설정 방법**

- @Configuration: 어노테이션 기반의 환경 구성을 돕는 어노테이션. IoC Container에게 해당 클래스를 Bean구성 Class임을 알려준다.
- @Bean: 개발자가 직접 제어가 불가능한 외부 라이브러리등을 Bean으로 만들 경우에 사용

### Swagger Dependency

```jsx
pom.xml	
	
	<dependency>
		<groupId>io.springfox</groupId>
		<artifactId>springfox-swagger-ui</artifactId>
		<version>2.9.2</version>
	</dependency>
	<dependency>
		<groupId>io.springfox</groupId>
		<artifactId>springfox-swagger2</artifactId>
		<version>2.9.2</version>
	</dependency>
```

- 위 dependency설정 이후 아래와 같은 maven update버튼을 눌러주면 자동으로 라이브러리가 설치되어 추가 된다.

![Untitled](7%20Swagger%20(%E1%84%92%E1%85%A7%E1%86%B8%E1%84%8B%E1%85%A5%E1%86%B8%20%E1%84%85%E1%85%A1%E1%84%8B%E1%85%B5%E1%84%87%E1%85%B3%E1%84%85%E1%85%A5%E1%84%85%E1%85%B5)%20X%20860e6d81746044e798ae69cea411b060/Untitled%201.png)

### Swagger Package

![Untitled](7%20Swagger%20(%E1%84%92%E1%85%A7%E1%86%B8%E1%84%8B%E1%85%A5%E1%86%B8%20%E1%84%85%E1%85%A1%E1%84%8B%E1%85%B5%E1%84%87%E1%85%B3%E1%84%85%E1%85%A5%E1%84%85%E1%85%B5)%20X%20860e6d81746044e798ae69cea411b060/Untitled%202.png)

- dependency추가 이후 해당 프로젝트의 [main.jav](http://main.java)a.project 내부에 config라는 패키지를 추가시켜 주고 원하는 Class를 생성해주면 된다.

```jsx
public class SwaggerConfiguration {
  @Bean
  public Docket api(){
      return new Docket(DocumentationType.SWAGGER_2)
              .apiInfo(apiInfo()) //아래에서 설정한 api의 정보를 담는 것
              .select()
              .apis(RequestHandlerSelectors.basePackage("com.example.demo"))
              //basePackage는 swagger가 REST controller를 스캔할 때 어디를 범위로 스캔할지 정해주는 것
              .paths(PathSelectors.any())
              .build();
  }

  private ApiInfo apiInfo() {
      return new ApiInfoBuilder()
              .title("Dachan Hub Open API Test with Swagger")
              .description("설명 부분")
              .version("1.0.0") //maven(pom.xml)에서 설정한 프로젝트 version
              .build();
  }
}
```