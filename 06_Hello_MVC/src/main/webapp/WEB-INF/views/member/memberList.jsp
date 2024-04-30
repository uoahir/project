<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>
<%@ page import="java.util.List" %>
<% List<Member> members = (List<Member>)request.getAttribute("members"); %>

<style type="text/css">
    section#memberList-container {text-align:center;}
    
    section#memberList-container table#tbl-member {width:100%; border:1px solid gray; border-collapse:collapse;}
    section#memberList-container table#tbl-member th, table#tbl-member td {border:1px solid gray; padding:10px; }
</style>
    
    <section id="memberList-container">
        <h2>회원관리</h2>
        <table id="tbl-member">
            <thead>
            	<tr>
		            <th>아이디</th>
				    <th>이름</th>
				    <th>성별</th>
				    <th>나이</th>
				    <th>이메일</th>
				    <th>전화번호</th>
				    <th>주소</th>
				    <th>취미</th>
				    <th>가입날짜</th>
                </tr>
            </thead>
            <tbody>
       	    	<%if(members.size()>0){ 
       	    		for(Member m : members){%>
       	    		<tr>
       	    			<td><%=m.getUserId() %></td>
       	    			<td><%=m.getUserName() %></td>
       	    			<td><%=m.getGender() %></td>
       	    			<td><%=m.getAge() %></td>
       	    			<td><%=m.getEmail() %></td>
       	    			<td><%=m.getPhone() %></td>
       	    			<td><%=m.getAddress() %></td>
       	    			<td>
       	    				<%if (m.getHobby()!=null){%>
       	    				<%for(String h: m.getHobby()){ %>
       	    					<p><%=h %></p>
       	    				<%} 
       	    				  }else{%>
       	    					<p>없음</p>
       	    				<%} %>
       	    			</td>
       	    			<td><%=m.getEnrollDate() %></td>
       	    		</tr>
       	    		<%} %>
       	    	<%} else{%>
       	    		<tr>
       	    			<td colspan="9">조회된 데이터가 없읍니다..</td>
       	    		</tr>
       	    	<%} %>
            </tbody>
        </table>
    </section>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>