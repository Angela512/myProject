package dr.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dr.controller.Action;
import dr.member.dao.MemberDAO;
import dr.member.vo.MemberVO;
import dr.util.PagingUtil;

public class AdminMemberListAction implements Action{
	//전체글개수
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth < 3) {//관리자로 로그인하지 않은 경우
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//관리자로 로그인한 경우
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		String mem_auth=request.getParameter("auth");
		String auth = null;
		
	//	int auth = Integer.parseInt(request.getParameter("auth"));
		if(mem_auth != null) {
			auth = "&auth=" + mem_auth;
		}
		
		
		MemberDAO dao = MemberDAO.getInstance();
		int count = dao.getMemberCountByAdmin(auth,keyfield, keyword);
		//페이지 처리
		//keyfield,keyword,currentPage,count,rowCount,pageCount,url
		PagingUtil page = new PagingUtil(keyfield,keyword,Integer.parseInt(pageNum),count,20,10,"memberList.do",auth);
		List<MemberVO> list = null;
		if(count > 0) {
			list = dao.getListMemberByAdmin(page.getStartRow(),page.getEndRow(),keyfield,keyword,auth);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/admin/memberList.jsp";
	}

}




