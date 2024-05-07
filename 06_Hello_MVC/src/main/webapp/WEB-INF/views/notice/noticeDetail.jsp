<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "mvc.jy.model.dto.Notice" %>
<% Notice notice = (Notice)request.getAttribute("notice"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    section#notice-container{width:600px; margin:0 auto; text-align:center;}
    section#notice-container h2{margin:10px 0;}
    table#tbl-notice{width:500px; margin:0 auto; border:1px solid black; border-collapse:collapse; clear:both; }
    table#tbl-notice th {width: 125px; border:1px solid; padding: 5px 0; text-align:center;} 
    table#tbl-notice td {border:1px solid; padding: 5px 0 5px 10px; text-align:left;}
</style>
</head>
<body>
	<div id="notice-container">
        <table id="tbl-notice">
        <% if(notice!=null){ %>
        <tr>
            <th>제 목</th>
            <td><%=notice.getNoticeTitle() %></td>
        </tr>
        <tr>
            <th>작성자</th>
            <td><%=notice.getNoticeWriter() %></td>
        </tr>
        <tr>
            <th>첨부파일</th>
            <td>
            <%if(!notice.getFilePath().equals("")) {%>
            	<img alt="" src="<%=request.getContextPath()%>/images/file.png">
            <%} else { %> 
            	<%=notice.getFilePath() %>
            <%} %>
            </td>
        </tr>
        <tr>
            <th>내 용</th>
            <td><%=notice.getNoticeContent() %></td>
        </tr>
        <%} %>
        <tr>
            <th colspan="2">
                <input type="button" value="수정하기" onclick="">
                <input type="button" value="삭제하기" onclick="">
            </th>
        </tr>
    </table>
    </div>
    
    
</body>
</html>