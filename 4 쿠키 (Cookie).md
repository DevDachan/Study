# 4. 쿠키 (Cookie)

**쿠키란?**

- 클라이언트의 상태 정보를 유지하기 위한 기술
- > 상태 정보는 클라이언트(웹 브라우저)에 Key-Value형태로 저장 했다가 다음 요청 시 전송한다.

**RFC 6265 쿠키표준**

- 최대 3000개까지 생성 가능
- 쿠키 하나의 최대 크기는 4096Bytes
- 하나의 호스트나 도메인에서 최대 50개까지 생성 가능

**동작 메커니즘**

<img src="https://user-images.githubusercontent.com/111109411/212597356-ed89f7c9-5481-4059-a486-7f2fc85532cc.png" width=60%>

1. Request: 클라이언트가 서버에 요청을 보낸다.
2. Response: 서버가 쿠키를 생성하여 HTTP Response Header에 실어 전송한다.
3. Save: 클라이언트는 쿠키를 받아 저장해준다.
4. Re-request: 다음 요청 시 저장해둔 쿠키를 HTTP Response Header에 실어 보낸다.
5. Read: 서버는 쿠키의 정보를 읽어 필요한 작업을 수행한다.
    
    

**속성과 API**

- 쿠키 구성속성: Name, Value, Domain, Path, Max-age
- setValue(String value): 쿠키의 값을 설정한다.
- setDomain(String Domain): 쿠키에 적용할 도메인을 설정 ex) www.nakja.co.kr
- setPath(String path): 쿠키가 적용될 경로를 지정한다.
- setMaxAge(int expire_seconds): 쿠키가 유지될 시간을 초 단위로 설정한다. 만약 기간이 설정되어 있지 않다면 웹 브라우저가 닫힐 때 쿠키도 같이 삭제된다.
- new Cookie(String name, String value): 새로운 쿠키를 생성한다.
- getName(), getValue(), getDomain(), getPath(), getMaxAge()

**기본 조작법**

- 쿠키 초기 설정
```
<%
    
    Cookie cookie = new Cookie(“myCookie”, “쿠키 맛있어요”);
    
    cookie.setPath(request.getContextPath());
  
    cookie.setMaxAge(3600);
   
    response.addCookie(cookie);
   
%>
```

- 쿠키는 기본적으로 하나의 Class에 해당하고 해당 class로 Object를 만들어 사용하는 것이다. 그리고 cookie에 대한 값을 모두 설정했으면 응답 헤더에 추가해주는 작업을 반드시 해야 한다.

- 쿠키 사용시

```
<%

Cookie[] cookies = request.getCookies();

%>
```


※ 주의 점: 앞서 말했듯이 쿠키를 사용하는 것은 서버와 한번의 통신이 이루어져야 사용이 가능하다. 때문에 client 측에서 만들자마자 바로 사용하는 것은 불가능함. (쿠키는 request에 존재, response는 보내주는 쪽)

**실무 응용 [레이어 팝업창 제어]**

- 웹 애플리케이션을 개발할 때 팝업창을 많이 사용하게 된다. 팝업창은 회원가입 시 아이디 중복 체크나 간단한 공지사항을 띄워주는 용도로 자주 사용한다. 이때 우리가 자주 보게 되는 하루동안 열지 않음과 같은 정보도 쿠키로 정보가 저장되는 것

**쿠키 미사용 팝업**

```
<style>

    div#popup {
    
        position: absolute; top:100px; left:50px; color:yellow;

        width:270px; height:100px; background-color:gray;

    }

    div#popup>div {

        position: relative; background-color:#ffffff; top:0px;

        border:1px solid gray; padding:10px; color:black;

    }

</style>
```

- 팝업 창은 기본적으로 가장 쉽게 만드는 것은 새로운 창을 띄우는게 아니라 그냥 위에 덮어 써버리는 것
```
<script>

    $(function() {

        $('#closeBtn').click(function() {

            $('#popup').hide();

        });

    });

</script>

<div id="popup">

    <h2 align="center">공지사항 팝업입니다.</h2>

    <div align="right"><form name="popFrm">

        <input type="checkbox" id="inactiveToday" value="1" />

        하루 동안 열지 않음

        <input type="button" value="닫기" id="closeBtn" />

    </form></div>

</div>
```
- form tag를 사용해서 checkbox의 결과를 바로 적용하도록 만들기.
- 적용 시 form의 특정 action이 있는 것이 아니라 그냥 form을 hide처리 해주면 된다.

**쿠키 사용 팝업 예시**
```
<%

    String popupMode = "on";

    Cookie[] cookies = request.getCookies();

    if (cookies != null) {

        for (Cookie c : cookies) {

            String cookieName = c.getName();

            String cookieValue = c.getValue();

            if (cookieName.equals("PopupClose")) {

                popupMode = cookieValue;

            }
       }
  }

%>
```


- 쿠키를 사용한다면 먼저 request에서 쿠키 값을 불러온 뒤 해당 PopupClose의 쿠키 정보가 존재할 경우 해당 값으로 mode를 설정하면 되고 값이 없다면 사용자에게 입력 받은 checkbox 값을 update시켜주면 된다.

<img src="https://user-images.githubusercontent.com/111109411/212597390-6a2cee65-6c4f-49be-b8d7-83f75313be07.png" width=60%>


- function부분은 check여부에 따라서 ajax() 함수를 호출하고 요청 성공 시 location을 바꿔주면 된다.
    
    (함수는 닫기 눌렀을 때 무조건 호출 됨)
    

**JQuery**

- JQuery는 HTML에서 각 tag 값에 있는 속성을 사용하기 위해 사용하는 형식


<img src="https://user-images.githubusercontent.com/111109411/212597398-54462466-62dd-42b7-a27c-1d7e9a87c9d8.png" width=60%>


**Ajax**

- 아주 간단하게 말하면 비동기 HTTP 요청을 보내는 함수로써 페이지에 직접 접속하지 않고 페이지 내용에 대해서 접근이 가능하도록 해주는 함수. 웹 브라우저의 페이지 접속 없이 HTTP 통신 가능하고 결과 값 return.

- **url**: 요청을 보낼 페이지의 URL(기본 값은 현재 페이지)
- **type**: get, post등 HTTP 메서드를 지정
- **data**: 서버로 보낼 데이터
- **datatype**: 서버로부터 받을 response data type
- **success**: 요청 성공 시 실행할 콜백 함수



<img src="https://user-images.githubusercontent.com/111109411/212597405-ccdd60d9-9d53-4f63-bf87-0f44026143cf.png" width=60%>

- JSP에서는 단순 페이지만 jsp파일로 나타내는 것이 아니라 특정 과정을 수행 할 때도 jsp파일로 따로 빼기 때문에 PopupCookie를 정하는 jsp를 따로 생성 한 다음 ajax 비동기 통신으로 data를 update

**로그인 아이디 저장**

- 쿠키를 사용하면서 가장 많이 사용되는 용도가 사용자의 아이디 로그인 정보를 저장하는 것이다. 그리고 로그인을 하면서는 이전 아이디 정보를 저장하는 기능도 존재
    
    ※ 만약 로그인 정보가 존재하지 않을 경우에는 로그인 화면으로 다시 돌려보내야 하기 때문에 JSP 파일을 통해서 해당 처리를 하도록 함.
    

**페이지 이동 (Class)**
```
<%

    JSFunction.alertLocation(“메시지“, “이동 페이지 경로”, out);

%>
```
- 기본적으로 사용하게 될 method의 형태는 다음과 같다. 단순히 tag를 만들어서 사용해도 되지만 편의를 위해서 class를 정의해서 사용
```
public class JSFunction{
    
   public static void alertLocation(String msg, String url, JspWriter out){
    
       try{
    
           String script = “<script>” + “alert(‘“+msg+ ”’);”
    
            + location.href=’” + url + “’;”
    
            + “</script>”
    
        }catch(Exception e){}
    
    }
    
    public static void alertBack(String msg, JspWriter out){
    
        try{
    
            String script = “<script>” + “ alert(‘”+msg +”’); ”
    
            + “history.back();”
    
            + “</script>;”
    
        }catch(Exception e){}
    }
    
}
```

- 기본적으로 alertLocation의 경우에는 location의 위치를 바꾸는 method이고 alertBack의 경우에는 현재 경로에서 뒤로가기를 하는 method이다.
- meg는 알림을 띄우게 될 message, url은 location의 URL, out의 경우에는 JS 코드를 삽입할 출력 Stream을 말한다. JSP 내장 객체를 클래스에서 사용하기 위해서는 매개변수 사용해야 함.

**쿠키 관리자 (Class)**

- 쿠키를 생성하거나 생성된 쿠키를 관리하는 방법 (기간, 경로, 객체 생성)
```
public class CookieManager{

    public static void makeCookie(HttpServletResponse response, String cName, String cValue, int cTime){

        Cookie cookie = new Cookie(cName, cValue);         // 쿠키 생성

        cookie.setPath(“/”);                     // 경로 설정

        cookie.setMaxAge(cTime);            // 유지기간

        response.addCookie(cookie);        // 응답 객체에 추가
    }

    public static String readCookie(HttpServletRequest request, String cName){

        String cookieValue = “”;   //반환 값

        Cookie[] cookies = request.getCookies();

        if(cookies != null){

            for(Cookie c : cookies){

                String cookiesName = c.getName();

                if(cookieName.equals(cName)){

                    cookieValue = c.getValue();

                }    

            }    

        }
        
        return cookieValue;
    }

    public static void deleteCookie(HttpServeletResponse, response, String cName){

        makeCookie(response, cName, “” 0)

    } // 삭제는 그냥 time을 0으로 만들어주면 알아서 삭제된다.

}
```
- 기본적으로 CookieManager는 3개의 method를 제공한다.
1. Create 2. Search 3. Delete

**로그인 기능 구현하기**

- 로그인 기능 구현 시 먼저 cookie data를 보고 이전에 로그인 한 기록을 찾아봐 check box의 value를 설정
    
    Ex) String loginId = CookieManager.readCookie(request, “loginId”);
    
- 로그인 기능은 그냥 간단하게 form tag 내부에서 여러 입력 값을 토대로 DB 값과 비교하면 된다.
```
<form action=”IdSaveProcess.jsp” method=”post”>    </form>

IdSaveProcess.jsp
```


- 로그인을 하면서 입력 받은 ID와 Pwd 확인 후 cookie 저장 (DB X)


```
<%

    String user_id = request.getParameter(“user_id”);

    String user_pw = request.getParameter(“user_pw”);

    String save_chek = request.getParameter(“save_check”);

    if(“must”.equals(user_id) && “1234”.equals(user_pw)){

        if(save_check != null && save_check.equals(“Y”)){

            CookieManager.makeCookie(response, “loginId”, user_id, 86400);

        }else{

            CookieManager.deleteCookie(response, “loginId”);  // 쿠키 삭제

        }

    }

    JSFunction.alertLocation(“로그인에 성공했습니다.”, ”IdSaveMain.jsp”, out);


%>
```
- 위 코드는 하드 코딩 된 ID:must | Pwd: 1234 의 값과 비교하여 로그인을 하고 해당 로그인 정보를 cookie에 저장하는 과정을 나타냈다.
