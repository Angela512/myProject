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
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/trade-style.css" type="text/css">
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
		
	<div class="align-center">
		<div class="btn-group" role="group" aria-label="Basic outlined example">
		<input type="button" value="삽니다" class="btn btn-outline-primary" onclick="location.href='trade.do?trade_head=0'">
		<input type="button" value="팝니다" class="btn btn-outline-primary" onclick="location.href='trade.do?trade_head=1'">
		</div>
	</div>
	
	<div class="list-space align-right">
		<span>총${count }개</span>
	</div>
	
	<c:if test="${count==0 }">
	<div class="result-display">
		표시할 게시물이 없습니다.
	</div>
	</c:if>
	
	<c:if test="${count>0 }">
		<div class="board-space">
			<form action="myTradeDelete.do" method="post" id="mytrade_delete_form">
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
				
				<span id="mytrade-title">${fn:substring(trade.trade_title,0,10) }</span>
				</a>
				
				<div class="board-detail" id="mytrade-detail">
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
			</c:forEach>
			
			<div class="float-clear">
				<hr width="100%" size="1" noshade="noshade">
			</div>
			
			
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
			
		</div>
	
		<div class="align-center">
			${page }
		</div>
		
		</c:if>
	
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>