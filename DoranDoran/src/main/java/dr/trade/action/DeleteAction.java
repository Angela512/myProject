package dr.trade.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dr.controller.Action;
import dr.member.dao.MemberDAO;
import dr.member.vo.MemberVO;
import dr.trade.dao.TradeDAO;
import dr.trade.vo.TradeVO;
import dr.util.FileUtil;

public class DeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session=request.getSession();
		Integer user_num=(Integer)session.getAttribute("user_num");
		Integer user_auth=(Integer)session.getAttribute("user_auth");
		
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		int trade_num=Integer.parseInt(request.getParameter("trade_num"));
		TradeDAO dao=TradeDAO.getInstance();
		TradeVO db_trade=dao.getTrade(trade_num);
		
		
		if(user_num!=db_trade.getMem_num() && user_auth!=3) {
			//로그인한 회원번호와 작성자 회원번호가 불일치,관리자가 아니면
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//로그인한 회원번호와 작성자 회원번호가 일치하거나 관리자일경우
		dao.deleteTrade(trade_num);
		//파일삭제
		FileUtil.removeFile(request, db_trade.getTrade_image1());
		FileUtil.removeFile(request, db_trade.getTrade_image2());
		FileUtil.removeFile(request, db_trade.getTrade_image3());
		
		
		
		return "redirect:/trade/list.do";
	}

}
