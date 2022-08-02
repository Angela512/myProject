package dr.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dr.admin.adminvo.AdminVO;
import dr.controller.Action;
import dr.member.dao.MemberDAO;
import dr.member.vo.MemberVO;
import dr.notice.vo.NoticeVO;
import dr.util.PagingUtil;

public class AdminDetailUserFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}

		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth < 3) {//관리자로 로그인하지 않은 경우
			return "/WEB-INF/views/common/notice.jsp";
		}

		//관리자로 로그인한 경우
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		
		int mem_num = Integer.parseInt(request.getParameter("mem_num"));
		String mnum = null;
		
		MemberDAO dao = MemberDAO.getInstance();	
		MemberVO member = dao.getMember(mem_num);

		int count = dao.getAdminBoardCount(mem_num); //총레코드수
		mnum = "&mem_num=" + Integer.toString(mem_num);
		
		//페이지 처리
		//keyfield,keyword,currentPage,count,rowCount,pageCount,url
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum),count,20,10,"detailUserForm.do",mnum);

		List<AdminVO> list = null;
		if(count > 0) {
			list = dao.getListAdminBoard(page.getStartRow(),page.getEndRow(),mem_num); //글목록(검색글목록)
		}

		request.setAttribute("member", member);
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());

		return "/WEB-INF/views/admin/detailUserForm.jsp";
	}

}



