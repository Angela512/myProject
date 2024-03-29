<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>
<link rel ="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel ="stylesheet" href="${pageContext.request.contextPath}/css/notice-style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/notice.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<jsp:include page="/WEB-INF/views/notice/banner.jsp"/>
	<div class="content-main">
		<jsp:include page="noticeHeader.jsp"/>
		<div class="list-space align-right">
		
		<%-- 공지게시판은 관리자만 글쓰기 가능 --%>
			${count}개의 공지
			<input id="home_button" type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">         
		
		</div>
		<c:if test="${count == 0}">
		<hr width="100%" color="#b5b5b5" size="1px" noshade>
		<div class="result-display">
			표시할 게시물이 없습니다.
		</div>
		<div class="write_button">
			<c:if test="${user_auth == 3 && !empty user_num}">
				<img src="../images/pen1.png" id="write_pen">
				<input id="write_button_box" type="button" value="글쓰기" onclick="location.href='writeForm.do'">
			</c:if>
		</div>
		<hr width="100%" color="#b5b5b5" size="1px" noshade>
		
		</c:if>
		<c:if test="${count > 0}">
		<table class="notice_table">
			<thead>
			<tr>
				<th>말머리</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>조회</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="notice" items="${list}">
			<tr>
				<c:if test="${notice.notice_head == '필독'}">
				<td><div class="must">${notice.notice_head}</div></td>
				<td><a href="detail.do?notice_num=${notice.notice_num}">${notice.notice_title}</a></td>
				<td>${notice.mem_name}</td>
				<td>${notice.notice_date}</td>
				<td>${notice.notice_count}</td>
				</c:if>
			</tr>
			</c:forEach>
			<c:forEach var="notice" items="${list}">
			<tr>
				<c:if test="${notice.notice_head == '공지'}">
				<td><div class="read">${notice.notice_head}</div></td>
				<td><a href="detail.do?notice_num=${notice.notice_num}">${notice.notice_title}</a></td>
				<td>${notice.mem_name}</td>
				<td>${notice.notice_date}</td>
				<td>${notice.notice_count}</td>
				</c:if>
			</tr>
			</c:forEach>
			</tbody>
		</table>
		<div class="write_button">
		<c:if test="${user_auth == 3 && !empty user_num}">
			<img src="../images/pen1.png" id="write_pen">
			<input id="write_button_box" type="button" value="글쓰기" onclick="location.href='writeForm.do'">
		</c:if>
		</div>
		<div class="board_page">
			${page}
		</div>
		<hr width="100%" color="#b5b5b5" size="1px" noshade>
		</c:if>
		<form id="search_form" action="list.do" method="get">
			<input type="hidden" name="head" value="${param.head}">
			<ul class="search_notice">
				<li>
					<select name="keyfield" style="width:100px; height:30px;">
						<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
						<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>작성자</option>
						<option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>내용</option>
					</select>
				</li>
				<li>
					<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}" style="width:180px; height:30px;">
				</li>
				<li>
					<input id="search_button" type="submit" value="검색" style="width:80px; height:30px;">
				</li>
			</ul>
		</form>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>

</body>
</html>







