<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내가 쓴 글</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/member.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
	<h2>마이페이지</h2>
	<hr size="1" noshade="noshade" width="100%">
	<ul>
			<li>
			<button onclick="location.href='myPage.do'">내정보</button>
			<button onclick="location.href='myWrite.do'">내가 쓴 글</button>
			<button onclick="location.href='myReply.do'">내가 쓴 댓글</button>
			<button onclick="location.href='myLike.do'">찜목록</button>
			</li>
		</ul>
	
	<ul>
		<li>
		<button onclick="location.href='myWrite.do'">맛집찾기</button>
		<button onclick="location.href='trade.do'">중고거래</button>
		<button onclick="location.href='job.do'">구인구직</button>
		<button onclick="location.href='board.do'">자유게시판</button>
		</li>
	</ul>
	</div>
</div>
</body>
</html>