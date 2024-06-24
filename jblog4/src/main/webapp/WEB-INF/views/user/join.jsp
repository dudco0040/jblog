<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
$(function() {
	$("#btn-checkemail").click(function() {
		var id = $("#blog-id").val();
		if(id == '') {
			alert("ID를 입력하세요.");
			return;
		}
		
		$.ajax({
			url: "/jblog4/user/api/checkid?id=" + id,
			type: "get",
			dataType: "json",
			error: function(xhr, status, err){
				console.error(err);			
			},
			success: function(response){
				console.log("AJAX 요청 성공:", response);
				if(response.exist) {
					alert("존재하는 ID 입니다. 다른 ID를 사용해 주세요.");
					$("#blog-id").val("");
					$("#blog-id").focus();
					return;
				}
				
				// 사용할 수 있는 id
				$("#btn-checkemail").hide();
				$("#img-checkemail").show();
			}
		});
	})
});
</script>

</head>
<body>
	<div class="center-content">
		<h1 class="logo">JBlog</h1>
		<ul class="menu">
			<li><a href="${pageContext.request.contextPath}/user/login">로그인</a></li>
			<li><a href="">회원가입</a></li>
			<li><a href="">로그아웃</a></li>
			<li><a href="">내블로그</a></li>
		</ul>
		<form:form 
			modelAttribute="userVo"
			class="join-form" 
			id="join-form" 
			method="post" 
			action="${pageContext.request.contextPath}/user/join">
			
			<label class="block-label" for="name">이름</label>
			<form:input path="name" />
			<spring:hasBindErrors name="userVo">
				<p style="text-align:left; padding:0">
					<c:if test="${errors.hasFieldErrors('name')}">
						<!-- default message -->
						${errors.getFieldError('name').defaultMessage }
					</c:if>
				</p>
			</spring:hasBindErrors>
	
			<label class="block-label" for="blog-id">아이디</label>
			<form:input path="id" id="blog-id" /> 
			<input id="btn-checkemail" type="button" value="id 중복체크">
			<img id="img-checkemail" src="${pageContext.request.contextPath}/assets/images/check.png" style="vertical-align:bottom; width:24px; height:24px; display:none">
			<spring:hasBindErrors name="userVo">
				<p style="text-align:left; padding:0">
					<c:if test="${errors.hasFieldErrors('id')}">
						<!-- default message -->
						${errors.getFieldError('id').defaultMessage }
					</c:if>
				</p>
			</spring:hasBindErrors>
			
			
			<label class="block-label" for="password">패스워드</label>
			<form:input path="password" />
			<spring:hasBindErrors name="userVo">
				<p style="text-align:left; padding:0">
					<c:if test="${errors.hasFieldErrors('password')}">
						<!-- default message -->
						${errors.getFieldError('password').defaultMessage }
					</c:if>
				</p>
			</spring:hasBindErrors>
			

			<fieldset>
				<legend>약관동의</legend>
				<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
				<label class="l-float">서비스 약관에 동의합니다.</label>
			</fieldset>

			<input type="submit" value="가입하기">

		</form:form>
	</div>
</body>
</html>
