# 7. Action Tag

**액션 태그란?**

- 액션 태그는 JSP의 표준 태그로 페이지 사이에서 이동을 제어하거나 자바빈을 생성할 때 사용되다. 특별한 선언 없이 <jsp:태그명 / >의 형태로 사용이 되고 태그처럼 작성이 되지만 그 뒤에서는 JSP가 수행된다. 즉 JSP와 마찬가지로 WAS에서 처리 된 후 결과만 출력되어서 웹 브라우저 측에서는 액션태그가 보이지 않는다.

**액션 태그 특징**

- 기본적으로 XML 문법을 따른다.
- 반드시 종료 태그를 사용해야 한다.
- 액션 태그 사이에 주석을 사용하면 에러가 발생한다.
- 액션 태그에 속성값을 부여 할 때는 표현식 <%=%>을 사용할 수 있다.
    
    **<jsp:include>:** 외부 파일을 현재 파일에 포함시킨다.
    
    **<jsp:forward>:** 다른 페이지로 요청을 넘긴다.
    
    **<jsp:useBean>, <jsp:setProperty>, <jsp:getProperty>:** 자바빈즈를 생성하고 값을 설정/추출한다
    
    **<jsp:param>:** 다른 페이지로 매개변수를 전달한다. 보통은 include, forward 액션 태그와 함께 사용한다.
    

**<jsp:include>**

- include 액션 태그는 외부 JSP파일을 현재 JSP파일로 포함시키는 기능을 한다. 그런데 우리는 이전에 include 지시어를 이미 배웠다. 둘은 비슷한 역할을 하지만 동작 방식의 차이가 있다.

**자바 빈즈란?**

- 자바 빈즈란 JSP의 표준 액션 태그로 접근 할 수 있는 자바 클래스로서 값을 가지는 속성(멤버변수), 값을 설정하는 메소드(setter), 값을 추출하는 메소드(getter)로 이루어져 있다.
    
    위에서 배운 내용에 따르면 UserDTO가 자바빈즈다.
    
- 자바 빈즈는 패키지로 존재를 해야 하고 해당 패키지 안에 UserDTO와 같은 class들이 여러 개 존재한다고 보면 된다.
- 그냥 쉽게 생각하면 모든 페이지에서 같이 사용할 수 있는 구조체를 만들어 놓은 것이다.

**→ 장점**

1) 폼 데이터 처리 용이

form은 사용자에게 Data를 입력 받아 처리를 하는 것인데 자바빈즈를 사용 할 경우 하나의 액션 태그로 한번에 정의가 가능

2) 자바빈즈 활용 범위 확장 용이

웹에서는 HTTP 프로토콜의 무상태 특성 때문에 상태 정보를 유지해야 하는데 자바빈즈를 이용하면 쉽게 유지가 가능하다. 

3) 컴포넌트 기반 구현 가능

JSP 페이지 내에서 필요한 비즈니스 로직을 컴포넌트로 만들어 사용하게 되면 페이지 크기가 작아지고 재활용성이 늘어난다.

**include 지시어 vs 액션태그**

![Untitled](7%20Action%20Tag%208b8416e387e047a49ba6c3e9c4b2c753/Untitled.png)

- 기본적인 기능에 있어서 특정 페이지를 현재 페이지에 포함시키는 것인데 지시어의 경우에는 페이지 자체를 현재 페이지에 포함 시킨 후 컴파일을 진행 하고 액션태그의 경우에는 실행의 흐름을 포함시킬 페이지로 이동시킨 후 실행한 결과를 현재 페이지에 포함 시킨다. 때문에 액션 태그의 경우 page영역의 객체가 공유되지 않는다.

![Untitled](7%20Action%20Tag%208b8416e387e047a49ba6c3e9c4b2c753/Untitled%201.png)

- 지시어를 통한 include는 페이지를 원본 그대로 현재 페이지에 포함 시킨 후 컴파일 하기 때문에 JSP코드만 서로 다른 페이지로 모듈화 시키는 것이다.

- 반면 액션 태그를 사용해서 include를 하게 되면 포함시킬 페이지로 요청의 흐름이 이동되어 컴파일을 진행하게 된다. 때문에 컴파일 된 결과물을 현재 페이지에 삽입하게 되고 단순한 HTML 형태로 변환된다.

![Untitled](7%20Action%20Tag%208b8416e387e047a49ba6c3e9c4b2c753/Untitled%202.png)

- 만약 액션태그의 포함되는 페이지로 매개변수를 넘기려면 <jsp:param>을 이용해서 넘겨야 한다. 이후 해당 내용에 대해서 더 자세하게 다룸.

**<jsp:forward>**

- forward는 현재 페이지에 들어온 요청(request, response)을 다음 페이지로 보내는 기능이다. 이전에 배운 RequestDispatcher객체의 forward() method도 동일한 기능을 한다.
- forward는 기본적으로 버퍼와 밀접한 관련이 있고 forward 액션 태그를 만나기까지의 모든 출력을 제거하고 forward할 페이지로 요청을 전달하게 된다. 연결된 페이지와 request 영역을 공유하게 된다는 특징이 있다.

![Untitled](7%20Action%20Tag%208b8416e387e047a49ba6c3e9c4b2c753/Untitled%203.png)

ForwardMain.jsp

![Untitled](7%20Action%20Tag%208b8416e387e047a49ba6c3e9c4b2c753/Untitled%204.png)

- 이때 가장 주의해야 할 것은 forward를 만나기 전까지의 모든 출력을 제거하고 넘기게 되므로 위와 같은 경우에는 해당 페이지에 출력문이 없게 된다.
- 위와 같이 포워딩 된 페이지에서 page영역과 request영역의 내용을 출력해보면 request영역의 data만이 넘어온 것을 확인 할 수 있다.
- 주소에서도 확인 할 수 있듯이 페이지의 URL은 ForwardMain.jsp로 구성이 되어 있지만 실제 페이지에 출력되는 내용은 포워딩 된 ForwardSub.jsp의 내용이 출력되는 것을 볼 수 있다.

![Untitled](7%20Action%20Tag%208b8416e387e047a49ba6c3e9c4b2c753/Untitled%205.png)

- 그렇다면 이러한 forward는 언제 사용하는가? > 상황에 따라서 페이지를 분기해야 하는 상황이 올 수 있다. 그때 if문과 같이 제어문을 통해서 forward하는 페이지를 구분함으로써 분기 페이지를 만들 때 사용이 된다.
    
    String nextPage = null;
    
    if( a = “1”){
    
    nextPage = “/forwardPage1”;
    
    }else{
    
    nextPage = “forwardPage2”;
    
    }
    
    <jsp:forward page=”<%=nextPage%>” />
    

**<jsp:useBean>, <jsp:setProperty>, <jsp:getProperty>**

- useBean 액션 태그는 자바빈즈를 생성하거나 설정할 때 사용한다. 자바빈지는 위에서 한번 설명했듯이 데이터를 저장하기 위한 멤버 변수와 getter/setter method로만 이루어진 클래스를 말한다.

**자바빈즈 생성 <jsp:useBean>**

<jsp:useBean id=”beans name” class=”패키지, 클래스명” scope=”저장될 영역”>

- id: 자바빈즈 객체의 이름을 지정한다. 만약 이미 생성된 객체가 있으면 해당 객체를 사용, 없으면 생성한다.
- class: 사용하려는 자바빈즈 객체의 실제 패키지명과 클래스명을 지정한다. 일반적으로 자바에서는 다른 패키지에 있는 클래스를 사용하려면 import를 해야하는데 그 과정을 하는 것이다.
- scope: 자바빈즈가 저장될 내장 객체 영역을 지정하는 것인데 생략한다면 기본값인 page영역에 지정이된다.

**멤버 변수 값 설정/추출 <jsp:setProperty>, <jsp:getProperty>**

<jsp:setProperty name=”자바빈즈 이름” property=”속성명(멤버 변수)” value=”설정할 값”  / >

<jsp:getProperty name=”자바빈즈 이름” property=”속성명(멤버 변수)”>

- name: id 속성에 지정한 자바빈즈의 이름을 지정한다.
- property: 자바빈즈 멤버 변수명을 지정하는데 이름을 명시하는 대신 *을 쓰면 form의 하위요소와 일치하는 자바빈즈의 모든 속성에 사용자가 전송한 값이 설정된다. 이때 value속성을 생략할 수 있다.
- value: 멤버변수에 설정할 값을 지정한다.

![Untitled](7%20Action%20Tag%208b8416e387e047a49ba6c3e9c4b2c753/Untitled%206.png)

- 기본적으로 액션 태그를 사용하는 모습은 다음과 같다. 처음 자바빈즈를 생성하면서 만약 request영역에 해당 person이라는 빈즈가 존재할 경우 해당 자바빈즈를 불러오도록 한다.

![Untitled](7%20Action%20Tag%208b8416e387e047a49ba6c3e9c4b2c753/Untitled%207.png)

- 이렇게 액션태그를 이용해 자바빈즈의 사용법을 확인해봤는데 그렇다면 왜 자바빈즈를 사용하는지 의문이 들 수도 있다. 그냥 request영역에 있는 data나 page에 있는 data를 직접 설정/추출하면 되지 않나?
- 이전 액션태그들과 마찬가지로 일반 자바 코드와 액션태그가 하는 일은 동일하지만 전체적인 과정의 생략, 사전에 data가 존재하는지를 확인해준다는 점에서 편리하다.

MVC 패턴

MVC패턴이란 Model-View-Controller패턴으로 전체적인 S/W을 3개의 부분으로 나누는 것이다.

Model: logic을 가지고 있으며 DB와 연동하는 부분이다. (JavaBeans)

View: 사용자에게 제공하는 화면으로 UI에 해당한다. (JSP page)

Controller: 뷰와 모델 사이에서 흐름을 제어한다. DAO역할

**와일드카드(*)로 form값 한번에 설정하기**

- 위에서 설명했듯이 *를 사용하면 자바빈즈의 값을 한번에 설정이 가능하다.

![Untitled](7%20Action%20Tag%208b8416e387e047a49ba6c3e9c4b2c753/Untitled%208.png)

- 위에서 form의 형태로 보내진 data를 setProperty에서 와일드카드를 사용할 경우 모든 property를 하나하나 설정해줄 필요 없이 자동으로 set이된다.

(주의할 점은 form에서 입력 받은 data의 name과 자바빈즈의 멤버 변수의 이름이 동일해야 한다는 것이다.)

**한글 인코딩 문제 해결**

- 자바를 사용하면서 한글 data를 사용할 경우 깨질 수 있다. 물론 처음 페이지 상단에서 UTF-8의 charset을 해주지만 data를 전송하는 입장에서 손상될 수 있다. 때문에 이전에 2장에서는 request의 내장 객체의 setCharacterEncoding()으로 인코딩 처리를 했었다.
- 이렇게 매번 encoding을 설정해주는 것은 번거로우니 web.xml에 필터를 추가해 한번에 다룰 수 있다.

![Untitled](7%20Action%20Tag%208b8416e387e047a49ba6c3e9c4b2c753/Untitled%209.png)

- <filter> 태그를 생성하고 해당 filter를 모든 request URL과 mapping시켜 필터를 적용하도록 해주면 된다. Filter는 encoding방식을 utf-8로 적용시키는 것이다.

**<jsp:param>**

- param은 액션태그 include나 forward를 사용할 때 다른 페이지에 값을 전달해주는 액션태그다. 전달할 수 있는 값은 오직 String뿐이다. 다른 객체를 전달하기 위해서는 내장 객체 영역을 이용해야한다.

![Untitled](7%20Action%20Tag%208b8416e387e047a49ba6c3e9c4b2c753/Untitled%2010.png)

- 포워드 되는 페이지와 Data를 공유하는 방법은 request영역에 자바빈즈를 만들어 공유하는 방법과 param을 통해서 직접적으로 전달해주는 방법이 있다.

![Untitled](7%20Action%20Tag%208b8416e387e047a49ba6c3e9c4b2c753/Untitled%2011.png)

- 위에서 제일 처음으로 useBean을 한 것은 자바빈즈를 사용해 request영역의 data를 넘긴 것이고 param은 forward기능을 가지고 한번에 data를 넘겨준 것이다. 이때 forward를 사용 할 때 forward는 <form>태그처럼 사용이 가능하다.

![Untitled](7%20Action%20Tag%208b8416e387e047a49ba6c3e9c4b2c753/Untitled%2012.png)

- 포워드 되는 페이지에서 자바빈즈를 사용할 경우에는 같은 멤버변수 명으로 빈즈를 생성해주면 되고 param으로 보냈을 경우에는 request에서 바로 사용이 가능하다.

.getParameter()

**Include 페이지에 매개변수 전달**

- 액션태그 include된 페이지에는 변수를 직접 전달 할 수 없다. 하지만 requset영역의 경우에는 공유가 되기 때문에 param이나 자바빈즈를 사용해서 data를 전달해줄 수 있다.

![Untitled](7%20Action%20Tag%208b8416e387e047a49ba6c3e9c4b2c753/Untitled%2013.png)

- include도 forward와 마찬가지로 <form> tag와 같은 방식으로 param을 전달해주면 된다.

**Include vs forward** : include는 다른 페이지를 포함시키는 것이고 forward는 페이지의 흐름 자체를 넘기는 것이다.

- JSP는 페이지 자체에서 모든 연산이 이루어지기 때문에 페이지의 흐름 또한 중요하다. 때문에 페이지간 daa를 어떻게 넘기는지, 각각의 객체는 어떤 영역에서 공유될 수 있는지를 확인 할 수 있어야 한다.
- 스크립틀릿을 사용해 자바코드를 HTML중간에 삽입 할 경우 약간의 이질감이 생기지만 액션태그는 말 그대로 태그의 형태를 띄고 있고 보다 짧은 코드로 동일한 기능을 구현 할 수 있다.

![Untitled](7%20Action%20Tag%208b8416e387e047a49ba6c3e9c4b2c753/Untitled%2014.png)