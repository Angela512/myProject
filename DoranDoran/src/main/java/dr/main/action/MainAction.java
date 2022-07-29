package dr.main.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dr.controller.Action;
import dr.trade.dao.TradeDAO;
import dr.trade.vo.TradeVO;

public class MainAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		TradeDAO tradeDao=TradeDAO.getInstance();
		List<TradeVO> tradeList=tradeDao.getListTrade(1, 5, null, null, null, null);
		
		request.setAttribute("tradeList", tradeList);
		
		
		return "/WEB-INF/views/main/main.jsp";
	}

}
