# 9. JSTL

**JSTL이란?**

- JSTL(JSP Standard Tag Library)은 JSP에서 자주 사용되는 조건문, 반복문 등을 처리해주는 태그들을 모아 표준으로 만들어 놓은 라이브러리이다. JSTL을 사용하면 스크립틀릿 없이 태그만으로 표현이 가능하기에 코드가 간결해진다.
    
    ![Untitled](9%20JSTL%202f77f1bf0372408b9ec0c60b196f30cb/Untitled.png)
    

< c:forEach begin=”start” end=”end” var=”var name”> </forEach> : 가장 많이 사용되는 태그 중 하나인 for문 태그이다. for문에서 시작, 끝 값을 지정하고 변수 이름을 지정해서 사용한다.

- 기본적으로 JSTL은 5가지 종류의 태그가 존재한다. 이중에서 주로 반복문, 조건문에서 사용되는 태그는 core 태그임

![Untitled](9%20JSTL%202f77f1bf0372408b9ec0c60b196f30cb/Untitled%201.png)

- JSTL을 사용하기 위해서는 JSP파일에서 taglib 지시어를 추가해야 한다. 이때 접두어와 URI가 사용된다.
    
    Ex) <%@ taglib prefix=”c” uri=”http://java.sun.com/jsp/jstl/core” %>
    

**JSTL 사용 설정**

- JSTL은 JSP의 기본 태그가 아닌 확장 태그이므로 별도의 라이브러리가 필요하다. 때문에 직접 해당 라이브러리를 다운 받아 환경에 추가시켜 줘야 함
    
    [https://mvnrepository.com/artifact/javax.servlet/jstl/1.2](https://mvnrepository.com/artifact/javax.servlet/jstl/1.2)
    
- 다운받은 library는 WEB-INF/lib에 jar형식으로 넣어주면 된다.

**코어(Core) 태그**

- Core태그는 프로그래밍 언어에서 가장 기본이 되는 변수 선언, 조건문, 반복문 등을 대체하는 태그를 제공한다.

![Untitled](9%20JSTL%202f77f1bf0372408b9ec0c60b196f30cb/Untitled%202.png)

**<c:set> 태그**

- set태그는 EL에서 사용할 변수나 자바빈즈를 생성할 때 사용한다. JSP에서 특정 영역에 속성을 저장할 때 사용하는 setAttribute()와 동일한 역할을 한다.
    
    <c:set var=”변수명” value=”값” scope=”영역”>
    
    or
    
    <c:set var=”변수명” scope=”영역”>
    
    Value 속성에 들어갈 값
    
    </c:set>
    
    - 만약 자바빈즈나 Collection을 생성할 때는 target과 property 속성을 사용한다.
    
    <c:set var=”변수명” value=”저장할 객체 혹은 collection” scope=”영역” / >
    
    <c:set target=”var로 설정한 변수명” property=”객체의 속성명” value=”속성 값”>
    

![Untitled](9%20JSTL%202f77f1bf0372408b9ec0c60b196f30cb/Untitled%203.png)

- 이런 식으로 특정 자바빈즈나 collection을 사용하면 처음 set을 하면서 변수 명, 저장할 객체나 collection의 constructor or 만들어진 객체를 지정해주고 아래에 target을 통해서 값을 저장한다.

**<c:remove> 태그**

- remove 태그는 set 태그로 설정한 변수를 제거할 때 사용이 되고 JSP에서 removeAttribute() method와 동일한 역할을 한다.

<c:remove var=”변수명” scope=”영역”>

- 만약 영역을 지정하지 않을 경우에는 동일한 이름의 변수가 모두 삭제된다.

**<c:if>태그**

- 자바의 if와 동일하게 제어 구문을 작성할 때 사용이 되는데 별도의 else문이 존재하지 않아 여러 조건을 나열하는 형태로 코드 작성은 힘들다
    
    <c:if test=”조건” var=”변수명” scope=”영역”>
    
    조건이 true일 때 출력할 문장
    
    </c:if>
    

![Untitled](9%20JSTL%202f77f1bf0372408b9ec0c60b196f30cb/Untitled%204.png)

- else문이 존재하지는 않지만 var에 들어가는 값을 통해서 else와 비슷하게 구문을 작성하는 것이 가능
    
    (Eq는 EL의 ==를 의미한다.)
    
- 이런 식으로 if의 조건이 되는 것을 같은 result를 사용한다면 else구문을 사용하는 것을 흉내 낼 수 있다.

**<c:choose>, <c:when>, <c:otherwise> 태그**

- choose 태그는 다중 조건을 통해 판단해야 할 때 사용하고 하위 태그로는 when과 otherwise 태그를 함께 사용한다.
- if/else구문을 하나로 합쳐 놓은 것이라고 생각하면 된다.
    
    <c:choose>
    
    <c:when test=”조건1”>조건1을 만족하는 경우 </c:when>         // if(조건1)
    
    <c:when test=”조건2”>조건2을 만족하는 경우 </c:when>            // else if (조건2)
    
    <c:otherwise>아무 조건도 만족하지 않는 경우 </c:otherwise>     // else
    
    </c:choose>
    

**<c:forEach>태그**

- 반복문을 위해 사용되는 태그로 두가지 형태의 for문을 제공한다. 시작과 종료를 지정하는 일반 for문과 배열이나 collection을 순회하는 iterator 반복문의 기능을 제공한다.
    
    <c:forEach var=”변수명” begin=”시작 값” end=”마지막 값” step=”증가 값” /> // 증가 없을 경우 default = 1
    
    <c:forEach var=”변수명” items=”collection or array” />
    
- loop를 돌면서 속성 추가로 현재 상태를 알려주는 변수 설정이 가능하다 ( varStatus=”저장 변수명”)

![Untitled](9%20JSTL%202f77f1bf0372408b9ec0c60b196f30cb/Untitled%205.png)

![Untitled](9%20JSTL%202f77f1bf0372408b9ec0c60b196f30cb/Untitled%206.png)

- 제어문에서부터 보였던 것처럼 이러한 태그 =안에서 특정 자바 변수를 사용하기 위해서는 표현식으로 나타내야 한다.

- c:set을 사용해 변수 선언된 data의 경우에는 표현식이 아니라 EL을 통해서 나타낼 수 있다.

![Untitled](9%20JSTL%202f77f1bf0372408b9ec0c60b196f30cb/Untitled%207.png)

**<c:forTokens> 태그**

- 자바의 StringTokenizer 클래스처럼 구분자를 기준으로 문자열을 나눠 토큰 개수만큼 반복해준다.
    
    <c:forTokens items=”문자열” delims=”문자열 구분자” var=”변수명”>
    

**토큰이란?**

- 일반적으로 토큰이란 의미 있는 최소 단위를 말하는데 여기서는 문자열을 구분할 구분자로 분리되는 문자열의 구성 요소를 의미한다.
- 쉽게 생각하면 for문을 사용하면서 iterator와 같이 사용이 되는데 특정 변수가 아닌 string의 구분자로써 반복이 되는 것이다.

![Untitled](9%20JSTL%202f77f1bf0372408b9ec0c60b196f30cb/Untitled%208.png)

**<c:import> 태그**

- import태그는 include 액션 태그와 같이 외부 파일을 현재 위치에 삽입할 때 사용한다. 또한 같은 웹 애플리케이션에 속하지 않은 외부 페이지도 삽입이 가능하다.
    
    <c:import url=”페이지 경로 혹은 URL” scope=”영역” />
    
    or
    
    <c:import url=”페이지 경로 혹은 URL” var=”변수명” scope=”영역” />
    
- 위와 같이 만약 var를 통해 변수 명을 추가해준 다면 외부 페이지가 지정한 변수에 저장이 되어 나중에도 사용이 가능해진다.
    
    <c:import url=”페이지 경로 혹은 URL?매개변수1=값1” scope=”영역” />
    
    <c:param name=”매개변수2” value=”값2”>
    
    </c:import>
    
- 만약 매개변수로 전달할 값이 있다면 url에 Query-String으로 직접 추가하거나 <c:param>태그를 사용해서 추가하면 된다.

**<c:redirect> 태그**

- redirect태그는 response 내장 객체의 sendRedirect()와 동일하게 페이지 이동을 처리한다.
    
    <c:redirect url=”이동할 경로 및 URL”>
    
- 만약 매개변수를 전달하고 싶다면 <c:import>태그와 동일하게 param을 사용하거나 쿼리문을 사용하면 된다.
- 또한 redirect의 경우에는 같은 웹 애플리케이션 내에서 이동하는 것이므로 request 영역에 추가해도 된다.
    
    <c:set var=”requestVar” value=”musthave” scope=”request”>
    
    <c:redirect url=”/list/test.jsp?datam=a”>
    
    <c:param name=”user_param” value=”테스트” />
    
    </c:redirect>
    
- 어차피 c:param으로 추가된 내용은 query에 추가된다.

**<c:url> 태그**

- url태그는 지정한 경로와 매개변수를 이용해서 context root를 포함한 URL을 생성한다. 이렇게 생성된 URL은 a 태그의 href속성이나 form의 action 속성에서 사용이 가능하다.
    
    <c:url value=”설정한 경로” scope=”영역” />
    
    or
    
    <c:url value=”설정한 경로” var=”home_page” scope=”영역” />
    
- 물론 url 태그를 사용하면서 param도 미리 정해줄 수 있다.

**<c:out> 태그**

- JSP의 표현식처럼 변수를 출력할 때 사용한다.
    
    <c:out value=”출력할 변수” default=”기본값” escapeXml=”특수문자 처리 유무” / >
    
- out태그가 표현식과 다른 점은 출력할 변수가 null일 경우 default 속성에 지정한 기본 값이 출력 된다는 것이다. 또한 escapeXml속성을 가지고 HTML 태그를 자유롭게 표현할 수 있다.
    
    <c:out value=”${param.name}” default=”이름 없음”>
    
    <c:out value=”<script type=’text/javascript’> alert(1);</script>” excapeXml=”true” >
    

**<c:catch> 태그**

- catch태그는 발생한 예외를 잡아 처리하는 역할을 한다. 예외가 발생하면 지정한 변수에 에러 메시지가 저장되어 전달 된다.
    
    <c:catch var=”변수 명”>
    
    //실행 코드
    
    <%
    
    Int result = num / 0;
    
    %>
    
    ${“일” + num2}
    
    </c:catch>
    
    예외 내용 : ${ 변수 명 }
    

**국제화(Formatting) 태그**

- JSTL의 Formatting 태그는 국제화 태그로 국가별로 다양한 언어, 날짜, 시간, 숫자 형식을 설정 할 때 사용된다. 접두어로는 fmt를 사용한다. 처음 부분에 해당 태그를 사용하기 위한 선언부가 필요하다.
    
    <%@ taglib prefix=”fmt” uri=”http://java.sun.com/jsp/jstl/fmt” %>
    
    ![Untitled](9%20JSTL%202f77f1bf0372408b9ec0c60b196f30cb/Untitled%209.png)
    

**숫자 포맷팅<fmt:formatNumber>**

<fmt:formatNumber value=”출력할 숫자” type=”문자열 양식 패턴” var=”변수 설정”

groupingUsed=”구분 기호 사용 여부” pattern=”숫자 패턴” scope=”영역” />

- groupingUsed: 세자리마다 콤마를 출력할지 여부를 결정하는 것이다. 기본값은 true이다.
- type: 출력 양식을 결정하는 것으로 percent, currency(통화), number등을 지원한다.

![Untitled](9%20JSTL%202f77f1bf0372408b9ec0c60b196f30cb/Untitled%2010.png)

**숫자 파싱<fmt:parseNumber>**

<fmt:parseNumber value=”파싱할 문자열” type=”출력 양식” var=”변수 설정” integerOnly=”정수만 파싱” pattern=”패턴” scope=”영역” />

- integerOnly: 기본 값은 false로써 정수 부분만 표시할지 여부를 결정한다.

![Untitled](9%20JSTL%202f77f1bf0372408b9ec0c60b196f30cb/Untitled%2011.png)

**날짜 포맷 <fmt:formatDate>**

- 날짜와 시간 포맷을 지정하는 태그이다.
    
    <fmt:formatDate value=”출력할 날짜” type=”출력 양식” var=”변수 설정” 
    
    dateStyle=”날짜 스타일” timeStyle=”시간 스타일” pattern=”날짜 패턴” scope=”영역” />
    
- dateStyle, timeStyle: default, short, medium, long, full 중 선택 할 수 있다.
- type: 날짜, 시간, 날짜 및 시간 3가지 중 하나를 선택 할 수 있다.

![Untitled](9%20JSTL%202f77f1bf0372408b9ec0c60b196f30cb/Untitled%2012.png)

**타임존<fmt:timeZone>**

- 출력할 시간의 시간대를 설정 할 수 있다. Ex) GMT, KST등

![Untitled](9%20JSTL%202f77f1bf0372408b9ec0c60b196f30cb/Untitled%2013.png)

**로케일 설정**

- setLocale 태그는 국가별로 다른 통화 기호나 날짜를 표현할 때 사용한다.
    
    <fmt:setLocale value=”ko_kr” />
    
    <fmt:setLocale value=”ja_JP” />
    
    <fmt:setLocale value=”en_US” />
    
- 이런 식으로 특정 국가를 설정하게 되면 해당 국가에서 사용하는 화폐나 시간이 자동으로 설정 된다.

**XML 태그**

- XML 문서를 처리하기 위한 것으로 XML 파싱 및 출력, 흐름제어 등의 기능을 제공해주는데 XML 태그를 사용하기 위해서는 “x”접두어의 지시어가 선언되어야 한다.
    
    <%@ taglib prefix=”x” uri=”http://java.sun.com/jsp/jstl/xml”%>
    

![Untitled](9%20JSTL%202f77f1bf0372408b9ec0c60b196f30cb/Untitled%2014.png)

- XML 태그의 종류는 core 태그의 종류와 유사하다.
- XML 태그는 XML문서의 Element에 접근하기 위해서 XPath를 사용한다. 여기서 XPath는 XML문서의 노드를 식별하고 탐색하는 역할을 한다.

![Untitled](9%20JSTL%202f77f1bf0372408b9ec0c60b196f30cb/Untitled%2015.png)

- 이런 식으로 두권의 책 정보를 담고 있는 XML문서가 있다고 가정해보자.

- XML 문서를 처리하기 위해서 먼저 <c:import> 를 사용해서 .xml 문서를 읽어와야 한다.
    
    <c:import url=”../inc/BookList.xml” var=”booklist” charEncoding=”UTF-8” />
    
    <x:parse xml=”${ booklist }” var=”blist”>    // 말 그대로 xml 파일을 파싱하기
    
    <x:out select=”$blist/booklist/book[1]/name” />
    
    <x:out select=”$blist/booklist/book[1]/author” />
    
    <x:out select=”$blist/booklist/book[1]/price” />
    
- 이렇게 .xml 파일 안에 존재하는 booklist 안에 book중 첫번째에 존재하는 tag의 정보에 접근이 가능하다.

<x:chooose>

<x:when select=”$item/price >= 20000”>

2만원 이상 <br />

</x:when>

<x:otherwise>

2만원 미만 <br />

<x:otherwise>

</x:chooose>

- 특정 data만을 선택하도록 하기 (조건문 사용)