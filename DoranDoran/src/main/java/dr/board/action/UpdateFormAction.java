package dr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dr.board.dao.BoardDAO;
import dr.board.vo.BoardVO;
import dr.controller.Action;

public class UpdateFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		//로그인이 된 경우
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		BoardDAO dao = BoardDAO.getInstance();
		BoardVO board = dao.getBoard(board_num);
		if(user_num != board.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호 불일치
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//로그인이 되어 있고 로그인한 회원번호와 작성자 회원번호 일치
		request.setAttribute("board", board);
		
		return "/WEB-INF/views/board/updateForm.jsp";
	}

}
