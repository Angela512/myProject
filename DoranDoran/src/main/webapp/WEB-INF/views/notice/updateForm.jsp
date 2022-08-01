<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel ="stylesheet" href="${pageContext.request.contextPath}/css/notice-style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/notice.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<hr width="100%" color="#b5b5b5" size="1px" id="hr_top1" noshade>
	<div class="content-main">
		<form action="update.do" method="post" enctype="multipart/form-data" id="write_form">
		<div class="wline1">
			<h2 id="write_text">공지사항 글수정</h2>
			<input id="write_submit" type="submit" value="수정">
		</div>
		<hr width="100%" color="#b5b5b5" size="1px" id="hr_top2" noshade>
		
			<input type="hidden" name="notice_num" value="${notice.notice_num}">
			<ul class="write_body">
				<li class="head_title">
					<select name="head" id="notice_head">
						<option value="필독" <c:if test="${notice.notice_head=='필독'}"> selected="selected" </c:if>>필독</option>
						<option value="공지" <c:if test="${notice.notice_head=='공지'}"> selected="selected" </c:if>>공지</option>
					</select>
				<!-- <label for="title">제목</label> -->	
					<input type="text" name="title" id="notice_title" value="${notice.notice_title}" maxlength="50">
				</li>
				<li>
				<!-- <label for="content">내용</label> -->	
					<textarea rows="20" cols="95" name="content" id="notice_content">${notice.notice_content}</textarea>
				</li>
			</ul>
			
			<ul class="image_add">
				<li>
					<span id="s">이미지 첨부</span>
				</li>
				<li>
				<!-- <label for="file1">파일1</label> -->	
					<input type="file" name="file1" id="file1" accept="image/gif,image/png,image/jpeg"> 
					
					<c:if test="${!empty notice.notice_file1}">
					<span class="file_detail"><br>
						(${notice.notice_file1}) 
						<input type="button" value="파일삭제" data-file="notice_file1" class="file_del">
					</span>
					</c:if>
					
				</li>
				
				<li>
				<!-- <label for="file2">파일2</label> -->	
					<input type="file" name="file2" id="file2" accept="image/gif,image/png,image/jpeg"> 
					
					<c:if test="${!empty notice.notice_file2}">
					<span class="file_detail"><br>
						(${notice.notice_file2}) 
						<input type="button" value="파일삭제" data-file="notice_file2" class="file_del">
					</span>
					</c:if>
					
				</li>
				
				<li>
				<!-- <label for="file2">파일3</label> -->	
					<input type="file" name="file3" id="file3" accept="image/gif,image/png,image/jpeg"> 
					
					<c:if test="${!empty notice.notice_file3}">
					<span class="file_detail"><br>
						(${notice.notice_file3}) 
						<input type="button" value="파일삭제" data-file="notice_file3" class="file_del">
					</span>
					</c:if>
					
					<c:if test="${!empty notice.notice_file1 or !empty notice.notice_file2 or !empty notice.notice_file3}">
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
									data:{notice_num:${notice.notice_num},notice_image:$(this).attr('data-file')},
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
			</ul>                   
		</form>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>




