<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>구인구직 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/member.js"></script>

<style>
.myjoblist {
	width:100%;
	margin:20px 0 0 0;
	padding:0;
}

.myjoblist table{
	    border: 1px #a39485 solid;
    font-size: 16px;
    width: 100%;
    border-collapse: collapse;
    border-radius: 5px;
    overflow: hidden;
}
</style>

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
	
	<div id="main_nav">
		<ul>
				<li>
					<a href="myWrite.do">맛집찾기</a>
				</li>
				
				<li>
					<a href="trade.do">중고거래</a>
				</li>

				<li>
					<a href="job.do">구인구직</a>
				</li>

				<li>
					<a href="board.do">자유게시판</a>
				</li>
		</ul>
	</div>
	
	
	<c:if test="${count==0 }">
	<div class="result-display">
		표시할 게시물이 없습니다.
	</div>
	</c:if>
	
	
	<c:if test="${count>0 }">
<%-- 	<c:if test="mem_num=${mem_num}">
 --%>	<div class="myjoblist">
		<table>
					<thead>
						<tr>
							<th>글번호</th>
							<th>제목</th>
							<th>작성자</th>
							<th>작성일</th>
							<th>조회</th>
						</tr>
					</thead>
					<c:forEach var="job" items="${list}">
						<tr>
							<td>${job.job_num}</td>
							<td><a href="${pageContext.request.contextPath}/job/detail.do?job_num=${job.job_num}">${job.job_title}</a></td>
							<td>${job.mem_num}</td>
							<td>${job.job_date}</td>
							<td>${job.job_count}</td>
						</tr>
					</c:forEach>
				</table>
		</div>
	
		<div class="align-center">
			${page}
		</div>
		
		</c:if>
<%-- 		</c:if>
 --%>	
	</div>
</div>
</body>
</html>