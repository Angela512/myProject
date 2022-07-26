<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고거래 상세페이지</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/trade.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/trade.like.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
	<c:if test="${trade.trade_head==0 }">
		<h5>중고거래 > 삽니다</h5>
	</c:if>

	<c:if test="${trade.trade_head==1 }">
		<h5>중고거래 > 팝니다</h5>
	</c:if>
	
	<h2>${trade.trade_title }</h2>
	<ul class="detail-info">
		<li>
			<c:if test="${!empty trade.mem_photo }">
			<img src="${pageContext.request.contextPath}/upload/${trade.mem_photo}" width="40" height="40" class="my-photo">
			</c:if>

			<c:if test="${empty trade.mem_photo }">
			<img src="${pageContext.request.contextPath}/images/face.png" width="40" height="40" class="my-photo">
			</c:if>
		</li>
		
		<li>
			${trade.mem_id }<br>
			${trade.trade_date } 조회수 ${trade.trade_count }
		</li>
	</ul>
	
	<hr size="1" noshade="noshade" width="100%">
	
	<div>
	<div>
	<c:if test="${!empty trade.trade_image1 }">
		<div class="align-center">
			<img src="${pageContext.request.contextPath}/upload/${trade.trade_image1}" class="detail-img">
		</div>
	</c:if>
	
	<c:if test="${!empty trade.trade_image2 }">
		<div class="align-center">
			<img src="${pageContext.request.contextPath}/upload/${trade.trade_image2}" class="detail-img">
		</div>
	</c:if>
	
	<c:if test="${!empty trade.trade_image3 }">
		<div class="align-center">
			<img src="${pageContext.request.contextPath}/upload/${trade.trade_image3}" class="detail-img">
		</div>
	</c:if>
	</div>
	
	<div>
	<h3>${trade.trade_title }</h3>
	<h2><fmt:formatNumber value="${trade.trade_price }" pattern="#,###"/>원</h2>
	전화모양 <h5>${trade.trade_phone }</h5>
	
	<%-- 좋아요 --%>
	<img id="output_fav" src="${pageContext.request.contextPath}/images/fav01.gif" width="50">
	찜하기
	<span id="output_fcount"></span>
	<input type="hidden" name="trade_num" value="${trade.trade_num }" id="trade_num">
	
	</div>
	</div>
	
	<hr size="1" noshade="noshade" width="100%">
	
	<br>
	${trade.trade_content }
	
	<hr size="1" noshade="noshade" width="100%">
	
	<ul class="detail-sub">
		<li>
			<input type="button" value="목록으로" onclick="location.href='list.do'">
		</li>
		
		<%-- 로그인한 회원번호와 작성자 회원번호가 일치해야 수정,삭제 가능 또는 관리자 --%>
		<c:if test="${user_num==trade.mem_num || user_auth==3}">
		<li>
			<c:if test="${user_num==trade.mem_num }">
			<input type="button" value="수정" onclick="location.href='updateForm.do?trade_num=${trade.trade_num}'">
			</c:if>
			<input type="button" value="삭제" id="delete_btn">
			<script type="text/javascript">
				let delete_btn = document.getElementById('delete_btn');
				//이벤트 연결
				delete_btn.onclick=function(){
					let choice = confirm('삭제하시겠습니까?');
					if(choice){
						location.replace('delete.do?trade_num=${trade.trade_num}');
					}
				};
			</script>
		</li>
		</c:if>
	</ul>
	
	
	</div>
</div>
</body>
</html>

