package dr.member.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.oreilly.servlet.MultipartRequest;

import dr.controller.Action;
import dr.member.dao.MemberDAO;
import dr.member.vo.MemberVO;
import dr.util.FileUtil;

public class UpdateMyPhotoAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String, String> mapAjax=new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		Integer user_num=(Integer)session.getAttribute("user_num");
		
		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인이 된 경우
			MemberDAO dao=MemberDAO.getInstance();
			MemberVO db_member=dao.getMember(user_num);
			
			//전송된 파일 업로드 처리
			MultipartRequest multi=FileUtil.createFile(request);
			
			//서버에 저장된 파일명 반환
			String photo=multi.getFilesystemName("photo");
			
			//프로필 사진 수정
			dao.updateMyPhoto(photo, user_num);
			
			//세션에 저장된 프로필 사진 정보 갱신
			session.setAttribute("user_photo", photo);
			
			//이전 프로필 이미지 삭제
			FileUtil.removeFile(request, db_member.getMem_photo());
			
			mapAjax.put("result", "success");
		}
		
		//JSON 데이터로 반환
		ObjectMapper mapper=new ObjectMapper();
		String ajaxData=mapper.writeValueAsString(mapAjax);
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
