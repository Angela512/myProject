<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글수정</title>
<link rel ="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/board.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>게시판 글수정</h2>
		<form action="update.do" method="post" enctype="multipart/form-data" id="write_form" > <%-- 아이디 공유한 것 --%>
			<input type="hidden" name="board_num" value="${board.board_num}">
			<ul>
				<li>
					<select name="board_head">
						<option value="말머리 선택">말머리 선택</option>
		     			<option value="동네소식">동네소식</option>
		     			<option value="도움요청">도움요청</option>
		     			<option value="함께해요">함께해요</option>
		     			<option value="기타">기타</option>
					</select>
				</li>
				<li>
					<label for="title">제목</label>
					<input type="text" name="title" id="title" value="${board.board_title}" 
					maxlength="50">
				</li>
				<li>
					<label for="content">내용</label>
					<textarea rows="5" cols="30" name="content" id="content">${board.board_content}</textarea>
				</li>
				<li>
					<label for="filename">이미지 첨부</label><br>
					<input type="file" name="board_image1" id="filename" 
					accept="image/gif,image/png,image/jpeg"><br>
					<input type="file" name="board_image2" id="filename" 
					accept="image/gif,image/png,image/jpeg"><br>
					<input type="file" name="board_image3" id="filename" 
					accept="image/gif,image/png,image/jpeg">
		
					<c:if test="${!empty board.board_image1}">
					<br>
					<span id="file_detail">
						(${board.board_image1})파일이 등록되어 있습니다.
						다시 파일을 업로드하면 기존 파일은 삭제됩니다.
						<input type="button" value="이미지 삭제" id="file_del">
					</span>
					<script type="text/javascript">
					$(function(){
						//이벤트 연결
						$('#file_del').click(function(){
							let choice = confirm('삭제하시겠습니까?');
							if(choice){
								$.ajax({
									url:'deleteFile.do',
									type:'post',        //el임. jsp에서 쓸 수 있음
									data:{board_num:${board.board_num}},
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