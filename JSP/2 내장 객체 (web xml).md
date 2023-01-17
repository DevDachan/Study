# 2. 내장 객체 (web.xml)

**내장 객체란?**

- 기본적으로 JSP에서 내장하고 있는 객체들을 말한다. HTTP 통신을 위한 response와 request등을 쉽게 다룰 수 있게 해준다.
- 내장객체는 JSP 페이지가 실행될 때 컨테이너가 자동으로 생성해준다.
    
    → 기본적으로 JSP를 실행 할 때 JSP는 Servlet파일로 컴파일 되는데 이때 _ispService() method가 생성되고 이 method안에 내장 객체들의 선언문이 있다.
    
- 내장 객체는 총 9개가 존재하며 각각의 내장 객체는 컨테이너가 미리 선언해 놓은 참조 변수를 이용해 사용한다.
    
    [request, response, out, session, application, page, exception, config, pageContext]
    
- 내장 객체는 <%스크립틀릿%>와 <%=표현식%>안에서만 사용이 가능하고 <%!선언부%>에서 즉시 사용 불가능
    
    
<img src="https://user-images.githubusercontent.com/111109411/212545538-dee61f33-1f48-4cf9-a086-c414d267a96f.png" width=60%>    

    
<img src="https://user-images.githubusercontent.com/111109411/212545551-33c489ff-00a7-4c24-b5b2-606abed5546e.png" width=60%>    


**Request**

- request는 웹 페이지에서 client 측의 요청에 대한 내용들을 담고 있다.
    
    (session과 request header포함)
    
- POST: message의 내용을 content에 직접 포함시켜서 전달, Data를 외부로 숨기기 가능
- GET: message의 내용을 URL에 포함시켜 전달함.

    
<img src="https://user-images.githubusercontent.com/111109411/212545576-6e10f7b7-c879-46d3-b486-009832f3ea7f.png" width=60%>    


GET이든 POST든 .getParameter()를 통해서 보낸 data에 접근이 가능하고 GET의 경우에는 query string이 존재한다

- request를 읽을 때에는 request.setCharacterEncoding(”UTF-8”)과 같이 request를 UTF-8형식으로 인코딩 해줘야 한다. 한글을 사용 할 경우 post 방식으로 전송된 data가 깨질 수 있음

**Response**

- response는 웹 페이지에서 client측의 요청에 대한 응답을 웹 브라우저로 보내주는 역할을 한다.
- sendRedirect() : response를 보내고 page를 이동할 때 사용하는 method로 <a>나 location.href를 사용하지 않고 JSP에서 페이지 이동시 사용
    ```
    <%
    
    response.sendRedirect(“ResponseMain.jsp”);
    
    %>
    ```
- getRequestDispatcher(“ResponseMain.jsp?loginErr=1”).forward(request, response) : 기본적으로 dispatcher에는 인자 값으로 이동할 페이지의 경로를 지정하고 forward() method를 통해 페이지를 이동시킨다. 이때 Dispatcher 안에는 뷰페이지경로.jsp가 들어간다

**response에 헤더 추가**

- response에 값을 추가하는 것은 set과 add 2가지 계열로 나뉘어 진다.
    
    **set:** set계열은 기존에 존재하는 헤더를 수정할 때 사용한다.
    
    **add:** add계열은 헤더 값을 새로 추가할 때 사용한다.
    
    ```
    response.addDateHeader(“myBirthday”, “2022-11-07”);          : 날짜 data 추가
    
    response.addIntHeader(“myNumber”,5);                            : 정수형 data 추가
    
    response.addHeader(“myName”, “Dachan”);                       : String data 추가
    
    response.setHeader(“myName”, “Zani”);                             : 이전 myName에 Zani로 수정
    ```

**out 객체**

- out 내장 객체는 웹 브라우저에 변수등의 값을 출력할 때 사용한다. 대부분의 상황에서는 <%=표현식%>을 사용하는 것이 더 편리하기 때문에 직접적으로 사용하는 경우가 많지 않음
- 하지만 스크립틀릿 내에서 변수를 웹 브라우저에 출력해야 한다면 표현식보다는 out 내장 객체를 사용하는 것이 좋다.
    
    (스크립틀릿 == <%%>)
    
- out은 기본적으로 버퍼를 사용한다. 출력되는 모든 정보를 버퍼에 먼저 저장한 후 웹 브라우저에 출력
    ```
    <%
    
    out.print(“출력되지 않은 텍스트”);  // 버퍼에 저장
    
    out.clearBuffer();                        // 버퍼를 비움 위의 출력 결과 사라짐
    
    out.print(“<h2>out 내장 객체</h2>”)
    
    out.print(“출력 버퍼 크기 : ”, + out.getBufferSize() + “<br>”);
    
    out.flush();                              // 버퍼 내용 출력하기
    
    %>
    ```
- 버퍼를 사용한다는 것 말고는 크게 어려운 점이 없지만 버퍼를 사용하기 때문에 버퍼의 size를 고려해야 한다.
    - **buffer 사용 method**
        
        out.getBufferSize()          : 현재 출력 버퍼의 크기 확인
        
        out.getRemaining()         : 남은 버퍼의 크기 확인
        
        out.clearBuffer()             : 버퍼에 담긴 내용 전부 지우기
        
        out.flush()                     : 버퍼에 담긴 내용 모두 출력
        
- buffer의 기본값은 8KB이다. 원래대로라면 buffer는 모두 채워졌을 때 자동으로 flush되면서 내용을 출력하지만 강제로 출력 할 경우에는 flush() method를 사용해야 한다.
- print() 대신 println()을 사용 가능한데 그냥 \n(줄바꿈)의 유무 차이

**Application 객체**

- app객체는 웹 애플리케이션당 하나만 생성되며 모든 JSP 페이지에서 접근할 수 있다. 기본적으로 ServeletContext 타입으로 웹 애플리케이션 전반에서 이용하는 정보를 저장하거나 서버의 정보, 서버의 물리적 경로를 얻어올 때 사용한다.
- web.xml : 웹 애플리케이션에 대한 여러가지 설정을 저장하는 곳으로 배포 서술자(deployment descriptor)라고도 부른다
    
    web.xml 파일은 WEB-INF 폴더에 존재하며 초기화 매개변수는 아래와 같이 사용한다.
    ```
    <%
    
    <context-param>
    
    <param-name> INIT_PARAM </param-name>
    
    <param-value> web.xml에 저장한 초기화 매개변수</param-name>
    
    </context-param>
    
    %>
    ```    
    
    → 해당 내장 객체에서 만든 매개변수를 이용하는 코드
    
    초기화 매개변수 : <%= application.getInitParameter(“INIT_PARAM”)%>
    

- web.xml은 이클립스 초기 설치 시에는 존재하지 않을 수도 있다. 때문에 따로 설정을 해주거나 web.xml을 직접 만들어 줘야 함.

    
<img src="https://user-images.githubusercontent.com/111109411/212545580-04a93b95-567f-4c83-8b16-384865e44f5f.png" width=60%>    
        
[https://gocoder.tistory.com/1256](https://gocoder.tistory.com/1256) 

**Exception 객체**

- exception 내장 객체는 오류명과 오류 메시지를 출력하는 부분에서 사용한다. 대부분의 에러는 404, 405, 500에러이다.
    
    **404(Not Found)**: 클라이언트가 요청한 경로에서 문서를 찾지 못함
    
    **405(Method Not Allowed)**: 허용되지 않은 method라는 뜻으로 GET or POST방식으로 요청 했는데 이를 처리할 컨트롤러가 없는 경우를 말함
    
    **500(Internal Server Error)**: 서버 내부 오류로 코드에 오류가 있거나 logic에 문제가 있어 웹 서버가 요청 사항을 처리 할 수 없음을 말한다.
    
    > 그럼 이러한 error의 분기를 어떻게 처리할 것인가? Web.xml에서 설정을 추가
    ```
    <error-page>
    
    <error-code>404</error-code>
    
    <location>/02ImplicitObject/Exception.jsp</location>
    
    </error-page>
    
    <error-page>
    
    <error-code>405</error-code>
    
    <location>/02ImplicitObject/Exception.jsp</location>
    
    </error-page>
    ```
        
- 위와 같은 내용을 xml에 추가시킴으로서 해당 error가 발생 했을 때 error 페이지로 분기 시켜준다.
