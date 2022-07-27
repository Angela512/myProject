package dr.board.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import dr.board.dao.BoardDAO;
import dr.board.vo.BoardReplyVO;
import dr.controller.Action;

public class WriteReplyAction implements Action{ //모델클래스(action)는 세션에서 데이터를 받아오기 위해 존재?

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//ajax통신임. detail.jsp에서 화면에 일부만 통신하며 변경하는 것.
		Map<String,String> mapAjax = new HashMap<String,String>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) { //로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {  //로그인이 된 경우
			//전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
			//전송된 데이터를 반환받아서 VO에 저장 //질문) 세션에 있는 값을 반환받는데 db에 있는것 전부 가능? 로그인부분외에도?
			BoardReplyVO reply = new BoardReplyVO();
			reply.setMem_num(user_num); //회원번호(작성자). 데이터를 세션에서 뽑아옴.  질문)mem_num이 언제 user_num과 동일하게 설정되었나요?
			reply.setReply_content(request.getParameter("reply_content"));
			reply.setMem_name(request.getParameter("mem_name"));
			reply.setBoard_num(Integer.parseInt(request.getParameter("board_num")));
			
			BoardDAO dao = BoardDAO.getInstance();
			dao.insertReplyBoard(reply);
			
			mapAjax.put("result", "success");
		}
		
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
