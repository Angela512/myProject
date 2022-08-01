<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내가 쓴 댓글 자유게시판</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel ="stylesheet" href="${pageContext.request.contextPath}/css/board-style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/member.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
	<h2>마이페이지</h2>
	<hr size="1" noshade="noshade" width="100%">
	<div id="main_nav">
		<ul>
				<li>
					<a href="myPage.do">내정보</a>
				</li>
				
				<li>
					<a href="myWrite.do">내가 쓴 글</a>
				</li>

				<li>
					<a href="myReply.do">내가 쓴 댓글</a>
				</li>

				<li>
					<a href="myLike.do">찜목록</a>
				</li>
		</ul>
	</div>

	<div id="main_nav">
		<ul>
				<li>
					<a href="myWrite.do">맛집찾기</a>
				</li>
				
				<li>
					<a href="trade.do">중고거래</a>
				</li>

				<li>
					<a href="job.do">구인구직</a>
				</li>

				<li>
					<a href="board.do">자유게시판</a>
				</li>
		</ul>
	</div>
	
	<hr size="1" noshade="noshade" width="100%">
	</div>
		<c:if test="${count == 0}">
		<div class="result-display">
			표시할 댓글이 없습니다.
		</div>
		</c:if>
		<div id="output"></div>
		<div class="paging-button" style="display:none;">
			<input type="button" value="다음글 보기">
		</div>
		<div id="loading" style="display:none;">
			<img src="${pageContext.request.contextPath}/images/ajax-loader.gif">
		</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>