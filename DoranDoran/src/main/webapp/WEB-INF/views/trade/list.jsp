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
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/trade-style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/trade.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<jsp:include page="/WEB-INF/views/trade/banner.jsp"/>
	<div class="content-main">
	
		<div class="align-center">
			<div class="btn-group" role="group" aria-label="Basic outlined example">
				<input type="button" value="삽니다" class="btn btn-outline-primary" onclick="location.href='list.do?trade_head=0'">
				<input type="button" value="팝니다" class="btn btn-outline-primary" onclick="location.href='list.do?trade_head=1'">
			</div>
		</div>
		
			
			
			<div class="sub_nav" id="sub_nav1">
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
			
		<div id="category-end"></div>
			
		<div class="list-space align-right">
			<input type="button" class="button" value="목록" style="width:80px; height:30px;" onclick="location.href='list.do'">
			<input type="button" class="button" value="홈으로" style="width:80px; height:30px;" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
			<br>
			<span>총${count }개</span>
		</div>
		
		
		
		
		<c:if test="${count==0 }">
		<div class="result-display">
			표시할 게시물이 없습니다.
		</div>
		</c:if>
		
		<c:if test="${count>0 }">
		<div class="board-space">
			<c:forEach var="trade" items="${list }">
			<a href="${pageContext.request.contextPath}/trade/detail.do?trade_num=${trade.trade_num}">
			<div class="horizontal-area">
				<c:if test="${!empty trade.trade_image1 }">
				<img class="board-image" src="${pageContext.request.contextPath}/upload/${trade.trade_image1}">
				</c:if>
				
				<c:if test="${empty trade.trade_image1 }">
				<img class="board-image" src="${pageContext.request.contextPath}/images/blank.png">
				</c:if>
			
				
				<div class="board-detail">
					<c:if test="${!empty trade.mem_photo }">
					<img src="${pageContext.request.contextPath}/upload/${trade.mem_photo}" width="25" height="25" class="my-photo">
					</c:if>

					<c:if test="${empty trade.mem_photo }">
					<img src="${pageContext.request.contextPath}/images/face.png" width="25" height="25" class="my-photo">
					</c:if>
					
					<span>${trade.mem_name }</span><br>
					<c:if test="${trade.trade_head==0 }">
					<span id="detail-title">[삽니다] ${fn:substring(trade.trade_title,0,8) }</span><br>
					</c:if>
					<c:if test="${trade.trade_head==1 }">
					<span id="detail-title">[팝니다] ${fn:substring(trade.trade_title,0,8) }</span><br>
					</c:if>
					
					<span><b><fmt:formatNumber value="${trade.trade_price }" pattern="#,###"/>원</b></span><br>
					<span class="small">조회수 ${trade.trade_count} | 찜 ${trade.like_count }</span>	
				</div>
			</div>
			</a>
			</c:forEach>
			<div class="float-clear">
				<hr width="100%" class="hr-look" size="1" noshade="noshade">
			</div>
			
		</div>
		
		
		
		
		<div class="align-center">
			${page }
		</div>
		
		<hr width="100%" class="hr-look" size="1" noshade="noshade">
		
			
		
		</c:if>
		
		<c:if test="${!empty user_num }">
			<div class=write_button>
				<img src="../images/pen1.png" id="write_pen">
				<input type="button" value="글쓰기" id="write_button_box" onclick="location.href='writeForm.do'">
			</div>
		</c:if>
		
		<form id="search_form" action="list.do" method="get">
			<input type="hidden" name="trade_head" value="${param.trade_head}">
			<input type="hidden" name="trade_category" value="${param.trade_category}">
			<ul class="search_board">	
				<li>
					<select name="keyfield" id="keyfield" style="width:100px; height:30px;">
						<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>제목</option>
						<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>작성자</option>
						<option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>내용</option>
					</select>
				</li>
				
				<li>
					<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword}" style="width:180px; height:30px;">
				</li>
				
				<li>
					<input type="submit" id="search_button" value="검색" style="width:80px; height:30px;">
				</li>
			</ul>
		</form>
		
	</div>
		
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
		
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>