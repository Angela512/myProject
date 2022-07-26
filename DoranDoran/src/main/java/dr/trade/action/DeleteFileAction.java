package dr.trade.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import dr.controller.Action;
import dr.trade.dao.TradeDAO;
import dr.trade.vo.TradeVO;
import dr.util.FileUtil;

public class DeleteFileAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		HttpSession session=request.getSession();
		Integer user_num=(Integer)session.getAttribute("user_num");
		
		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인이 된 경우
			int trade_num=Integer.parseInt(request.getParameter("trade_num"));
			String trade_image=request.getParameter("trade_image");
			
			TradeDAO dao = TradeDAO.getInstance();
			TradeVO db_trade=dao.getTrade(trade_num);
			
			if(user_num!=db_trade.getMem_num()) {
				mapAjax.put("result", "wrongAccess");
			}else {
				dao.deleteFile(trade_num, trade_image);
				
				//파일 삭제
				if(trade_image.equals("trade_image1")) {
					FileUtil.removeFile(request, db_trade.getTrade_image1());
				}else if(trade_image.equals("trade_image2")) {
					FileUtil.removeFile(request, db_trade.getTrade_image2());
				}else if(trade_image.equals("trade_image3")) {
					FileUtil.removeFile(request, db_trade.getTrade_image3());
				}
				
				mapAjax.put("result", "success");
			}
		}
		
		//JSON 데이터 생성
		ObjectMapper mapper=new ObjectMapper();
		String ajaxData=mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
