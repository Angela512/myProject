<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 목록</title>
<link rel ="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel ="stylesheet" href="${pageContext.request.contextPath}/css/board-style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/board.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<jsp:include page="/WEB-INF/views/board/banner.jsp"/>
	<div class="content-main">
		<div class="sub_nav" id="sub_nav1">
			<ul>
				<li><a href="${pageContext.request.contextPath}/board/list.do">전체보기</a></li>
				<li><a href="${pageContext.request.contextPath}/board/list.do?head=동네소식">동네소식</a></li>
				<li><a href="${pageContext.request.contextPath}/board/list.do?head=도움요청">도움요청</a></li>
				<li><a href="${pageContext.request.contextPath}/board/list.do?head=함께해요">함께해요</a></li>
				<li><a href="${pageContext.request.contextPath}/board/list.do?head=기타">기타</a></li>
			</ul>
		</div>
		<div class="list-space align-right">
			<div class="sub_nav" id="sub_nav2">
			<select name="sort" id="sort" style="width:110px; height:30px;">
				<option value="1" <c:if test="${param.sort==1}">selected</c:if>>최신순</option>
				<option value="2" <c:if test="${param.sort==2}">selected</c:if>>조회순</option>
				<option value="3" <c:if test="${param.sort==3}">selected</c:if>>댓글순</option>
			</select>
			</div>
			<script>
				$('#sort').change(function(){
					location.href='list.do?keyfield=${param.keyfield}&keyword=${param.keyword}&head='+$('#h_head').val()+'&sort='+$('#sort').val();
				});
			</script>
		<ul>
			<li id="total">총${count}개</li>
		</ul><br>
		</div>
		<c:if test="${count == 0}">
		<div class="result-display">
			표시할 게시물이 없습니다.
		</div>
		</c:if>
		<c:if test="${count > 0}">
		<table class="board_table">
		<thead>
			 <tr>
			 	<th>말머리</th>
			 	<th>제목</th>
			 	<th>작성자</th>
			 	<th>작성일</th>
			 	<th>조회수</th>
			 </tr>
		</thead>
			 <c:forEach var="board" items="${list}">
			 <tr id="tr_hover">
			 	<td>${board.board_head}</td>
			 	<td><a href="detail.do?board_num=${board.board_num}">${board.board_title}</a>  <c:if test="${board.reply_cnt>0}">[${board.reply_cnt}]</c:if></td>
			 	<td>${board.mem_name}</td>
			 	<td>${board.board_date}</td>
			 	<td>${board.board_count}</td>
			 </tr>
			 </c:forEach>
		</table>
		<c:if test="${!empty user_num}">
			<div class=write_button>
			<img src="../images/pen1.png" id="write_pen">
			<input id="write_button_box" type="button" value="글쓰기" onclick="location.href='writeForm.do'">
			</div>
		</c:if>
			<%-- <input type="button" value="목록" onclick="location.href='list.do'">
			<input type="button" value="홈으로" 
			onclick="location.href='${pageContext.request.contextPath}/main/main.do'"> --%>
		<div class="board_page">
			${page}
		</div>
		<hr width="100%" color="#b5b5b5" size="1px"noshade>
		</c:if>
		<form id="search_form" action="list.do" method="get">
		    <input type="hidden" name="head" id="h_head" value="${param.head}">
			<ul class="search_board">
				<li>
					<select id="keyfield" name="keyfield" style="width:100px; height:30px;">
						<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
						<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>작성자</option>
						<option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>내용</option>
					</select>
				</li>
				<li>
					<input type="search" size="16" name="keyword" id="keyword" 
					value="${param.keyword}" style="width:180px; height:30px;">
				</li>
				<li>
					<input id="search_button" type="submit" value="검색" style="width:80px; height:30px;">
				</li>
			</ul>
		</form>
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>