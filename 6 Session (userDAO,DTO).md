# 6. Session (userDAO,DTO)

**세션이란?**

- 클라이언트가 웹 브라우저를 통해 서버에 접속한 후 용무를 처리하고 웹 브라우저를 닫아 서버와의 접속을 종료하는 하나의 단위를 세션이라고 한다. 세션 정보의 목적은 클라이언트가 서버에 접속해 있는 동안 그 상태를 유지하는 것이다.

**세션 유지시간 설정**

- 세션은 웹 브라우저를 실행할 때마다 새롭게 생성이 된다. 이렇게 생성된 세션은 특정 유지 시간 동안만 유지되며 유지 시간이 만료되기 전에 새로운 요청이 들어오면 수명이 연장된다.
    
    1) /WEB-INF/web.xml에서 설정하기
    
    <session-config>
    
    <session-timeout>20 </session-timeout>
    
    </session-config>
    
- 위에 보이는 코드와 같이 세션의 유지 시간을 애플리케이션 영역에서 설정해주는 것이 가능하다. 이때 유지 시간은 분 단위로 설정이 된다.
    
    2) JSP파일에서 내장 객체가 제공하는 setMaxInactiveInterval() method사용하기.
    
    <%
    
    session.setMaxInactiveInterval(1800);
    
    %>
    
- 이때는 애플리케이션 영역과 다르게 초 단위로 입력 값을 받게 된다.

**설정 값 확인**

- 세션에는 유지 시간 외에 세션을 구분하기 위한 아이디, 마지막 요청 시각, 생성 시각 등의 속성도 포함하고 있다.
    
    .getCreationTime() : 처음 생성 시각
    
    .getLastAccessedTime() : 마지막 요청 시각
    
    - 다음과 같이 형변환을 시켜 줌으로써 날짜 형식의 data를 String 객체로 표현이 가능해진다.
    
    SimpleDateFormat dateFormat = new SimpleDateFormat(“HH:mm:ss”);
    
    String a = dateFormat.format(new Date(creationTime))
    

.getId(), getAttribute() ,.getMaxInactiveInterval(): get으로 정보 받기

.setId(), setAttribute() ,setMaxInactiveInterval()

- 세션 정보는 하나의 쿠키에 모든 정보들을 저장하는 것이고 해당 쿠키의 구조는 Attribute라고 부르게 된다. 특정 data를 저장할 때 key-value로 저장을 하게 됨.

**세션 삭제**

- 세션은 웹 브라우저를 닫으면 종료되지만 웹 브라우저를 닫지 않고 웹 브라우저 설정에서 쿠키를 삭제해 세션 정보를 삭제 할 수 있다.
- 크롬에서는 브라우저 인터넷 사용 기록 삭제를 하면 됨

![Untitled](6%20Session%20(userDAO,DTO)%20e52b439bf13a4a31b79f5670283d8a11/Untitled.png)

- 이러한 기능의 원리는 먼저 저장되는 쿠키를 보면 JSESSIONID라는 쿠키가 생성 되어있는 것을 볼 수 있다. 해당 쿠키는 우리가 만든 것이 아니라 톰캣 컨테이너가 새로운 웹 브라우저를 접속했을 때 세션을 유지하기 위해 자동으로 생성해 놓은 쿠키이다.
- 이 쿠키는 응답 시 세션 아이디를 값으로 가지는 쿠키이다. 때문에 세션 정보를 유지 할 때 JSESSIONID 쿠키를 Response Header에 담아 웹 브라우저로 보내게 된다. 즉 브라우저에서 세션 영역의 상태를 유지할 때 JSESSIONID라는 쿠키를 사용하게 되므로 웹 브라우저의 모든 쿠키를 삭제하면 세션 정보를 담고 있는 JSESSIONID도 삭제가 되어 세션 정보가 지워지는 것

![Untitled](6%20Session%20(userDAO,DTO)%20e52b439bf13a4a31b79f5670283d8a11/Untitled%201.png)

**DB 연동**

- DTO(Data Transfer Object) : 계층 사이에서 데이터를 교환하기 위한 객체로써 속성(멤버 변수), Getter, Setter 만 각춘 class이다. 그래서 값 객체(Value Object)라고도 부른다.
- DAO(Data Access Object) : 데이터베이스의 데이터에 접근하기 위한 객체이다. 보통은 JDBC를 통해 구현이 되고 DB table에 대한 CRUD를 담당한다. Create Read Update Delete
- 간단하게 요약하자면 DTO의 경우 DB에서 불러온 내용을 객체화 시켜주는 class이고 DAO는 실제로 SQL 쿼리문을 DB로 전송하고 값을 받아오는 class이다. DAO는 JDBConnect 클래스를 상속 받아 DB에 접속을 한다.

**UserDTO**

- 기본 path는 Java Resources/src 내부에 저장이 되어야 하고 class이기 때문에 .java파일로 저장 된다.
    
    public class UserDTO{
    
    private String id;
    
    private String pass;
    
    private String name;
    
    public String getId(){                                      // getter
    
    return id;
    
    }
    
    public void setId(String id){                      // setter
    
    this.id = id;
    
    }
    
    …..
    
    public UserDTO(String id, String pass, String name){   // Constructor
    
    super();
    
    this.id = id;
    
    this.pass = pass;
    
    this.name = name;
    
    }
    
    }
    

※ Encapsulation: 캡슐화 과정을 거쳐서 객체에서 직접적으로 변수에 접근을 하지 못하게 하는 것은 정보 은닉에 있다. 객체가 제공하는 필드와 메소드를 통해서만 변수에 접근이 가능하도록 해서 객체 내의 정보 손상과 오용을 방지하고 데이터가 변경되어도 다른 객체에 영향을 주지 않게 하기 위해서 캡슐화 과정을 거친다. 만약 캡슐화 과정을 거치지 않는다면 객체의 변수에 접근할 때 array같은 경우 주소 값을 가져오기 때문에 data를 바꿀 때 객체 내의 데이터가 손상 될 수 있다.

**UserDAO**

public class UserDAO extends JDBConnect {

public UserDAO(String drv, String url, String id, String pw){

super(drv, url, id, pw);

}

public UserDTO getMemberDTO(String uid, String pass){

MemberDTO dto = new MemberDTO();

String query = “SELECT * FROM user WHERE id=? AND pass=?”;

try{

psmt = con.prepareStatement(query);

psmt.setString(1, uid);

psmt.setString(2. pass);

rs = psmt.executeQuery();

if(rs.next()){

dto.setId(rs.getString(“id”));

dto.setPass(rs.getString(“pass”));

dto.setName(rs.getString(3));

}

}catch(Exception e){

e.printStackTrace();

}

return dto;

}  }  }

- 기본적으로 psmt와 con의 경우에는 부모 클래스인 JDBConnect엣 정의한 멤버 변수이고 extends로 부모 클래스의 내용을 상속 받았기에 바로 사용이 가능한 것이다.
- 만약 web.xml에 미리 data를 입력시켜 놓았다면 아래와 같이 app scope를 사용하면 된다.

![Untitled](6%20Session%20(userDAO,DTO)%20e52b439bf13a4a31b79f5670283d8a11/Untitled%202.png)

 

![Untitled](6%20Session%20(userDAO,DTO)%20e52b439bf13a4a31b79f5670283d8a11/Untitled%203.png)

- UserDTO와 UserDAO를 직접적으로 jsp 파일에서 사용할 때는 다음과 같이 사용 할 수 있다.
- application 영역에 존재하는 DB 로그인 정보를 불러 온 뒤 해당 내용으로 DAO 객체를 만들고 내가 원하는 method를 DTO로 호출시키면 됨
- > 이때 왜 DAO에 로그인 정보들을 입력하는가?
- 앞에서 설명 했듯이 DTO는 단순히 Data를 Object 형식으로 바꿔주는 틀일 뿐이고 DB와 자바의 연결은 DAO에서 상속한 JDBConnect에서 이루어지기 때문이다.

**로그아웃 처리**

- 위 내용으로 session에 로그인 내용을 저장했다면 로그아웃을 했을 때는 그냥 session.removeAttribute()를 사용해서 ID와 Name을 지워주고 session.invalidate();를 사용함으로써 세션 쿠키를 초기화 시키면 된다.

※ 공통 링크 사용: 페이지를 사용하면서 공통적으로 나타내야 할 tag들이 존재 할 수 있다. 그럴 경우 별도의 jsp 파일로 만들고 jsp:inclue를 통해 page값을 불러오면 된다.

![Untitled](6%20Session%20(userDAO,DTO)%20e52b439bf13a4a31b79f5670283d8a11/Untitled%204.png)

- 한가지 스킬은 로그인 상태에 따라서 로그인 or 로그아웃 버튼을 표시해야 되는데 그럴 때는 JSP의 장점을 살려서 if문을 <%%> 지시문으로 나눠서 나타내면 된다.
    
    <% if(session.getAttribute(“UserId”) == null) {   %>
    
    <a href=”../LoginForm.jsp”>로그인 </a>
    
    <% }  else   {  %>
    
    <a href=”../Loout.jsp”>로그아웃 </a>
    
    <%     }  %>
    

**쿠키 vs 세션**

- 로그인 기능을 사용할 때는 쿠키가 아닌 세션을 이용해 구현하는 것이 좋다. 세션의 정의에서도 알 수 있지만 일단 세션은 로그인 정보를 저장하는 것이고 페이지가 닫히면 사라지는 정보이기 때문에 보안적인 부분에서도 덜 문제가 된다.
- 쿠키의 경우에는 서버가 아닌 클라이언트 측 웹 브라우저 데이터를 활용하기 때문에 속도 적인 측면에 있어서 세션보다 이점이 있다. 하지만 PC에 직접적으로 저장 되기에 보안적으로 취약하고 용량 제한이 있다.

![Untitled](6%20Session%20(userDAO,DTO)%20e52b439bf13a4a31b79f5670283d8a11/Untitled%205.png)