<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글쓰기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/job-style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/job.js"></script>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  					 <script>
  $( function() {
    $( "#datepicker" ).datepicker({
    	dateFormat:"yy-mm-dd"
    });
  } );
  </script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>구인구직 글쓰기</h2>
		<hr>
		<form id="write_form" action="write.do" 
		   method="post" enctype="multipart/form-data">
			<ul>
				<li class="row">
					<label for="title" class="col">제목</label>
					<input type="text" name="job_title" 
					      id="title" maxlength="50" class="form-control col" placeholder="제목 입력" required>
				</li>
				<li class="row">
					<label for="title" class="job_ct col">카테고리</label>
							<select name="job_category" class="form-select col">
								<option value="" hidden="">카테고리</option>
								<option value="어업">어업</option>
								<option value="기계">기계</option>
								<option value="농업">농업</option>
								<option value="요식">요식</option>
								<option value="기획">기획</option>
								<option value="서비스">서비스</option>
								<option value="생산">생산</option>
								<option value="기타">기타</option>
							</select>
					
				</li>
				<li>
					<label for="content" class="col">모집내용</label>
					<textarea rows="5" cols="30" name="job_content"
					     id="content" class="form-control" placeholder="모집 내용 입력" required></textarea>
				</li>
				<li class="row">
					<label for="filename" class="col">기업 로고</label>
					<input type="file"  class="btn btn-secondary col" name="job_logo" id="filename" accept="image/gif,image/png,image/jpeg">
				</li>
				<li class="row">
					<label for="Homepage" class="col">홈페이지</label>
					<input type="text" name="job_link" id="link" class="form-control col" placeholder="alpha.com의 형태로 입력하세요.">
				</li>
				<li class="row">
					<label for="enddate" class="col">마감일</label>
					<input type="date"  name="job_enddate"  class="form-control col">
					<!-- id="datepicker" -->
				</li>
				<li>
				<div class="row">
					<label for="Homepage" class="col">주소</label>
					<input type="text" id="sample3_postcode" name="job_zipcode" placeholder="우편번호" class="form-control col" required>
					<input type="button" onclick="sample3_execDaumPostcode()" value="찾기" class="btn btn-secondary col"><br>
				</div>
				<div class="row">
					<input type="text" id="sample3_address" name="job_addr1" placeholder="주소" class="form-control col" required>
					<input type="text" id="sample3_detailAddress" name="job_addr2" placeholder="상세주소" class="form-control col">
					<input type="text" id="sample3_extraAddress" placeholder="참고항목" class="form-control col">
				</div>
					<div id="wrap" style="display:none;border:1px solid;width:500px;height:300px;margin:5px 0;position:relative">
					<img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnFoldWrap" style="cursor:pointer;position:absolute;right:0px;top:-1px;z-index:1" onclick="foldDaumPostcode()" alt="접기 버튼">
					</div>
					
					<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
					<script>
					    // 우편번호 찾기 찾기 화면을 넣을 element
					    var element_wrap = document.getElementById('wrap');
					
					    function foldDaumPostcode() {
					        // iframe을 넣은 element를 안보이게 한다.
					        element_wrap.style.display = 'none';
					    }
					
					    function sample3_execDaumPostcode() {
					        // 현재 scroll 위치를 저장해놓는다.
					        var currentScroll = Math.max(document.body.scrollTop, document.documentElement.scrollTop);
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
					                    // 조합된 참고항목을 해당 필드에 넣는다.
					                    document.getElementById("sample3_extraAddress").value = extraAddr;
					                
					                } else {
					                    document.getElementById("sample3_extraAddress").value = '';
					                }
					
					                // 우편번호와 주소 정보를 해당 필드에 넣는다.
					                document.getElementById('sample3_postcode').value = data.zonecode;
					                document.getElementById("sample3_address").value = addr;
					                // 커서를 상세주소 필드로 이동한다.
					                document.getElementById("sample3_detailAddress").focus();
					
					                // iframe을 넣은 element를 안보이게 한다.
					                // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
					                element_wrap.style.display = 'none';
					
					                // 우편번호 찾기 화면이 보이기 이전으로 scroll 위치를 되돌린다.
					                document.body.scrollTop = currentScroll;
					            },
					            // 우편번호 찾기 화면 크기가 조정되었을때 실행할 코드를 작성하는 부분. iframe을 넣은 element의 높이값을 조정한다.
					            onresize : function(size) {
					                element_wrap.style.height = size.height+'px';
					            },
					            width : '100%',
					            height : '100%'
					        }).embed(element_wrap);
					
					        // iframe을 넣은 element를 보이게 한다.
					        element_wrap.style.display = 'block';
					    }
					</script>
			<!-- 	<input type="text" name="job_addr1" id="link">-->
					
				</li>
			</ul>
			<div class="align-center">
				<input type="submit" class="btn btn-primary" value="등록">
				<input type="button" class="btn btn-success" value="목록" 
				             onclick="location.href='list.do'">
			</div>
		</form>
	</div>
</div>

</body>
</html>






