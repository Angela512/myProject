<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내가 쓴 글 자유게시판</title>
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
	
		<hr size="1" noshade="noshade" width="100%">
		
	<!-- <ul class="search">
			
		<li>
			<input type="button" value="삽니다" onclick="location.href='trade.do?trade_head=0'">
			<input type="button" value="팝니다" onclick="location.href='trade.do?trade_head=1'">
		</li>
	</ul> -->
	
	
	<c:if test="${count==0 }">
	<div class="result-display">
		표시할 게시물이 없습니다.
	</div>
	</c:if>
	
	<c:if test="${count>0 }">
		 <div style="width:100%">
      <div class="tboard">
      	<div class="tname"><h3>자유게시판</h3></div>
       </div>
         <table class="main_table">
         <thead>
            <tr>
               <th>말머리</th>
               <th>제목</th>
               <th>작성자</th>
               <th>작성일</th>
               <th>조회수</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="board" items="${boardList}">
            <tr>
               <td>${board.board_head}</td>
               <td><a href="${pageContext.request.contextPath }/board/detail.do?board_num=${board.board_num}">${board.board_title}</a></td>
               <td>${board.mem_name}</td>
               <td>${board.board_date}</td>
               <td>${board.board_count}</td>
            </tr>
            </c:forEach>
            </tbody>
         </table>
         </div>
		<div class="align-center">
			${page}
		</div>
		</c:if>
	</div>
</div>
</body>
</html>