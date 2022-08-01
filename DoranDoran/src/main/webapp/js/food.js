$(function(){
	//============== 게시판 글쓰기 ==============//
	$('#write_form').submit(function(){
		if($('#title').val().trim()==''){
			alert('제목을 입력하세요!');
			$('#title').val('').focus();
			return false;
		}
		if($('#content').val().trim()==''){
			alert('내용을 입력하세요!');
			$('#content').val('').focus();
			return false;
		}
		if($('#phone2').val().trim()==''){
			alert('번호를 입력하세요!');
			$('#phone2').val('').focus();
			return false;
		}
		if($('#phone3').val().trim()==''){
			alert('번호를 입력하세요!');
			$('#phone3').val('').focus();
			return false;
		}
		if($('#zipcode').val().trim()==''){
			alert('주소를 입력하세요!');
			$('#zipcode').val('').focus();
			return false;
		}
		if($('#address1').val().trim()==''){
			alert('주소를 입력하세요!');
			$('#address1').val('').focus();
			return false;
		}
		if($('#address2').val().trim()==''){
			alert('주소를 입력하세요!');
			$('#address2').val('').focus();
			return false;
		}
	});
	
	//============== 목록 검색창 ==============//
	$('#search_form').submit(function(){
		if($('#keyword').val().trim()==''){
			alert('검색어를 입력하세요!');
			$('#keyword').val('').focus();
			return false;
		}
	});
	
});