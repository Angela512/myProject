package dr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dr.controller.Action;
import dr.trade.dao.TradeDAO;
import dr.trade.vo.TradeVO;
import dr.util.FileUtil;

public class MyTradeDeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session=request.getSession();
		Integer user_num=(Integer)session.getAttribute("user_num");
		
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		request.setCharacterEncoding("utf-8");
		
		String[] xx_num=request.getParameterValues("trade_num");
		int[] trade_nums=new int[xx_num.length];
		
		TradeDAO dao=TradeDAO.getInstance();
		
		
		for(int i=0;i<trade_nums.length;i++) {
			trade_nums[i]=Integer.parseInt(xx_num[i]);
			TradeVO db_trade = dao.getTrade(trade_nums[i]);
			
			if(user_num!=db_trade.getMem_num()) {
				//로그인한 회원번호와 작성자 회원번호가 불일치
				return "/WEB-INF/views/common/notice.jsp";
			}
			
			//로그인한 회원번호와 작성자 회원번호가 일치
			dao.deleteTrade(trade_nums[i]);
			
			//파일삭제
			FileUtil.removeFile(request, db_trade.getTrade_image1());
			FileUtil.removeFile(request, db_trade.getTrade_image2());
			FileUtil.removeFile(request, db_trade.getTrade_image3());
		}
				
				
		
		return "redirect:/member/trade.do";
	}

}
