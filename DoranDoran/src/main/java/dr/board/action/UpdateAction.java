package dr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import dr.board.dao.BoardDAO;
import dr.board.vo.BoardVO;
import dr.controller.Action;
import dr.util.FileUtil;

public class UpdateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		MultipartRequest multi = FileUtil.createFile(request);
		int board_num = Integer.parseInt(multi.getParameter("board_num"));
		String board_image1 = multi.getFilesystemName("board_image1");
		String board_image2 = multi.getFilesystemName("board_image2");
		String board_image3 = multi.getFilesystemName("board_image3");
		
		BoardDAO dao = BoardDAO.getInstance();
		//수정 전 데이터
		BoardVO db_board = dao.getBoard(board_num);
		if(user_num != db_board.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			
			//업로드된 파일이 있으면 파일 삭제
			FileUtil.removeFile(request, board_image1);
			FileUtil.removeFile(request, board_image2);
			FileUtil.removeFile(request, board_image3);
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//로그인한 회원번호와 작성자 회원번호가 일치
		BoardVO board = new BoardVO();
		board.setBoard_num(board_num);
		board.setTitle(multi.getParameter("title"));
		board.setContent(multi.getParameter("content"));
		board.setBoard_image1(board_image1);
		board.setBoard_image2(board_image2);
		board.setBoard_image3(board_image3);
		
		System.out.println(board);
		
		dao.updateBoard(board);
		if(board_image1 != null) {
			//새 파일로 교체할 때 원래 파일은 제거
			FileUtil.removeFile(request, db_board.getBoard_image1());
		}
		if(board_image2 != null) {
			//새 파일로 교체할 때 원래 파일은 제거
			FileUtil.removeFile(request, db_board.getBoard_image2());
		}
		if(board_image3 != null) {
			//새 파일로 교체할 때 원래 파일은 제거
			FileUtil.removeFile(request, db_board.getBoard_image3());
		}
		return "redirect:/board/detail.do?board_num="+board_num;
	}

}
