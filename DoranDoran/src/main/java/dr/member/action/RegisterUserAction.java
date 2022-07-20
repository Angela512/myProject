package dr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class RegisterUserAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		//자바빈(VO) 생성
		MemberVO member = new MemberVO();
		//전송된 데이터를 자바빈에 저장
		member.setId(request.getParameter("mem_id"));
		member.setName(request.getParameter("mem_name"));
		member.setPasswd(request.getParameter("mem_pw"));
		member.setPhone(request.getParameter("mem_phone"));
		member.setEmail(request.getParameter("mem_email"));
		member.setZipcode(request.getParameter("mem_zipcode"));
		member.setAddress1(request.getParameter("mem_addr1"));
		member.setAddress2(request.getParameter("mem_addr2"));
		  
		MemberDAO dao = MemberDAO.getInstance();
		dao.insertMember(member);
		
		return "/WEB-INF/views/member/registerUser.jsp";
	}

}



