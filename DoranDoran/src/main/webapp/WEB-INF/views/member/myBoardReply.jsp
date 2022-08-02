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
		<c:if test="${count > 0}">
		<table class="board_table">
		<thead>
			 <tr>
			 	<th>작성자</th>
			 	<th>내용</th>
			 	<th>작성일</th>
			 </tr>
		</thead>
			 <c:forEach var="board_reply" items="${list}">
			 <tr id="tr_hover">
			 	<td>${boardReply.mem_num}</td>
			 	<td><a href="${pageContext.request.contextPath}/board/detail.do?board_num=${board.board_num}">${boardReply.reply_content}</a></td>  
			 	<td>${boardreply.reply_date}</td>
			 </tr>
			 </c:forEach>
		</table>
		<div class="board_page">
			${page}
		</div>
		<hr width="100%" color="#b5b5b5" size="1px"noshade>
		</c:if>
	</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>