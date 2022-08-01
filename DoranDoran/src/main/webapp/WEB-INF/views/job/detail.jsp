<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글상세</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/job-style.css"
	type="text/css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/job.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<body>
	<div class="page-main">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<jsp:include page="/WEB-INF/views/job/banner.jsp" />
		<div class="content-main">
			<ul class="detail-sub">
				<li>
					<%-- <c:if test="${!empty job.modify_date}">
				최근 수정일 : ${job.modify_date}
				</c:if> --%>
				<c:if test="${user_num == job.mem_num}">
					<input type="button" value="수정" onclick="location.href='updateForm.do?job_num=${job.job_num}'">
				</c:if>
				<c:if test="${user_num == job.mem_num || user_auth == 3}">

						<input type="button" value="삭제" id="delete_btn" >
						<script type="text/javascript">
							let delete_btn = document
									.getElementById('delete_btn');
							//이벤트 연결
							delete_btn.onclick = function() {
								let choice = confirm('삭제하시겠습니까?');
								if (choice) {
									location.replace('delete.do?job_num=${job.job_num}');
								}
							};
						
						</script>
					</c:if>
				</li>
			</ul>
			
			<h2>${job.job_title}</h2>
			
			<hr>
			
			<div class="detail-info">
					<dt>작성자</dt>
					<dd>
						<c:if test="${!empty member_detail.mem_photo}">
						<img src="${pageContext.request.contextPath}/upload/${member_detail.mem_photo}" width="40" height="40" class="my-photo">
						</c:if>
						<c:if test="${empty member_detail.mem_photo}">
							<img src="${pageContext.request.contextPath}/images/face.png" width="40" height="40" class="my-photo">
						</c:if>
					</dd>
					<dd>${job.job_num}</dd>
			
					<dt>작성일자</dt>
					<dd>${job.job_date}</dd>
			
					<dt>조회수</dt>
					<dd>${job.job_count}</dd>
			</div>
			
	<%-- 		<ul class="detail-info">
				<li>
				<c:if test="${!empty member_detail.mem_photo}">
					<img src="${pageContext.request.contextPath}/upload/${member_detail.mem_photo}" width="40" height="40" class="my-photo">
				</c:if>
				<c:if test="${empty member_detail.mem_photo}">
					<img src="${pageContext.request.contextPath}/images/face.png" width="40" height="40" class="my-photo">
				</c:if>
				</li>
				<li>${job.job_num}</li>
				<li>작성일자 : ${job.job_date}</li>
				<li>조회수 : ${job.job_count}</li>
			</ul>
 --%>
 
 
			<div class="job_detail">
			
				<div class="logo_image">
					<c:if test="${!empty job.job_logo}">
						<img src="${pageContext.request.contextPath}/upload/${job.job_logo}" class="logo_image">
					</c:if>
					<c:if test="${empty job.job_logo}">
						<p>로고가 없는 회사입니다.</p>
					</c:if>
				</div>

				<br>

				<!-- 디데이 계산 -->
				<!-- 				
				<li>
					<div id="count">
					<script>	// D-Day
					const dday = new Date('${job.job_enddate}').getTime();
					
					setInterval(function() {
					  const today = new Date().getTime();
					  const gap = dday - today;
					  const day = Math.floor(gap / (1000 * 60 * 60 * 24))+1;
					
					  	document.getElementById("count").innerHTML = "D-" + day;
					}, 1000);
					</script>
				</li>
				 -->
				
			<table>
				<tr>
					<th>카테고리</th>
					<td>${job.job_category}</td>
				</tr>
				<tr>
					<th>홈페이지</th>
					<td>${job.job_link}</td>
				</tr>
				<tr>
					<th>모집 내용</th>
					<td>${job.job_content}</td>
				</tr>
				<tr>
					<th>마감일</th>
					<td>${job.job_enddate}</td>
				</tr>
				<tr>
					<th>주소</th>
					<td>[${job.job_zipcode}] &nbsp; ${job.job_addr1} &nbsp;
					${job.job_addr2}</td>
				</tr>
			</table>	
		<%-- 		
			<ul>
				<li>카테고리</li>
				<li>${job.job_category}</li>

				<li>홈페이지</li>
				<li>${job.job_link}</li>

				<li>모집 내용</li>
				<li>${job.job_content}</li>

				<li>마감일</li>
				<li>${job.job_enddate}</li>
				
				<li>주소</li>
				<li>[${job.job_zipcode}] &nbsp; ${job.job_addr1} &nbsp;
					${job.job_addr2}</li>
			</ul> --%>

				
			</div>
				<h3>위치</h3>
					<div id="map" style="width: 50%; height: 350px;"></div>
					<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=bf3f71975ef18c6a4e24b426de1e346e&libraries=services"></script>
					<script>
					var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
					    mapOption = {
					        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
					        level: 3 // 지도의 확대 레벨
					    };  
					
					// 지도를 생성합니다    
					var map = new kakao.maps.Map(mapContainer, mapOption); 
					
					// 주소-좌표 변환 객체를 생성합니다
					var geocoder = new kakao.maps.services.Geocoder();
					
					// 주소로 좌표를 검색합니다
					geocoder.addressSearch('${job.job_addr1}', function(result, status) {
					
					    // 정상적으로 검색이 완료됐으면 
					     if (status === kakao.maps.services.Status.OK) {
					
					        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
					
					        // 결과값으로 받은 위치를 마커로 표시합니다
					        var marker = new kakao.maps.Marker({
					            map: map,
					            position: coords
					        });
					
					
					        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
					        map.setCenter(coords);
					    } 
					});    
					</script>

			<hr size="1" noshade="noshade" width="100%">


		</div>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />

</body>
</html>





