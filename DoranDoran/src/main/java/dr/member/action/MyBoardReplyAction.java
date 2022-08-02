package dr.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dr.board.vo.BoardReplyVO;
import dr.board.vo.BoardVO;
import dr.controller.Action;
import dr.member.dao.MemberDAO;
import dr.util.PagingUtil;

public class MyBoardReplyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session=request.getSession();
		Integer user_num=(Integer)session.getAttribute("user_num");
		
		if(user_num==null) {//로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		String pageNum=request.getParameter("pageNum");
		if(pageNum==null) pageNum="1";
		
		MemberDAO dao = MemberDAO.getInstance();
		int count = dao.getMyBoardReplyCount(user_num);
		
		//페이지 처리
		PagingUtil page= new PagingUtil(Integer.parseInt(pageNum), count, 10, 5, "myReply.do");
		
		List<BoardReplyVO> list=null;
		
		if(count>0) {
			list=dao.getMyListBoardReply(page.getStartRow(), page.getEndRow(), user_num);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/member/myBoardReply.jsp";
	}

}
