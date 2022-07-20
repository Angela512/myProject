package dr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dr.controller.Action;
import dr.member.dao.MemberDAO;
import dr.member.vo.MemberVO;

public class LoginAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		//전송된 데이터 반환
		String mem_id = request.getParameter("mem_id");
		String mem_pw = request.getParameter("mem_pw");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.checkMember(mem_id);
		boolean check = false;
		
		System.out.println("1:"+member);
		
		if(member!=null) {
			//비밀번호 일치 여부 체크
			check = member.isCheckedPassword(mem_pw);
			System.out.println("2:"+check);
			//로그인 실패시 auth 체크용(정지회원 체크)
			request.setAttribute("auth", member.getAuth());
		}
		
		System.out.println("3:"+check);
		
		if(check) { //인증 성공
			//로그인 처리
			HttpSession session = request.getSession();
			session.setAttribute("user_num", member.getMem_num());
			session.setAttribute("user_id", member.getMem_id());
			session.setAttribute("user_auth", member.getAuth());
			session.setAttribute("user_photo", member.getMem_photo());
			
			//인증 성공시 호출
			return "redirect:/main/main.do";
		}
		//인증 실패시 호출
		return "/WEB-INF/views/member/login.jsp";
	}
	
}
