<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/member.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
	<h2>비밀번호 수정</h2>
	<hr size="1" noshade="noshade" width="100%">
	<form action="modifyPassword.do" method="post" id="password_form">
		<ul>
			<li>
					<label for="id">아이디</label>
					<input type="text" name="mem_id" id="id" maxlength="12">
			</li>
			
			<li>
					<label for="origin_passwd">현재 비밀번호</label>
					<input type="password" name="origin_passwd" id="origin_passwd" maxlength="12">
			</li>
			
			<li>
					<label for="passwd">새 비밀번호</label>
					<input type="password" name="passwd" id="passwd" maxlength="12">
			</li>
			
			<li>
					<label for="cpasswd">새 비밀번호 확인</label>
					<input type="password" name="cpasswd" id="cpasswd" maxlength="12">
					<span id="message_cpasswd"></span>
			</li>
		</ul>
		
		<div class="align-center">
				<input type="submit" value="완료">
				<input type="button" value="취소" onclick="location.href='myPage.do'">
		</div>
	</form>
	</div>
</div>
</body>
</html>