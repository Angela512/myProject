<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고거래 글쓰기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/trade-style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/trade.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<hr width="100%" color="#b5b5b5" size="1px" id="hr_top1" noshade>
	<div class="content-main">
	<form id="write_form" action="write.do" method="post" enctype="multipart/form-data">
	<div class="wline1">
		<h2 id="write_text">중고거래 글쓰기</h2>
		<input type="submit" id="write_submit" value="등록">
		<!-- <input type="button" class="button" value="목록" onclick="location.href='list.do'"> -->
	</div>
	
	<hr width="100%" color="#b5b5b5" size="1px" id="hr_top2" noshade>
		<ul class="write_body">
			<li>
				<!-- <label>말머리</label> -->
				<input type="radio" name="trade_head" value="0" class="write_trade_head" id="trade_head1"/><span class="write-head">삽니다</span>
				<input type="radio" name="trade_head" value="1" class="write_trade_head" id="trade_head2" checked/><span class="write-head">팝니다</span>
			</li>
			
			<li>
				<!-- <label>상품분류</label> -->
				<select name="trade_category" id="trade_category">
					<option>카테고리 선택</option>
					<option value="1">가전제품</option>
					<option value="2">가구</option>
					<option value="3">옷</option>
				</select>
			</li>
			
			<li>
				<!-- <label for="trade_title">제목</label> -->
				<input type="text" name="trade_title" id="trade_title" maxlength="50">
			</li>
			
			<li>
				<!-- <label for="trade_content">내용</label> -->
				<textarea rows="20" cols="95" name="trade_content" id="trade_content"></textarea>
			</li>
			
			<li>
				<!-- <label for="trade_phone"> --><b>연락처 입력</b><!-- </label> -->
			</li>
			<li>
				<input type="text" name="trade_phone" id="trade_phone" maxlength="15">
			</li>
			
			<li>
				<!-- <label for="trade_price"> --><b>가격</b><!-- </label> -->
			</li>
			
			<li>
				<!-- 숫자만 입력하세요 조건체크 -->
				<input type="text" name="trade_price" id="trade_price" pattern="[0-9]+" maxlength="12">
				<span>숫자만 입력하세요.</span>
			</li>
		</ul>
		
		<ul class="image_add">
			<li>
				<span><b>파일 첨부</b></span>
			</li>
			
			<li>
				<!-- <label for="trade_image1">대표 이미지</label> -->
				<input type="file" name="trade_image1" id="trade_image1" accept="image/gif,image/png,image/jpeg">
			</li>
			
			<li>
				<!-- <label for="trade_image2">두번째 이미지</label> -->
				<input type="file" name="trade_image2" id="trade_image2" accept="image/gif,image/png,image/jpeg">
			</li>
			
			<li>
				<!-- <label for="trade_image3">세번째 이미지</label> -->
				<input type="file" name="trade_image3" id="trade_image3" accept="image/gif,image/png,image/jpeg">
			</li>
			
			<li>
				<p>* 이미지는 최대 3개까지 첨부할 수 있습니다.</p>
			</li>
			
		</ul>
		
			
	</form>
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>