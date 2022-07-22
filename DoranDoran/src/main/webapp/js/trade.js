$(function(){
//===============================중고거래 글쓰기==========================================//
	$('#write_form').submit(function(){
		if($('#trade_title').val().trim()==''){
			alert('제목을 입력하세요!');
			$('#trade_title').val('').focus();
			return false;
		}

		if($('#trade_content').val().trim()==''){
			alert('내용을 입력하세요!');
			$('#trade_content').val('').focus();
			return false;
		}

		if($('#trade_price').val().trim()==''){
			alert('가격을 입력하세요!');
			$('#trade_price').val('').focus();
			return false;
		}

		if($('#trade_phone').val().trim()==''){
			alert('연락처를 입력하세요!');
			$('#trade_phone').val('').focus();
			return false;
		}
	});
});