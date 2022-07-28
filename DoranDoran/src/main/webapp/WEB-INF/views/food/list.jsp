<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>맞집 찾기 게시판</title>
<link href="https://fonts.googleapis.com/css?family=Nanum+Pen+Script:400" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Song+Myung:400" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/food.js"></script>
<!--   <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
 -->
 <style type="text/css">
 .containerr{
	position:relative;
	text-align:center;
	color:blue;
}
.centered {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-family: Nanum Pen Script;
	
	font-size:100px;
	color:white;
  
}

    /* 화면 디자인 맛집 게시판용 css */
.board-spacee .horizontal-areaa{
	margin:70px ;
	padding:3px;
	width:300px;
	height:600px;
	float:left;
	overflow: hidden;
}

.board-spacee .horizontal-areaa img.board-imagee{
	width:500px;
	height:300px;
}
 </style>
 </head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<br>
	<br>
	<div class="content-main">
	
	<div class="containerr">
   	    <img alt="" src="${pageContext.request.contextPath}/images/brooke-lark-wMzx2nBdeng-unsplash.jpg" width="900" height="150">
	 	<div class="centered">
	 		<span>맛집 찾기
	 		</span>
		</div>
	</div>
		<!-- <h2 align= "center">맛집 찾기</h2> -->
		<form id="search_form" action="list.do" method="get">
		    <input type="hidden" name="food_local" value="${param.food_local}">
			<ul class="search">
				<li>
				    <input type="button" value="전체" onclick="location.href='list.do'">
					<input type="button" value="서울" onclick="location.href='list.do?food_local=서울'">
					<input type="button" value="경기" onclick="location.href='list.do?food_local=경기'">
					<input type="button" value="인천" onclick="location.href='list.do?food_local=인천'">
					<input type="button" value="제주" onclick="location.href='list.do?food_local=제주'">
				</li>
				<li>
					<select name="keyfield">
						<option value="1">제목</option>
						<option value="2">작성자</option>
						<option value="3">내용</option>
					</select>
				</li>
				<li>
					<input type="search" size="16" 
					  name="keyword" id="keyword"
					  value="${param.keyword}">
				</li>
				<li>
					<input type="submit" value="검색">
				</li>
			</ul>
			
			
			
			
		</form>
		<div class="list-space align-right">
		    <c:if test="${!empty user_num}">
			<input type="button" value="글쓰기"
			   onclick="location.href='writeForm.do'">
			</c:if>   
			<input type="button" value="목록"
			       onclick="location.href='list.do'"> 
			<input type="button" value="홈으로"
			 onclick="location.href='${pageContext.request.contextPath}/main/main.do'">         
		</div>
		<c:if test="${count == 0}">
		<div class="result-display">
			표시할 게시물이 없습니다.
		</div>
		</c:if>
		
		
		<c:if test="${count > 0}">
		<div class="board-spacee">
			<c:forEach var="food" items="${list}">
			<div class="horizontal-areaa">
				<a href="${pageContext.request.contextPath}/food/detail.do?food_num=${food.food_num}">
				<c:if test="${!empty food.food_image1}">
				<img class="board-imagee" src="${pageContext.request.contextPath}/upload/${food.food_image1}">
				</c:if>
				<c:if test="${empty food.food_image1}">
				<img class="board-imagee" src="${pageContext.request.contextPath}/images/blank.png">
				</c:if>
				<div>
				<ul>
					<li>
						<span style="font-family:'Song Myung'; font-size:25px;">[${food.food_local}] ${fn:substring(food.food_name,0,15)}</span>
						
					<li>
					<br>
					
					<li>
						번호 : ${food.food_phone1}-${food.food_phone2}-${food.food_phone3}
					</li>				
					<li>
						주소 : ${food.food_addr1} ${food.food_addr2}
					</li>
					<li>
						설명 : ${food.food_content}
					</li>
				</ul>
				</div>
				</a>
				<div class="board-detail">
					<c:if test="${!empty food.mem_photo}">
					<img src="${pageContext.request.contextPath}/upload/${food.mem_photo}" width="25" height="25" class="my-photo">
					</c:if>
					<c:if test="${empty food.mem_photo}">
					<img src="${pageContext.request.contextPath}/images/face.png" width="25" height="25" class="my-photo">
					</c:if>
					<span>${food.mem_name}</span>
					<span>/ 조회수 : ${food.food_count}</span>
				</div>
			</div>
			</c:forEach>
			<div class="float-clear">
				<hr width="100%" size="1" noshade="noshade">
			</div>
		</div>
		
		
		<div class="align-center">
			${page}
		</div> 
		</c:if>
	</div>
</div>
<!--     <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
 --> </body>
</html>







