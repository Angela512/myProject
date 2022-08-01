<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보(관리자 전용)</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel ="stylesheet" href="${pageContext.request.contextPath}/css/admin-style.css" type="text/css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<hr width="100%" color="#b5b5b5" size="1px" id="hr_top1" noshade>
	<div class="content-main">
		<h2>${member.mem_name}님의 회원정보 (관리자 전용)</h2>
		<form action="detailUser.do" method="post" id="detail_form">
			<input type="hidden" name="mem_num" value="${member.mem_num}">
			<div class="container">
				<div class="container_table1">
				<c:if test="${empty member.mem_photo }">
					<img src="${pageContext.request.contextPath}/images/face.png" width="200" height="200" class="my-photo">
				</c:if>
				<c:if test="${!empty member.mem_photo}">
					<img src="${pageContext.request.contextPath}/upload/${member.mem_photo}" width="200" height="200" class="my-photo">
				</c:if>
				
			</div>
			<div class="container_table2">
			<ul >
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
				</c:if>
				<li>
				<input type="submit" value="등급변경" id="button_box">
				<input type="button" value="회원목록" id="button_box" onclick="location.href='memberList.do'">
				</li>
			</ul>
			
			</div>
			</div>
		</form>
		<hr width="100%" color="#b5b5b5" size="1px" id="hr_top1" noshade>
			<c:if test="${empty list}">
				<p style="text-align:center;">작성한 글이 없습니다</p>
			</c:if>
			
			<table class="admin_table">
				<c:if test="${!empty list}">
				<p>작성한 글 ${count}개</p>
				<thead>
					<tr>
						<th>게시판</th>
						<th>제목</th>
						<th>작성일</th>
					</tr>
				</thead>
				</c:if>
			<tbody>
				<c:forEach var="member" items="${list}">
				<tr>
					<td>
						<c:if test="${member.tname=='board'}">자유게시판</c:if>
						<c:if test="${member.tname=='notice'}">공지사항</c:if>
						<c:if test="${member.tname=='food'}">맛집</c:if>
						<c:if test="${member.tname=='job'}">구인구직</c:if>
						<c:if test="${member.tname=='trade'}">중고거래</c:if>
					</td>
					<td>
					    <c:if test="${member.tname=='board'}"><a href="${pageContext.request.contextPath}/${member.tname}/detail.do?board_num=${member.num}">${member.title}</a></c:if>
						<c:if test="${member.tname=='notice'}"><a href="${pageContext.request.contextPath}/${member.tname}/detail.do?notice_num=${member.num}">${member.title}</a></c:if>
						<c:if test="${member.tname=='food'}"><a href="${pageContext.request.contextPath}/${member.tname}/detail.do?food_num=${member.num}">${member.title}</a></c:if>
						<c:if test="${member.tname=='job'}"><a href="${pageContext.request.contextPath}/${member.tname}/detail.do?job_num=${member.num}">${member.title}</a></c:if>
						<c:if test="${member.tname=='trade'}"><a href="${pageContext.request.contextPath}/${member.tname}/detail.do?trade_num=${member.num}">${member.title}</a></c:if>
					</td>
					<td>${member.ndate}</td>
				</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="board_page">
				${page}
			</div>
		<hr width="100%" color="#b5b5b5" size="1px" noshade>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>







