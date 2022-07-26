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

public class WriteLikeAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		
		HttpSession session=request.getSession();
		Integer user_num=(Integer)session.getAttribute("user_num");
		
		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인이 된 경우
			//전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
			
			int trade_num=Integer.parseInt(request.getParameter("trade_num"));
			
			TradeDAO dao=TradeDAO.getInstance();
			TradeLikeVO tradeLike=dao.selectLike(trade_num, user_num);
			
			if(tradeLike!=null) {//좋아요 등록된 상태
				dao.deleteLike(tradeLike.getLike_num());
				
				mapAjax.put("result", "success");
				mapAjax.put("status", "noLike");
				mapAjax.put("count", dao.selectLikeCount(trade_num));
			}else {//좋아요 등록이 안된 상태
				dao.insertLike(trade_num, user_num);
				
				mapAjax.put("result", "success");
				mapAjax.put("status", "yesLike");
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
