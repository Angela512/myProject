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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/notice.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>게시판 글수정</h2>
		<form action="update.do" method="post"
		      enctype="multipart/form-data" id="write_form">
			<input type="hidden" name="notice_num" value="${notice.notice_num}">
			<ul>
				<li>
					<label for="head">말머리</label>
					<select name="head">
						<option value="필독" <c:if test="${notice.notice_head==0}"> selected="selected" </c:if>>필독</option>
						<option value="공지" <c:if test="${notice.notice_head==1}"> selected="selected" </c:if>>공지</option>
					</select>
				</li>
				<li>
					<label for="title">제목</label>
					<input type="text" name="title" id="title"
					    value="${notice.notice_title}" maxlength="50">
				</li>
				<li>
					<label for="content">내용</label>
					<textarea rows="5" cols="30" name="content"
					   id="content">${notice.notice_content}</textarea>
				</li>
				<li>
					*다시 파일을 업로드하면 기존 파일은 삭제됩니다.<br>
					<label for="file1">파일1</label>
					<c:if test="${empty notice.notice_file1}">
					
					<input type="file" name="file1" id="file1" accept="image/gif,image/png,image/jpeg">
					
					</c:if>
					
					<c:if test="${!empty notice.notice_file1}">
					<span id="file_detail">
						(${notice.notice_file1}) 
						<input type="button" value="파일삭제" id="file_del1">
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
									data:{notice_num:${notice.notice_num}, notice_image:'notice_file1'},
									dataType:'json',
									cache:false,
									timeout:30000,
									success:function(param){
										if(param.result == 'logout'){
											alert('로그인 후 사용하세요!');
										}else if(param.result == 'success'){
											$('#file_detail').hide();
										}else if(param.result == 'wrongAccess'){
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
					
					<!-- 이미지2 --> 
					<br>
					<label for="file2">파일2</label>
					<c:if test="${empty notice.notice_file2}">
					
					<span id="file_detail2">
					<input type="file" name="file2" id="file2" accept="image/gif,image/png,image/jpeg">
					</span>
					
					</c:if>
					<c:if test="${!empty notice.notice_file2}">
					<span id="file_detail">
						(${notice.notice_file2}) 
						<input type="button" value="파일삭제" id="file_del2">
						
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
									data:{notice_num:${notice.notice_num}, notice_image:'notice_file2'},
									dataType:'json',
									cache:false,
									timeout:30000,
									success:function(param){
										if(param.result == 'logout'){
											alert('로그인 후 사용하세요!');
										}else if(param.result == 'success'){
											$('#file_detail').hide();
											//$('#file2').show();
										}else if(param.result == 'wrongAccess'){
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
					
					
					<!-- 이미지3 -->
					<br>
					<label for="file3">파일3</label>
					<c:if test="${empty notice.notice_file3}">
					
					<input type="file" name="file3" id="file3" accept="image/gif,image/png,image/jpeg">
					
					</c:if>
					<c:if test="${!empty notice.notice_file3}">
					<span id="file_detail">
						(${notice.notice_file3}) 
						<input type="button" value="파일삭제" id="file_del3">
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
									data:{notice_num:${notice.notice_num}, notice_image:'notice_file3'},
									dataType:'json',
									cache:false,
									timeout:30000,
									success:function(param){
										if(param.result == 'logout'){
											alert('로그인 후 사용하세요!');
										}else if(param.result == 'success'){
											$('#file_detail').hide();
										}else if(param.result == 'wrongAccess'){
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
			<div class="align-center">
				<input type="submit" value="수정">
				<input type="button" value="목록" onclick="location.href='list.do'">
			</div>                      
		</form>
	</div>
</div>
</body>
</html>




