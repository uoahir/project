<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>
<%@ page import = "java.util.List,mvc.jy.model.dto.Notice,java.text.SimpleDateFormat" %>
<% List<Notice> notice = (List<Notice>)request.getAttribute("notice"); %>
    <style>
    section#notice-container{width:600px; margin:0 auto; text-align:center;}
    section#notice-container h2{margin:10px 0;}
    table#tbl-notice{width:500px; margin:0 auto; border:1px solid black; border-collapse:collapse; clear:both; }
    table#tbl-notice th {width: 125px; border:1px solid; padding: 5px 0; text-align:center;} 
    table#tbl-notice td {border:1px solid; padding: 5px 0 5px 10px; text-align:left;}
    </style>
	
	<section id="notice-container">
        <h2>공지사항</h2>
        <table id="tbl-notice">
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>첨부파일</th>
                <th>작성일</th>
            </tr>
            <tbody>
            <%if(notice !=null){
            	for(Notice n : notice){%>
           		
           		<tr>
           			<td><%= n.getNoticeNo() %></td>
           			<td onclick="location.assign('<%=request.getContextPath()%>/notice/noticedetail.do?noticeNo=<%=n.getNoticeNo()%>');"><%= n.getNoticeTitle() %></td>
           			<td><%= n.getNoticeWriter()%></td>
           			<% if(!n.getFilePath().equals("")) {%>
           			<td><img alt="" src="<%=request.getContextPath()%>/images/file.png"></td>
           			<%} else{ %>
           			<td><%= n.getFilePath() %></td>
           			<%} %>
           			<td><%= new SimpleDateFormat("yyyy.MM.dd").format(n.getNoticeDate())%></td>
           			<%-- <td><%=n.getNoticeDate() %></td> --%>
           		</tr>
           <%} 
           }%> 
           </tbody>
        </table>
    </section>
    <script>
    	// 제목을 눌렀을 때.. 해당 번호가 넘어가게 전환시키기 .. . !! 
    	function detail(){
    		console.log("들어와라");
    		location.assign("<%=request.getContextPath()%>/notice/noticedetail.do?noticeNo=");
    	}
    	
    </script>
    
    


<%@ include file="/WEB-INF/views/common/footer.jsp"%>