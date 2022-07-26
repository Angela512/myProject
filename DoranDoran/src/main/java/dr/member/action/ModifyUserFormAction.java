package dr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dr.controller.Action;
import dr.member.dao.MemberDAO;
import dr.member.vo.MemberVO;

public class ModifyUserFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session=request.getSession();
		Integer user_num=(Integer)session.getAttribute("user_num");
		
		if(user_num==null) {//로그인 되지 않는 경우
			return "redirect:/member/loginForm.do";
		}
		
		//로그인 된 경우
		MemberDAO dao=MemberDAO.getInstance();
		MemberVO member=dao.getMember(user_num);
		
		request.setAttribute("member", member);
		
		return "/WEB-INF/views/member/modifyUserForm.jsp";
	}

}
