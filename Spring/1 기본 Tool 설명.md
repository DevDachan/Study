# 1. 기본 Tool 설명

**Study Method:** [https://www.youtube.com/watch?v=rHJgMRimJ4Y&feature=youtu.be](https://www.youtube.com/watch?v=rHJgMRimJ4Y&feature=youtu.be)

- 기본적으로 Spring Boot를 사용할 때 IntelliJ를 사용할 것이다.
    
    [https://www.jetbrains.com/idea/](https://www.jetbrains.com/idea/)
    
- 위 사이트 접속해서 다운로드를 하는데 학생이라면 학생 계정으로 로그인 할 시 구매 없이 사용 가능

<img src="https://user-images.githubusercontent.com/111109411/213862163-bbb93a81-9619-4468-9ce3-824f4fa72092.png" width=60%>



- 특별한 설치 옵션은 없고 위와 같은 옵션만 체크해주면 된다.

출처

[https://goddaehee.tistory.com/195](https://goddaehee.tistory.com/195)

## **프로젝트 구조**
<img src="https://user-images.githubusercontent.com/111109411/213862167-ba7f9900-82a3-4cb1-90bb-398899bd6e58.png" width=60%>


- **Java:** 자바 내부에 있는 파일들을 기본적인 소스코드 파일들을 포함하고 있는 폴더임.

<img src="https://user-images.githubusercontent.com/111109411/213862173-6187fec6-c6fd-4894-a2ce-a4f87a57d7e3.png" width=60%>



- **Resources:** static이나 templates의 경우 프론트엔드 부분의 요소들을 담고 있고application.properties는 JSP에서 application 영역과 동일하게 DB 연결과 같은 부분에서 커넥트 풀을 생성해주는 파일이다.

<img src="https://user-images.githubusercontent.com/111109411/213862180-b7de23cf-0313-44f0-ba04-08f6b947f143.png" width=60%>


- **Test:** Class를 테스트하는 방식으로 실제 적용 전 로직 테스트 할 때 사용된다.
<img src="https://user-images.githubusercontent.com/111109411/213862190-decaa0de-27f1-4369-9572-22401b8993ec.png" width=60%>

<img src="https://user-images.githubusercontent.com/111109411/213862200-9c8e6815-2591-4324-bb6f-a6401105d733.png" width=60%>

<img src="https://user-images.githubusercontent.com/111109411/213862205-969bcc56-9273-4cf5-bb93-80d399353be0.png" width=60%>



- 우리가 선택한 maven에서 사용되는 파일로 각각의 태그 이해하면 좋다
    
    <parent>: 상위 폼 파일을 찾아가 의존성 상속 받기
    
    <version>: 임의 설정 가능
    
    <groupId, artifactId>: 프로젝트 설정 내용
    
    <description>: 간단한 설명
    
    <dependencies>: 사용하게 될 오픈소스
    
    <build>: 사용하는 여러 플로그인 내용들
    

## **Maven과 Gradle**

- 대표적인 자바 빌드 관리 툴

- **빌드 관리 도구란?** 프로젝트에서 필요한 xml, properties, jar파일들을 자동으로 인식하여 빌드해 주는 도구
- 소스코드를 컴파일, 테스트, 정적 분석등을 하여 실행 가능한 앱으로 빌드해 줌
- 프로젝트 정보 관리, 테스트 빌드, 배포등의 작업을 진행해줌
- 외부 라이브러리를 참조하여 자동으로 다운로드 및 업데이트 관리해줌
    
    자바 대표적인 빌드 도구: Ant, Maven, Gradle
    

### **Maven**

– 자바의 대표적인 관리 도구였던 Ant를 대체하기 위해 개발되었으며 프로젝트의 외부 라이브러리를 쉽게 참조하기 위해 pom.xml파일로 명시하여 관리한다.

<img src="https://user-images.githubusercontent.com/111109411/213862211-3c3f6684-a695-451b-a847-8c896babab1b.png" width=60%>        
        

        
**왜 Maven을 사용?**

1. 기존에 사용하던 Ant는 빌드의 기능만을 가지고 있음
2. 자동으로 라이브러리를 관리해주는 기능이 추가됨
3. 다운 받아 사용하던 라이브러리에 변동 사항이 있으면 자동으로 업데이트하여 적용됨

| Ant | Maven |
| --- | --- |
| - XML 기반의 빌드 스크립트
- 자유로운 빌드 단위 지정
- 간단하고 사용이 쉬움
- 대규모 프로젝트에서 복잡해지는 경향이 있음
- 라이프 사이클이 없음 | - XML기반의 빌드 스크립트
- 라이프 사이클 도입
- pom.xml로 편하게 dependency 관리 |

**Maven 사용법**

- 사실 라이프 사이클이 뭔 지 쉽게 와닿지 않기 때문에 직접 해봐야 알기 쉬울 것이다.
- maven을 사용할 때는 pom.xml파일을 활용하여 빌드 및 관리를 한다.
- xml의 역할
    - 프로젝트 정보 관리
    - 해당 프로젝트에서 사용하는 외부 라이브러리 관리
    - 해당 프로젝트의 빌드 관련 설정

**Maven의 대표 태그**

**modelVersion:** maven의 버전을 의미

**groupId:** 프로젝트 그룹 id를 말하며 일반적으로 대표하는 사이트 도메인을 역순으로 적어서 사용한다.

**artifactId:** groupId외에 다른 프로젝트와는 구분될 수 있는 프로젝트 ID를 작성

**version**: 프로젝트 버전을 의미, 개발 단계 따라 구분작성 (개발자의 자유지만 정해진 규칙이 있다.)

**name:** 프로젝트 이름

**description:** 프로젝트의 간단한 설명

**properties:** pom.xml파일 내에서 빈번하게 사용되는 중복 상수를 정의하는 영역, 해당 상수를 사용할 때는 ${태그명}로 사용하면 된다.

**Dependencies:** 해당 프로젝트의 의존성을 가지는 라이브러리를 정의하는 영역으로 가장 중요하다고 말할 수 있다.

**Build:** 프로젝트 빌드와 관련된 정보를 설정하는 영역

### **Gradle**

- Groovy 스크립트를 활용한 빌드 관리 도구로써 안드로이드 프로젝트의 표준 빌드 시스템으로 채택되었다.
- 멀티 프로젝트 빌드에 최적화되어 있으며 Maven에 비해 더 빠르게 처리할 수 있고 간결한 구성이 가능
    
    (xml로 작성 할 경우 tag를 열고, 닫고 신경쓸게 많지만 Groovy 스크립트를 사용하기에 간결한 구성)
    

**Gradle과 Maven비교**

- Gradle에 비해 Maven이 점유율이 더 높은 상황이다.
- 이전에 사용하던 maven을 모두 바꾸는 것이 한번에 되지 않기 때문
- 성능적으로는 Gradle이 Maven에 비해 우수하다.
- Maven: pom.xml, Gradle: build.gradle
- Gradle은 설치 없이 사용이 가능하다(Gradle Wrapper)

**Gradle 대표 용어**

Repositories: 라이브러리가 저장된 위치 등 설정

mavenCentral: 기본 Maven Repository

dependencies: 라이브러리 사용을 위한 의존성 설정

<img src="https://user-images.githubusercontent.com/111109411/213862219-3b418137-6191-446a-ad15-8f27fc2ebf27.png" width=60%>

 
        
**대표적인 Maven Repository Site**

[https://mvnrepository.com/](https://mvnrepository.com/)
