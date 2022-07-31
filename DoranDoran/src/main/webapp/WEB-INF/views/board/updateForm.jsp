<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글수정</title>
<link rel ="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel ="stylesheet" href="${pageContext.request.contextPath}/css/board-style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/board.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<hr width="100%" color="#b5b5b5" size="1px" id="hr_top1" noshade>
	<div class="content-main">
		<form action="update.do" method="post" enctype="multipart/form-data" id="write_form" > <%-- 아이디 공유한 것 --%>
		<div class="wline1">
			<h2 id="write_text">자유게시판 글수정</h2>
			<input id="write_submit" type="submit" value="수정">
		</div>
		
		<hr width="100%" color="#b5b5b5" size="1px" id="hr_top2" noshade>
		
			<input type="hidden" name="board_num" value="${board.board_num}">
			<ul class="write_body">
				<li class="head_title">
					<!-- <label>말머리</label> -->
					<select name="board_head" id="board_head">
		     			<option value="동네소식" <c:if test="${board.board_head=='동네소식'}">selected</c:if>>동네소식</option>
		     			<option value="도움요청" <c:if test="${board.board_head=='도움요청'}">selected</c:if>>도움요청</option>
		     			<option value="함께해요" <c:if test="${board.board_head=='함께해요'}">selected</c:if>>함께해요</option>
		     			<option value="기타" <c:if test="${board.board_head=='기타'}">selected</c:if>>기타</option>
					</select>
					<!-- <label for="title">제목</label> -->
					<input type="text" name="board_title" id="board_title" value="${board.board_title}" 
					maxlength="50">
				</li>
				<li>
					<!-- <label for="content">내용</label> -->
					<textarea rows="20" cols="95" name="board_content" id="board_content">${board.board_content}</textarea>
				</li>
			</ul>
				
			<ul class="image_add">
				<li>
					<span id="s">이미지 첨부</span>
				</li>
				<li>
					<!-- <label for="board_image1">이미지1</label> -->
					<input type="file" name="board_image1" id="board_image1" 
					accept="image/gif,image/png,image/jpeg">
					<c:if test="${!empty board.board_image1}">
					<span class="file_detail"><br>
						(${board.board_image1})
						<input type="button" value="파일삭제" data-file="board_image1" class="file_del">
					</span>
					</c:if>
				</li>
				<li>
					<!-- <label for="board_image2">이미지2</label> -->
					<input type="file" name="board_image2" id="board_image2" 
					accept="image/gif,image/png,image/jpeg">
					<c:if test="${!empty board.board_image2}">
					<span class="file_detail"><br>
						(${board.board_image2})
						<input type="button" value="파일삭제" data-file="board_image2" class="file_del">
					</span>
					</c:if>
				</li>
				<li>
					<!-- <label for="board_image3">이미지3</label> -->
					<input type="file" name="board_image3" id="board_image3" 
					accept="image/gif,image/png,image/jpeg">
					<c:if test="${!empty board.board_image3}">
					<span class="file_detail"><br>
						(${board.board_image3})
						<input type="button" value="파일삭제" data-file="board_image3" class="file_del">
					</span>
					</c:if>
					<c:if test="${!empty board.board_image1 or !empty board.board_image2 or !empty board.board_image3}">
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
									data:{board_num:${board.board_num},board_image:$(this).attr('data-file')},
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
			<!-- <div class="align-center">
				
				<input type="button" value="목록" onclick="location.href='list.do'">
			</div> -->
		</form>
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>