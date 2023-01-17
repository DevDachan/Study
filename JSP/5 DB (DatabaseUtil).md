# 5. DB (DatabaseUtil)

**DB** - JSP에서는 JDBC (Java Database Connectivity)를 통해 데이터베이스와 연동이 가능하다

**JDBC 설정**

JDBC는 한마디로 자바로 DB와 연결 및 관련 작업을 할 때 사용하는 API를 말한다. 그렇기 때문에 JDBC를 사용하기 위해서는 JDBC 드라이버가 있어야 함.

- JDBC는 먼저 해당 드라이버를 다운 받아 project 내부에 WebContent/WEB-INF/lib 경로에 jar파일을 추가 시켜줘야 한다.

<img src="https://user-images.githubusercontent.com/111109411/212598472-1dfb9bdc-679c-43be-83f4-db25f266cfa3.png" width=30%>



**MySQL 사용시 JDBC 주소**

[https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.24](https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.24)

**eclipse 변경사항 적용 안될 때**

[https://ldne.tistory.com/320](https://ldne.tistory.com/320)

**연결 관리 class작성**

- JDBC를 사용할 때 단순히 jar파일만 가져온다고 되는 것이 아니라 내가 연결하게 될 DB에 대한 정보를 입력시킨 class를 만들어 줘야 한다.

  → Oracle 예제 (JDBConnect.java)   [해당 파일 경로 = Java Resources/src]

<img src="https://user-images.githubusercontent.com/111109411/212598495-a676188e-fa2a-4d43-89f5-cd60e53c84c3.png" width=60%>

- **Connection**: DB와 직접적인 연결을 담당한다.
- **Statement:** 인파라미터가 없는 정적 쿼리문을 실행할 때 사용된다.
- **PrepareStatement**: 인파라미터가 있는 동적 쿼리문을 실행할 때 사용한다. 이때 인파라미터란 쿼리문을 작성하면서 String에 직접적으로 들어가있지 않고 사용자로부터 입력을 받아 작성되는 부분을 말한다
    
    Ex) SELECT name FROM ID WHERE id = ?       : 이때 ?가 바로 인파라미터
    
- **ResultSet**: SELECT 쿼리문의 결과를 저장할 때 사용된다.

**생성자**

url: url은 기본적으로 DB에 연결하기 위해 접속해야 하는 주소를 의미한다. @ 앞쪽에 위치하는 것은 프로토콜을 말하고 IP주소 - PORT번호 – SID 순으로 입력이 된다.

id, pwd : id와 pwd는 local DB에 등록되어 있는 아이디와 비밀번호를 의미한다.

DriverManager.getConnection(url,id,pwd); : 최종적으로 생성자들을 합쳐서 DB와 JAVA를 연동시키는 method

<img src="https://user-images.githubusercontent.com/111109411/212598528-4647512b-56ad-4462-a3c2-adea70be6495.png" width=60%>



- url: url은 기본적으로 DB에 연결하기 위해 접속해야 하는 주소를 의미한다. @ 앞쪽에 위치하는 것은 프로토콜을 말하고 IP주소 - PORT번호 – SID 순으로 입력이 된다.
- id, pwd : id와 pwd는 local DB에 등록되어 있는 아이디와 비밀번호를 의미한다.
- DriverManager.getConnection(url,id,pwd); : 최종적으로 생성자들을 합쳐서 DB와 JAVA를 연동시키는 method

**MySQL**

<img src="https://user-images.githubusercontent.com/111109411/212598861-bf0cf874-6266-4ecc-96cd-5a68d7a680b0.png" width=60%>




- MySQL을 사용할 때도 전체적인 Driver구조는 오라클과 동일하다. 다만 프로토콜 이름이 다름

(useSSL=false: mysql의 경우에는 default로 SSL을 사용하기 때문에 false로 해줘야 연결이 된다)

<img src="https://user-images.githubusercontent.com/111109411/212598634-b4b1d88a-d569-482f-a5fe-82155102bb32.png" width=60%>



위에서 사용한 DatabaseUtil은 [동빈나]가 사용한 예제이고 조금 더 관리 적인 측면에서 connection을 만들기 위해서는 책의 내용대로 새로운 connection을 해야 한다.



<img src="https://user-images.githubusercontent.com/111109411/212598650-400d02b3-0ef3-4401-9d53-4e5558784f09.png" width=30%>



- 해당 class에 필수적인 것은 아니지만 close() method를 추가시켜서 연결된 자원을 해제하는 부분이 있으면 좋다.

**기본 동작 확인**

<img src="https://user-images.githubusercontent.com/111109411/212598702-600c88a8-892d-4dce-a354-1ae8aca77f97.png" width=60%>




- 제일 먼저 DB를 사용하기 위해 page import부분에서 이전에 만들었던 JDBConnect.java파일을 추가시켜 줘야 한다.
- 이후 사용할 때에는 <% %>기본 지시어 내에서 JDBConnect a = new JDBConnect();처럼 연결을 하고 a.close()로 닫으면 된다.
- 추가적으로 DB를 사용하면서 각각의 jsp 파일에서 DB의 연결을 시도하는 것은 코드 전체 흐름을 방해 할 수 있으므로 UserDTO와 UserDAO라는 java class파일을 만들어서 DB와 연동하는 부분만 처리하는 class를 만들어주는 것이 좋다.
- 이후에 해당 부분 설명 예정

**연결 설정 개선방법 1**

- DB를 사용하면서 서버 이전 등으로 접속 정보가 변경되는 경우 클래스를 수정한 후 다시 컴파일 해야 하는 불편함이 있기 때문에 서버 환경과 관련된 정보를 한 곳에서 정리하도록 web.xml에 미리 입력 해 놓으면 좋다.
    
    <context-param>으로 컨텍스트 초기화 매개변수로 입력 à 해당 변수를 jsp에서 불러와서 class의 constructor 매개변수로 넘겨주기
    
- 이때 web.xml은 가장 기본적으로 애플리케이션 영역에 해당하는 객체의 정보를 담고 있는 파일이다. 해당 부분에 대해 궁금하다면 이전 내용을 다시 확인해보기

<img src="https://user-images.githubusercontent.com/111109411/212598702-600c88a8-892d-4dce-a354-1ae8aca77f97.png" width=60%>


<img src="https://user-images.githubusercontent.com/111109411/212599025-42b37b62-fd55-410b-b3ff-0f55768503da.png" width=60%>
  
  
- Class.forName() 동적으로 메모리 로드
- application.getInitParameter(): 애플리케이션 영역의 변수 불러오기

  
Oracle
  
<img src="https://user-images.githubusercontent.com/111109411/212599058-3e832f35-5d90-4968-b64d-42be13f39d21.png" width=60%>




MySQL

<img src="https://user-images.githubusercontent.com/111109411/212599080-f045b0de-ae4e-4bc7-8da9-db7bc0110627.png" width=60%>
  
  
  
**연결 설정 개선 방법 2**

- web.xml에 입력한 후에 내장 객체를 통해 가져오는 것도 DB 접속이 필요할 때마다 동일한 코드를 JSP에서 반복 기술해야 한다. 따라서 컨텍스트 초기화 매개변수를 constructor에서 직접 가져올 수 있도록 정의하는 것이 좋다.

<img src="https://user-images.githubusercontent.com/111109411/212599107-127d1b34-af87-408a-aab0-57d0d68937f4.png" width=60%>


  
  
- 이처럼 매개변수에 ServletContext application을 입력하면 거기서 바로 Data를 불러오는 것이 가능함

<img src="https://user-images.githubusercontent.com/111109411/212599204-ca2553a2-afd0-47c0-a959-c4866747dae2.png" width=60%>

  
  
**커넥션 풀로 성능 개선**

- 웹은 클라이언트의 요청에 서버가 응답하는 구조이다. 그런데 요청마다 DB와 연결을 새로 한다면 시간이 너무 오래 걸리게 된다. 그래서 커넥션 풀 (Connection Pool)이란 Connection 객체를 미리 생성해 Pool에 넣어 놓고 이미 생성된 Connection 객체를 가져다 사용하는 방법이다.

**커넥션 풀과 JNDI**

- JSP 프로그래밍 시 커넥션 풀은 직접 만드는 것보다는 WAS가 제공하는 것을 이용하는 것이 좋다. WAS 하나에 여러 개의 웹 애플리케이션을 구동시키는 경우가 많은데 애플리케이션마다 자원을 따로 관리하면 낭비도 심하고 관리하기도 어렵기 때문이다.
- WAS는 기본적으로 JNDI(Java Naming and Directory Interface)서비스를 통해 커넥션 풀을 제공한다. JNDI란 자바 S/W가 객체나 데이터를 전체 경로를 몰라도 이름만 찾아 쓸 수 있는 디렉토리 서비스를 말한다.


<img src="https://user-images.githubusercontent.com/111109411/212599233-8516136b-6b42-4e00-8d25-2b251a8ab4bb.png" width=60%>


  
- 흔히 웹 상에서 사용되는 도메인 주소 서비스인 DNS와 비슷하다고 생각하면 된다.
- WAS가 시작될 때 server.xml과 context.xml에 기본적으로 커넥션 풀을 생성한다.

**커넥션 풀 설정**

- 위에서 설명 했듯이 커넥션 풀은 WAS에서 설정을 해줘야 하기 때문에 Tomcat에 존재하는 server.xml과 context.xml을 수정해야 한다. (경로 conf/server.xml, conf/context.xml)

<img src="https://user-images.githubusercontent.com/111109411/212599255-b2329c68-4fce-4c62-ae98-b2f232533251.png" width=60%>

  
  
**server.xml 수정**
```
<GlobalNamingResources>

<%-- 이후 내용 추가--%>

<Resource auth=”Container”

driverClassName=”oracle.jdbc.OracleDriverer”

type=”javax.sql.DataSource”

initialSize=”0”

minIdle=”5”

maxTotal=”20”

maxIdle=”20”

maxWaitMillis=”5000”

url=”jdbc:oracle:thin@localhost:1521:xe”

name=”dbcp_myoracle”

username=”dachan”

pwd=”1234”

/>

</GlobalNamingResources>
```
<img src="https://user-images.githubusercontent.com/111109411/212599276-4b4669bb-f50b-4c1a-8959-f4f73ec7e066.png" width=60%>
  
  
  
- 위에서 type으로 지정한 javax.sql.DataSource는 물리적 데이터 소스와 연결을 생성해주는 자바 표준 인터페이스며 driverClassName으로 지정한 oracle.jdbc.OracleDriver 클래스가 이 인터페이스를 구성하고 있다.

**context.xml 수정**
```
<context>

<%-- 기존 내용 유지 --%>

<ResourceLink global=”dbcp_myoracle” name=”dbcp_myoracle” type=”javax.sql.DataSource”/ >

</context>
```
- global: 전역 자원 이름
- name: 자원 이름
- 전체 자원은 아래와 같이 연결이 된다.

<img src="https://user-images.githubusercontent.com/111109411/212599307-535753d6-9410-4255-908b-7be5b8e1cff3.png" width=60%>
  

  
**커넥션 풀을 통한 연결 얻기**
```
public DBConnPool(){

  try{

    Context initCtx = new InitialContext();

    Context ctx = (Context)initCtx.lookup(“java:comp/env”);

    DataSource source = (DataSource)ctx.lookup(“dbcp_myoracle”);

    con = source.getConnection();
  }
}
```
  
- dbcp_myoracle은 위에서 보이는 것과 같이 contextxml에서 선언한 전역 자원의 이름이고 server.xml에서 정의된 container의 data와 연동이 된다. 그렇게 받아온 data를 DataSource에 넣고 해당 data를 가지고 Connection을 바로 얻으면 됨.
- Context란 자바 네이밍 서비스(JNDI)에서 이름과 실제 객체를 연결해주는 개념이다.
- 두번째로 java:comp/env는 현재 애플리케이션의 root dir라고 생각하면 된다. (App의 모든 자원 존재)

**쿼리 작성법**

- DB작업이라면 기본적으로 쿼리문을 작성하고 실행하여 그 결과를 얻어오는 일을 말한다. JDBC에서 쿼리문은 java.sql.Statement 인터페이스로 표현되며 Statement 객체는 Connection 객체를 통해 얻어오도록 되어 있다.
- Statement: 인파라미터가 없는 정적 쿼리를 처리할 때 사용
- PreparedStatement: 인파라미터가 존재하는 동적 쿼리를 처리할 때 사용
- CallableStatement: 프로시져나 함수를 호출할 때 사용
- State개열의 객체로 쿼리문을 실행할 때는 다음의 method를 사용한다.
- executeUpdate(): INSERT, UPDATE, DELETE 쿼리문을 실행할 때 사용한다. 기존 레코드를 변화시키거나 새로운 레코드를 입력함
- executeQuery(): SELECT 쿼리문과 같이 기존 레코드를 조회하는 쿼리문을 실행할 때 사용한다.

**동적 쿼리문 사용**
```
<%

JDBConnect jdbc = new JDBConnect();

  String id = “test”;

  String pass= “1234”;

  String name = “테스트 회원”;

  String sql = “INSERT INTO member VALUES (? , ? , ? , sysdate)”;

  PreparedStatement psmt = jdbc.con.prepareStatement(sql);

  psmt.setString(1,id);

  psmt.setString(2,pass);

  psmt.setString(3,name);

  int inResult = psmt.executeUpdate();

%>
```
- 먼저 우리는 동적으로 쿼리문에 인파라미터를 넣어줄 것이기 때문에 preparedStatement를 선택해줘야 함
- 우리는 INSERT라는 쿼리문을 만들것이기 때문에 executeUpdate()를 사용해서 실행시켜줘야 한다.

**인파라미터 정의**

- 인파라미터를 정의하는 것은 Data type, 순서 등을 가지고 정의 할 수 있다.
- setInte(int index, int value)
- setDate(int index, Date value)
- setString(int index, String value)

**ResultSet 결과 가져오기**

- SELECT문과 같이 조건에 맞는 레코드를 선택하고 해당 결과를 받아오는 method의 경우에 Data는 array보다는 stack과 같은 자료구조로 불러온다.
- ResultSet에서는 DB의 레코드를 모두 불러오고 제일 처음 레코드를 커서가 가리킨다. 이때 값을 넘어가는 것은 next() method를 사용해서 커러를 이동 가능함.
- get() method의 경우에는 현재 커서의 data를 읽어드린다.
- getInt(int columnIndex) or getInt(String columnLabel)
- getDate(int columnIndex) or getDate(String columnLable)
- getString(int columnIndex) or getString(String columnLable)


<img src="https://user-images.githubusercontent.com/111109411/212599337-815c91c2-f558-478e-bb0a-590b8e796f75.png" width=60%>
  

  
  
- 결과 확인은 다음과 같이 whilte문을 통해서 확인한다.
    - Result Set의 경우에는 별도의 길이를 알 수 있는 method가 존재하지 않기 때문에 별도의 쿼리문을 통해서 길이를 불러오거나 rs의 option을 변경하여 커서가 가리키는 방향을 옮길 수 있게 해서 제일 뒤에 존재하는 row번호를 불러와 사용해야 한다
