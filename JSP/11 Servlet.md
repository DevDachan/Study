# 11. Servlet

**서블릿이란?**

- 제일 처음 설명했던 것처럼 서블릿(Servlet)은 JSP가 나오기 전 자바로 웹 애플리케이션을 개발할 수 있도록 만든 기술이다. 서블릿은 서버 단에서 클라이언트의 요청을 받아 처리한 후 응답하는 역할을 한다.
- 클라이언트의 요청에 대해 동적으로 작동하는 웹 애플리케이션 컴포넌트이다.
- MVC 모델에서 Controller역할을 한다.
- 모든 method는 스레드로 동작한다.
- javax.servlet.http 패키지의 HttpServlet 클래스를 상속 받는다.

**서블릿 컨테이너**

- 서블릿을 만들었다고 해서 바로 작동하는 것이 아니라 컨테이너가 서블릿을 관리해 주는 것이다.

<img src="https://user-images.githubusercontent.com/111109411/213120730-dfe7779f-7066-478f-aa51-e915e46f0f61.png" width=60%>



- 서블릿 컨테이너는 톰캣을 사용한다. 톰캣은 서블릿의 수명 주기를 관리하고 요청이 오면 스레드를 생성해 처리해준다.
    - 통신지원 : port와 socket을 열고 I/O 스트림을 생성하는 과정을 API하나로 제공해준다.
    - 수명주기 관리 : 서블릿을 인스턴스화한 수 초기화하고 요청에 맞는 적절한 method 호출한다.
    - 멀티스레딩 관리 : 기본으로 서블릿 요청들은 스레드를 생성해 처리하므로 멀티스레드 방식으로 처리한다.
    - 선언적인 보안 관리 및 JSP 지원: 서블릿 컨테이너 자체에서 보안기능을 지원하므로 별도 구현하지 않는다.

**그렇다면 JSP와 Servlet의 차이는 무엇인가?**

- 단순히 기능의 차이는 없고 역할의 차이라고 생각하면 된다.
- Servlet = 자바 코드 안에 HTML 코드, JSP = HTML코드 안에 자바 코드

**서블릿의 동작 방식**

- 서블릿은 MVC 패턴에서 Controller 역할을 하는데 클라이언트의 요청을 받아서 분석 후 요청을 처리할 서블릿을 찾게 된다.
- 찾은 서블릿을 통해 비즈니스 서비스 로직을 호출하고 Model로부터 그 결과값을 받아 request나 session 영역에 저장한 후 결과 값을 출력하게 될 View를 선택한다.
- 최종으로 선택된 View(jsp페이지)에 결과값을 출력한 후 클라이언트에 response를 보내게 된다.
- 
<img src="https://user-images.githubusercontent.com/111109411/213120779-81b4b546-5ec7-472b-80ea-a9faf9af71db.png" width=60%>




**서블릿 작성 규칙**

1) 기본적으로 javax.servlet,  javax.servlet.http,  java.io 패키지를 import한다.

2) 서블릿 클래스는 반드시 public으로 선언 되어야 하고 HttpServlet을 상속받아야 한다.

3) 사용자의 요청을 처리하는 doGet(), doPost() 추상 method에 대해서 Overriding해줘야 한다.

Overriding : 부모 클래스로부터 상속받은 method를 자식 클래스에서 재정의 해 사용하는 것을 말한다.

Overloading: 한 클래스 내에 이미 사용하려는 이름과 같은 method가 있더라도 parameter를 다르게 해서 method를 정의해 사용하는 것

4) doGet() or doPost() method는 ServletException과 IOException 예외를 던지도록 throws 선언한다.

5) doGet() or doPost() method를 호출할 때의 매개변수는 HttpServletRequest와 HttpServletResponse를 사용한다.

<img src="https://user-images.githubusercontent.com/111109411/213120837-80ddce3d-3d85-4937-8ebb-ccb55e58c18f.png" width=60%>



req.getRequestDispatcher(“/13Servlet/HelloServlet.jsp”)

- 자바에서 @Override는 별다른 의미는 없고 주석이라고 생각하면 된다.

**서블릿 작성**

- 서블릿 작성은 클라이언트의 요청을 전달할 요청명을 결정하는 일부터 시작한다. JSP는 클라이언트의 요청을 JSP가 직접 처리하지만 서블릿은 요청명을 기준으로 이를 처리할 서블릿을 선택하게 된다.

**Mapping :** 요청명(URL)과 서블릿을 연결해주는 작업

1) web.xml에 기술하는 방법

2) @WebServlet 주석을 사용하여 코드에 직접 명시하는 방법

1. **web.xml에서 mapping**

<img src="https://user-images.githubusercontent.com/111109411/213120880-1aefd494-1bc4-40a9-8021-cd9a812d898a.png" width=60%>

- 이런 식으로 서블릿을 등록한 후 등록한 서블릿과 요청 URL을 mapping해준다.

<img src="https://user-images.githubusercontent.com/111109411/213120916-8a96ea1f-60a7-4828-a2c0-8c1a0e8bb61b.png" width=60%>



- context root란 Content directory의 경로로 META-INF와 WEB-INF폴더를 가지고 있다.
- web.xml에서 파일 위치는 <web-app> 요소 하위라면 어디든 상관 없다.

<img src="https://user-images.githubusercontent.com/111109411/213120957-65a18bbc-c314-4994-b867-41bd90c72c6e.png" width=60%>
    

    
    
- HelloServelet.do에 접속하게 된다면 해당 servlet은 mapping되어 있는 HelloServlet을 사용한다.

<img src="https://user-images.githubusercontent.com/111109411/213121002-f625f4b8-2c8d-4558-9a39-77378158fd0e.png" width=60%>

    
    
<HelloServlet.jsp>

- 페이지 구성

<Java Resources/src/servlet/HelloServlet.java>

- 실제 servlet 안 class를 작성할 때는 보이는 것과 같이 doGet() method를 Override해준 뒤 request 영역에 message를 입력한 다음 dispatcher를 통해 미리 입력 해 놓은 jsp 파일로 요청을 넘겨준다.

<img src="https://user-images.githubusercontent.com/111109411/213121042-9c0a9788-ccf3-4082-ae3f-fb8baffe3e30.png" width=60%>

<img src="https://user-images.githubusercontent.com/111109411/213121066-0f62d709-828c-46f2-af08-3149c446a662.png" width=60%>


    
    
- 옆에 결과에서 확인할 수 있듯이 그저 HelloServlet.jsp에 들어가면 null값이 출력되지만 [바로가기] 링크를 눌러 HelloServlet.do에 접속하면 message에 입력한 Hello Servlet이 출력된다.

- 이처럼 servlet에 미리 servlet을 지정 해 놓으면 mapping된 data에 따라서 servlet은 HelloServlet.do를 HelloServlet.java에 mapping이 해준다.
- 서블릿은 요청명을 통해 서블릿이 직접 요청을 처리한 후 -> 데이터 영역에 저장하고 결과를 출력할 JSP를 선택해 공유되어 있는 영역 데이터를 출력하는 형식으로 클라이언트에게 응답한다.

1. **@WebServlet 애너테이션(주석)으로 Mapping**

<img src="https://user-images.githubusercontent.com/111109411/213121101-443d747e-0735-4e1e-9cac-530a838b1a0f.png" width=60%>


    
    
- JSP에서는 href 설정 시 request.getContextPath()를 가져와 미리 경로를 설정해야 한다.

- 서블릿 파일은 동일하게 Java Resources/src/servlet안에 class로 생성해주면 된다.

<img src="https://user-images.githubusercontent.com/111109411/213121125-45d56321-e424-47a4-94cb-d5405ca88348.png" width=60%>
 

    
    
- 애너테이션 사용 시에는 class 생성 이전에 어떤 경로를 처리할지 미리 선언해야 한다. (Context root 제외한 경로)
- 나머지 방법은 처음 web.xml에서 작성했던 것과 동일하게 작성해주면 된다.

**그렇다면 왜 복잡한 web.xml방식을 사용하는가?**

- @WebSerbelt 애너테이션은 요청명(url-pattern)이 변경된다면 해당 서블릿 클래스를 수정 한 후 다시 컴파일 해야 하고 서블릿 개수가 많아지면 특정 요청 명을 처리하는 클래스를 찾기 힘들어진다. 하지만 web.xml의 경우에는 요청 명 만으로 클래스를 쉽게 찾을 수 있다.

**JSP 없이 서블릿에서 바로 응답 출력**

- 서블릿 방식은 공통적으로 JSP를 사용하여 응답 내용을 출력했었다. 이제는 JSP를 사용하지 않고 서블릿에서 내용을 즉시 출력하는 방법을 알아보자. (여기 서는 get이 아닌 post방식을 사용할 것이다.)

<img src="https://user-images.githubusercontent.com/111109411/213121190-be68aa50-10c9-45df-8892-cb85c5928c1c.png" width=60%>
    
    
- 기본적으로 해당 페이지로 이동하기 위한 <form> 태그를 jsp파일에 입력 시켜준다.

(출력을 JSP에서 하지 않는 것이지 어쨌든 해당 URL을 들어가기 위한 방법은 JSP를 사용한다.)

- web.xml에는 요청을 담당할 서블릿 mapping을 작성한다.

<img src="https://user-images.githubusercontent.com/111109411/213121233-0d4b937c-215e-4819-8b63-bf7b0c5eacd6.png" width=60%>

   
<img src="https://user-images.githubusercontent.com/111109411/213121271-d78ea9c9-3308-4d87-b147-5a57bc66b410.png" width=60%>
    
    
- 서블릿에서 mapping한 Override 클래스에서 이처럼 writer.println으로 HTML 값을 출력할 수 있다.
- witer를 사용할 때는 마지막에 close로 닫아줘야 함.

**언제 서블릿에서 바로 출력을 해야 하는가?**

- 대부분의 경우에는 JSP를 통해 출력하는 쪽이 편하지만 비동기 방식으로 통신할 때 XML이나 JSON을 사용하는 경우나 순수 데이터만 출력해야 하는 경우에는 서블릿에서 직접 출력 하는게 편하다.

**여러 개의 요청 처리**

<img src="https://user-images.githubusercontent.com/111109411/213121315-b1b2f38f-c821-4278-90e4-2aca3a3fde6e.png" width=60%>

    
    
- 여러 개의 요청을 처리할 때는 @WebServlet에서 *를 통해 모든 path를 불러오고 각각의 path를 나누는 것은 class안에서 라우팅 해주면 된다.
- 일단 doGet, doPost로 들어오게 되면 URI를 parsing해서 마지막 component를 분석해서 각각 페이지 처리에 해당하는 method를 호출해주면 됨.

- 페이지 처리는 별다른 것 없고 request에 다르게 포함해야 하는 변수들 담는 것이다.

(어차피 전체 페이지 처리는 forward되는 페이지에서 처리함)

<img src="https://user-images.githubusercontent.com/111109411/213121350-2448cdd4-c599-4b2c-bf4f-bc755bfa1cd8.png" width=60%>


    
**서블릿의 수명 주기 method**

<img src="https://user-images.githubusercontent.com/111109411/213121385-8f7f5665-fee7-4307-b000-924e3981075a.png" width=60%>
    
    
- 서블릿은 클라이언트의 요청이 들어오면 가장 먼저 서블릿 객체를 생성하고 초기화한 후 요청을 처리한다. 그리고 서버가 종료 될 때 서블릿 객체를 소멸시키게 되는데 이것을 서블릿 생명주기라고 한다.
- 생명주기는 서블릿 컨테이너가 담당하고 각 단계마다 메서드를 호출해 필요한 기능을 수행하는데 이 Callback method를 LifeCycle(수명주기) method라고 한다.
    
    **@PostConstruct**
    
    - 객체 생성 직후 init() method를 호출하기 전에 호출되며 매서드명은 개발자가 정한다.
    
    **init()**
    
    - 서블릿의 초기화 작업을 수행하기 위해 호출되며 최초 요청 시 딱 한번만 호출된다.
    
    **service()**
    
    - 클라이언트의 요청을 처리하기 위해 호출된다.
    - 전송 방식이 get이면 doGet()을, post면 doPost()를 호출한다.
    
    **destroy()**
    
    - 서블릿이 새롭게 컴파일되거나 서버가 종료될 때 호출된다.
    
    **@PreDestroy**
    
    - destroy() method가 실행 되고 난 후 컨테이너가 서블릿 객체를 제거하는 과정에서 호출된다.

<img src="https://user-images.githubusercontent.com/111109411/213121423-67eec0f4-1664-4da3-b00d-cceb900ff343.png" width=60%>
    


    
- @가 붙은 method들은 이름을 개발자가 원하는 이름으로 하면 된다.

- 해당 예제 코드는 단순히 LifeCycle에서 어떤 순서로 실행되는지를 알기 위함이다.

- init에서는 보통 DB를 여는 작업을 실행한다. (web.xml에서 DB 연결 정보를 얻어DAO 생성)

- service에서 doGet or doPost를 지정하는데 DB를 이용할 때는 회원 인증을 구현을 담당하고 인증을 요청한 ID와 PWD를 입력 받아 DTO를 생성하여 찾은 회원의 권한에 따른 처리를 하게 된다
    
    (req.setAttrubute()한 이후 getRequestDispatcher를 호출해서 흐름 제어를 한다.)
    

- Get을 호출한 뒤 Post를 호출했을 때 출력되는 결과이다. 결과에서 보면 Post를 호출할 때는 곧바로 service()부터 호출이 되는데 이것이 바로 서블릿의 장점이다.


<img src="https://user-images.githubusercontent.com/111109411/213121455-42bd47fc-6e77-4383-b7e5-f767d4e2344a.png" width=60%>


    
    
(처음 서블릿 객체를 생성하면 그 다음부터는 기존 객체를 사용하기에 처리 속도가 빨라 짐)


<img src="https://user-images.githubusercontent.com/111109411/213121486-d84e1d9b-b7df-4b55-b084-4bab59fdf401.png" width=60%>

    
    
- eclipse에서 서버를 종료하면 보이는 것과 같이 destroy()와 @PostDestroy가 호출되는 것을 볼 수 있다.

**요약**

- 서블릿을 사용하면 MVC 패턴을 적용한 모델2 방식으로 웹 애플리케이션 개발을 할 수 있다.
- 요청명(URL)과 이를 처리할 파일(서블릿)이 분리되어 있어 그 둘을 mapping해주는 작업이 필요하다.
- * 와일드카드를 이용하면 여러 요청을 하나의 서블릿에서 처리가 가능하다.
