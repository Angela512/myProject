<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>구인구직</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/job-style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/notice.js"></script>

<style>
/* 	.active, .category_btn:hover {
  background-color: #666;
  color: white;
} */
</style>
</head>
<body>
	
	<div class="page-main">
	
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		
		<jsp:include page="/WEB-INF/views/job/banner.jsp" />
		
		<div class="content-main">
			<div id="category_btn job-category" class="job_ct">
				<input class="category_btn" type="button" value="전체보기" onclick="location.href='list.do'">
				<input class="category_btn" type="button" value="어업" onclick="location.href='list.do?job_category=어업'">
				<input class="category_btn" type="button" value="농업" onclick="location.href='list.do?job_category=농업'">
				<input class="category_btn" type="button" value="요식" onclick="location.href='list.do?job_category=요식'">
				<input class="category_btn" type="button" value="기획" onclick="location.href='list.do?job_category=기획'">
				<input class="category_btn" type="button" value="서비스" onclick="location.href='list.do?job_category=서비스'">
				<input class="category_btn" type="button" value="생산" onclick="location.href='list.do?job_category=생산'">
				<input class="category_btn" type="button" value="기타" onclick="location.href='list.do?job_category=기타'">
				<Button class="category_btn"  value="기타" onclick="location.href='list.do?job_category=요식'">
			
			</div>

			<!-- 글쓰기, 목록, 홈 버튼 -->
<%-- 			<div class="list-space align-right">
					<input type="button" class="btn btn-primary" value="목록" onclick="location.href='list.do'"> <input type="button" class="btn btn-primary" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
			</div> --%>
			
			<!-- count가 0인 경우 -->
			<c:if test="${count == 0}">
				<div class="result-display">표시할 게시물이 없습니다.</div>
									<input type="button" value="글쓰기" id="write_button_box" onclick="location.href='writeForm.do'">
				
			</c:if>

			<!-- count가 0 이상 -->
			<c:if test="${count > 0}">
				<table class="job_table">
					<thead>
						<tr>
							<th>글번호</th>
							<th>제목</th>
							<th>작성자</th>
							<th>작성일</th>
							<th>조회</th>
						</tr>
					</thead>
					<c:forEach var="job" items="${list}">
						<tr>
							<td>${job.job_num}</td>
							<td><a href="detail.do?job_num=${job.job_num}">${job.job_title}</a></td>
							<td>${job.mem_name}</td>
							<td>${job.job_date}</td>
							<td>${job.job_count}</td>
						</tr>
					</c:forEach>
				</table>
				
			<c:if test="${!empty user_num}">
				<div class="write_button">
					<img src="../images/pen1.png" id="write_pen">
					<input type="button" value="글쓰기" id="write_button_box" onclick="location.href='writeForm.do'">
				</div>
				</c:if>	
				
			<div class="job_page">${page}</div>
				
			<!-- 검색 창 -->

				<form id="search_form" action="list.do" method="get">
					<input type="hidden" name="job_category" value="${param.job_category}">
					<ul class="search_job">
						<li>
							<select name="keyfield" style="width:100px; height:30px;">
								<option value="1">제목</option>
								<option value="2">작성자</option>
								<option value="3">내용</option>
							</select>
						</li>
						<li>
							<input type="search" class="form-control" size="16" name="keyword" id="keyword" value="${param.keyword}"  style="width:180px; height:30px;">
						</li>
						<li>
							<input id="search_button" type="submit" value="검색" style="width:80px; height:30px;">
						</li>
					</ul>
				</form>


				
			</c:if>
			
		</div>
		
		<!-- 푸터 -->
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />

	</div>
<!-- 

	<script>
		var menu = document.querySelector('.job_ct');

		function clickHandler(e) { // eventHandler써주면 자동으로 들어오지만 활용하려면 e를 써주기.  e는 이벤트 객체로써, 발생한 이벤트에 대한 많은 정보를 담고 있는 객체.
			console.log(e.target); // event 객체의 타겟
			console.log(e.currentTarget);
			console.log(this); // this는 menu이다. menu가 addEventLisner를 호출하였으므로
			console.log(this == e.currentTarget);
		}

		menu.addEventListener('click', clickHandler);
	</script> -->
</body>
<script>
// Add active class to the current button (highlight it)
var header = document.getElementById("job-category");
var btns = header.getElementsByClassName("category_btn");
for (var i = 0; i < btns.length; i++) {
  btns[i].addEventListener("click", function() {
  var current = document.getElementsByClassName("active");
  current[0].className = current[0].className.replace(" active", "");
  this.className += " active";
  });
}
</script>
</html>







