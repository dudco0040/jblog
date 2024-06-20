<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
$(function(){
	$("#languages a").click(function(event){
		event.preventDefault();
		console.log($(this).data("lang"));
		
		document.cookie = 
			"lang=" + $(this).data("lang") + ";" +				// name=value => resolver-locale: ko
			"path=${pageContext.request.contextPath}" + ";" + 	// path 
			"max-age=" + (30*24*60*60)							// max-age
			
		// reload
		location.reload();
	});
});
</script>
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
			<div id="languages">
				<c:choose>
					<c:when test='${language == "en"}'>
						<a href="" data-lang="ko">KR</a>
						<a href="" class="active" data-lang="en">EN</a>
					</c:when>
					<c:otherwise>
						<a href="" data-lang="ko" class="active">KR</a>
						<a href="" data-lang="en">EN</a>
					</c:otherwise>
				</c:choose>
			</div>
			<ul>
				<!-- login 한 경우만 -->
				<c:if test="${empty authUser }">
					<li><a href="${pageContext.request.contextPath}/user/login"><spring:message code="header.gnb.login" /></a></li>
				</c:if>
				<li><a href="${pageContext.request.contextPath}/user/logout"><spring:message code="header.gnb.logout" /></a></li>
				<!-- 본인인 경우만 -->
				<c:if test="${not empty authUser && authUser.id == id}">
					<li><a href="${pageContext.request.contextPath}/${id}/admin/basic"><spring:message code="header.gnb.blogmanagement" /></a></li>
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
			<h2><spring:message code="navigation.li.category" /></h2>
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