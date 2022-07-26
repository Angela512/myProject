package dr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dr.controller.Action;
import dr.member.dao.MemberDAO;
import dr.member.vo.MemberVO;
import dr.util.FileUtil;

public class DeleteUserAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		//로그인이 된 경우
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		//전송된 데이터 반환
		String mem_id=request.getParameter("mem_id");
		String passwd=request.getParameter("passwd");
		
		//로그인한 아이디
		String user_id=(String)session.getAttribute("user_id");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO db_member = dao.checkMember(mem_id);
		boolean check=false;
		
		//사용자가 입력한 아이디가 존재하고 로그인한 아이디와 일치하며 입력한 이메일과 저장된 이메일 일치 여부 체크
		if(db_member!=null && mem_id.equals(user_id)) {
			check=db_member.isCheckedPassword(passwd);
		}
		
		if(check) {
			//회원정보 삭제
			dao.deleteMember(user_num);
			//프로필 사진 삭제
			FileUtil.removeFile(request, db_member.getMem_photo());
			//로그아웃
			session.invalidate();
		}
		
		request.setAttribute("check", check);
		
		
		
		
		return "/WEB-INF/views/member/deleteUser.jsp";
	}

}
