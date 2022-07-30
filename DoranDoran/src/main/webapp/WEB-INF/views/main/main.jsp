<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
   
<style>
.container{
	display:flex;
	justify-content: space-between;
}
.tboard{
	display: flex;
  justify-content: space-between;
}
.tname {
  height: 50px;
  text-align: center;
}
.main_table {
  border: 1px #a39485 solid;
  font-size: .9em;
  
  width: 100%;
  border-collapse: collapse;
  border-radius: 5px;
  overflow: hidden;
}
thead {
  font-weight: bold;
  background: #E1E1E1;
}
td, th {
  padding: 1em .5em;
  vertical-align: middle;
}
  
 td {
  border-bottom: 1px solid rgba(0,0,0,.1);
  background: #fff;
}



* {
   box-sizing: border-box;
}

body {
   font-family: Verdana, sans-serif;
}

.mySlides {
   display: none;
}

.mySlides img {
   vertical-align: middle;
   /*
   width: 1920px;
   height: 1080px;
   */
}

/* Slideshow container */

.slideshow-container {
   /*
   max-width: 100%;
   position: relative;
   margin: auto;
   */
   margin-top:20px;
}

/* Caption text */
/* .text {
   color: #f2f2f2;
   font-size: 15px;
   padding: 8px 12px;
   position: absolute;
   bottom: 8px;
   width: 100%;
   text-align: center;
} */

/* Number text (1/3 etc) */
.numbertext {
   color: #f2f2f2;
   font-size: 12px;
   padding: 8px 12px;
   position: absolute;
   top: 0;
}

/* The dots/bullets/indicators */
.dot {
   height: 15px;
   width: 15px;
   margin: 0 2px;
   background-color: #bbb;
   border-radius: 50%;
   display: inline-block;
   transition: background-color 0.6s ease;
}

.active {
   background-color: #717171;
}

/* Fading animation */
.fade {
   animation-name: fade;
   animation-duration: 1.5s;
}

@
keyframes fade {
   from {opacity: .4
}

to {
   opacity: 1
}

}

/* On smaller screens, decrease text size */
@media only screen and (max-width: 300px) {
   .text {
      font-size: 11px
   }
}



 /* 화면 디자인 맛집 게시판용 css  106 ~ 138*/

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
    <jsp:include page="/WEB-INF/views/common/header.jsp" />
      <div class="slideshow-container">

         <div class="mySlides fade">
<!--             <div class="numbertext">1 / 3</div>
 -->            <img src="../images/coffee1.jpg" style="width: 100%;height:450px;">
<!--             <div class="text">Caption Text</div>
 -->         </div>

         <div class="mySlides fade">
<!--             <div class="numbertext">2 / 3</div>
 -->            <img src="../images/coffee2.jpg" style="width: 100%;height:450px;">
<!--             <div class="text">Caption Two</div>
 -->         </div>

         <div class="mySlides fade">
<!--             <div class="numbertext">3 / 3</div>
 -->            <img src="../images/aabb.jpg" style="width: 100%;height:450px;">
<!--             <div class="text">Caption Three</div>
 -->         </div>

      </div>
   <div class="page-main">
      <br>

      <div style="text-align: center">
         <span class="dot"></span> <span class="dot"></span> <span class="dot"></span>
      </div>

      <div class="content-main">
         <h2>게시판 최신글</h2>
  
      
      <!-- 공지 시작 -->
      <div class="container">
	      <div style="width:48%">
      <div class="tboard">
      <div class="tname"><h3>공지사항</h3></div>
         <div class="tname"><h4><a href="${pageContext.request.contextPath}/notice/list.do">더보기></a></h4></div>
        </div> 
         <table class="main_table">
         <thead>
            <tr>
               <th>말머리</th>
               <th>제목</th>
               <th>작성자</th>
               <th>작성일</th>
               <th>조회수</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="notice" items="${noticeList}">
            <tr>
               <td>${notice.notice_head}</td>
               <td><a href="${pageContext.request.contextPath }/notice/detail.do?notice_num=${notice.notice_num}">${notice.notice_title }</a></td>
               <td>${notice.mem_name}</td>
               <td>${notice.notice_date}</td>
               <td>${notice.notice_count}</td>
            </tr>
            </c:forEach>
            </tbody>
         </table>
     
      </div>
      <!-- 공지 끝 -->
      
      <!-- 맛집 찾기 시작 
      <div style="width:48%">
      <div class="tboard">
	      <div class="tname"><h3>맛집 찾기</h3></div>
		  <div class="tname"><h4><a href="${pageContext.request.contextPath }/food/list.do">더보기></a></h4></div>
	  </div>	
		
		
			<div class="board-spacee">
			<c:forEach var="food" items="${foodList }">

			<div class="horizontal-areaa">
				<a href="${pageContext.request.contextPath}/food/detail.do?food_num=${food.food_num}">
				<c:if test="${!empty food.food_image1}">
				<img class="board-imagee" src="${pageContext.request.contextPath}/upload/${food.food_image1}">
				</c:if>
				<c:if test="${empty food.food_image1}">
				<img class="board-imagee" src="${pageContext.request.contextPath}/images/blank.png">
				</c:if>
				
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
			</div>
			<br>
      </div>
     
       맛집 찾기 끝 -->
      </div> 
      
      <!-- 중고거래 시작 -->
      <div class="container">
	      <div style="width:48%">
		      <div class="tboard">
		      	<div class="tname"><h3>중고거래</h3></div>
		      	<div class="tname"><h4><a href="${pageContext.request.contextPath }/trade/list.do">더보기></a></h4></div>
		      </div>
	      <table class="main_table">
      <thead>
         <tr>
            <th>제목</th>
            <th>작성자</th>
            <th>작성일</th>
         </tr>
       </thead>  
        <tbody>
         <c:forEach var="trade" items="${tradeList }">
         <tr>
            <td><a href="${pageContext.request.contextPath }/trade/detail.do?trade_num=${trade.trade_num}">${trade.trade_title }</a></td>
            <td>${trade.mem_name }</td>
            <td>${trade.trade_date }</td>
         </tr>
         </c:forEach>
      </tbody>
      </table>
      
      </div>
       <!-- 중고거래 끝 -->
       
      <!-- 구인구직 시작 -->
      
      <div style="width:48%">
      <div class="tboard">
      <div class="tname"><h3>구인구직</h3></div>
		<div class="tname"><h4><a href="${pageContext.request.contextPath}/job/list.do">더보기 ></a></h4></div>
	  </div>
			<table class="main_table">
			<thead>
			<tr>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="job" items="${jobList}">
			<tr>
				<td><a href="detail.do?job_num=${job.job_num}">${job.job_title}</a></td>
				<td>${job.job_num}</td>
				<td>${job.job_date}</td>
			</tr>
			</c:forEach>
			</tbody>
		</table>
		<p></p>
		</div>
		</div>
      <!-- 구인구직 끝 -->
      
      <!-- 자유게시판 시작 -->
      <div style="width:100%">
      <div class="tboard">
      	<div class="tname"><h3>자유게시판</h3></div>
      	<div class="tname"><h4><a href="${pageContext.request.contextPath}/board/list.do">더보기 ></a></h4></div>
       </div>
         <table class="main_table">
         <thead>
            <tr>
               <th>말머리</th>
               <th>제목</th>
               <th>작성자</th>
               <th>작성일</th>
               <th>조회수</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="board" items="${boardList}">
            <tr>
               <td>${board.board_head}</td>
               <td><a href="${pageContext.request.contextPath }/board/detail.do?board_num=${board.board_num}">${board.board_title}</a></td>
               <td>${board.mem_name}</td>
               <td>${board.board_date}</td>
               <td>${board.board_count}</td>
            </tr>
            </c:forEach>
            </tbody>
         </table>
         </div>
      <!-- 자유게시판 끝 -->
      </div> 
      <jsp:include page="/WEB-INF/views/common/footer.jsp" />
   </div>


   <script>
      let slideIndex = 0;
      showSlides();

      function showSlides() {
         let i;
         let slides = document.getElementsByClassName("mySlides");
         let dots = document.getElementsByClassName("dot");
         for (i = 0; i < slides.length; i++) {
            slides[i].style.display = "none";
         }
         slideIndex++;
         if (slideIndex > slides.length) {
            slideIndex = 1
         }
         for (i = 0; i < dots.length; i++) {
            dots[i].className = dots[i].className.replace(" active", "");
         }
         slides[slideIndex - 1].style.display = "block";
         dots[slideIndex - 1].className += " active";
         setTimeout(showSlides, 2000); // Change image every 2 seconds
      }
   </script>   
</body>
</html>




