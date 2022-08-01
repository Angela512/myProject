<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/notice-style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/notice.js"></script>
<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/notice.fav.js"></script> -->
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<hr width="100%" color="#b5b5b5" size="1px" id="hr_top1" noshade>
	<div class="content-main">
	<c:if test="${notice.notice_head == '필독'}">
		<td><div id="badge_must">공지사항  > ${notice.notice_head}</div></td>
	</c:if>
	<c:if test="${notice.notice_head == '공지'}">
		<td><div id="badge_read">공지사항  > ${notice.notice_head}</div></td>
	</c:if>
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
			<li id="name">
				<span>${notice.mem_name}</span><br>
				${notice.notice_date}
			 	<c:if test="${!empty notice.notice_modify_date}">
				| 최근 수정일 ${notice.notice_modify_date}
				</c:if>
			 	
			</li>
			<li id="n_count"> 조회수 ${notice.notice_count}</li>
		</ul>
		<hr size="1" noshade="noshade" width="100%">
		
		<!-- 업로드 사진 -->
		<c:if test="${!empty notice.notice_file1}">
		<div class="align-center">
			<img src="${pageContext.request.contextPath}/upload/${notice.notice_file1}" class="detail-img">
		</div>
		</c:if>
		<c:if test="${!empty notice.notice_file2}">
		<div class="align-center">
			<img src="${pageContext.request.contextPath}/upload/${notice.notice_file2}" class="detail-img">
		</div>
		</c:if>
		<c:if test="${!empty notice.notice_file3}">
		<div class="align-center">
			<img src="${pageContext.request.contextPath}/upload/${notice.notice_file3}" class="detail-img">
		</div>
		</c:if>
		<!-- 업로드사진 -->
		<p style="margin:50px 0 50px 0;">
			${notice.notice_content}
		</p>
		<hr size="1" noshade="noshade" width="100%">
		
			<!--
				<c:if test="${!empty board.modify_date}">
				최근 수정일 : ${board.modify_date}
				</c:if>
			  -->
				<%-- 로그인한 회원번호와 작성자 회원번호가 일치해야 수정,삭제 가능 --%>
				<div class="detail_bottom_btn">
					<input id="list_btn" type="button" value="목록으로" onclick="location.href='list.do'">
					<c:if test="${user_num == notice.mem_num || user_auth == 3}">
					<input type="button" id="modify_btn" value="수정" 
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
				</div>
			
		
	</div>
</div>
</body>
</html>





