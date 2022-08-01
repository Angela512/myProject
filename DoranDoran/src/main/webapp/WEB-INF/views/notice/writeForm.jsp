<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글쓰기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/notice-style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/notice.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<hr width="100%" color="#b5b5b5" size="1px" id="hr_top1" noshade>
	<div class="content-main">
		<form id="write_form" action="write.do" method="post" enctype="multipart/form-data">
		   <div class="wline1">
				<h2 id="write_text">공지사항 글쓰기</h2>
				<input id="write_submit" type="submit" value="등록">
					<!-- <input type="button" value="목록" onclick="location.href='list.do'"> -->
			</div>
		   <hr width="100%" color="#b5b5b5" size="1px" id="hr_top2" noshade>
			<ul class="write_body">
				<li class="head_title">
					<select name="head" id="notice_head">
						<option value="필독">필독</option>
						<option value="공지">공지</option>
					</select>
					<!-- <label for="title">제목</label> -->	
					<input type="text" name="title" id="notice_title" maxlength="50">
				</li>
				<li>
				<!-- <label for="content">내용</label> -->	
					<textarea rows="20" cols="95" name="content" id="notice_content"></textarea>
				</li>
			</ul>
			<ul class="image_add">
				<li>
					<span>이미지 첨부</span>
				</li>
				<li>
					<input type="file" name="file1" id="file1" accept="image/gif,image/png,image/jpeg">
				</li>
				<li>
					<input type="file" name="file2" id="file2" accept="image/gif,image/png,image/jpeg">
				</li>
				<li>
					<input type="file" name="file3" id="file3" accept="image/gif,image/png,image/jpeg">
				</li>
				<li>
				<p>* 이미지는 최대 3개까지 첨부할 수 있습니다.</p>
				</li>
			</ul>
		</form>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>






