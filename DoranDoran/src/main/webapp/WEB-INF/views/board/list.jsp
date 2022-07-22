<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 목록</title>
<link rel ="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/board.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>자유게시판 목록</h2>
		<form id="search_form" action="list.do" method="get">
			<ul class="search">
				<li>
					<select name="keyfield">
						<option value="1">제목</option>
						<option value="2">작성자</option>
						<option value="3">내용</option>
					</select>
				</li>
				<li>
					<input type="search" size="16" name="keyword" id="keyword" 
					value="${param.keyword}">
				</li>
				<li>
					<input type="submit" value="검색">
				</li>
			</ul>
		</form>
		<div class="list-space align-right">
			<c:if test="${!empty user_num}">
			<input type="button" value="글쓰기" onclick="location.href='writeForm.do'">
			</c:if>
			<input type="button" value="목록" onclick="location.href='list.do'">
			<input type="button" value="홈으로" 
			onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
		</div>
		<c:if test="${count == 0}">
		<div class="result-display">
			표시할 게시물이 없습니다.
		</div>
		</c:if>
		<c:if test="${count > 0}">
		<table>
			 <tr>
			 	<th>글번호</th>
			 	<th>제목</th>
			 	<th>작성자</th>
			 	<th>작성일</th>
			 	<th>조회</th>
			 </tr>
			 <c:forEach var="board" items="${list}">
			 <tr>
			 	<td>${board.board_num}</td>
			 	<td><a href="detail.do?board_num=${board.board_num}">${board.board_title}</a></td>
			 	<td>${board.mem_id}</td>
			 	<td>${board.board_date}</td>
			 	<td>${board.board_count}</td>
			 </tr>
			 </c:forEach>
		</table>
		<div class="align-center">
			${page}
		</div>
		</c:if>
		
	</div>
</div>
</body>
</html>