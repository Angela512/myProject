<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/food.js"></script>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/board.fav.js"></script>  --%>

<style type="text/css">

li.j{
	margin:20px 0px;
}

</style>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		
		<ul class="detail-info">
			<li>
				<br>
				<c:if test="${!empty food.mem_photo}">
				<img src="${pageContext.request.contextPath}/upload/${food.mem_photo}" width="40" height="40" class="my-photo">
				</c:if>
				<c:if test="${empty food.mem_photo}">
				<img src="${pageContext.request.contextPath}/images/face.png" width="40" height="40" class="my-photo">
				</c:if>
			</li>
			<li>
				${food.mem_name}<br>
				조회 : ${food.food_count}
			</li>
		</ul>
		<h2 align="center">[${food.food_local}] ${food.food_name}</h2>
		<hr size="1" noshade="noshade" width="100%">
		<c:if test="${!empty food.food_image1}">
		<div class="align-center">
			<img src="${pageContext.request.contextPath}/upload/${food.food_image1}" class="detail-img">
		</div>
		
		</c:if>
		<br><br><br>
		<div>
			<ul style="margin:50px; padding-left:160px;">
				<li class="j">
					<b>운영시간</b><span style="padding-left:30px;">${food.food_timeh1}:${food.food_timem1} ~ ${food.food_timeh2}:${food.food_timem2}</span>
				</li>
				<li class="j">
					<b>전화번호</b><span style="padding-left:30px;">${food.food_phone1} - ${food.food_phone2} - ${food.food_phone3}</span>
				</li>
				
				<li class="j">
					<b>주소</b><span style="padding-left:60px;">${food.food_addr1}  ${food.food_addr2}</span>
				</li>
				<li class="j">
					<b>설명</b><span style="padding-left:60px;">${food.food_content}</span>
				</li>				
			</ul>
		</div>
		<br><br><br>
			<div id="map" style="width:500px;height:300px;  margin: auto !important; text-align:center; ">
			</div>
			<br>
		
		<hr size="1" noshade="noshade" width="100%">
		<ul class="detail-sub">
			<li>
				<c:if test="${!empty food.food_date_modi}">
				최근 수정일 : ${food.food_date_modi}
				</c:if> 
				작성일 : ${food.food_date}
				<%-- 로그인한 회원번호와 작성자 회원번호가 일치해야 수정,삭제 가능 --%>
				<c:if test="${(user_auth == 3) || (user_num == food.mem_num)}">
				<input type="button" value="수정" 
				 onclick="location.href='updateForm.do?food_num=${food.food_num}'">
				<input type="button" value="삭제" id="delete_btn">
				<script type="text/javascript">
					let delete_btn = document.getElementById('delete_btn');
					//이벤트 연결
					delete_btn.onclick=function(){
						let choice = confirm('삭제하시겠습니까?');
						if(choice){
							location.replace('delete.do?food_num=${food.food_num}');
						}
					};
				</script>
				</c:if>
			</li>
		</ul>
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=0982dcd2faa86918f8990b344af7f993&libraries=services"></script>
<script type="text/javascript">
	window.onload=function(){
		function getMyLocation(){
			var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
		    mapOption = {
		        center: new daum.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표(초기 값으로 주소를 좌표로 바꿔서 제공하면 중심 좌표가 바뀜)
		        level: 3 // 지도의 확대 레벨
		    };  

			// 지도를 생성합니다    
			var map = new daum.maps.Map(mapContainer, mapOption); 
		
			// 주소-좌표 변환 객체를 생성합니다
			var geocoder = new daum.maps.services.Geocoder();
		
			// 주소로 좌표를 검색합니다
			geocoder.addressSearch('${food.food_addr1}', function(result, status) {
		
			    // 정상적으로 검색이 완료됐으면 
			     if (status === daum.maps.services.Status.OK) {
		
			        var coords = new daum.maps.LatLng(result[0].y, result[0].x);
		
			        // 결과값으로 받은 위치를 마커로 표시합니다
			        var marker = new daum.maps.Marker({
			            map: map,
			            position: coords
			        });
		
			        // 인포윈도우로 장소에 대한 설명을 표시합니다
			        /* var infowindow = new daum.maps.InfoWindow({
			            content: '<div style="width:300px;text-align:center;padding:6px 0;"></div>'
			        });
			        infowindow.open(map, marker); */
		
			        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
			        map.setCenter(coords);
			    } 
			});    
		}
		getMyLocation();
	};
</script>      
<!-- 중앙 컨텐츠 시작 -->
	
</body>
</html>





