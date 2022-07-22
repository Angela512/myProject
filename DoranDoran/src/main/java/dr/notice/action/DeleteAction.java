package dr.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dr.controller.Action;
import dr.notice.dao.NoticeDAO;
import dr.notice.vo.NoticeVO;
import dr.util.FileUtil;

public class DeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		int notice_num = Integer.parseInt(request.getParameter("notice_num"));
		NoticeDAO dao = NoticeDAO.getInstance();
		NoticeVO db_notice = dao.getNotice(notice_num);
		if(user_num != db_notice.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//로그인한 회원번호와 작성자 회원번호가 일치
		dao.deleteNotice(notice_num);
		//파일 삭제
		FileUtil.removeFile(request, db_notice.getNotice_file1());
		
		return "redirect:/notice/list.do";
	}

}
