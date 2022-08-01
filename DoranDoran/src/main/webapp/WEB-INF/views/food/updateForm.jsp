<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/food.js"></script>
<style type="text/css">
/*탑 버튼*/
.button{
	color: #fff;
	font-size:15px;
	border: 0;
	background-color: #FA5D57;
	border-radius:3px;
}
</style>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2 align="center">맛집 찾기 글수정</h2>
		<hr size="1" class="hr-look" noshade="noshade" width="100%">
		<form action="update.do" method="post"
		      enctype="multipart/form-data" id="write_form">
			<input type="hidden" name="food_num" 
			                       value="${food.food_num}">
			<ul>
				<li>
					<select name='local'>
						<option value="${food.food_local}">${food.food_local}</option>
						<option disabled>-------</option>
						<option value="서울">서울</option>
						<option value="경기">경기</option>
						<option value="인천">인천</option>
						<option value="제주">제주</option>
					</select>
					<label for="title"></label>
					<input type="text" name="title" 
					      id="title" maxlength="50" placeholder="가게 이름을 입력" value="${food.food_name}">
				</li>
				<li>
				    <br>
					<label for="content"></label>
					<textarea rows="7" cols="70" name="content" placeholder="내용및 설명을 입력"
					     id="content">${food.food_content}</textarea>
				</li>
				<li>
					<br>
					<label for="menu"><b>대표메뉴</b></label>
					<br><br>
					<input type="text" name="menu" 
					      id="menu" maxlength="20" style="width:200px;" value="${food.food_menu}">
				</li>
				<li>
					<br><br>
					<label for="filename"><b>가게 & 메뉴 사진</b></label>
					<br><br>
					
					
					<input type="file" name="filename1" 
					 id="filename1" 
					 accept="image/gif,image/png,image/jpeg">
					 <c:if test="${!empty food.food_image1}">
					 &nbsp; &nbsp;
						(${food.food_image1})
						<input type="button" value="파일삭제" data-file="food_image1" class="file_del">
					
					</c:if>					 		
					 <br>
					 
					 <input type="file" name="filename2" 
					 id="filename2" 
					 accept="image/gif,image/png,image/jpeg">
					 <c:if test="${!empty food.food_image2}">
					 &nbsp; &nbsp;
						(${food.food_image2})
						<input type="button" value="파일삭제" data-file="food_image2" class="file_del">
					
					</c:if>	
					 <br>
					 
					 <input type="file" name="filename3" 
					 id="filename3" 
					 accept="image/gif,image/png,image/jpeg">
					 <c:if test="${!empty food.food_image3}">
					 &nbsp; &nbsp;
						(${food.food_image3})
						<input type="button" value="파일삭제" data-file="food_image3" class="file_del">
					
					</c:if>	
					
					<c:if test="${!empty food.food_image1 or !empty food.food_image2 or !empty food.food_image3}">
					<script type="text/javascript">
					$(function(){
						//이벤트 연결
						$('.file_del').click(function(){
							let choice = confirm('삭제하시겠습니까?');
							let event_btn = $(this);
							if(choice){
								$.ajax({
									url:'deleteFile.do',
									type:'post',        //el임. jsp에서 쓸 수 있음
									data:{food_num:${food.food_num},food_image:$(this).attr('data-file')},
									dataType:'json',
									cache:false,
									timeout:30000,
									success:function(param){
										if(param.result == 'logout'){
											alert('로그인 후 사용하세요!');
										}else if(param.result == 'success'){
											event_btn.parent().hide();
										}else{
											alert('파일 삭제 오류 발생');
										}
									},
									error:function(){
										alert('네트워크 오류 발생');
									}
								});
							}
						});
					});
					</script>
					</c:if>
					 
				</li>
				<li>
					<br><br>
					<label for="time"><b>가게 운영시간</b></label>
					<br><br>
					<select name='hour1'>
						<option value="${food.food_timeh1}">${food.food_timeh1}</option>
						<option disabled>-------</option>
						<option value="00">00</option>
						<option value="01">01</option>
						<option value="02">02</option>
						<option value="03">03</option>
						<option value="04">04</option>
						<option value="05">05</option>
						<option value="06">06</option>
						<option value="07">07</option>
						<option value="08">08</option>
						<option value="09">09</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option>
						<option value="16">16</option>
						<option value="17">17</option>
						<option value="18">18</option>
						<option value="19">19</option>
						<option value="20">20</option>
						<option value="21">21</option>
						<option value="22">22</option>
						<option value="23">23</option>
					</select>
					:
					<select name='min1'>
						<option value="${food.food_timem1}">${food.food_timem1}</option>
						<option disabled>-------</option>
						<option value="00">00</option>
						<option value="30">30</option>
					</select>
					~
					<select name='hour2'>
						<option value="${food.food_timeh2}">${food.food_timeh2}</option>
						<option disabled>-------</option>
						<option value="00">00</option>
						<option value="01">01</option>
						<option value="02">02</option>
						<option value="03">03</option>
						<option value="04">04</option>
						<option value="05">05</option>
						<option value="06">06</option>
						<option value="07">07</option>
						<option value="08">08</option>
						<option value="09">09</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option>
						<option value="16">16</option>
						<option value="17">17</option>
						<option value="18">18</option>
						<option value="19">19</option>
						<option value="20">20</option>
						<option value="21">21</option>
						<option value="22">22</option>
						<option value="23">23</option>
					</select>
					:
					<select name='min2'>
						<option value="${food.food_timem2}">${food.food_timem2}</option>
						<option disabled>-------</option>
						<option value="00">00</option>
						<option value="30">30</option>
					</select>
					
				</li>
				<li>
					<br><br>
					<label for="phone"><b>가게 전화번호</b></label>
					<br>
					<br>
					<select name = 'phone1'>
						<option value="${food.food_phone1}">${food.food_phone1}</option>
						<option disabled>-------</option>
						<option value="02">02</option>
						<option value="031">031</option>
						<option value="032">032</option>
						<option value="064">064</option>
					</select>
					
					- <input type="text" name="phone2" id="phone2" maxlength="4" style="width:50px;" value="${food.food_phone2}">
					- <input type="text" name="phone3" id="phone3" maxlength="4" style="width:50px;" value="${food.food_phone3}">
					
				</li>
				<li>
					<br><br>
					<label for="link"><b>가게 홈페이지</b></label>
					<br><br>
					<input type="text" name="link" 
					      id="link" maxlength="100" placeholder="https://www.xxxx.com" value="${food.food_link}"  style="width:500px;">
				</li>
				<li>
					<br><br>
					<label for="address"><b>가게 위치</b></label>
					<br><br>
					<label for="zipcode"><b>우편번호</b></label>
					<input type="text" name="zipcode" id="zipcode" 
					   maxlength="5" autocomplete="off" value="${food.food_zipcode}">
					<input type="button" onclick="sample2_execDaumPostcode()" value="우편번호 찾기">
				</li>
				<li>
					<label for="address1"><b>주소</b></label>
					<input type="text" name="address1" id="address1" 
					                                        maxlength="30" value="${food.food_addr1}">
				</li>
				<li>
					<label for="address2"><b>상세 주소</b></label>
					<input type="text" name="address2" id="address2" 
					                                        maxlength="30" value="${food.food_addr2}">
				</li>
			</ul> 
			<div class="align-center">
				<input type="submit" value="수정" class="button">
				<input type="button" value="목록" class="button"
				         onclick="location.href='list.do'">
			</div>                      
		</form>
		<hr size="1" class="hr-look" noshade="noshade" width="100%">
	</div>
</div>
	<!-- 다음 우편번호 찾기 시작 -->
<!-- iOS에서는 position:fixed 버그가 있음, 적용하는 사이트에 맞게 position:absolute 등을 이용하여 top,left값 조정 필요 -->
<div id="layer" style="display:none;position:fixed;overflow:hidden;z-index:1;-webkit-overflow-scrolling:touch;">
<img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnCloseLayer" style="cursor:pointer;position:absolute;right:-3px;top:-3px;z-index:1" onclick="closeDaumPostcode()" alt="닫기 버튼">
</div>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    // 우편번호 찾기 화면을 넣을 element
    var element_layer = document.getElementById('layer');

    function closeDaumPostcode() {
        // iframe을 넣은 element를 안보이게 한다.
        element_layer.style.display = 'none';
    }

    function sample2_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    //(주의)address1에 참고항목이 보여지도록 수정
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    //(수정) document.getElementById("address2").value = extraAddr;
                
                } 
                //(수정) else {
                //(수정)    document.getElementById("address2").value = '';
                //(수정) }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('zipcode').value = data.zonecode;
                //(수정) + extraAddr를 추가해서 address1에 참고항목이 보여지도록 수정
                document.getElementById("address1").value = addr + extraAddr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("address2").focus();

                // iframe을 넣은 element를 안보이게 한다.
                // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
                element_layer.style.display = 'none';
            },
            width : '100%',
            height : '100%',
            maxSuggestItems : 5
        }).embed(element_layer);

        // iframe을 넣은 element를 보이게 한다.
        element_layer.style.display = 'block';

        // iframe을 넣은 element의 위치를 화면의 가운데로 이동시킨다.
        initLayerPosition();
    }

    // 브라우저의 크기 변경에 따라 레이어를 가운데로 이동시키고자 하실때에는
    // resize이벤트나, orientationchange이벤트를 이용하여 값이 변경될때마다 아래 함수를 실행 시켜 주시거나,
    // 직접 element_layer의 top,left값을 수정해 주시면 됩니다.
    function initLayerPosition(){
        var width = 300; //우편번호서비스가 들어갈 element의 width
        var height = 400; //우편번호서비스가 들어갈 element의 height
        var borderWidth = 5; //샘플에서 사용하는 border의 두께

        // 위에서 선언한 값들을 실제 element에 넣는다.
        element_layer.style.width = width + 'px';
        element_layer.style.height = height + 'px';
        element_layer.style.border = borderWidth + 'px solid';
        // 실행되는 순간의 화면 너비와 높이 값을 가져와서 중앙에 뜰 수 있도록 위치를 계산한다.
        element_layer.style.left = (((window.innerWidth || document.documentElement.clientWidth) - width)/2 - borderWidth) + 'px';
        element_layer.style.top = (((window.innerHeight || document.documentElement.clientHeight) - height)/2 - borderWidth) + 'px';
    }
</script>
	<!-- 다음 우편번호 찾기 끝 -->
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>	
</body>
</html>




