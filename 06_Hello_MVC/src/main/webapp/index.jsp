<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ page import="mvc.jy.model.dto.Member" %>
<% Member m = (Member)request.getAttribute("member"); %>
	<section id= "content">
		<h2 align="center" style="margin-top:200px">회원조회</h2>
		<%if(m!=null) {%>
			<table>
				<tr>
					<th>아이디</th>
					<th>비밀번호</th>
					<th>이름</th>
					<th>성별</th>
					<th>나이</th>
					<th>이메일</th>
					<th>전화번호</th>
					<th>주소</th>
					<th>취미</th>
					<th>가입일</th>
				</tr>
				<tr>
					<td><%=m.getUserId() %></td>
					<td><%=m.getPassword() %></td>
					<td><%=m.getUserName() %></td>
					<td><%=m.getGender() %></td>
					<td><%=m.getAge() %></td>
					<td><%=m.getEmail() %></td>
					<td><%=m.getPhone() %></td>
					<td><%=m.getAddress() %></td>
					<td>
						<%if(m.getHobby().length != 0) {%>
						<ul>
							<%for(int i = 0; i< m.getHobby().length;i++){ %>
								<li><%=m.getHobby()[i] %></li>
							<%} %>
						</ul>
						<%} %>
					</td>
					<td><%=m.getEnrollDate() %></td>
				</tr>
			</table>
		<%} else{%>
		<h3> 해당 회원이 없읍니다 . . . </h3>
		<%} %>
	</section>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>	