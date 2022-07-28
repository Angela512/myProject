package dr.trade.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dr.controller.Action;
import dr.trade.dao.TradeDAO;
import dr.trade.vo.TradeVO;
import dr.util.PagingUtil;

public class ListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageNum=request.getParameter("pageNum");
		if(pageNum==null) pageNum="1";
		
		String keyfield=request.getParameter("keyfield");
		String keyword=request.getParameter("keyword");
		String trade_head=request.getParameter("trade_head");
		String trade_category=request.getParameter("trade_category");
		String first_head=null;
		String first_category=null;
		
		TradeDAO dao = TradeDAO.getInstance();
		int count=dao.getTradeCount(trade_head, trade_category, keyfield, keyword);
		
		if(trade_head!=null) {
			first_head="&trade_head="+trade_head;
		}
		if(trade_category!=null) {
			first_category="&trade_category="+trade_category;
		}
		
		
		
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield, keyword, Integer.parseInt(pageNum), count, 8,5,"list.do",first_head,first_category);
		
		List<TradeVO> list=null;
		
		if(count>0) {
			list=dao.getListTrade(page.getStartRow(), page.getEndRow(), trade_head, trade_category,keyfield,keyword);
			
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/trade/list.jsp";
	}

}
