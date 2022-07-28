package dr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dr.board.dao.BoardDAO;
import dr.board.vo.BoardVO;
import dr.controller.Action;
import dr.util.FileUtil;

public class DeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth"); 
		if(user_num==null) { //로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		BoardDAO dao = BoardDAO.getInstance();
		BoardVO db_board = dao.getBoard(board_num);
		if(user_num != db_board.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			return "WEB-INF/views/common/notice.jsp";
		}
		if(user_num != db_board.getMem_num() && user_auth != 3) {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			return "WEB-INF/views/common/notice.jsp";
		}
		//로그인한 회원번호와 작성자 회원번호가 일치
		dao.deleteBoard(board_num);
		//파일 삭제
		FileUtil.removeFile(request, db_board.getBoard_image1());
		
		return "redirect:/board/list.do";
	}

}
//딜리트 폼액션은 원래 없음. 로그인이 된 상태이기 때문에.