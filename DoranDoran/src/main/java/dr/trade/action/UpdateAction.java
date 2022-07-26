package dr.trade.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import dr.controller.Action;
import dr.trade.dao.TradeDAO;
import dr.trade.vo.TradeVO;
import dr.util.FileUtil;

public class UpdateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session=request.getSession();
		Integer user_num=(Integer)session.getAttribute("user_num");
		
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		MultipartRequest multi=FileUtil.createFile(request);
		int trade_num=Integer.parseInt(multi.getParameter("trade_num"));
		String trade_image1=multi.getFilesystemName("trade_image1");
		String trade_image2=multi.getFilesystemName("trade_image2");
		String trade_image3=multi.getFilesystemName("trade_image3");
		
		TradeDAO dao=TradeDAO.getInstance();
		//수정 전 데이터
		TradeVO db_trade=dao.getTrade(trade_num);
		
		
		if(user_num!=db_trade.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			
			//업로드된 파일이 있으면 파일 삭제
				FileUtil.removeFile(request, trade_image1);
				FileUtil.removeFile(request, trade_image2);
				FileUtil.removeFile(request, trade_image3);
			
			
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//로그인한 회원번호와 작성자 회원번호가 일치
		TradeVO trade=new TradeVO();
		trade.setTrade_head(Integer.parseInt(multi.getParameter("trade_head")));
		trade.setTrade_category(multi.getParameter("trade_category"));
		trade.setTrade_title(multi.getParameter("trade_title"));
		trade.setTrade_content(multi.getParameter("trade_content"));
		trade.setTrade_price(Integer.parseInt(multi.getParameter("trade_price")));
		trade.setTrade_image1(trade_image1);
		trade.setTrade_image2(trade_image2);
		trade.setTrade_image3(trade_image3);
		trade.setTrade_phone(multi.getParameter("trade_phone"));
		trade.setTrade_num(trade_num);
		
		dao.updateTrade(trade);
		
		if(trade_image1!=null) {
			//새 파일로 교체할 때 원래 파일 제거
			FileUtil.removeFile(request, db_trade.getTrade_image1());
		}
		if(trade_image2!=null) {
			//새 파일로 교체할 때 원래 파일 제거
			FileUtil.removeFile(request, db_trade.getTrade_image2());
		}
		if(trade_image3!=null) {
			//새 파일로 교체할 때 원래 파일 제거
			FileUtil.removeFile(request, db_trade.getTrade_image3());
		}
		
		return "redirect:/trade/detail.do?trade_num="+trade_num;
	}

}
