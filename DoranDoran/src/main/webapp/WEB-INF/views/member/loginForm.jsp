<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/member.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>로그인</h2>
		<hr width="100%" color="#b5b5b5" size="1px" noshade>
		<form id="login_form" action="login.do" 
		                                    method="post">
			<ul>
				<li>
					<label for="id">아이디</label>
					<input type="text" name="mem_id" id="id" class="underline"
					       maxlength="12" autocomplete="off">
				</li>
				<li>	
					<label for="passwd">비밀번호</label>
					<input type="password" name="mem_pw" class="underline"
					       id="passwd" maxlength="12">
				</li>
			</ul>
			<div class="align-center">
				<input type="submit" value="로그인" id="login_btn">
				<input type="button" value="홈으로" id="home_btn"
				 onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
			</div>
		</form>
	</div>
	<hr width="100%" color="#b5b5b5" size="1px" noshade>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>






