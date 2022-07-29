<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MY페이지</title>
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
		
		<div class="mypage-div">
			<h3>프로필 사진</h3>
			<ul>
				<li>
					<c:if test="${empty member.mem_photo }">
					<img src="${pageContext.request.contextPath}/images/face.png" width="200" height="200" class="my-photo">
					</c:if>
					
					<c:if test="${!empty member.mem_photo}">
					<img src="${pageContext.request.contextPath}/upload/${member.mem_photo}" width="200" height="200" class="my-photo">
					</c:if>
				</li>
				
				<li>
					<div class="align-center">
						<input type="button" value="수정" id="photo_btn">
					</div>
					<div id="photo_choice" style="display:none;">
						<input type="file" id="photo" accept="image/gif,image/png,image/jpeg">
						<input type="button" value="전송" id="photo_submit">
						<input type="button" value="취소" id="photo_reset">
					</div>
				</li>
			</ul>
			
			<h3>비밀번호 수정</h3>
			<ul>
				<li>
					<input type="button" value="비밀번호 수정" onclick="location.href='modifyPasswordForm.do'">
				</li>
			</ul>
			
			<h3>회원탈퇴</h3>
			<ul>
				<li>
					<input type="button" value="회원 탈퇴" onclick="location.href='deleteUserForm.do'">
				</li>
			</ul>
		</div>
		
		<div class="mypage-div">
			<h3>연락처</h3>
			<ul>
				<li>이름 : ${member.mem_name }</li>
				<li>전화번호 : ${member.mem_phone }</li>
				<li>이메일 : ${member.mem_email }</li>
				<li>우편번호 : ${member.mem_zipcode }</li>
				<li>주소 : ${member.mem_addr1 } ${member.mem_addr2 }</li>
				<li>가입일 : ${member.mem_date }</li>
				<c:if test="${!empty member.mem_modify_date }">
				<li>최근 정보 수정일 : ${member.mem_modify_date }</li>
				</c:if>
				<li>
					<input type="button" value="연락처 수정" onclick="location.href='modifyUserForm.do'">
				</li>
			</ul>
		</div>
		
		<div class="mypage-end"></div>
	</div>
</div>
</body>
</html>