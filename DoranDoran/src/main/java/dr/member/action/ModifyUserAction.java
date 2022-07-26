package dr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dr.controller.Action;
import dr.member.dao.MemberDAO;
import dr.member.vo.MemberVO;

public class ModifyUserAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num=(Integer)session.getAttribute("user_num");
		
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		//로그인 된 경우
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		//자바빈(VO)생성
		MemberVO member=new MemberVO();
		member.setMem_num(user_num);
		member.setMem_name(request.getParameter("mem_name"));
		member.setMem_phone(request.getParameter("mem_phone"));
		member.setMem_email(request.getParameter("mem_email"));
		member.setMem_zipcode(request.getParameter("mem_zipcode"));
		member.setMem_addr1(request.getParameter("mem_addr1"));
		member.setMem_addr2(request.getParameter("mem_addr2"));
		
		MemberDAO dao=MemberDAO.getInstance();
		dao.updateMember(member);
		
		
		return "/WEB-INF/views/member/modifyUser.jsp";
	}

}
