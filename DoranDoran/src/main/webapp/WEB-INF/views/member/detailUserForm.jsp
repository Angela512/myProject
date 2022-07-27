<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보(관리자 전용)</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>${member.mem_name}님의 회원정보 (관리자 전용)</h2>
		<form action="detailUser.do" method="post" id="detail_form">
			<input type="hidden" name="mem_num" value="${member.mem_num}">
			<ul>
				<c:if test="${empty member.mem_photo }">
					<img src="${pageContext.request.contextPath}/images/face.png" width="100" height="100" class="my-photo">
				</c:if>
				<c:if test="${!empty member.mem_photo}">
					<img src="${pageContext.request.contextPath}/upload/${member.mem_photo}" width="100" height="100" class="my-photo">
				</c:if>
				<li>
					<label>등급</label>
					<c:if test="${member.auth != 3}">
					<input type="radio" name="auth" value="1" id="auth1"
					    <c:if test="${member.auth == 1}">checked</c:if>/>정지
					<input type="radio" name="auth" value="2" id="auth2"
					    <c:if test="${member.auth == 2}">checked</c:if>/>일반    
					</c:if>
					<c:if test="${member.auth == 3}">
					<input type="radio" name="auth" value="3" id="auth3" checked>관리
					</c:if>
				</li>
			</ul>
			<div class="align-center">
				<input type="submit" value="수정">
				<input type="button" value="목록" onclick="location.href='memberList.do'">
			</div>  
			<ul>
				<li>
					<label>아이디</label>${member.mem_id}
				</li>
				<li>
					<label>전화번호</label>${member.mem_phone}
				</li>
				<li>
					<label>이메일</label>${member.mem_email}
				</li>
				<li>
					<label>우편번호</label>${member.mem_zipcode}
				</li>
				<li>
					<label>주소</label>
					${member.mem_addr1} ${member.mem_addr2}
				</li>
				<li>
					<label>가입일</label> ${member.mem_date}
				</li>
				<c:if test="${!empty member.mem_modify_date}">
				<li>
					<label>회원정보 수정일</label> ${member.mem_modify_date}
				</li>
				<li>
					${member.mem_name}님이 작성한 글
				</li>
				</c:if>
			</ul>    
				
		</form>
	</div>
</div>
</body>
</html>







