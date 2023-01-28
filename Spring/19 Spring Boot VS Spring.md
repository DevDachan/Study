# 19 Spring Boot VS Spring

## Spring이란?

- 정확한 표현으로는 ‘스프링 프레임워크’로써 자바에서 가장 많이 사용되는 프레임 워크이다.
- 의존성 주입(DI: Dependency Injection) 과 제어 역전(IOC: Inversion of Control), 관점 지향 프로그래밍(AOP)이 가장 중요한 요소이다.
- 위 요소들을 통해 느슨한 결함(Loose Coupling)을 달성할 수 있음
- 이러한 Loose Coupling으로 개발한 애플리케이션은 단위 테스트를 수행하기에 용이하다.

## 의존성 주입(DI: Dependency Injection)

- 만약 의존성 주입을 사용하지 않는다면?
    
    ```jsx
    @RestController
    public class NoDIController{
      private MyService service = new MyServiceImpl();
    
    	@GetMapping("hello")
    	public String getHello(){
    		return service.getHello();
      }
    }
    ```
    
    - Controller는 MyService 객체에 의존하게 됨
    - 예제 코드처럼 객체의 인스턴스를 얻게 되면 객체 간의 결합도가 올라간다.
    - 이런 코드 작성은 단위 테스트를 위해 Mock 객체를 사용할 수 없게 된다.
- 의존성 주입을 사용한 코드
    
    ```jsx
    @Service
    public class MyServiceImpl implements MyServie{
      @Override
    	public String getHello(){
    		return "Hello";
      }
    }
    
    @RestController
    public class DIController{
    	MyService myService;
    	
    	@Autowired
    	public DIController(MyService myService){
    		this.myService = myService;
    	}
    	
    	@GetMapping("/hello")
    	public String getHello(){
    		return myService.getHello();
    	}
    }
    ```
    
    - @Service, @Autowired 어노테이션을 통해 MyService의 인스턴스를 획득
    - 위와 같이 코드를 작성하면 단위 테스트 상황에서 Service객체를 Mock객체로 대체하여 쉽게 테스트할 수 있음
    

## 관점 지향 프로그래밍 (AOP: Aspect Oriented Programming)

- 스프링 프레임 워크에서 제공하는 강력한 기능 중 하나
- AOP는 쉽게 말해 OOP를 보완하는 수단으로 여러 곳에 쓰이는 공통 기능을 모듈화하여 필요한 곳에 연결함으로써 유지보수 또는 재사용에 용이하도록 하는 것을 의미한다.
- AOP를 통해 기존 프로젝트에 다양한 기능을 로직 수정 없이 추가할 수 있음

## 스프링 프레임워크 대표적 모듈

- Spring JDBC
- Spring MVC
- Spring AOP
- Spring ORM
- Spring Test
- Spring Expression Language(SpEL)

## 스프링 부트가 나오게 된 이유

- Spring Boot makes it easy to create stan-alone, production-grade Spring based Applications that you can "just run".
    - 스프링 부트는 단지 실행만 하면 되는 스프링 기반의 애플리케이션을 쉽게 만들 수 있다
- 스프링은 다양한 기능을 제공하고 있지만 그 기능을 사용하기 위한 설정에 많은 시간이 걸린다.
    - Transaction Manager, Hibernate Datasource등

## 스프링 부트가 제공하는 기능

- 스프링 부트는 자동 설정(AutoConfiguration)을 이용
    - 애플리케이션 개발에 필요한 모든 디펜던시를 프레임워크에서 관리
    - jar파일이 클래스 패스에 있는 경우 스프링 부트는 Dispatcher Servlet으로 자동 구성함
    - 스프링 부트는 미리 설정되어 있는 Starter 프로젝트를 제공
    - xml 설정 없이 자바 코드를 쉽게 설정할 수 있음
- 애플리케이션을 개발하면서 사용되는 의존성들을 호환되는 버전으로 관리해줘야 함
    - 이런 복잡도를 줄이기 위해 스프링 부트는 SpringBoot-Starter를 제공하여 자동으로 호환되는 버전을 관리해준다.
- 모니터링 관리를 위한 스프링 액추에이터(Spring Actuator)제공
    - 서비스가 정상적으로 동작하는지 상태 모니터링 기능 제공
    - 스프링 액추에이터는 스프링 부트에서 제공하는 상태 정보를 보다 쉽게 모니터링할 수 있게 기능을 제공

## 스프링 부트 의존성 관리

- spring boot starter dependency를 통해 다양한 패키지를 수용하고 있음
- 이를 통해 개발자는 dependency관리 (호환성 체크 등)에 대해 고려할 필요가 없어짐

## Spring boot starter Dependency

- spring-boot-starter-web-service: SOAP 웹 서비스
- spring-boot-starter-web: RESTful 응용 프로그램
- spring-boot-starter-test: 단위 테스트, 통합 테스트
- spring-boot-starter-jdbc: 기본적인 JDBC
- spring-boot-starter-security: 스프링 시큐리티(인증, 권한)
- spring-boot-starter-data-jpa: Spring Data JPA(Hibernate)
- spring-boot-starter-cache: 캐시