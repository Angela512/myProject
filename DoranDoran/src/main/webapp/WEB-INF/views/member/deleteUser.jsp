<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${check }">
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원탈퇴</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>회원탈퇴 완료</h2>
		<div class="result-display">
			<div class="align-center">
				회원탈퇴가 완료되었습니다.
				<p> <!-- <br>2개랑 같음 -->
				<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
			</div>
		</div>
	</div>
</div>
</body>
</html>
</c:if>

<c:if test="${!check }">
	<script>
		alert('입력한 정보가 정확하지 않습니다!');
		history.go(-1);
	</script>
</c:if>