# 5. Hello World

### **컨트롤러**

- 웹 어플리케이션의 요청을 받아 그 요청에 대한 응답을 해주는 부분
- 해당 강의에서는 백엔드 측면을 다루기 때문에 View부분은 다루지 않는다.

### **어노테이션이란?**

자바 소스코드에서 사용되는 메타데이터의 일종으로써 설정 파일에 존재하는 데이터들을 관리하는 표시라고 생각하면 된다.

- 코드 문법 에러체크, 코드 자동 생성 정보 제공, 런타임시 특정 기능 실행하는 정보 제공 등

**@RestController**

- Spring Framework 4 버전부터 사용가능한 어노테이션
- @Controller에 @ResponseBody가 결합된 어노테이션
- 기존에는 웹 응답을 통해 ResponseBody를 명시해야 했지만 어노테이션 없이 문자열과 JSON등을 전송이 가능하다.
- View를 거치지 않고 HTTP ResponseBody에 직접 Return값을 담아 보내게 된다.

<img src="https://user-images.githubusercontent.com/111109411/213903614-ea4e8c70-8639-43ab-bf59-33a80a15625b.png" width=60%>


**@RequestMapping**

- MVC의 핸드러매핑을 위해서 DefaultAnnotationHandlerMapping을 사용
- 클래스와 메소드의 RequestMapping을 통해 URL을 매핑하여 경로를 설정하여 해당 메소드에서 처리한다.
    - Value: url설정
    - Method: GET, POST, DELETE, PUT, PATCH 등
- 스프링 4.3버전부터 메소드를 지정하는 방식보다 간단하게 사용할 수 있는 어노테이션을 사용할 수 있음
    - @GetMapping,   @PostMapping,   @DeleteMapping,  @PutMapping,  @PatchMapping

<img src="https://user-images.githubusercontent.com/111109411/213903618-fdc200b7-77f4-4e91-88c4-c5c4f736bf94.png" width=60%>


- Spring을 하면서 Chrome API를 사용하면 더 편하게 개발 가능

**기본 helloworld 구조**

- 기존에 설명한 RequestMapping과 RestController를 사용해서 컨트롤러 부분 제작
- 해당 자바 파일은 패키지 안에 존재.

<img src="https://user-images.githubusercontent.com/111109411/213903623-37c4330a-0fc2-4a4b-8f34-b1bda021db6f.png" width=60%>



- 위 파일 실행 이후 tomcat에서 정상 실행되었다는 문구가 보이면 위에서 설치한 Talend API Tester를 통해 URI에 HTTP request를 보내면 Hello world가 출력 되는 것을 볼 수 있다.

<img src="https://user-images.githubusercontent.com/111109411/213903632-7977c0a9-2781-4e0d-9997-7b4ce2dfcfa2.png" width=60%>

<img src="https://user-images.githubusercontent.com/111109411/213903634-cc876c13-aa47-4676-b80b-835ffa0e78d6.png" width=60%>


- 실제 localhost:8080/hello에 접속해봐도 Hello world가 출력이 된다.

<img src="https://user-images.githubusercontent.com/111109411/213903635-c7135f15-bec1-4f40-a06a-7b731cc199ec.png" width=60%>


- 고전적인 방법으로는 이렇게 작성 했을 경우에는 POST, GET 과 같은 method를 전부 같이 처리를 하게 되고 특정 Method만을 거를 때에는 method를 추가해줘야 했다.


<img src="https://user-images.githubusercontent.com/111109411/213903650-754018d4-3d5d-4c22-9fd3-1acdd28d1a66.png" width=60%>


- 하지만 이제는 그냥 annotation에서 직접적으로 Method에 따른 annotation을 사용함으로써 method지정을 따로 하지 않는다.
    - (import 자동으로 해주는 단축키 Alt + Shift + Enter)

<img src="https://user-images.githubusercontent.com/111109411/213903658-9e65d0ff-79b7-4961-92a1-34de89bda10d.png" width=60%>


- method를 바꾼 뒤 Request를 보내 봤을 때 보이는 것과 같이 405 Error가 나오면서 Header에 GET을 사용하라고 알려준다.
