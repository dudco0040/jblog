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
<script src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
$(function() {
	$("#btn-checkemail").click(function() {
		var id = $("#blog-id").val();
		if(id == '') {
			return;
		}
		
		$.ajax({
			url: "/jblog3/user/api/checkid?id=" + id,
			type: "get",
			dataType: "json",
			error: function(xhr, status, err){
				console.error(err);			
			},
			success: function(response){
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
			<li><a href="">로그인</a></li>
			<li><a href="">회원가입</a></li>
			<li><a href="">로그아웃</a></li>
			<li><a href="">내블로그</a></li>
		</ul>
		<form class="join-form" id="join-form" method="post" action="">
			<label class="block-label" for="name">이름</label>
			<input id="name"name="name" type="text" value="">
			
			<label class="block-label" for="blog-id">아이디</label>
			<input id="blog-id" name="id" type="text"> 
			<input id="btn-checkemail" type="button" value="id 중복체크">
			<img id="img-checkemail" style="display: none;" src="${pageContext.request.contextPath}/assets/images/check.png">

			<label class="block-label" for="password">패스워드</label>
			<input id="password" name="password" type="password" />

			<fieldset>
				<legend>약관동의</legend>
				<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
				<label class="l-float">서비스 약관에 동의합니다.</label>
			</fieldset>

			<input type="submit" value="가입하기">

		</form>
	</div>
</body>
</html>
