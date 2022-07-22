package dr.trade.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import dr.controller.Action;
import dr.trade.dao.TradeDAO;
import dr.trade.vo.TradeVO;
import dr.util.FileUtil;

public class WriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num=(Integer)session.getAttribute("user_num");
		
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.jsp";
		}
		
		MultipartRequest multi=FileUtil.createFile(request);
		TradeVO trade = new TradeVO();
		trade.setMem_num(user_num);
		trade.setTrade_head(Integer.parseInt(multi.getParameter("trade_head")));
		trade.setTrade_category(multi.getParameter("trade_category"));
		trade.setTrade_title(multi.getParameter("trade_title"));
		trade.setTrade_content(multi.getParameter("trade_content"));
		trade.setTrade_price(Integer.parseInt(multi.getParameter("trade_price")));
		trade.setTrade_image1(multi.getFilesystemName("trade_image1"));
		trade.setTrade_image2(multi.getFilesystemName("trade_image2"));
		trade.setTrade_image3(multi.getFilesystemName("trade_image3"));
		trade.setTrade_phone(multi.getParameter("trade_phone"));
		
		TradeDAO dao=TradeDAO.getInstance();
		dao.insertTrade(trade);
		
		
		return "/WEB-INF/views/trade/write.jsp";
	}

}
