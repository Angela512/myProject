$(function(){
	let idChecked = 0;
	
	//============= 회원가입 ==============//
	//아이디 중복 체크 이벤트 연결
	$('#id_check').click(function(){
		if($('#id').val().trim()==''){
			alert('아이디를 입력하세요!');
			$('#id').val('').focus();
			return;
		}
		
		//아이디 중복 여부를 표시하는 메시지 초기화
		$('#message_id').text('');
		
		//서버와 데이터 통신
		$.ajax({
			url:'checkDuplicatedId.do',
			type:'post',
			data:{id:$('#id').val()},
			dataType:'json',
			cache:false,
			timeout:30000,
			success:function(param){
				if(param.result == 'idNotFound'){
					idChecked = 1;
					$('#message_id').css('color','#000000').text('등록 가능 ID');
				}else if(param.result == 'idDuplicated'){
					idChecked = 0;
					$('#message_id').css('color','red').text('중복된 ID');
					$('#id').val('').focus();
				}else{
					idChecked = 0;
					alert('아이디 중복 체크 오류 발생');
				}
			},
			error:function(){
				idChecked = 0;
				alert('네트워크 오류 발생');
			}
		});
	});//end of click
	
	//아이디 중복 안내 메시지 초기화 및 아이디 중복 값 초기화
	$('#register_form #id').keydown(function(){
		idChecked = 0;
		$('#message_id').text('');
	});
	
	//회원 정보 등록 유효성 체크
	$('#register_form').submit(function(){
		if($('#id').val().trim()==''){
			alert('아이디를 입력하세요!');
			$('#id').val('').focus();
			return false;
		}
		if(idChecked==0){
			alert('아이디 중복 체크 필수');
			return false;
		}
		if($('#name').val().trim()==''){
			alert('이름을 입력하세요!');
			$('#name').val('').focus();
			return false;
		}
		if($('#passwd').val().trim()==''){
			alert('비밀번호를 입력하세요!');
			$('#passwd').val('').focus();
			return false;
		}
		if($('#phone').val().trim()==''){
			alert('전화번호를 입력하세요!');
			$('#phone').val('').focus();
			return false;
		}
		if($('#email').val().trim()==''){
			alert('이메일을 입력하세요!');
			$('#email').val('').focus();
			return false;
		}
		if($('#zipcode').val().trim()==''){
			alert('우편번호를 입력하세요!');
			$('#zipcode').val('').focus();
			return false;
		}
		if($('#address1').val().trim()==''){
			alert('주소를 입력하세요!');
			$('#address1').val('').focus();
			return false;
		}
		if($('#address2').val().trim()==''){
			alert('나머지 주소를 입력하세요!');
			$('#address2').val('').focus();
			return false;
		}
		
	});
	
	//============= 로그인 ==============//
	//로그인 이벤트 연결
	$('#login_form').submit(function(){
		if($('#id').val().trim()==''){
			alert('아이디를 입력하세요!');
			$('#id').val('').focus();
			return false;
		}
		if($('#passwd').val().trim()==''){
			alert('비밀번호를 입력하세요!');
			$('#passwd').val('').focus();
			return false;
		}
	});
	
	//============= 프로필 사진 업데이트 ==============//
	//프로필 사진 업데이트 이벤트 연결
	$('#photo_btn').click(function(){
		$('#photo_choice').show();
		$(this).hide();//수정 버튼 감추기
	});
	
	//이미지 미리 보기
	//처음 화면에 보여지는 이미지 읽기
	let photo_path = $('.my-photo').attr('src');
	let my_photo;
	$('#photo').change(function(){
		my_photo = this.files[0];
		if(!my_photo){
			$('.my-photo').attr('src',photo_path);
			return;
		}
		
		//파일의 용량 체크
		if(my_photo.size > 1024*1024){
			alert(Math.round(my_photo.size/1024) + 'kbytes(1024kbytes까지만 업로드 가능)');
			$('.my-photo').attr('src',photo_path);
			$(this).val('');
			return;
		}
		
		var reader = new FileReader();
		reader.readAsDataURL(my_photo);
		
		reader.onload=function(){
			$('.my-photo').attr('src',reader.result);
		}
	});//end of change
	
	//이미지 전송
	$('#photo_submit').click(function(){
		if($('#photo').val()==''){
			alert('파일을 선택하세요!');
			$('#photo').focus();
			return;
		}
		
		//파일 전송
		let form_data = new FormData();
		form_data.append('photo',my_photo);
		$.ajax({
			url:'updateMyPhoto.do',
			type:'post',
			data:form_data,
			dataType:'json',
			contentType:false,//데이터 객체를 문자열로 바꿀지에 대한 값. true면 일반문자
			processData:false,//해당 타입을 true로 하면 일반 text로 구분
			enctype:'multipart/form-data',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인 후 사용하세요!');
				}else if(param.result == 'success'){
					alert('프로필 사진이 수정되었습니다.');
					photo_path = $('.my-photo').attr('src');
					$('#photo').val('');
					$('#photo_choice').hide();
					$('#photo_btn').show();//수정 버튼 노출
				}else{
					alert('파일 전송 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
			
		});
	});//end of click
	
	//이미지 미리보기 취소
	$('#photo_reset').click(function(){
		//이미지 미리보기 전 원래 이미지로 되돌리기
		$('.my-photo').attr('src',photo_path);
		$('#photo').val('');
		$('#photo_choice').hide();
		$('#photo_btn').show();//수정 버튼 노출
	});//end of click
	
	//============= 회원정보수정 ==============//
	$('#modify_form').submit(function(){
		if($('#name').val().trim()==''){
			alert('이름을 입력하세요!');
			$('#name').val('').focus();
			return false;
		}
		if($('#phone').val().trim()==''){
			alert('전화번호를 입력하세요!');
			$('#phone').val('').focus();
			return false;
		}
		if($('#email').val().trim()==''){
			alert('이메일을 입력하세요!');
			$('#email').val('').focus();
			return false;
		}
		if($('#zipcode').val().trim()==''){
			alert('우편번호를 입력하세요!');
			$('#zipcode').val('').focus();
			return false;
		}
		if($('#address1').val().trim()==''){
			alert('주소를 입력하세요!');
			$('#address1').val('').focus();
			return false;
		}
		if($('#address2').val().trim()==''){
			alert('나머지 주소를 입력하세요!');
			$('#address2').val('').focus();
			return false;
		}
	});
	
	//============= 비밀번호수정 ==============//
	$('#password_form').submit(function(){
		if($('#id').val().trim()==''){
			alert('아이디를 입력하세요!');
			$('#id').val('').focus();
			return false;
		}
		if($('#origin_passwd').val().trim()==''){
			alert('현재 비밀번호를 입력하세요!');
			$('#origin_passwd').val('').focus();
			return false;
		}
		if($('#passwd').val().trim()==''){
			alert('새비밀번호를 입력하세요!');
			$('#passwd').val('').focus();
			return false;
		}
		if($('#cpasswd').val().trim()==''){
			alert('새비밀번호 확인을 입력하세요!');
			$('#cpasswd').val('').focus();
			return false;
		}
		if($('#passwd').val()!=$('#cpasswd').val()){
			alert('새비밀번호와 새비밀번호 확인 불일치');
			$('#passwd').val('').focus();
			$('#cpasswd').val('');
			return false;
		}
	});//end of submit
	
	//새 비번 확인까지 한 다음 다시 새 비번 수정할 경우 새 비번확인 초기화
	$('#passwd').keyup(function(){
		$('#cpasswd').val('');
		$('#message_cpasswd').text('');
	});
	
	//새비밀번호와 새비밀번호 확인 일치시 메시지 처리
	$('#cpasswd').keyup(function(){
		if($('#passwd').val()==$('#cpasswd').val()){
			$('#message_cpasswd').text('새비밀번호 일치');
		}else{
			$('#message_cpasswd').text('');
		}
	}); //end of keyup
	
	//==================회원 탈퇴================
	$('#delete_form').submit(function(){
		if($('#id').val().trim() == ''){
			alert('아이디를 입력하세요');
			$('#id').val('').focus();
			return false;
		}
		if($('#passwd').val().trim() == ''){
			alert('비밀번호를 입력하세요');
			$('#passwd').val('').focus();
			return false;
		}
		if($('#email').val().trim() == ''){
			alert('이메일을 입력하세요');
			$('#email').val('').focus();
			return false;
		}
		if($('#cpasswd').val().trim() == ''){
			alert('비밀번호 확인을 입력하세요');
			$('#cpasswd').val('').focus();
			return false;
		}
		if($('#passwd').val() != $('#cpasswd').val()){
			alert('비밀번호 불일치');
			$('#passwd').val('').focus();
			$('#cpasswd').val('');
			return false;
		}
	}); //end of submit
	
	//비번 확인까지 한 후다시 비번 변경하면 비번 확인 및 메세지 초기화
	$('#passwd').keyup(function(){
		$('#cpasswd').val('');
		$('#message_id').text('');
	});
	
	//비번과 비번확인 일치 여부 체크
	$('#cpasswd').keyup(function(){
		if($('#passwd').val() == $('#cpasswd').val()){
			$('#message_id').text('비밀번호 일치');
		}else{
			$('#message_id').text('');
		}
	});
	
	//=============== 회원관리 목록 =================
	$('#search_form').submit(function(){
		if($('#keyword').val().trim() == ''){
			alert('검색어를 입력하세요');
			$('#keyword').val('').focus();
			return false;
		}
	});
	
});





