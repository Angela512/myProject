<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel ="stylesheet" href="${pageContext.request.contextPath}/css/admin-style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/member.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<hr width="100%" color="#b5b5b5" size="1px" id="hr_top1" noshade>
	<div class="content-main">
		<h2>회원목록(관리자 전용)</h2>
		<jsp:include page="adminHeader.jsp"/>
		<div class="list-space align-right">
			${count}명의 회원목록
			<input type="button" id="home_button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">     
		</div>
		<c:if test="${count == 0}">
		<div class="result-display">
			표시할 내용이 없습니다.
		</div>
		</c:if>
		<c:if test="${count > 0}">
		<table class="admin_table">
			<thead>
			<tr>
				<th>ID</th>
				<th>이름</th>
				<th>이메일</th>
				<th>전화번호</th>
				<th>가입일</th>
				<th>등급</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="member" items="${list}">
			<tr>
				<c:if test="${member.auth == 3}">
				<td>
					<a href="detailUserForm.do?mem_num=${member.mem_num}">${member.mem_id}</a>
				</td>
				<td>${member.mem_name}</td>
				<td>${member.mem_email}</td>
				<td>${member.mem_phone}</td>
				<td>${member.mem_date}</td>
				<td>관리</td>
				</c:if>
			</tr>
			</c:forEach>
			
			<c:forEach var="member" items="${list}">
			<tr>
				<c:if test="${member.auth == 2}">
				<td>
					<a href="detailUserForm.do?mem_num=${member.mem_num}">${member.mem_id}</a>
				</td>
				<td>${member.mem_name}</td>
				<td>${member.mem_email}</td>
				<td>${member.mem_phone}</td>
				<td>${member.mem_date}</td>
				<td>일반</td>
				</c:if>
			</tr>
			</c:forEach>
			
			<c:forEach var="member" items="${list}">
			<tr>
				<c:if test="${member.auth == 1}">
				<td>
					<a href="detailUserForm.do?mem_num=${member.mem_num}">${member.mem_id}</a>
				</td>
				<td>${member.mem_name}</td>
				<td>${member.mem_email}</td>
				<td>${member.mem_phone}</td>
				<td>${member.mem_date}</td>
				<td>정지</td>
				</c:if>
			</tr>
			</c:forEach>
			
			<c:forEach var="member" items="${list}">
			<c:if test="${member.auth == 0}">
			<tr>
				<td>
					${member.mem_id}
				</td>
				<td>${member.mem_name}</td>
				<td>${member.mem_email}</td>
				<td>${member.mem_phone}</td>
				<td>${member.mem_date}</td>
				<td>탈퇴</td>
			</tr>
			</c:if>
			</c:forEach>
			</tbody>
		</table>
		<div class="align-center">
			${page}
		</div>
		<hr width="100%" color="#b5b5b5" size="1px" noshade>
		</c:if>
		<form id="search_form" action="memberList.do" method="get">
		<!-- <input type="hidden" name="auth" value="${param.auth}"> -->
			<ul class="search_notice">
				<li>
					<select name="keyfield" style="width:100px; height:30px;">
						<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>ID</option>
						<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>이름</option>
						<option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>email</option>
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





