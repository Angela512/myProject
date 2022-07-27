package dr.board.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import dr.board.dao.BoardDAO;
import dr.board.vo.BoardReplyVO;
import dr.controller.Action;
import dr.util.PagingUtil;

public class ListReplyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) {
			pageNum = "1";
		}
		
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		BoardDAO dao = BoardDAO.getInstance();
		int count = dao.getReplyBoardCount(board_num);
		
		/*
		 * ajax 방식으로 목록을 표시하기 때문에 PagingUtil은 페이지 수 표시가 목적이 아니라
		 * 목록 데이터의 페이지 처리를 위해 rownum 번호를 구하는 것이 목적임
		 */
		int rowCount = 10;
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum),
						//페이지 카운트
				count,rowCount,1,null);
		List<BoardReplyVO> list = null;
		if(count > 0) {
			list = dao.getListReplyBoard(page.getStartRow(), page.getEndRow(),board_num);
		}else {
			list = Collections.emptyList();
		}
				         
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		mapAjax.put("count", count);
		mapAjax.put("rowCount", rowCount);
		mapAjax.put("list", list);
		//로그인한 회원번호와 작성자 회원번호 일치 여부를 체크하기 위해 씀. 같아야 수정,삭제 가능
		mapAjax.put("user_num", user_num);
		
		//JSON 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
