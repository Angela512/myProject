<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고거래 글쓰기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/trade.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
	<form id="write_form" action="write.do" method="post" enctype="multipart/form-data">
		<ul>
			<li>
				<label>말머리</label>
				<input type="radio" name="trade_head" value="0" id="trade_head1"/>삽니다
				<input type="radio" name="trade_head" value="1" id="trade_head2" checked/>팝니다
			</li>
			
			<li>
				<label>상품분류</label>
				<select name="trade_category">
					<option value="1">가전제품</option>
					<option value="2">가구</option>
					<option value="3">옷</option>
				</select>
			</li>
			
			<li>
				<label for="trade_title">제목</label>
				<input type="text" name="trade_title" id="trade_title" maxlength="50">
			</li>
			
			<li>
				<label for="trade_content">내용</label>
				<textarea rows="5" cols="30" name="trade_content" id="trade_content"></textarea>
			</li>
			
			<li>
				<!-- 숫자만 입력하세요 조건체크 -->
				<label for="trade_price">가격</label>
				<input type="text" name="trade_price" id="trade_price" pattern="[0-9]+" maxlength="12">
				<span>숫자만 입력하세요.</span>
			</li>
			
			<li>
				<label for="trade_image1">대표 이미지</label>
				<input type="file" name="trade_image1" id="trade_image1" accept="image/gif,image/png,image/jpeg">
			</li>
			
			<li>
				<label for="trade_image2">두번째 이미지</label>
				<input type="file" name="trade_image2" id="trade_image2" accept="image/gif,image/png,image/jpeg">
			</li>
			
			<li>
				<label for="trade_image3">세번째 이미지</label>
				<input type="file" name="trade_image3" id="trade_image3" accept="image/gif,image/png,image/jpeg">
			</li>
			
			<li>
				<label for="trade_phone">연락처</label>
				<input type="text" name="trade_phone" id="trade_phone" maxlength="15">
			</li>
		</ul>
		
		<div class="align-center">
			<input type="submit" value="등록">
			<input type="button" value="목록" onclick="location.href='list.do'">
		</div>
	</form>
	</div>
</div>
</body>
</html>