<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>
<%@ page import="java.util.List" %>
<% List<Member> members = (List<Member>)request.getAttribute("members"); %>

<style type="text/css">
    section#memberList-container {text-align:center;}
    
    section#memberList-container table#tbl-member {width:100%; border:1px solid gray; border-collapse:collapse;}
    section#memberList-container table#tbl-member th, table#tbl-member td {border:1px solid gray; padding:10px; }
	
 	div#search-container {margin:0 0 10px 0; padding:3px; 
    background-color: rgba(0, 188, 212, 0.3);}
    div#search-userId{display:inline-block;}
    div#search-userName{display:none;}
    div#search-gender{display:none;}
    div#numPerpage-container{float:right;}
    form#numperPageFrm{display:inline;}
</style>    
	<%if(!loginMember.getUserId().equals("admin")) {
		response.sendRedirect(request.getContextPath());}%>
		
	
    <section id="memberList-container">
        <h2>회원관리</h2>
                <div id="search-container">
        	검색타입 : 
        	<select id="searchType">
        		<option value="userId" >아이디</option>
        		<option value="userName" >회원이름</option>
        		<option value="gender" >성별</option>
        	</select>
        	<div id="search-userId">
        		<form action="<%=request.getContextPath()%>/admin/searchMember">
        			<input type="hidden" name="searchType" value="userId" >
        			<input type="text" name="searchKeyword" size="25" 
        			placeholder="검색할 아이디를 입력하세요" >
        			<button type="submit">검색</button>
        		</form>
        	</div>
        	<div id="search-userName">
        		<form action="<%=request.getContextPath()%>/admin/searchMember">
        			<input type="hidden" name="searchType" value="userName">
        			<input type="text" name="searchKeyword" size="25" 
        			placeholder="검색할 이름을 입력하세요">
        			<button type="submit">검색</button>
        		</form>
        	</div>
        	<div id="search-gender">
        		<form action="<%=request.getContextPath()%>/admin/searchMember">
        			<input type="hidden" name="searchType" value="gender">
        			<label><input type="radio" name="searchKeyword" value="M" >남</label>
        			<label><input type="radio" name="searchKeyword" value="F" >여</label>
        			<button type="submit">검색</button>
        		</form>
        	</div>
        </div>
        <div id="numPerpage-container">
        	페이지당 회원수 : 
        	<form id="numPerFrm" action="<%=request.getContextPath()%>/admin/searchMember">
        		<select name="numPerpage" id="numPerpage">
        			<option value="10">10</option>
        			<option value="5" >5</option>
        			<option value="3" >3</option>
        		</select>
        	</form>
        </div>
        
        
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
        <div id="pageBar">
        	<%=request.getAttribute("pageBar") %>
        </div>
    </section>
    
    
    <script>
    	
		const type = document.querySelector("#searchType");
		
		type.addEventListener("change",(e)=>{
			let target = $(e.target).val();
			switch(target){
				case "userId" : 
					$("#search-userId").css("display","inline-block");
					$("#search-userName").css("display","none");
					$("#search-gender").css("display","none");
					break;
				case "userName" : 
					$("#search-userId").css("display","none");
					$("#search-userName").css("display","inline-block");
					$("#search-gender").css("display","none");
					break;
				case "gender" : 
					$("#search-userId").css("display","none");
					$("#search-userName").css("display","none");
					$("#search-gender").css("display","inline-block");
					break;
			}
		})
		
		const page = document.querySelector("#numPerpage");
		page.addEventListener("change",(e)=>{
			let target = e.target.value;
			$("#numPerpage").attr("value",target);
		})
	</script>


<%@ include file="/WEB-INF/views/common/footer.jsp"%>