<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글쓰기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/notice.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>게시판 글쓰기</h2>
		<form id="write_form" action="write.do" 
		   method="post" enctype="multipart/form-data">
			<ul>
				<li>
					<label for="title">제목</label>
					<input type="text" name="title" 
					      id="title" maxlength="50">
				</li>
				<li>
					<label for="content">내용</label>
					<textarea rows="5" cols="30" name="content"
					     id="content"></textarea>
				</li>
				<li>
					<label for="file1">파일1</label>
					<input type="file" name="file1" id="file1" 
					 accept="image/gif,image/png,image/jpeg">
				</li>
				<li>
					<label for="file2">파일2</label>
					<input type="file" name="file2" id="file2" 
					 accept="image/gif,image/png,image/jpeg">
				</li>
				<li>
					<label for="file3">파일3</label>
					<input type="file" name="file3" id="file3" 
					 accept="image/gif,image/png,image/jpeg">
				</li>
			</ul>
			<div class="align-center">
				<input type="submit" value="등록">
				<input type="button" value="목록" 
				             onclick="location.href='list.do'">
			</div>
		</form>
	</div>
</div>
</body>
</html>






