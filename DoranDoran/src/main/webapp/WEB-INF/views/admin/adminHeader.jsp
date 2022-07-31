<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 공지 header 시작 -->
<div id="main_sub_nav">
	<ul>
		<li>
			<a href="${pageContext.request.contextPath}/admin/memberList.do">전체글</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/admin/memberList.do?auth=3">관리</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/admin/memberList.do?auth=2">일반</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/admin/memberList.do?auth=1">정지</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/admin/memberList.do?auth=0">탈퇴</a>
		</li>
	</ul>
</div>
<!-- 공지 header 끝 -->


