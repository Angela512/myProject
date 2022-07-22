<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유게시판 글쓰기</title>
<link rel ="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/board.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>자유게시판 글쓰기</h2>
		<form id="write_form" action="write.do" method="post" enctype="multipart/form-data">
			<ul>
				<li>
					<label>말머리</label>
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
					<input type="text" name="board_title" id="title" maxlength="50">
				</li>
				<li>
					<label for="content">내용</label>
					<textarea rows="5" cols="30" name="board_content" id="content"></textarea>
				</li>
				<li>
					<span>이미지 첨부</span>
				</li>
				<li>	
					<label for="board_image1">이미지1</label>
					<input type="file" name="board_image1" id="board_image1" 
					accept="image/gif,image/png,image/jpeg">
				</li>
				<li>	
					<label for="board_image2">이미지2</label>
					<input type="file" name="board_image2" id="board_image2" 
					accept="image/gif,image/png,image/jpeg">
				</li>
				<li>
					<label for="board_image3">이미지3</label>
					<input type="file" name="board_image3" id="board_image3" 
					accept="image/gif,image/png,image/jpeg">
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