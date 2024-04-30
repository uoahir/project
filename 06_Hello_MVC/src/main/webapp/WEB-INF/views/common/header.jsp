<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="mvc.jy.model.dto.Member" %>
<%
	Member loginMember = (Member)session.getAttribute("loginMember");
	// Cookie 가저오기
	Cookie[] cookies = request.getCookies();
	String saveId=null;
	if(cookies != null){
		for(Cookie c : cookies){
			if(c.getName().equals("saveId")){
				saveId = c.getValue();
				break;
			}
		}
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JY</title>
 <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css"/>
 <script src="<%=request.getContextPath()%>/js/jquery-3.7.1.min.js"></script>
</head>
<body>
	<div id="header">
	<header>
		<h1>HelloMVC</h1>
		<div class="login-container">
		<%if (loginMember == null){%>
			<form action="<%=request.getContextPath() %>/login.do" method="post">
				<table>
					<tr>
						<td>
							<input type="text" name="userId" id="userId" placeholder="아이디입력"
							value="<%=saveId!=null?saveId:""%>">
						</td>
						<td></td>
					</tr>
					<tr>
						<td>
							<input type="password" name="password" id="password" placeholder="패스워드입력">
						</td>
						<td>
							<input type="submit" value="로그인">
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type = "checkbox" name="saveId" id="saveId"
							<%=saveId!=null?"checked":""%>>
							<label for="saveId">아이디저장</label>
							<input type="button" value="회원가입" onclick="location.replace('<%=request.getContextPath()%>/member/enrollMember.do')">
						</td>
					</tr>
				</table>
			</form>
			<script>
				const check = document.querySelector("input[id='saveId']").ckecked;
				if(check){
					const userId= document.querySelector("input[id='userId']").value;
					
				}
				
			</script>
			<%} else{%>
				<table id="logged-in">
					<tr>
						<td colspan="2">
							<%=loginMember.getUserName() %>님, 방가방가 :)
						</td>
					</tr>
					<tr>
						<td>
							<input type = "button" value="마이패이지" onclick="location.assign('<%=request.getContextPath()%>/member/memberview.do?userId=<%=loginMember.getUserId()%>')">
						</td>
						<td>
							<input type = "button" value="로그아웃" onclick="location.replace('<%=request.getContextPath()%>/logout.do')">
						</td>
					</tr>
				</table>
			<%} %>
			
		<script>
	<%-- 		const logout = document.querySelector("input[value='로그아웃']");
			console.log(logout);
			logout.addEventListener("click",e=>{
				location.replace("<%=request.getContextPath()%>/logout.do");
			}); --%>
		<%-- 	const singup = document.querySelector("input[value='회원가입']");
			signup.addEventListener("click",e=>{
				location.replace("<%=request.getContextPath()%>/member/enrollMember.do");
			}); --%>
		</script>
		</div>
		<nav>
			<ul class="main-nav">
				<li class="home">
					<a href="">Home</a>
				</li>
				<li id="notice">
					<a href="">공지사항</a>
				</li>
				<li>
					<a href="">게시판</a>
				</li>
				<%if(loginMember!=null && loginMember.getUserId().equals("admin")){ %>
				<li>
					<a href="<%=request.getContextPath()%>/admin/memberlist.do">회원관리</a>
				</li>
				<%} %>
			</ul>
		</nav>
	</header>
