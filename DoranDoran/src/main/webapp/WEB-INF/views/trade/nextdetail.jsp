<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:if test="${next!=0 }">
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고거래 상세페이지</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/trade-style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/trade.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/trade.like.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<jsp:include page="/WEB-INF/views/trade/banner.jsp"/>
	<div class="content-main">
	<div class="align-right">
		<button class="button" onclick="location.href='prevdetail.do?trade_num=${trade.trade_num}'">이전글</button>
		<button class="button" onclick="location.href='nextdetail.do?trade_num=${trade.trade_num}'">다음글</button>
	</div>
	<c:if test="${trade.trade_head==0 }">
		<h5 class="trade-detail-head">중고거래 > 삽니다</h5>
	</c:if>

	<c:if test="${trade.trade_head==1 }">
		<h5 class="trade-detail-head">중고거래 > 팝니다</h5>
	</c:if>
	
	<h1>${trade.trade_title }</h1>
	<ul class="detail-info" id="detail-top">
		<li>
			<c:if test="${!empty trade.mem_photo }">
			<img src="${pageContext.request.contextPath}/upload/${trade.mem_photo}" width="40" height="40" class="my-photo">
			</c:if>

			<c:if test="${empty trade.mem_photo }">
			<img src="${pageContext.request.contextPath}/images/face.png" width="40" height="40" class="my-photo">
			</c:if>
		</li>
		
		<li id="li-margin">
			${trade.mem_name }<br>
			${trade.trade_date } 조회수 ${trade.trade_count } 찜 <span id="output_fcount"></span>
		</li>
	</ul>
	
	<div>
	<hr size="1" class="hr-look" noshade="noshade" width="100%"/>
	</div>
	
	
	
	<div class="detail-div">
	<div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
		<div class="carousel-indicators">
			<c:if test="${!empty trade.trade_image1 }">
			    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
			</c:if> 
			<c:if test="${!empty trade.trade_image2 }">
			    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
			</c:if>
			<c:if test="${!empty trade.trade_image3 }">
			    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
			</c:if>
	  	</div>
	  	
	  	<div class="carousel-inner">
			<c:if test="${empty trade.trade_image1 && empty trade.trade_image2 && empty trade.trade_image3 }">
				<div class="carousel-item active">
					<img src="${pageContext.request.contextPath}/images/blank.png" class="d-block w-100">
				</div>
			</c:if>
			<c:if test="${!empty trade.trade_image1 }">
	  			<div class="carousel-item active">
					<img src="${pageContext.request.contextPath}/upload/${trade.trade_image1}" class="d-block w-100">
				</div>
			</c:if>
	
			<c:if test="${!empty trade.trade_image2 }">
			 	<div class="carousel-item">
					<img src="${pageContext.request.contextPath}/upload/${trade.trade_image2}" class="d-block w-100">
				</div>
			</c:if>
	
			<c:if test="${!empty trade.trade_image3 }">
				<div class="carousel-item">
					<img src="${pageContext.request.contextPath}/upload/${trade.trade_image3}" class="d-block w-100">
				</div>
			</c:if>
		</div>
	 	<button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
		    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
		    <span class="visually-hidden">Previous</span>
  		</button>
	  	<button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
	   	 	<span class="carousel-control-next-icon" aria-hidden="true"></span>
	    	<span class="visually-hidden">Next</span>
	  	</button>
	</div>
	</div>
	
	
	<div class="detail-div" id="detail-margin">
	<h3 id="detail-div-title">${trade.trade_title }</h3>
	<h2 id="detail-div-price">가격 <span> <fmt:formatNumber value="${trade.trade_price }" pattern="#,###"/>원</span></h2>
	<h2 id="detail-div-call-phone">연락처 ${trade.trade_phone }</h2>
	
	
	<%-- 좋아요 --%>
	<div id="detail-div-like">
	<img id="output_fav" src="${pageContext.request.contextPath}/images/nolike.png" width="50">
	
	</div>
	<input type="hidden" name="trade_num" value="${trade.trade_num }" id="trade_num">
	</div>
	
	<div class="detail-end"></div>
	
	<hr size="1" class="hr-look" noshade="noshade" width="100%">
	
	<br>
	${trade.trade_content }
	
	<hr size="1" class="hr-look" noshade="noshade" width="100%">
	<div id="flex">
		<div id="flex1">
		<input type="button" class="button" value="목록으로" style="width:120px; height:35px;" onclick="location.href='list.do'">
		</div>
		<%-- 로그인한 회원번호와 작성자 회원번호가 일치해야 수정,삭제 가능 또는 관리자 --%>
		<div id="flex2">
			<c:if test="${user_num==trade.mem_num || user_auth==3}">
				<c:if test="${user_num==trade.mem_num }">
				<input type="button" class="button" value="수정" style="width:80px; height:30px;" onclick="location.href='updateForm.do?trade_num=${trade.trade_num}'">
				</c:if>
				<input type="button" class="button" value="삭제" style="width:80px; height:30px;" id="delete_btn">
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
			</c:if>
		</div>
	</div>
	</div>
	<br>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>
</c:if>

<c:if test="${next==0 }">
	<script>
		alert('마지막 글입니다!');
		history.go(-1);
	</script>
</c:if>
