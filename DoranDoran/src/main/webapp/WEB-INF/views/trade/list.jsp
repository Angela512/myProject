<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고거래 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/trade-style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/trade.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
	
		<form id="search_form" action="list.do" method="get">
			<input type="hidden" name="trade_head" value="${param.trade_head}">
			<input type="hidden" name="trade_category" value="${param.trade_category}">
			
			<ul>
				<li>
					<input type="button" value="삽니다" class="button-1" onclick="location.href='list.do?trade_head=0'">
					<input type="button" value="팝니다" onclick="location.href='list.do?trade_head=1'">
				</li>
			</ul>
			
			
			<div id="main_nav">
				<ul>
					<c:if test="${!empty param.trade_head }">
						<li>
							<a href="list.do?trade_head=${param.trade_head}&trade_category=1">가전제품</a>
						</li>
						
						<li>
							<a href="list.do?trade_head=${param.trade_head}&trade_category=2">가구</a>
						</li>

						<li>
							<a href="list.do?trade_head=${param.trade_head}&trade_category=3">옷</a>
						</li>
					</c:if>

					<c:if test="${empty param.trade_head }">
						<li>
							<a href="list.do?trade_category=1">가전제품</a>
						</li>
						
						<li>
							<a href="list.do?trade_category=2">가구</a>
						</li>

						<li>
							<a href="list.do?trade_category=3">옷</a>
						</li>
					</c:if>
				</ul>
			</div>
				
			<ul class="search">	
				<li>
					<select name="keyfield">
						<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
						<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>작성자</option>
						<option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>내용</option>
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
					
					<span>${trade.mem_name }</span>
					<%-- <span><fmt:formatNumber value="${trade.trade_price }" pattern="#,###"/>원</span> --%>
					<span>조회수 ${trade.trade_count }</span>
					<span>찜 ${trade.like_count }</span>
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