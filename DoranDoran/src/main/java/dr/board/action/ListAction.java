package dr.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dr.board.dao.BoardDAO;
import dr.board.vo.BoardVO;
import dr.controller.Action;
import dr.util.PagingUtil;

public class ListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		String sort = request.getParameter("sort");
		String head = request.getParameter("head");
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		BoardDAO dao = BoardDAO.getInstance();
		int count = dao.getBoardCount(keyfield, keyword, head); //키워드가 없을때 전체 카운트 읽고, 있으면 키워드 카운트 읽음
		
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield,keyword,Integer.parseInt(pageNum),
				count, 20,10,"list.do","&head="+head+"&sort="+sort);
		List<BoardVO> list = null;
		if(count > 0) {
			list = dao.getListBoard(page.getStartRow(), page.getEndRow(), keyfield, keyword, head, sort);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/board/list.jsp";
	}

}
