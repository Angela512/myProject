package dr.board.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import dr.board.dao.BoardDAO;
import dr.board.vo.BoardVO;
import dr.controller.Action;
import dr.util.FileUtil;

public class DeleteFileAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String,String> mapAjax = new HashMap<String,String>();
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num == null) { //로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else { //로그인 된 경우 (삭제작업)
			int board_num = Integer.parseInt(request.getParameter("board_num"));
			String board_image=request.getParameter("board_image");
			
			BoardDAO dao = BoardDAO.getInstance();
			BoardVO db_board = dao.getBoard(board_num);
			if(user_num!=db_board.getMem_num()) {
				mapAjax.put("result", "wrongAccess"); //남의 글 삭제면 잘못됐다고 함
			}else {
				dao.deleteImage(board_num, board_image); //일단 파일명만 지우는 것
				
				//파일 삭제 (실제로)
				if(board_image.equals("board_image1")) {
					FileUtil.removeFile(request, db_board.getBoard_image1());
				}else if(board_image.equals("board_image2")) {
					FileUtil.removeFile(request, db_board.getBoard_image2());
				}else if(board_image.equals("board_image3")) {
					FileUtil.removeFile(request, db_board.getBoard_image3());
				}
				mapAjax.put("result", "success");
			}
		}
		
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
//DAO 다하면 모델클래스(Action붙은것) 만들어야함
}
