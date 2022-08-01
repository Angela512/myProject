<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내가 쓴 글</title>
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
	
	<c:if test="${count==0 }">
	<div class="result-display">
		표시할 게시물이 없습니다.
	</div>
	</c:if>
	
			
	<c:if test="${count>0 }">
		<div class="board-space">
			<form action="myFoodDelete.do" method="post" id="myfood_delete_form">
			<c:forEach var="food" items="${list }">
			<div class="horizontal-area">
               <%--<input type="checkbox" name="food_num" value="${food.food_num }" class="food_num"> --%>
				<a href="${pageContext.request.contextPath}/food/detail.do?food_num=${food.food_num}">
				<c:if test="${!empty food.food_image1 }">
				<img class="board-image" src="${pageContext.request.contextPath}/upload/${food.food_image1}">
				</c:if>
				
				<c:if test="${empty food.food_image1 }">
				<img class="board-image" src="${pageContext.request.contextPath}/images/blank.png">
				</c:if>
				
				<span style="padding-left:20px;"><b>[${food.food_local}]</b> ${fn:substring(food.food_name,0,10) }</span>
				</a>
				
				<div class="board-detail">
					<c:if test="${!empty food.mem_photo }">
					<img src="${pageContext.request.contextPath}/upload/${food.mem_photo}" width="25" height="25" class="my-photo">
					</c:if>

					<c:if test="${empty food.mem_photo }">
					<img src="${pageContext.request.contextPath}/images/face.png" width="25" height="25" class="my-photo">
					</c:if>
					
					<span>${food.mem_name }</span>
					<%-- <span><fmt:formatNumber value="${trade.trade_price }" pattern="#,###"/>원</span> --%>
					<span>조회수 ${food.food_count }</span>
				</div>
			</div>
			</c:forEach>
			
			<div class="float-clear">
				<hr width="100%" size="1" noshade="noshade">
			</div>
			
			
                    <!--<input type="submit" value="삭제"> -->
					<script type="text/javascript">
					let myForm = document.getElementById('myfood_delete_form');
					//이벤트 연결
					myForm.onsubmit=function(){
						let foodnum = document.getElementsByClassName('food_num');
						let count=0;
						for(let i=0;i<foodnum.length;i++){
							if(foodnum[i].checked){
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
</div>
</body>
</html>