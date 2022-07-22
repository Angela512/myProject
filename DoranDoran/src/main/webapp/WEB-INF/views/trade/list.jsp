<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고거래 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/trade.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
	
		<form id="search_form" action="list.do" method="get">
			<ul class="search">
			
				<li>
					<input type="radio" name="trade_head" value="0"/>삽니다
					<input type="radio" name="trade_head" value="1"/>팝니다
				</li>
				
				<li>
					<input type="radio" name="trade_category" value="1"/>가전제품
					<input type="radio" name="trade_category" value="2"/>가구
					<input type="radio" name="trade_category" value="3"/>옷
				</li>
				
				<li>
					<select name="keyfield">
						<option value="1">제목</option>
						<option value="2">작성자</option>
						<option value="3">내용</option>
					</select>
				</li>
				
				<li>
					<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}">
				</li>
				
				<li>
					<input type="submit" value="검색">
				</li>
			</ul>
		</form>
		
		<div class="list-space align-right">
			<c:if test="${!empty user_num }">
			<input type="button" value="글쓰기" onclick="location.href='writeForm.do'">
			</c:if>
			<input type="button" value="목록" onclick="location.href='list.do'">
			<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
		</div>
		
		<c:if test="${count==0 }">
		<div class="result-display">
			표시할 게시물이 없습니다.
		</div>
		</c:if>
		
		<c:if test="${count>0 }">
		<div class="board-space">
			<c:forEach var="trade" items="${list }">
			<div class="horizontal-area">
				<a href="${pageContext.request.contextPath}/trade/detail.do?trade_num=${trade.trade_num}">
				<c:if test="${!empty trade.trade_image1 }">
				<img class="board-image" src="${pageContext.request.contextPath}/upload/${trade.trade_image1}">
				</c:if>
				
				<c:if test="${empty trade.trade_image1 }">
				<img class="board-image" src="${pageContext.request.contextPath}/images/blank.png">
				</c:if>
				
				<span>${fn:substring(trade.trade_title,0,12) }</span>
				</a>
				
				<div class="board-detail">
					<c:if test="${!empty trade.mem_photo }">
					<img src="${pageContext.request.contextPath}/upload/${trade.mem_photo}" width="25" height="25" class="my-photo">
					</c:if>

					<c:if test="${empty trade.mem_photo }">
					<img src="${pageContext.request.contextPath}/images/face.png" width="25" height="25" class="my-photo">
					</c:if>
					
					<span>${trade.mem_id }</span>
				</div>
			</div>
			</c:forEach>
			<div class="float-clear">
				<hr width="100%" size="1" noshade="noshade">
			</div>
			
		</div>
		
		
		
	
	
	
	
		
		<div class="align-center">
			${page }
		</div>
		
		</c:if>
		
	</div>
</div>

</body>
</html>