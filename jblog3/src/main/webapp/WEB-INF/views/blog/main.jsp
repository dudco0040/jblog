<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
		<div id="header">
			<h1>${blogVo.title}</h1>
			<ul>
				<!-- login 한 경우만 -->
				<c:if test="${empty authUser }">
					<li><a href="${pageContext.request.contextPath}/user/login">로그인</a></li>
				</c:if>
				<li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
				<!-- 본인인 경우만 -->
				<c:if test="${not empty authUser && authUser.id == id}">
					<li><a href="${pageContext.request.contextPath}/${id}/admin/basic">블로그 관리</a></li>
				</c:if>
			</ul>
		</div>
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
					<h4>${postVo.title}</h4>
					<p>
						${postVo.contents}
					<p>
				</div>
				<ul class="blog-list">
					<c:forEach var="post" items="${posts}">
						<li><a href="${pageContext.request.contextPath}/${authUser.id}/${post.categoryNo}/${post.no}">${post.title}</a> <span>${post.regDate}</span>	</li>
					</c:forEach>
				</ul>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath}/${blogVo.logo}">
			</div>
		</div>

		<div id="navigation">
			<h2>카테고리</h2>
			<c:forEach var="category" items="${categories}">
				<ul>
					<li><a href="${pageContext.request.contextPath}/${id}/${category.no }">${category.name }</a></li>
				</ul>
			</c:forEach>
		</div>
		
		<div id="footer">
			<p>
				<strong>${blogVo.title}</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>
</html>