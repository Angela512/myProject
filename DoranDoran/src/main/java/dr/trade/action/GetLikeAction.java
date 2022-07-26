package dr.trade.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import dr.controller.Action;
import dr.trade.dao.TradeDAO;
import dr.trade.vo.TradeLikeVO;

public class GetLikeAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		int trade_num=Integer.parseInt(request.getParameter("trade_num"));
		
		Map<String, Object> mapAjax=new HashMap<String, Object>();
		
		HttpSession session=request.getSession();
		Integer user_num=(Integer)session.getAttribute("user_num");
		
		TradeDAO dao=TradeDAO.getInstance();
		
		if(user_num==null) {
			mapAjax.put("status", "noLike");
			mapAjax.put("count", dao.selectLikeCount(trade_num));
		}else {//로그인 된 경우
			//로그인된 아이디 셋팅
			TradeLikeVO tradeLike = dao.selectLike(trade_num, user_num);
			
			if(tradeLike!=null) {//찜하기 클릭된상태
				mapAjax.put("status", "yesFav");
				mapAjax.put("count", dao.selectLikeCount(trade_num));
			}else {//찜하기 안한 상태
				mapAjax.put("status", "noLike");
				mapAjax.put("count", dao.selectLikeCount(trade_num));
			}
		}
		
		//JSON 데이터 생성
		ObjectMapper mapper=new ObjectMapper();
		String ajaxData=mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
