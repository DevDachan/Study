# 1. JSP 기초

**JSP(Java Server Page)** : 동적 웹 페이지 개발 기술

- web 기본 용어
    
    Server(서버) : 웹에서 서비스를 제공하는 컴퓨터
    
    Web Server(웹서버) : 사용자로부터 HTTP를 통해 요청 받는 S/W
    
    Web Container(웹 컨테이너) : 웹 서버가 전송해준 요청을 기초로 동적 웹 페이지 생성
    
![Untitled](https://user-images.githubusercontent.com/111109411/212545399-11f084ae-4dfe-4655-8fd0-7f7d0eb5bebc.png)


웹 페이지는 기본적으로 2가지로 분류된다.

1. **정적 웹 페이지** : 웹 서버에 저장되어 있는 파일을 그대로 웹 브라우저에 전송해주는 가장 기본적인 웹 페이지
2. **동적 웹 페이지** : 그때그때 달라지는 데이터를 표현하기 위해 서버가 클라이언트의 요청을 해석하여 가장 적절한 페이지를 표현

![Untitled 1](https://user-images.githubusercontent.com/111109411/212545437-2fe3248b-ff42-44c7-b735-f6b8f53d2e9b.png)


**Applet VS Servlet**

**애플릿**이란 가장 고대의 자바 기술을 사용한 동적 웹 페이지 기술로 웹에서 실행하도록 설계된 자바 App을 통째로 웹 브라우저로 전송 한 뒤 JVM을 탑재한 브라우저가 이를 실행

**서블릿**이란 애플릿의 속도, 보안, 유연성의 문제를 보완하여 서버 측에서 실행하도록 만듦

서블릿이 직접 당당하는 부분은 ‘전처리’ 부분이며 .java를 컴파일한 .class 형태를 사용하고 대표적인 서블릿 컨테이너가 Tomcat이다.

![Untitled 2](https://user-images.githubusercontent.com/111109411/212545455-befb4f64-f812-4b4d-ba7b-51c90f19bd9e.png)


**JSP(Java Server Page)**

서블릿을 사용하면서 모든 HTML 코드를 자바를 사용해 생성하다 보니 너무 많은 코드가 필요 했고 이것을 보완하고자 기본을 HTML을 사용하고 필요한 부분만 JAVA를 사용하도록 바꾼 것이 JSP이다.

![Untitled 3](https://user-images.githubusercontent.com/111109411/212545472-fce36d4c-748f-43da-ad76-ddec02eab52d.png)


**가장 최근의 웹 사이트**

- 모든 것을 동적으로 웹 페이지를 구성하는 것은 좋은 것이 아니다. 때로는 속도 적인 측면과 효율 측면에서정적인 웹 페이지를 사용해야 할 때도 있다. 그래서 오늘날의 웹 사이트는 동적 웹페이지 + 정적 웹 페이지 두개를 모두 사용하는 형태로 변화되었다.


![Untitled 4](https://user-images.githubusercontent.com/111109411/212545481-777b029e-2939-4989-86f9-2bacafb64fcb.png)


**JSP 사용법**

- JSP를 사용하면서 기본적으로 스크립트 요소를 선언하는 방법은 HTML 코드에서 <% %>를 통해 일반 자바에서 사용하는 변수, method등을 선언해 사용 하는 것이다. 스크립트의 진행 방식은 아래로 순차적으로 내려가면서 실행이 된다

**지시어**

- 지시어란 JSP 페이지를 자바 코드로 변환하는데 필요한 정보를 JSP 엔진에 알려주는 역할을 한다. 보통은 스크립트 언어나 인코딩 방식등을 설정하고 import하는 부분도 지시어에 해당한다
- 지시어는 <%@ %>와 같이 @를 하나 더 붙여주면 된다

**지시어 종류**

page : JSP페이지에 대한 정보 설정합니다.

include : 외부 파일을 현재 JSP 페이지에 포함시킵니다

taglib : 표현 언어에서 사용할 자바 클래스나 JSTL을 선언합니다

1. **page 지시어**
    ```
    <%@ page language=”java” contentType=”text/html; charset=UTF-8” pageEncoding=”UTF-8” %>
    
    <%@ page import=”java.util.Data” %>
    ```
    Error 처리
    
    1) try-catch사용: 자바에서 사용하듯이 try-catch문을 사용해서 직접적으로 예외처리
    ```
    <%
    
    try {
    
    }catch (exception e){
    
    out.println(“예외 발생”);
    
    }
    
    %>
    ```
    2) **errorPage, isErrorPage** 속성 사용
    ```
    <%@ page language=”java” contentType=”text/html; charset=UTF-8” pageEncoding=”UTF-8”
    
    errorPage=”isErrorPage.jsp” %>
    
    è 이런식으로 만약 error가 발생 할 경우 isErrorPage.jsp 페이지로 넘어가도록 처음 page를 설정
    
    isErrorPage.jsp
    
    <%@ page language=”java” contentType=”text/html; charset=UTF-8” pageEncoding=”UTF-8” isErrorPage=”true” %>
    
    <p>
    
    오류명 : <%= exception.getClass().getName %>
    
    오류 메시지 : <%= exception.getMessage() %>
    
    </p>
    ```
- 이런 식으로 isErrorPage를 true로 설정해줄 경우 이전에 발생한 error 내용을 가져올 수 있다.

**<%@ page trimDirectiveWhitespaces=”true” %>** : 페이지를 사용하면서 안드로이드와 같은 외부 기기와 연동 시에는 import하는 부분이 실제로 페이지에서는 공백으로 보여서 문제가 될 수 있다. 

(공백도 엄연히 문자로 취급되기 때문에). 이런 상황을 대비하여 trimDirectiveWhitespaces를 true로 활성화 시켜면 page 지시어로 생긴 공백을 제거해준다.

1. **include 지시어**

<%@ include file=”파일 경로”%>

**스크립트 요소**

- 스크립트 요소는 JSP에서 자바 코드를 직접 작성할 수 있게 해준다. 용도에 따라 선언부, 스크립틀릿, 표현식이 있다.

1) 선언부     <%! %> : !를 추가해서 선언부임을 알려주고 멤버 변수나 method를 선언할 때 사용한다.

2) 스크립틀릿 <% %>: 앞에서도 계속 사용하던 부분인데 일반적인 자바 코드를 작성하는 영역이다.

3) 표현식     <%= %>: =를 추가함으로써 실행 결과로 하나의 값이 남는 문장을 의미한다. 즉 변환 값이 존재해서 HTML에서 직접적인 값을 표현하는 부분을 말한다.
