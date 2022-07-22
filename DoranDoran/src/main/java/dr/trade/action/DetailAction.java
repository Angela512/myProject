package dr.trade.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dr.controller.Action;
import dr.member.dao.MemberDAO;
import dr.member.vo.MemberVO;
import dr.trade.dao.TradeDAO;
import dr.trade.vo.TradeVO;
import dr.util.StringUtil;

public class DetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session=request.getSession();
		Integer user_num=(Integer)session.getAttribute("user_num");
		
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		
		//글번호 반환
		int trade_num=Integer.parseInt(request.getParameter("trade_num"));
		
		TradeDAO dao=TradeDAO.getInstance();
		
		//조회수 증가
		//dao.updateReadcount(trade_num);
		
		//글상세 정보 반환
		TradeVO trade=dao.getTrade(trade_num);
		
		//HTML을 허용하지 않음
		trade.setTrade_title(StringUtil.useNoHtml(trade.getTrade_title()));
		
		//HTML을 허용하지 않으면서 줄바꿈 처리
		trade.setTrade_content(StringUtil.useBrNoHtml(trade.getTrade_content()));
		
		MemberDAO memdao=MemberDAO.getInstance();
		MemberVO member=memdao.getAuth(user_num);
		
		request.setAttribute("trade", trade);
		request.setAttribute("member", member);
		
		return "/WEB-INF/views/trade/detail.jsp";
	}

}
