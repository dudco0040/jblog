<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="header">
				<h1>
					${blogVo.title }
				</h1>
			<ul>
				<!-- login 한 경우만 -->
				<c:if test="${empty authUser}">
					<li><a href="${pageContext.request.contextPath}/user/login">로그인</a></li>
				</c:if>
				<li><a href="${pageContext.request.contextPath}/${id }">내 블로그</a></li>
				<li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
				<!-- <li><a href="">블로그 관리</a></li>  -->
			</ul>
		</div>
</body>
</html>