package dr.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dr.controller.Action;
import dr.food.vo.FoodVO;
import dr.member.dao.MemberDAO;
import dr.util.PagingUtil;

public class MyWriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		HttpSession session=request.getSession();
		Integer user_num=(Integer)session.getAttribute("user_num");


		
		MemberDAO dao = MemberDAO.getInstance();
		int count = dao.getMyFoodCount(user_num);
		
		
		PagingUtil page = new PagingUtil(
				Integer.parseInt(pageNum),count,4,10,"list.do");
		
		List<FoodVO> list = null;
		if(count > 0) {
			list = dao.getMyFoodList(page.getStartRow(),
					       page.getEndRow(), user_num);
		}
		
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		
		return "/WEB-INF/views/member/myWrite.jsp";
		
	}


}
