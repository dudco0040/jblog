<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp" />
		<div id="wrapper">
			<div id="content" class="full-screen">
				<sec:authorize access="isAuthenticated()">
				<sec:authentication property="principal" var="principal"/>
					<ul class="admin-menu">
						<li class="selected">기본설정</li>
						<li><a href="${pageContext.request.contextPath}/${principal.id}/blog/category">카테고리</a></li>
						<li><a href="${pageContext.request.contextPath}/${principal.id}/blog/write">글작성</a></li>
					</ul>
					<form action="${pageContext.request.contextPath }/${principal.id}/admin/update" enctype="multipart/form-data" method="post">
		 		      	<table class="admin-config">
				      		<tr>
				      			<td class="t">블로그 제목</td>
				      			<td><input type="text" size="40" name="title" value="${blogVo.title}"></td>
				      		</tr>
				      		<tr>
				      			<td class="t">로고이미지</td>
				      			<td><img src="${pageContext.request.contextPath}${blogVo.logo}">
	         				        <input type="hidden" name="logo" value="${blogVo.logo}">
	       				        </td>			
				      		</tr>      		
				      		<tr>
				      			<td class="t">&nbsp;</td>
				      			<td><input type="file" name="logo-file"></td>		
				      		</tr>           		
				      		<tr>
				      			<td class="t">&nbsp;</td>
				      			<td class="s"><input type="submit" value="기본설정 변경"></td>      			
				      		</tr>           		
				      	</table>
					</form>
				</sec:authorize>
			</div>
		</div>
		<div id="footer">
			<p>
				<strong>신나는 jblog 탐험기</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>
</html>