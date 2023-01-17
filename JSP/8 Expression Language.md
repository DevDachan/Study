# 8. Expression Language

**표현 언어(Expression Language)란?**

- 표현 언어는 변수의 값을 출력할 때 사용하는 스크립트 언어이다. 지금까지는 표현식 (<%= %>)만으로도 충분히 값을 출력할 수 있었지만 모든 영역에 값을 출력할 수 없기에 표현 언어를 사용하여 4가지 영역에 저장된 값을 출력할 수 있다.
- 표현 언어는 매우 간결하고 형변환과 예외에 있어서 관대하다는 특징이 있다.

- JSP 내장 객체의 영역에 담긴 속성을 사용할 수 있다.
- 산술, 비교, 논리 연산이 가능하다
- 자바 클래스에 정의된 메서드를 호출할 수 있다.
- 표현 언어만의 객체를 통해 JSP와 동일한 기능을 수행 할 수 있다.

**EL 기본 사용법**

${ 속성 } : 여기서 속성은 영역에 저장된 속성을 말한다. 표현식은 값을 바로 쓸 수 있지만 EL은 JSP에서 생성한 변수에 접근하기 위해서는 반드시 영역에 저장 후 사용해야 한다.

1. ex) <h2>${ saveVar } </h2>
- EL은 HTML 태그나 자바스크립트, CSS 어디에서든 사용할 수 있다.
    
    <c:set var=”elVar” value=”${ elVar }”  / >   액션 태그와 함께 사용
    
    <jsp:include page=”${ pathVar }”>
    
- 한 가지 주의해야 할 점은 JSP 스크립트 요소 (선언부, 표현식, 스크립틀릿)에서는 사용이 불가능하다.
    
    <%!
    
    void myMethod (${ errorVar }){
    
    }
    
    %>  // Error
    
- EL은 그냥 쉽게 생각하면 HTML이나 CSS와 같은 부분에서 자바의 객체나 변수를 직접 넣고 싶을 때 사용하는 언어라고 생각하면 된다.

**객체 표현 방식**

- EL에서 객체를 표현할 때는 .(점)이나 [ ](대괄호)를 사용한다.
    
    ${ param.name }
    
    ${ param[“name”] }
    
- 만약 특수기호나 한글이 포함되어 있을 경우에는 대괄호만 사용 가능
    
    ${ header[“user-agent”] }
    
    ${ header.user-agent }      // Error
    
    ${ King[“한글”] }
    
    ${ King.한글 }                // Error
    

**EL 내장 객체 (4가지 영역에 속성값 저장하고 읽어오기)**

pageScope, requestScope, sessionScope, applicationScoper : 각각의 영역의 data를 접근할 때는 다음과 같은 객체 명으로 접근이 가능하다.

${ pageScope.scopeValue }

${ requestScope.scopeValue }

${ sessionScope.scopeValue }

${ applicationScope.scopeValue }

- scopeValue란 해당 영역에 저장되어 잇는 속성 값을 출력해준다.
- 만약 영역을 따로 지정하지 않고 socpeValue를 출력 할 경우에는 request영역의 value가 출력되게 된다.

**Form Data 처리하기**

- JSP에서는 전송 방식(GET/POST)에 상관 없이 request.getParameter()로 data를 받을 수 있다. EL에서도 동일하게 form data를 받아올 수 있음
    
    param: request.getParameter(“매개변수명”)과 동일하게 요청 매개변수의 값을 불러온다.
    
    paramValues: request.getParameterValues(“매개변수명”)과 동일하게 요청 매개변수의 값을 String array로 받아온다. 주로 checkbox와 같이 다중 선택이 가능한 form data를 받을 때 사용한다.
    
    ${ param.name }     &     ${ paramValue.inter[1] }
    

**객체 전달하기**

- 객체는 form으로 전송이 안되기 때문에 특정 영역을 가지고 전송을 해줘야 한다. 여러 페이지에서 객체를 공유하기 위해서는 특정 영역에 저장하고 해당 내장 객체의 영역이 공유된다는 특징을 이용해야 한다.
    
    <%
    
    request.setAttribute(“personObj”, new Person(“홍길동”,33));
    
    %>
    
    <jsp:forward page=”ObjectResult.jps”>
    
    <jsp:param value=”10” name=”firstNum”>
    
    <jsp:param value=”20” name=”secondNum”>
    
    </jsp:forward>
    
- 이런 식으로 forward 전송을 하게 되면 이전에 request영역에 저장했던 내용과 forward form안에서 param으로 선언된 변수들이 같이 다음 페이지로 전송이 된다.

- forward는 기본적으로 request 영역을 공유한다.

![Untitled](8%20Expression%20Language%20accc9f69b6f64a65930b4ef2d77959e4/Untitled.png)

- 이처럼 순수 JSP로 만들어서 나타내는 것을 EL로는 더 간략하게 나타내기가 가능하다. request영역에 저장된 객체들은 특정 객체 형식이 지정된 것이 아니라 Object 객체 형식을 띄고 있기 때문에 Object로 불러온 뒤에 형변환 과정을 거쳐야 한다. 하지만 request 영역에 EL로써 저장을 한다면 형변환이 필요 없음

**쿠키, HTTP 헤더, 컨텍스트 초기화 매개변수 출력하기**

- cookie: 쿠키 읽을 때 사용
- header: request.getHeader(헤더명)와 동일하게 헤더 값 읽을 때 사용
- headerValues: request.getHeaders(헤더명)과 동일하게 헤더값을 배열 형태로 읽을 때 사용
- initParam: web.xml에 설정한 Context 초기화 매개변수를 읽을 때 사용. (context 초기화 매개변수 : 페이지가 처음 실행될 때 미리 필요한 데이터를 입력 시켜 놓은 것)
- pageContext: JSP의 pageContext 내장 객체와 동일한 역할을 한다. (Page영역에 저장된 객체)

**collection 사용하기**

- collection이란 여러 데이터들을 효율적으로 관리하기 위해 하나의 자료 구조로 합쳐 그룹화한 객체를 말한다. 기본적으로 Map, Set, List, Queue등이 포함 된다.
- JSP에서 collection data를 넘길 때는 동일하게 EL을 사용할 수 있다.

![Untitled](8%20Expression%20Language%20accc9f69b6f64a65930b4ef2d77959e4/Untitled%201.png)

- EL 코드 주석처리 방법은 앞에 \를 붙여주기.

**EL의 연산자들**

- 자바에는 산술 연산자, 비교 연산자 등 여러가지 연산자가 있다. 이것은 EL에서도 동일하게 제공한다.
- 할당 연산자
    
    ${ numberVar = 10}  ß 할당과 동시에 출력
    
    ${ numberVar = 10, ‘’} ß 할당만 되고 출력은 되지 않음
    
- 산술 연산자
    
    +, -, *, /(div), %(mod)
    
- 비교 연산자
    
    >(gt), >=(ge), <(lt), ==(eq), !=(ne)
    
- 논리 연산자
    
    &&, ||, !
    
- empty 연산자
    
    Null, 빈문자열, 길이가 0인 배열, size가 0인 collection
    
- 삼항 연산자
    
    ${ 조건 ? “true일 때 선택” : “false일 때 선택” }
    

**EL에서 인스턴스 메서드 호출**

- EL에서 자바코드를 직접 사용할 수 없기에 EL은 method를 호출 할 수 있는 방법을 제공해준다.
- 가장 먼저 el이라는 패키지를 만들어 그 안에서 작성해줘야 한다.

![Untitled](8%20Expression%20Language%20accc9f69b6f64a65930b4ef2d77959e4/Untitled%202.png)

- Java Resources/src/el 경로 내부에 특정 class 생성

(예제에서는 getGender, isNumber, showGugudan 3개 method를 class에 포함 시켜 놓음)

**메서드 호출하기**

- EL은 영역에 저장된 값을 가져오는 기법이므로 method를 호출하려면 먼저 객체를 만들어 영역에 저장해야 한다.

<%@ page import=”el.MyELClass”%>

<%

MyELClass myClass = new MyELClass();

pageContext.setAttribute(“myClass”, myClass)

%>

<p> 001225-300000 => ${ myClass.getGender(“001225-300000”) }

- 이런 식으로 page 처음 부분에 pageContext에 ELclass를 추가 시켜 준 뒤 필요한 method를 빼서 사용하면 된다.

**정적 메서드 호출**

- EL에서 자바 클래스에 정의된 정적 메서드를 호출하는 방법은 크게 클래스명을 통해 호출하는 방법과 TLD를 이용하는 방법이 있다.

**Class명을 통한 정적 메서드 호출**

<%@ page import=”el.MyELClass”%>

${ MyELClass.showGugudan(7) }

**TLD를 이용한 정적 메서드 호출**

- 여기서 TLD란 Tag Library Descriptor로써 사용자 정의 태그나 JSTL 태그들을 설정하기 위한 XML파일을 말한다. WEB-INF폴더에 작성하며 xml대신 tld 파일 확장자를 사용한다.
- 호출 과정
    
    1) 호출할 메서드를 담은 자바 클래스를 작성한다. (public으로 선언한 정적 메서드만 호출 가능)
    
    2) TLD 파일을 생성한 후 클래스와 메서드를 등록한다.
    
    3) JSP 파일에서 taglib 지시어로 tld 파일의 경로와 이 tld를 지칭할 접두어를 설정한다.
    
    4) 접두어를 통해 EL에서 메서드를 호출한다.
    

**Eclipse 생성 방법**

[New] -> [Other] ->[XML File] -> 파일명 tld 확장자로 바꾸기

- > Create XML file from an XML schema file
- > Select XML Catalog entry (java.sun.com/xml/ns/j2ee ~~~ web-jsptaglibrary_2_0.xsd)
- > Namespace 첫번째 항목 Edit -> Prefix 비우고 OK
- > Finish

![Untitled](8%20Expression%20Language%20accc9f69b6f64a65930b4ef2d77959e4/Untitled%203.png)

**TLD 파일에 메서드 등록**

- 생성된 TLD 파일에 version, function, short-name등을 바꿔주면 된다.
- 내용을 바꿀 때는 아래쪽에 위치한 source tab에서 수정을 하면 되고 해당 library안에 이전에 만들어 놓은 class의 method들을 넣어주면 된다.
    
    function-class : method가 포함되어 있는 class 명 (package명까지 포함시켜야 한다.)
    
    function-signature : return type과 매개변수 포함 (매개변수 또한 package와 Class를 포함한 객체 명)
    
    [String = java.lang.String,   Date=java.util.Date, Object = java.lang.Object]
    

**JSP에서 호출**

- JSP에서 호출 할 때는 uri에 tld 파일을 추가 시켜 주고 그냥 method 호출하면 된다.

<%@ taglib prefix=”mytag” uri=”/WEB-INF/MyTagLib.lib”%>

<p> mytag:isNumber(100) => ${ mytag:isNumber(100) }

tag library을 추가하는 지시어는 taglib이며 prefix 속성에는 EL에서 사용할 접두어를 지정하고 uri에는 tld파일의 경로를 지정해준다.