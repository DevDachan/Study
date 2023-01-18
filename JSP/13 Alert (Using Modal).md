# 15. Alert (Using Modal)

<img src="https://img.shields.io/badge/Bootstrap-F43059?style=flat&logo=Bootstrap&logoColor=white"/>
	
- 웹 페이지를 사용하면서 alert()와 같이 사용자에게 현재 페이지에 대한 내용을 알려줘야 할 경우들이 생긴다. 하지만 자바스크립트 내에 존재하는 alert의 경우에는 각각의 기기와 웹 브라우져에 따라 어떤 형태로 나타날지를 알지 못한다.
    - 때문에 이러한 alert()를 효과적으로 나타내기 위한 방법 중 하나는 modal을 사용해서 사용자에게 내용을 전달해주는 것이다.


### **Modal**

<img src="https://user-images.githubusercontent.com/111109411/213123064-a646b40b-a2ac-487b-b37e-e1f07281c604.png" width=60%>



- Modal은 사용자의 이목을 끌기 위해 사용하는 화면 전환 기법을 의미한다. 쉽게 생각하면 우리가 웹 페이지를 사용하면서 현재 창 위에 작은 창 하나가 뜨고 주변이 불투명한 검정 영역으로 변하는 것을 본 적 있을텐데 그러한 영역을 말한다.
- modal을 사용할 때 직접 만들어서 사용해도 되지만 가장 흔히 사용되는 bootstrap을 이용해 alert기능을 구현해 보았다.
    - modal을 만들 때는 CSS를 수정해서 div tag를 사용해 중앙에 배치하는 컴포넌트는 불투명도를 100으로 설정하고 나머지 영역에 대해서는 불투명도를 낮춰서 투과해서 보이도록 한다. 그리고 box-shadow()를 설정, backdrop-filter등을 설정하는 것으로 window 영역과 backdrop영역을 구분하고 window 영역만을 설정하는 것이다.

### 기본 원리

- 내가 여기서 사용한 방법은 먼저 alert.jsp 파일을 생성 해 놓은 뒤 alert를 필요로 하는 page에서 해당 jsp파일을 action tag로 include하고 include하면서 param으로 alert의 내용을 전달함으로써 기능을 분리 시켰다.

### 인코딩 문제

- 기본적으로 page 상단 부분에서 UTF-8로 charset을 지정해주지만 action tag의 <jsp:param>으로 값을 넘겨 줄 때는 한글 값이 ?처럼 깨지는 현상이 발생한다.
- 때문에 param으로 data를 넘기면서 encoding을 하고 받는 페이지 측에서는 decoding해서 data를 사용해야 한다.

```jsx
<%@ page import='java.net.URLEncoder' %>
<%@ page import='java.net.URLDecoder' %>

<%=URLEncoder.encode(\"로그인 정보가 존재합니다.\", \"UTF-8\") %>
String content = URLDecoder.decode(request.getParameter("content"), "UTF-8");
```

<img src="https://user-images.githubusercontent.com/111109411/213123117-fd0a61ac-2eec-4004-a039-ac3719021ebe.png" width=60%>


- 아래의 예시는 기본적으로 session 정보가 존재할 경우에 특정 페이지로 이동 시키는 예제이다. 사용할 때는 아래와 같이 <jsp: include> action tag로 페이지를 불러와 스크립 틀릿의 조건 안에서 사용하면 된다.
- param으로 넘겨줘야 할 값은 title(제목), content(내용), url(취소를 누르고 이동할 페이지)

*.jsp

```jsx
if(session.getAttribute("userID") != null){
		userID = (String) session.getAttribute("userID");
%>
		<jsp:include page='alert.jsp'> 
				<jsp:param name="title" value="<%=URLEncoder.encode(\"안내\", \"UTF-8\") %>" />
				<jsp:param name="content" value="<%=URLEncoder.encode(\"로그인 정보가 존재합니다.\", \"UTF-8\") %>" />
				<jsp:param name="url" value="location.href = 'index.jsp';"/>
		</jsp:include>	
<%
}
%>
```

alert.jsp

```
	<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import='java.net.URLDecoder' %>
<%
request.setCharacterEncoding("UTF-8");
String title =URLDecoder.decode(request.getParameter("title"), "UTF-8");
String content = URLDecoder.decode(request.getParameter("content"), "UTF-8");
String url = URLDecoder.decode(request.getParameter("url"), "UTF-8");
%>
<a id="sessionModal" data-toggle="modal" href="#loginModal" style="display:none;"></a>

<script>
window.onload=function(){
document.getElementById("sessionModal").click();	
}
</script>

<div class="modal fade" id="loginModal" tabindex="-1" data-backdrop="static" data-keyboard="false" role="dialog" aria-labelledby="modal" aria-hidden="true">
<div class="modal-dialog">
<div class="modal-content">
<div class="modal-header">
<h5 class="modal-title" id ="modal">
<%=title %>
</h5>
<button type="button" class="close" data-dismiss="modal" onClick="<%=url%>" aria-label="Close" >
<span aria-hidden="true">×</span>
</button>	
</div>	
	<div class="modal-body">
			<div class="form-row">
				<div class="form-group col-sm-12">
					<label><%=content %></label>
				</div>
				<div class="modal-footer col-sm-12">
					<div class="form-group col-sm-12">
						<button type="button" id="close_modal" class="btn btn-secondary" style="width:100%;" data-dismiss="modal" onClick="<%=url%>">close</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</div>

```

[https://getbootstrap.com/docs/4.3/components/modal/](https://getbootstrap.com/docs/4.3/components/modal/)

