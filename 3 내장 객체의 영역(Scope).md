# 3. 내장 객체의 영역(Scope)

**내장 객체의 영역?** – 각 객체가 저장되는 메모리의 유효기간. 자바에서 지역 변수를 생각해봤을 때 지역변수는 해당 method를 벗어나면 더 이상 사용을 할 수 없다. 이러한 영역을 말하는 것

**page 영역**: 동일한 페이지에서만 공유 된다.

**request 영역**: 하나의 요청에 의해 호출된 페이지와 포워드(요청 전달)된 페이지까지 공유된다.

**session 영역**: 클라이언트가 처음 접속한 후 웹 브라우저를 닫을 때까지 공유된다.

**application 영역**: 한번 저장되면 웹 애플리케이션이 종료될 때까지 유지된다.

Application > Session > Request > Page

- **setAttribute(String name, Object value)**
    
    각 영역에 속성을 저장
    
    값의 타입은 Object이므로 모든 타입의 객체를 저장할 수 있다.
    
- **Object getAttribute(String name)**
    
    영역에 저장된 속성값을 얻어온다.
    
    Object로 자동 형 변환되어 저장되므로 원래 타입으로 형 변환 이후 사용해야 한다.
    
- **void removeAttribute(String name)**
    
    영역에 저장된 속성을 삭제한다.
    
    삭제할 속성명이 존재하지 않아도 오류는 발생하지 않음
    

**DTO(Data Transfer Object)**

- 데이터 전송 객체는 데이터를 저장하거나 전송하는데 쓰이는 객체로 다른 logic없이 순수하게 Data만을 담고 있다.
- 기본적인 path는 Java Resources내부에 src 폴더 내부에 존재하고 class로 존재한다. 가장 먼저 class에서 사용하게 될 변수들을 private(encapsulation)으로 선언해준 뒤 Setter와 Getter를 추가 시켜 준다.
- Getter, Setter가 만들어 졌으면 전체 class를 초기화 시켜주는 Constructor를 선언해줌

**Page 영역**

- page영역은 기본적으로 클라이언트의 요청을 처리하는데 관여하는 JSP 페이지마다 하나씩 생성된다. 해당 부분은 코드 제일 상단 부분에 선언되며 가장 기본적으로 JAVA에서 사용하는 모듈을 위한 import문과 HTML의 형식을 지정해주는 영역이라고 생각하면 된다.
- 선언하는 부분이기 때문에 @를 붙여 사용
    
    <%@ page import=”common.Person”%>
    
    <%@ page language=”java” contentTye=”text/html; charset=UTF8” pageEncoding=”UTF-8”%>
    
    <%
    
    pageContext.setAttribute(“pageInteger”, 1000);
    
    pageContext.setAttribute(“pageString”, “페이지 영역의 문제열”);
    
    pageContext.setAttribute(“pagePerson”, new Person(“한석봉”,99));
    
    %>
    
- 이런 식으로 처음 부분에서 page를 선언하고 pageContext에 여러 속성들을 저장할 수 있다.

- 스크립틀릿에서 page context불러오기
    
    String pString = pageContext.getAttribute(“pageString”).toString()
    
    pPerson.getName()
    
- Page영역의 경우 include 된 파일의 page 영역 또한 공유가 가능하다. Include는 말 그대로 포함 관계이기 때문에 가능

<%@ include file=”PageInclude.jsp”%>

**Request 영역**

- 클라이언트가 요청을 할 때마다 새로운 request객체가 사용됨. 같은 요청을 처리하는데 사용되는 모든 JSP페이지는 공유한다.
    
    <%
    
    request.setAttribute(“requestString”, “request 영역의 문자열”)
    
    request.getAttribute(”requestString”)
    
    request.getParameter(”requestString”)
    
    %>
    
- include와 마찬가지로 forward 된 페이지에서도 request 영역의 객체는 공유된다.
    
    <%
    
    request.getRequestDispatcher(“RequestForward.jsp?paramHan=한글”).forward(request,response)
    
    %>
    
    → 위 코드는 아래와 같이 작성도 가능
    
    <%
    
    requestDispatcher rd = request.getRequestDispatcher(“RequestForward.jsp?paramHan=한글”);
    
    rd.forward(request, response);
    
    %>
    
- forwarding된 페이지에서 객체 사용시에는 그냥 request로 사용하면 된다.
- redirect로 연 페이지라면 request 공유 X
- 만약 checkbox와 같이 다중 값을 넘기고 싶을 때에는 request.getParameterValues()[]를 사용하고 일반적인 post값은 request.getParameter()를 사용해서 접근이 가능하다.

**Session 영역**

- client가 웹 브라우저를 최초로 열고난 후 닫을 때까지 요청되는 모든 페이지는 session객체를 공유할 수 있다.
- Session: 서버에 접속해 있는 상태 혹은 단위를 말하는 것으로 주로 로그인 상태를 유지하는 처리에 사용된다.
    
    <%
    
    session.setAttribute(“lists”, lists);
    
    session.getAttribute(“lists”);
    
    %>
    

**Application 영역**

- 웹 애플리케이션은 단 하나의 application 객체만 생성하고 클라이언트가 요청하는 모든 페이지가 application 객체를 공유하게 된다. 해당 객체는 웹 브라우저를 닫았다가 새롭게 접속해도 삭제되지 않고 서버 자체를 내려야 삭제된다.
    
    <%
    
    Map<String, Person> maps = new HashMap<>();
    
    Maps.put(“actor1”, new Person(“이수일”,30));
    
    Maps.put(“actor2”, new Person(“심순애”,30));
    
    application.setAttribute(“maps”,map);
    
    application.getAttribute(“maps”);
    
    %>
    
    > Map은 그냥 자료구조 사용법 확인하기.
    
    ![Untitled](3%20%E1%84%82%E1%85%A2%E1%84%8C%E1%85%A1%E1%86%BC%20%E1%84%80%E1%85%A2%E1%86%A8%E1%84%8E%E1%85%A6%E1%84%8B%E1%85%B4%20%E1%84%8B%E1%85%A7%E1%86%BC%E1%84%8B%E1%85%A7%E1%86%A8(Scope)%2085b8f283cdd24b56b622bd29b2f411b8/Untitled.png)
    

---

해당 챕터에서 가장 중요하게 생각해야 할 것은 JSP에서 각각의 Data를 어떻게 처리하는 지이다. 단순히 변수를 사용해서 data들을 다루어도 되지만 웹 서비스는 계속해서 페이지가 바뀌기 때문에 여러 data들을 사용하는 용도에 따라서 분류하고 저장 시켜야 함

- 이때 내장 객체가 일종의 전역 변수의 역할을 해주는 것이다.