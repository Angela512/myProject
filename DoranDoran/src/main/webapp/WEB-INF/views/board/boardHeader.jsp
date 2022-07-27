<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- header 시작 -->
<div id="main_nav">
	<ul>
		<li>
			<a href="${pageContext.request.contextPath}/board/list.do">전체</a>
		</li>
		
		<li>
			<a href="${pageContext.request.contextPath}/board/list.do?head=동네소식">동네소식</a>
		</li>
		
		<li>
			<a href="${pageContext.request.contextPath}/board/list.do?head=도움요청">도움요청</a>
		</li>
		
		<li>
			<a href="${pageContext.request.contextPath}/board/list.do?head=함께해요">함께해요</a>
		</li>
		
		<li>
			<a href="${pageContext.request.contextPath}/board/list.do?head=기타">기타</a>
		</li>
	</ul>
</div>
<!-- header 끝 -->


