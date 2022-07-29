<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>구인구직</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/job-style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/notice.js"></script>

</head>
<body>
 <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<div class="content-main">
			<h2>구인구직</h2>
			<div id="category_btn">
		
				<input class="category_btn" type="button" value="전체보기" onclick="location.href='list.do'">
				<input class="category_btn" type="button" value="어업"  onclick="location.href='list.do?job_category=어업'">
				<input class="category_btn" type="button" value="농업"  onclick="location.href='list.do?job_category=농업'">
				<input class="category_btn" type="button" value="요식"  onclick="location.href='list.do?job_category=요식'">
				<input class="category_btn" type="button" value="기획"  onclick="location.href='list.do?job_category=기획'">
				<input class="category_btn" type="button" value="서비스"  onclick="location.href='list.do?job_category=서비스'">
				<input class="category_btn" type="button" value="생산"  onclick="location.href='list.do?job_category=생산'">
				<input class="category_btn" type="button" value="기타"  onclick="location.href='list.do?job_category=기타'">
				

			</div>
			<div id="demo">
			<script>
				function start(){
					document.getElementById("demo").innerHTML = "Hello World";
					
				}
			</script>
			</div>
			
			
			<div class="list-space align-right">
				<c:if test="${!empty user_num}">
					<input type="button" value="글쓰기" class="btn btn-primary"
						onclick="location.href='writeForm.do'">
				</c:if>
				<input type="button" class="btn btn-primary" value="목록" onclick="location.href='list.do'">
				<input type="button" class="btn btn-primary" value="홈으로"
					onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
			</div>
			<c:if test="${count == 0}">
				<div class="result-display">표시할 게시물이 없습니다.</div>
			</c:if>
			
			<c:if test="${count > 0}">
				<table class="table table-hover col-4">
					<tr>
						<th scope="col">글번호</th>
						<th scope="col">제목</th>
						<th scope="col">작성자</th>
						<th scope="col">작성일</th>
						<th scope="col">조회</th>
					</tr>
					<c:forEach var="job" items="${list}">
						<tr>
							<th scope="row">${job.job_num}</td>
							<td><a href="detail.do?job_num=${job.job_num}">${job.job_title}</a></td>
							<td>${job.mem_num}</td>
							<td>${job.job_date}</td>
							<td>${job.job_count}</td>
						</tr>
					</c:forEach>
				</table>
				
				<form id="search_form" action="list.do" method="get">
					<input type="hidden" name="job_category" value="${param.job_category}">
					<ul class="search row align-items-center" >
						<li class="col">
						<select name="keyfield" class="form-select">
							<option value="1">제목</option>
							<option value="2">작성자</option>
							<option value="3">내용</option>
						</select>
						</li>
						
						<li class="col-auto col">
						<input type="search" class="form-control" size="16" name="keyword" id="keyword" value="${param.keyword}"></li>
						</li>
						<input type="submit" class="btn btn-primary col-lg-2"  value="검색"></li>
					</ul>
				</form>
				

				<div class="align-center">${page}</div>
			</c:if>
		</div>
				<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
		
	</div>
</body>
</html>







