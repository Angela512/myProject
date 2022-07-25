package dr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dr.board.dao.BoardDAO;
import dr.board.vo.BoardVO;
import dr.controller.Action;
import dr.util.StringUtil;

public class DetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//글번호 반환
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		BoardDAO dao = BoardDAO.getInstance();
		//조회수 증가
		dao.updateReadcount(board_num);
		//글상세 정보 반환
		BoardVO board = dao.getBoard(board_num);
		
		//HTML을 허용하지 않음
		board.setTitle(StringUtil.useNoHtml(board.getTitle()));
		//HTML을 허용하지 않으면서 줄바꿈 처리
		board.setContent(StringUtil.useBrNoHtml(board.getContent()));
		
		request.setAttribute("board", board);
		
		return "/WEB-INF/views/board/detail.jsp";
	}

}
