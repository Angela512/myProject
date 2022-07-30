<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 공지 header 시작 -->
<div id="main_sub_nav">
	<ul>
		<li>
			<a href="${pageContext.request.contextPath}/notice/list.do">전체글</a>
		</li>
		
		<li>
			<a href="${pageContext.request.contextPath}/notice/list.do?head=필독">필독</a>
		</li>
		
		<li>
			<a href="${pageContext.request.contextPath}/notice/list.do?head=공지">공지</a>
		</li>
	</ul>
</div>
<!-- 공지 header 끝 -->


