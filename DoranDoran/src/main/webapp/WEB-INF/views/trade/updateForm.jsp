<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고거래 글수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/trade.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
	<form id="write_form" action="update.do" method="post" enctype="multipart/form-data">
		<ul>
			<li>
				<label>말머리</label>
				<input type="radio" name="trade_head" value="0" id="trade_head1"
					<c:if test="${trade.trade_head==0 }">checked</c:if>/>삽니다
				<input type="radio" name="trade_head" value="1" id="trade_head2"
					<c:if test="${trade.trade_head==1 }">checked</c:if>/>팝니다
			</li>
			
			<li>
				<label>상품분류</label>
				<select name="trade_category">
					<option value="1" <c:if test="${trade.trade_category==1 }">selected="selected"</c:if>>가전제품</option>
					<option value="2" <c:if test="${trade.trade_category==2 }">selected="selected"</c:if>>가구</option>
					<option value="3" <c:if test="${trade.trade_category==3 }">selected="selected"</c:if>>옷</option>
				</select>
			</li>
			
			<li>
				<label for="trade_title">제목</label>
				<input type="text" name="trade_title" id="trade_title" value="${trade.trade_title }" maxlength="50">
			</li>
			
			<li>
				<label for="trade_content">내용</label>
				<textarea rows="5" cols="30" name="trade_content" id="trade_content">${trade.trade_content}</textarea>
			</li>
			
			<li>
				<!-- 숫자만 입력하세요 조건체크 -->
				<label for="trade_price">가격</label>
				<input type="text" name="trade_price" id="trade_price" value="${trade.trade_price }" maxlength="12">
			</li>
			
			<li>
				<label for="trade_image1">대표 이미지</label>
				<input type="file" name="trade_image1" id="trade_image1" accept="image/gif,image/png,image/jpeg">
				<c:if test="${!empty trade.trade_image1 }">
					<br>
					<span id="file_detail1">
						(${trade.trade_image1})파일이 등록되어 있습니다.
						다시 파일을 업로드하면 기존 파일은 삭제됩니다.
						<input type="button" value="파일삭제" id="file_del1">
					</span>
				</c:if>
			</li>
			
			<li>
				<label for="trade_image2">두번째 이미지</label>
				<input type="file" name="trade_image2" id="trade_image2" accept="image/gif,image/png,image/jpeg">
				<c:if test="${!empty trade.trade_image2 }">
					<br>
					<span id="file_detail2">
						(${trade.trade_image2})파일이 등록되어 있습니다.
						다시 파일을 업로드하면 기존 파일은 삭제됩니다.
						<input type="button" value="파일삭제" id="file_del2">
					</span>
				</c:if>
			</li>
			
			<li>
				<label for="trade_image3">세번째 이미지</label>
				<input type="file" name="trade_image3" id="trade_image3" accept="image/gif,image/png,image/jpeg">
				<c:if test="${!empty trade.trade_image3 }">
					<br>
					<span id="file_detail3">
						(${trade.trade_image3})파일이 등록되어 있습니다.
						다시 파일을 업로드하면 기존 파일은 삭제됩니다.
						<input type="button" value="파일삭제" id="file_del3">
					</span>
				</c:if>
			</li>
			
			<li>
				<label for="trade_phone">연락처</label>
				<input type="text" name="trade_phone" id="trade_phone" value="${trade.trade_phone }" maxlength="15">
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