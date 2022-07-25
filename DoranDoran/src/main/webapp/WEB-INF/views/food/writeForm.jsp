<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글쓰기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/food.js"></script>

<style type="text/css">
	input{
		text-align:center;
	}
</style>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2 align="center">맛집 찾기 글쓰기</h2>
		<form id="write_form" action="write.do" 
		   method="post" enctype="multipart/form-data">
			<ul>
				<li>
					<select name = 'tag'>
						<option value="seoul">서울</option>
						<option value="gyeon">경기</option>
						<option value="in">인천</option>
					</select>
					<label for="title"></label>
					<input type="text" name="title" 
					      id="title" maxlength="50" placeholder="가게 이름을 입력">
				</li>
				<br>
				<li>
					<label for="content"></label>
					<textarea rows="7" cols="70" name="content" placeholder="내용및 설명을 입력"
					     id="content"></textarea>
				</li>
	
				<li>
					<br><br>
					<label for="filename">가게 & 메뉴 사진</label>
					<br><br>
					<input type="file" name="filename" 
					 id="filename" 
					 accept="image/gif,image/png,image/jpeg">
				</li>
					<br><br>
				<li>
					<label for="phone">가게 전화번호</label>
					<br>
					<br>
					<select name = 'phone'>
						<option value="02">02</option>
						<option value="031">031</option>
						<option value="032">032</option>
					</select>
					<input type="text"  maxlength="4" style="width:50px;">
					<input type="text"  maxlength="4" style="width:50px;">
				</li>
				<li>
					<br>
					<label for="address">가게 위치</label>
					
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






