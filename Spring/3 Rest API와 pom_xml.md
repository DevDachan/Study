# 3. Rest API와 pom.xml

### **API(Application Programming Interface)란?**

- 응용 프로그램에서 사용할 수 있도록 다른 응용 프로그램을 제어할 수 있게 만든 인터페이스를 뜻 함.

(쉽게 말해 응용프로그램 간 제어를 위해 만든 인터페이스)

- API를 사용하면 내부 구현 로직을 모르더라도 정의된 기능을 쉽게 사용할 수 있다.

→ Interface: 어떤 장치 간 정보를 교환하기 위한 수단이나 방법을 의미함

### **REST (Representational State Transfer)란?**

- 자원의 이름으로 구분하여 해당 자원의 상태를 교환하는 것을 의미
- Rest는 서버와 클라이언트 통신 방식 중 하나임 (규약보다는 Architecture에 가까움)
- HTTP URI를 통해 자원을 명시하고 HTTP Method(CRUD)를 통해 자원을 교환하는 것

### **REST 특징(규칙)**

- **Server-Client 구조**
    - 자원이 있는 쪽이 Server, 요청하는 쪽이
    
    서로 독립적으로 분리되어 있어야 한다. (공유하는 자원이 없어야 함, DB 분리)
    
- **Stateless**
    - 요청 간에 클라이언트 정보가 서버에 저장되지 않음.
    - 서버는 각각의 요청을 완전히 별개의 것으로 인식하고 처리
- **Cacheable**
    - HTTP 프로토콜을 그대로 사용하기 때문에 HTTP의 특징인 캐싱 기능을 적용해 대량의 요청을 효율적 처리가 가능해진다.
- **계층화**
    - 클라이언트는 서버의 구성과 상관없이 REST API서버로 요청
    - 서버는 다중 계층으로 구성될 수 있다. (로드밸런싱, 보안요소, 캐시 등)
- **Code on Demand (Optional)**
    - 요청을 받으면 서버에서 클라이언트로 코드 또는 스크립트를 전달하여 클라이언트 기능 확장
- **인터페이스 일관성**
    - 정보가 표준 형식으로 전송되기 위해 구성 요소간 통합 인터페이스를 제공
    - HTTP 프로토콜을 따르는 모든 플랫폼에서 사용 가능하게끔 설계
    

**REST 장점**

- HTTP 표준 프로토콜을 사용하는 모든 플랫폼에서 호환 가능
- 서버와 클라이언트의 역할을 명확하게 분리
- 여러 서비스 설계에서 생길 수 있는 문제를 최소화

**REST API란?**

- REST 아키텍처의 조건을 준수하는 어플리케이션 프로그래밍 인터페이스를 말한다
- 최근 많은 API가 REST API로 제공되고 있고 일반적으로 REST 아키텍처를 구현하는 웹 서비스를 RESTful하다고 표현한다.

**REST API 특징**

- REST 기반으로 시스템을 분산하여 확장성과 재사용성을 높임
- HTTP 표준을 따르고 있어 여러 프로그래밍 언어로 구현이 가능하다.

**REST API 설계 규칙**

- 웹 기반의 REST API를 설계할 경우에는 URI를 통해 자원을 표현해야 함
    - [https://thinkground.studio/member/589](https://thinkground.studio/member/589)
        
        Resource : member
        
        Resource id: 589
        
    - 위에서 보이는 것과 같이 URI는 단순히 자원을 표현하는 것이고 행위를 포함하면 안된다.
- 자원에 대한 조작은 HTTP Method(CRUD)를 통해 표현해야 함
    - URI에 행위가 들어가면 안되고 HEADER를 통해 CRUD를 표현하여 동작을 요청해야 함
- 메시지를 통한 리소스 조작
    - HEADER를 통해 content-type을 지정하여 데이터를 전다
    - 대표적 형식은 HTML, XML, JSON, TEXT가 있다.

### **REST API 설계 규칙**

- URI에는 소문자를 사용
- Resource의 이름이나 URI가 길어질 경우에는 하이픈(-)을 통해 가독성을 높일 수 있음
- 언더바(_)는 사용하지 않는다. (정보 교환에 있어서 하이퍼링크로 잡힐 때 잘 보이지 않기 때문)
- 파일 확장자를 표현하지 않는다.

## **pom.xml (Project Object Model)**

- Maven 프로젝트를 생성하면 루트 디렉토리에 생성되는 파일
- 주요 설정 정보
    - 프로젝트 정보: 프로젝트의 이름, 개발자 목록, 라이선스 등
    - 빌드 설정 정보: 소스, 리소스, 라이프 사이클 등 실행할 플러그인 등
    - POM 연관 정보: 의존 프로젝트(모듈), 상위 프로젝트, 하위 모듈 등

### **프로젝트 기본 정보**

- <name>: 프로젝트 명
- <url>: 프로젝트 사이트 URL
- <description>: 프로젝트에 대한 간단한 설명
- <organization>: 프로젝트를 관리하는 단체 설명

![Untitled](3%20Rest%20API%E1%84%8B%E1%85%AA%20pom%20xml%2020d5c8d7a0f74c1086f794b997b8aed5/Untitled.png)

### **프로젝트 연관 정보**

- <groupId>: 프로젝트의 그룹 ID 설정
- <artifactId>: 프로젝트 아티팩트 ID 설정
- <version>: 프로젝트의 버전
- <packaging>: 패키징 타입 설정
    - jar: 자바 프로젝트 압축 파일
    - war: 웹 어플리케이션을 위한 패키징 방식

![Untitled](3%20Rest%20API%E1%84%8B%E1%85%AA%20pom%20xml%2020d5c8d7a0f74c1086f794b997b8aed5/Untitled%201.png)

### **프로젝트 의존 설정**

- <dependencies>: 라이브러리 의존성 정보를 가지고 있는 dependency 태그를 묶은 태그
- <dependency>: 각 라이브러리의 정보를 담는 태그
- <groupId>: 의존성 라이브러리의 group ID
- <artifactId>: 의존성 라이브러리의 아티팩트 ID
- <version>: 의존성 라이브러리의 버전
- <scope>: 해당 라이브러리의 이용 범위를 지정
- <optional>: 다른 프로젝트에서 이 프로젝트를 의존성 설정을 할 경우 사용할지 결정

![Untitled](3%20Rest%20API%E1%84%8B%E1%85%AA%20pom%20xml%2020d5c8d7a0f74c1086f794b997b8aed5/Untitled%202.png)

### **Scope 태그**

- **compile (default)**
    - 이 값으로 설정하는 경우 모든 클래스 경로에서 사용할 수 있음
    - 컴파일 배포 상황에서 같이 제공됨
- **Provided**
    - Compile과 유사 하지만 JDK 혹은 Container가 런타임 시에만 제공
    - 컴파일 혹은 테스트 경로에서만 사용하며, 배포 시에는 빠짐
- **Runtime**
    - 컴파일 시에는 사용하지 않고 실행 상황에서만 사용됨
    - 런타임과 테스트 경로에서는 있지만 컴파일 클래스 경로에는 존재하지 않음
- **Test**
    - 테스트 상황에서만 사용되는 라이브러리를 말하고 실 가동 상황에서는 필요없는 라이브러리를 사용할 경우 설정한다.
- **System**
    - Provide와 유사하지만 저장소에서 관리되지 않고 직접 관리하는 JAR를 추가(system path 설정해야 함)
    
    → 이러한 의존성은 크게 봤을 때 프로젝트를 하면서 사용되는 여러 라이브러리, 패키지 파일들을 어떤 방식으로 가져오고 포함할 지를 결정하는 옵션이라고 생각하면 된다.
    

**현재 시점에 설정되어 있는 라이브러리 설명**

- Spring Boot Starter Parent
    - 프로젝트에서 사용하는 다양한 라이브러리 간의 버전 충돌 문제가 발생할 수 있는 것을 방지
    - 의존성 조합간 충돌 문제가 없는 검증된 버전 정보 조합을 제공한다.
- Spring Boot Starter Web
    - Spring MVC를 사용한 REST 서비스를 개발하는데 사용
- Spring Boot Starter Test
    - Junit, Hamcrest, Mockito를 포함한 스프링 어플리케이션의 테스트 기능 제공
    
    → 이 3개의 starter 라이브러리는 Spring Boot를 사용할 때 가장 기본적으로 사용되는 라이브러리들이다.
    

+++ 추가하면 좋은 것들

<dependency>

<groupId>org.projectlombok</groupId>

<artifactId>lombok</artifactId>

<dependency>

- 편하게 method를 만들지 않고 annotation으로 대체하는 기능을 한다.