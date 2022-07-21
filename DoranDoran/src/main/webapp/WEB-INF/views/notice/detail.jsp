<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/notice.js"></script>
<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/notice.fav.js"></script> -->
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>${notice.notice_title}</h2>
		<ul class="detail-info">
			<li>
				<c:if test="${!empty notice.mem_photo}">
				<img src="${pageContext.request.contextPath}/upload/${notice.mem_photo}" width="40" height="40" class="my-photo">
				</c:if>
				<c:if test="${empty notice.mem_photo}">
				<img src="${pageContext.request.contextPath}/images/face.png" width="40" height="40" class="my-photo">
				</c:if>
			</li>
			<li>
				${notice.mem_name}<br>
				조회 : ${notice.notice_count}
			</li>
		</ul>
		<hr size="1" noshade="noshade" width="100%">
		<c:if test="${!empty notice.notice_file1}">
		<div class="align-center">
			<img src="${pageContext.request.contextPath}/upload/${notice.notice_file1}" class="detail-img">
		</div>
		</c:if>
		<p>
			${notice.notice_content}
		</p>
		<hr size="1" noshade="noshade" width="100%">
		<ul class="detail-sub">
			<li>
				<%-- 좋아요 --%>
				<img id="output_fav" src="${pageContext.request.contextPath}/images/fav01.gif" width="50">
				좋아요
				<span id="output_fcount">0</span>
			</li>
			<li>
			<!--
				<c:if test="${!empty board.modify_date}">
				최근 수정일 : ${board.modify_date}
				</c:if>
			  -->
				작성일 : ${notice.notice_date}
				<%-- 로그인한 회원번호와 작성자 회원번호가 일치해야 수정,삭제 가능 --%>
				<c:if test="${user_num == notice.mem_num}">
				<input type="button" value="수정" 
				 onclick="location.href='updateForm.do?notice_num=${notice.notice_num}'">
				<input type="button" value="삭제" id="delete_btn">
				<script type="text/javascript">
					let delete_btn = document.getElementById('delete_btn');
					//이벤트 연결
					delete_btn.onclick=function(){
						let choice = confirm('삭제하시겠습니까?');
						if(choice){
							location.replace('delete.do?notice_num=${notice.notice_num}');
						}
					};
				</script>
				</c:if>
			</li>
		</ul>
		<!-- 댓글 시작 
		<div id="reply_div">
			<span class="re-title">댓글 달기</span>
			<form id="re_form">
				<input type="hidden" name="board_num" value="${board.board_num}" id="board_num">
			</form>
		</div>
		댓글 끝 -->
	</div>
</div>
</body>
</html>





