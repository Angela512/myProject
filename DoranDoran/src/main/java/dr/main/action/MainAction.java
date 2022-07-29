package dr.main.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dr.controller.Action;
import dr.notice.dao.NoticeDAO;
import dr.notice.vo.NoticeVO;
import dr.trade.dao.TradeDAO;
import dr.trade.vo.TradeVO;

public class MainAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		NoticeDAO noticeDao = NoticeDAO.getInstance();
		List<NoticeVO> noticeList = noticeDao.getListNotice(1, 5, null, null, null);
		
		TradeDAO tradeDao=TradeDAO.getInstance();
		List<TradeVO> tradeList=tradeDao.getListTrade(1, 5, null, null, null, null);
		
		request.setAttribute("noticeList", noticeList);
		request.setAttribute("tradeList", tradeList);
		
		
		return "/WEB-INF/views/main/main.jsp";
	}

}
