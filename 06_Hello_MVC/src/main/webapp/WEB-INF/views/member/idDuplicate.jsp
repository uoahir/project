<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	/* String id = (String)request.getAttribute("userId"); */ 
	String id = (String)request.getParameter("userId");
	boolean result = (Boolean)request.getAttribute("result");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디중복확인</title>
<style>
	div#checkId-container{
		text-align:center;
		padding-top:50px;
	}
	span#duplicated{
		color:red;
		
	}
</style>
</head>
<body>
	<div id="checkId-container">
		<%if(result){ %>
			[<span><%=id %></span>]는 사용가능합니다.	
			<br><br>
			<button type="button" >닫기</button>
		<%}else{ %>
			[<span id="duplicated"><%=id %></span>]는 사용중입니다.
			<br><br>
			<!-- 아이디 재입력창 구성 -->
			<form action="<%=request.getContextPath() %>/member/idDuplicate.do" method="get">
				<input type="text" name="userId" id="userId">
				<input type="submit" value="중복검사" >
			</form>
		<%} %>
	</div>
	<script>
		document.querySelector("button").addEventListener("click",e=>{
			const $userId = opener.document.querySelector("#userId_");
			$userId.value="<%=id%>"; 
			$userId.readOnly = true;
			$userId.style.backgroundColor="lightgray";
			/* 이거 다시 복습하기... */
			window.close();
		});
	</script>
</body>
</html>