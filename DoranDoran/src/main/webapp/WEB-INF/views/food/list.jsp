<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>맛집 찾기 게시판</title>
<link href="https://fonts.googleapis.com/css?family=Nanum+Pen+Script:400" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Song+Myung:400" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/food.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/job-style.css" type="text/css">
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
	margin:100px ;
 	padding:0px; 
	width:350px;
	height:600px;
	float:left;
	overflow: hidden;
	maring:10px;
}

img.board-imagee{
	width:500px;
	height:300px;
}
ul{
	padding:0px;
}
.in{
	padding-left:20px;
}

 </style>
 </head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<br>
	<jsp:include page="/WEB-INF/views/food/banner.jsp"/>
	<br>
	<br>
	<div class="content-main">
	
	<div class="containerr">
<%--    	    <img alt="" src="${pageContext.request.contextPath}/images/brooke-lark-wMzx2nBdeng-unsplash.jpg" width="1060" height="150">
 --%>	 	<!-- <div class="centered">
	 		<span>맛집 찾기
	 		</span> -->
		</div>
	</div>
		<!-- <h2 align= "center">맛집 찾기</h2> -->
		<form id="search_form" action="list.do" method="get">
		    <input type="hidden" name="food_local" value="${param.food_local}">
			<%-- <ul class="search">
				<li>
				    <input type="button" value="전체" onclick="location.href='list.do'">
					<input type="button" value="서울" onclick="location.href='list.do?food_local=서울'">
					<input type="button" value="경기" onclick="location.href='list.do?food_local=경기'">
					<input type="button" value="인천" onclick="location.href='list.do?food_local=인천'">
					<input type="button" value="제주" onclick="location.href='list.do?food_local=제주'">
				</li>
				
				<li>
					<br><br><br>
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
			</ul> --%>
			<div id="category_btn">
		
				<input class="category_btn" type="button" value="전체보기" onclick="location.href='list.do'">
				<input class="category_btn" type="button" value="서울"  onclick="location.href='list.do?job_category=서울'">
				<input class="category_btn" type="button" value="경기"  onclick="location.href='list.do?job_category=경기'">
				<input class="category_btn" type="button" value="인천"  onclick="location.href='list.do?job_category=인천'">
				<input class="category_btn" type="button" value="제주"  onclick="location.href='list.do?job_category=제주'">
			</div>
		
		</form>
		<div class="list-space align-right">
		    <c:if test="${!empty user_num}">
			<input type="button" value="글쓰기" class="btn btn-primary"
			   onclick="location.href='writeForm.do'">
			</c:if>   
			<input type="button" value="목록" class="btn btn-primary"
			       onclick="location.href='list.do'"> 
			<input type="button" value="홈으로" class="btn btn-primary"
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
			<div class="horizontal-areaa" style="border:5px solid #f6da7a; border-radius:16px; box-shadow:5px 5px 5px 5px #E0E0E0;">
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
						<br>
						<p style="font-family:'Song Myung'; font-size:25px; text-align:center;">[${food.food_local}] ${fn:substring(food.food_name,0,15)}</p>
						 <hr width = "100%" style="height:2px; color:blue;">
					</li>
					<br>
					
					<li class="in">
						<b>번호</b> &nbsp; ${food.food_phone1}-${food.food_phone2}-${food.food_phone3}
					</li>				
					<li class="in">
						<b>주소</b>  &nbsp; ${food.food_addr1} 
					</li>
					<li class="in">
						<b>설명</b>  &nbsp; ${food.food_content}
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
					<span>| 조회수 : ${food.food_count}</span>
				</div>
			</div>
			</c:forEach>
			<div class="float-clear">
				<hr width="100%" size="1" noshade="noshade">
			</div>
			
			
			
		</div>
		<form id="search_form" action="list.do" method="get" style="margin-right:10px;">
					<input type="hidden" name="fool_local" value="${param.food_local}" >
					<ul class="search row align-items-center" >
						<li class="col">
						<select name="keyfield" class="form-select">
							<option value="1">제목</option>
							<option value="2">작성자</option>
							<option value="3">내용</option>
						</select>
						</li>
						
						<li class="col-auto col">
						<input type="search" class="form-control" size="16" name="keyword" id="keyword" value="${param.keyword}">
						</li>
						<li>
						<input type="submit" class="btn btn-primary col-lg-2"  value="검색">
						</li>
					</ul>
				</form>
		
		<div class="align-center">
			${page}
		</div> 
		</c:if>
	</div>
			<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>







