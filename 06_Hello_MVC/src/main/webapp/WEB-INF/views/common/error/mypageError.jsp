<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage = "true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
	<div class = "mainImg">
		<img src = "<%=request.getContextPath()%>/images/denied.jpg" width="200" height="200">
	</div>
	<script>
		setTimeout(()=>{
			<%-- location.replace("<%=request.getContextPath()%>/"); --%>
			location.replace("<%=request.getAttribute("prevPage")%>");
		},1000)
	</script>
</body>
<style>
   .mainImg{
      width:100%;
      height:800px;
      display:flex;
      justify-content:center;
      align-items:center;
   }

</style>
</html>