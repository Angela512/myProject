<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글상세</title>
<link rel ="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel ="stylesheet" href="${pageContext.request.contextPath}/css/board-style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/board.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/board.reply.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<hr width="100%" color="#b5b5b5" size="1px" id="hr_top1" noshade>
	<div class="content-main">
		<c:if test="${board.board_head == '동네소식'}">
			<td><div id="badge_news">자유게시판  > ${board.board_head}</div></td>
		</c:if>
		<c:if test="${board.board_head == '도움요청'}">
			<td><div id="badge_help">자유게시판  > ${board.board_head}</div></td>
		</c:if>
		<c:if test="${board.board_head == '함께해요'}">
			<td><div id="badge_together">자유게시판  > ${board.board_head}</div></td>
		</c:if>
		<c:if test="${board.board_head == '기타'}">
			<td><div id="badge_etc">자유게시판  > ${board.board_head}</div></td>
		</c:if>
		<h2>${board.board_title}</h2>
		<ul class="detail-info">
			<li>
				<c:if test="${!empty board.mem_photo}">
				<img src="${pageContext.request.contextPath}/upload/${board.mem_photo}" 
				width="40" height="40" class="my-photo">
				</c:if>
				<c:if test="${empty board.mem_photo}">
				<img src="${pageContext.request.contextPath}/images/face.png"
				width="40" height="40" class="my-photo">
				</c:if>
			</li>
			<li id="name">
				<span>${board.mem_name}</span><br>
				${board.board_date} 
				<c:if test="${!empty board.board_modifydate}">
				| 수정 ${board.board_modifydate}
				</c:if>
			</li>
			<li id="reply_view">댓글 ${board.reply_cnt} | 조회수 ${board.board_count}</li>
		</ul>
		<hr size="1" noshade="noshade" width="100%">
		<c:if test="${!empty board.board_image1}">
		<div class="align-center">
			<img src="${pageContext.request.contextPath}
			/upload/${board.board_image1}" class="detail-img">
		</div>
		</c:if>
		<c:if test="${!empty board.board_image2}">
		<div class="align-center">
			<img src="${pageContext.request.contextPath}
			/upload/${board.board_image2}" class="detail-img">
		</div>
		</c:if>
		<c:if test="${!empty board.board_image3}">
		<div class="align-center">
			<img src="${pageContext.request.contextPath}
			/upload/${board.board_image3}" class="detail-img">
		</div>
		</c:if>
		<p style="margin:50px 0 50px 0;">
			${board.board_content}
		</p>
		<hr size="1" noshade="noshade" width="100%">
		<ul class="detail-sub align-right">
			<li>
				<%-- <c:if test="${!empty board.board_modifydate}">
				최근 수정일 : ${board.board_modifydate}
				</c:if>
				작성일 : ${board.board_date} --%>
				<%-- 로그인한 회원번호와 작성자 회원번호가 일치해야 수정,삭제 가능 --%>
					
			</li>
		</ul>
		<!-- 댓글 시작 -->
		<div id="reply_div">
			<span class="re-title">댓글</span>
			<form id="re_form">
				<input type="hidden" name="board_num" value="${board.board_num}" id="board_num">
				<textarea rows="20" cols="95" name="reply_content" id="reply_content" class="rep-content"
					<c:if test="${empty user_num}">disabled="disabled"</c:if>
					><c:if test="${empty user_num}">로그인해야 작성할 수 있습니다.</c:if></textarea>
				<c:if test="${!empty user_num}">
				<div id="re_first">
					<span class="letter-count">300/300</span>
				</div>
				<div id="re_second" class="align-right">
					<input class="submit" type="submit" value="전송">
				</div>
				</c:if>
			</form>
		</div>
		<!-- 댓글 목록 출력 시작(댓글은 번호가 아닌 다음글 누르면 내용이 붙음) -->
		<div id="output"></div>
		<div class="paging-button" style="display:none;">
			<input type="button" value="다음글 보기">
		</div>
		<div id="loading" style="display:none;">
			<img src="${pageContext.request.contextPath}/images/ajax-loader.gif">
		</div>
		<!-- 댓글 목록 출력 끝 -->
		<!-- 댓글 끝 -->
		
		<!-- 하단 버튼 들 시작-->
		<div class="detail_bottom_btn">
			<input id="list_btn" type="button" value="목록으로" onclick="location.href='list.do'">
			<c:if test="${user_num == board.mem_num}">
				<input type="button" value="수정" id="modify_btn"
					onclick="location.href='updateForm.do?board_num=${board.board_num}'">
				<input type="button" value="삭제" id="delete_btn">
					<script type="text/javascript">
						let delete_btn = document.getElementById('delete_btn');
						//이벤트 연결
						delete_btn.onclick=function(){
							let choice = confirm('삭제하시겠습니까?');
							if(choice){
								location.replace('delete.do?board_num=${board.board_num}');
							}
						};
					</script>
			</c:if>
		</div>
			<c:if test="${!empty user_num && user_auth == 3}">
				<input type="button" value="관리자 삭제" id="delete_btn">
					<script type="text/javascript">
						let delete_btn = document.getElementById('delete_btn');
						//이벤트 연결
						delete_btn.onclick=function(){
							let choice = confirm('[관리자 전용]삭제하시겠습니까?');
							if(choice){
								location.replace('AdminDelete.do?board_num=${board.board_num}');
							}
						};
					</script>
					</c:if>
					<!-- 하단 버튼 들 끝-->
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>






