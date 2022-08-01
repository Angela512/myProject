<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>중고거래 글수정</title>
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
	<form id="write_form" action="update.do" method="post" enctype="multipart/form-data">
		<div class="wline1">
			<h2 id="write_text">중고거래 글수정</h2>
			<input type="submit" id="write_submit" value="수정">
		</div>
	
		<input type="hidden" name="trade_num" value="${trade.trade_num}">
		
		<hr width="100%" color="#b5b5b5" size="1px" id="hr_top2" noshade>
		<ul class="write_body">
			<li>
				<!-- <label>말머리</label> -->
				<input type="radio" name="trade_head" value="0" class="write_trade_head" id="trade_head1"
					<c:if test="${trade.trade_head==0 }">checked</c:if>/><span class="write-head">삽니다</span>
				<input type="radio" name="trade_head" value="1" class="write_trade_head" id="trade_head2"
					<c:if test="${trade.trade_head==1 }">checked</c:if>/><span class="write-head">팝니다</span>
			</li>
			
			<li>
				<!-- <label>상품분류</label> -->
				<select name="trade_category" id="trade_category">
					<option value="1" <c:if test="${trade.trade_category==1 }">selected="selected"</c:if>>가전제품</option>
					<option value="2" <c:if test="${trade.trade_category==2 }">selected="selected"</c:if>>가구</option>
					<option value="3" <c:if test="${trade.trade_category==3 }">selected="selected"</c:if>>옷</option>
				</select>
			</li>
			
			<li>
				<!-- <label for="trade_title">제목</label> -->
				<input type="text" name="trade_title" id="trade_title" value="${trade.trade_title }" maxlength="50">
			</li>
			
			<li>
				<!-- <label for="trade_content">내용</label> -->
				<textarea rows="20" cols="95" name="trade_content" id="trade_content">${trade.trade_content}</textarea>
			</li>
			
			<li>
				<!-- <label for="trade_phone"> --><b>연락처 입력</b><!-- </label> -->
			</li>
			
			<li>
				<input type="text" name="trade_phone" id="trade_phone" value="${trade.trade_phone }" maxlength="15">
			</li>
			
			<li>
				<!-- 숫자만 입력하세요 조건체크 -->
				<!-- <label for="trade_price"> --><b>가격</b><!-- </label> -->
			</li>
			
			<li>
				<input type="text" name="trade_price" id="trade_price" pattern="[0-9]+" value="${trade.trade_price }" maxlength="12">
				<span>숫자만 입력하세요.</span>
			</li>
		</ul>
		
			
		<ul class="image_add">
			<li>
				<span id="s"><b>파일 첨부</b></span>
			</li>
			
			<li>
				<!-- <label for="trade_image1">대표 이미지</label> -->
				<input type="file" name="trade_image1" id="trade_image1" accept="image/gif,image/png,image/jpeg">
				<c:if test="${!empty trade.trade_image1 }">
					<span id="file_detail1" class="file_detail"><br>
						(${trade.trade_image1})
						<input type="button" value="파일삭제" id="file_del1" class="file_del">
					</span>
					
					<script type="text/javascript">
					$(function(){
						//이벤트 연결
						$('#file_del1').click(function(){
							let choice = confirm('삭제하시겠습니까?');
							if(choice){
								$.ajax({
									url:'deleteFile.do',
									type:'post',
									data:{trade_num:${trade.trade_num},trade_image:'trade_image1'},
									dataType:'json',
									cache:false,
									timeout:30000,
									success:function(param){
										if(param.result=='logout'){
											alert('로그인 후 사용하세요!');
										}else if(param.result=='success'){
											$('#file_detail1').hide();
										}else if(param.result=='wrongAccess'){
											alert('잘못된 접속입니다.');
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
				<!-- <label for="trade_image2">두번째 이미지</label> -->
				<input type="file" name="trade_image2" id="trade_image2" accept="image/gif,image/png,image/jpeg">
				<c:if test="${!empty trade.trade_image2 }">
					<span id="file_detail2" class="file_detail"><br>
						(${trade.trade_image2})
						<input type="button" value="파일삭제" id="file_del2" class="file_del">
					</span>
					
					<script type="text/javascript">
					$(function(){
						//이벤트 연결
						$('#file_del2').click(function(){
							let choice = confirm('삭제하시겠습니까?');
							if(choice){
								$.ajax({
									url:'deleteFile.do',
									type:'post',
									data:{trade_num:${trade.trade_num},trade_image:'trade_image2'},
									dataType:'json',
									cache:false,
									timeout:30000,
									success:function(param){
										if(param.result=='logout'){
											alert('로그인 후 사용하세요!');
										}else if(param.result=='success'){
											$('#file_detail2').hide();
										}else if(param.result=='wrongAccess'){
											alert('잘못된 접속입니다.');
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
				<!-- <label for="trade_image3">세번째 이미지</label> -->
				<input type="file" name="trade_image3" id="trade_image3" accept="image/gif,image/png,image/jpeg">
				<c:if test="${!empty trade.trade_image3 }">
					<br>
					<span id="file_detail3" class="file_detail">
						(${trade.trade_image3})
						<input type="button" value="파일삭제" id="file_del3" class="file_del">
					</span>
					
					<script type="text/javascript">
					$(function(){
						//이벤트 연결
						$('#file_del3').click(function(){
							let choice = confirm('삭제하시겠습니까?');
							if(choice){
								$.ajax({
									url:'deleteFile.do',
									type:'post',
									data:{trade_num:${trade.trade_num},trade_image:'trade_image3'},
									dataType:'json',
									cache:false,
									timeout:30000,
									success:function(param){
										if(param.result=='logout'){
											alert('로그인 후 사용하세요!');
										}else if(param.result=='success'){
											$('#file_detail3').hide();
										}else if(param.result=='wrongAccess'){
											alert('잘못된 접속입니다.');
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
			
			
		</ul>
		
			
			<!-- <input type="button" class="button" value="목록" onclick="location.href='list.do'"> -->
	</form>
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>