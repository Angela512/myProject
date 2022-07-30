$(function(){
	//============== 게시판 글쓰기 ==============//
	$('#write_form').submit(function(){
		if($('#board_head option:selected').val()=='말머리 선택'){
			alert('말머리를 선택해주세요!');
			$('#board_head').focus();
			return false;
		}
		if($('#board_title').val().trim()==''){
			alert('제목을 입력하세요!');
			$('#board_title').val('').focus();
			return false;
		}
		if($('#board_content').val().trim()==''){
			alert('내용을 입력하세요!');
			$('#board_content').val('').focus();
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
