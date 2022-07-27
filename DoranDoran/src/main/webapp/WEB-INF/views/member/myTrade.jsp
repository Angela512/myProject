<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내가 쓴 글 중고거래</title>
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
	<ul>
			<li>
			<button onclick="location.href='myPage.do'">내정보</button>
			<button onclick="location.href='myWrite.do'">내가 쓴 글</button>
			<button onclick="location.href='myReply.do'">내가 쓴 댓글</button>
			<button onclick="location.href='myLike.do'">찜목록</button>
			</li>
		</ul>
	
	<ul>
		<li>
		<button onclick="location.href='myWrite.do'">맛집찾기</button>
		<button onclick="location.href='trade.do'">중고거래</button>
		<button onclick="location.href='job.do'">구인구직</button>
		<button onclick="location.href='board.do'">자유게시판</button>
		</li>
	</ul>
	
		<hr size="1" noshade="noshade" width="100%">
		
	<ul class="search">
			
		<li>
			<input type="button" value="삽니다" onclick="location.href='trade.do?trade_head=0'">
			<input type="button" value="팝니다" onclick="location.href='trade.do?trade_head=1'">
		</li>
	</ul>
	
	
	<c:if test="${count==0 }">
	<div class="result-display">
		표시할 게시물이 없습니다.
	</div>
	</c:if>
	
	<c:if test="${count>0 }">
		<div class="board-space">
			<%-- <form action="myTradeDelete.do" method="post" id="mytrade_delete_form"> --%>
			<c:forEach var="trade" items="${list }">
			<div class="horizontal-area">
				<input type="checkbox" name="trade_num" value="${trade.trade_num }" class="trade_num">
				<a href="${pageContext.request.contextPath}/trade/detail.do?trade_num=${trade.trade_num}">
				<c:if test="${!empty trade.trade_image1 }">
				<img class="board-image" src="${pageContext.request.contextPath}/upload/${trade.trade_image1}">
				</c:if>
				
				<c:if test="${empty trade.trade_image1 }">
				<img class="board-image" src="${pageContext.request.contextPath}/images/blank.png">
				</c:if>
				
				<span>${fn:substring(trade.trade_title,0,10) }</span>
				</a>
				
				<div class="board-detail">
					<c:if test="${!empty trade.mem_photo }">
					<img src="${pageContext.request.contextPath}/upload/${trade.mem_photo}" width="25" height="25" class="my-photo">
					</c:if>

					<c:if test="${empty trade.mem_photo }">
					<img src="${pageContext.request.contextPath}/images/face.png" width="25" height="25" class="my-photo">
					</c:if>
					
					<span>${trade.mem_id }</span>
					<%-- <span><fmt:formatNumber value="${trade.trade_price }" pattern="#,###"/>원</span> --%>
					<span>조회수 ${trade.trade_count }</span>
					<span>찜 ${trade.like_count }</span>
				</div>
			</div>
			</c:forEach>
			
			<div class="float-clear">
				<hr width="100%" size="1" noshade="noshade">
			</div>
			
				<%-- 
					<input type="submit" value="삭제">
					<script type="text/javascript">
					let myForm = document.getElementById('mytrade_delete_form');
					//이벤트 연결
					myForm.onsubmit=function(){
						let tradenum = document.getElementsByClassName('trade_num');
						let count=0;
						for(let i=0;i<tradenum.length;i++){
							if(tradenum[i].checked){
								count++;
							}
						}
						
						if(count<1){
							alert('1개 이상 선택하세요!');
							return false;
						}
					};
					</script>
			
			</form>
			  --%>
		</div>
	
		<div class="align-center">
			${page }
		</div>
		
		</c:if>
	
	</div>
</div>
</body>
</html>