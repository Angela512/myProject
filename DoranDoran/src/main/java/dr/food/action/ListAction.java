package dr.food.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dr.food.dao.FoodDAO;
import dr.food.vo.FoodVO;
import dr.controller.Action;
import dr.util.PagingUtil;

public class ListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		String food_local = request.getParameter("food_local");
		String local=null;
		
		FoodDAO dao = FoodDAO.getInstance();
		int count = dao.getFoodCount(keyfield, keyword,food_local);
		
		if(food_local!=null) {
			local="&food_local="+food_local;
		}
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield,keyword,
				Integer.parseInt(pageNum),count,4,10,"list.do",local);
		
		List<FoodVO> list = null;
		if(count > 0) {
			list = dao.getListFood(page.getStartRow(),
					       page.getEndRow(), keyfield, keyword, food_local);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/food/list.jsp";
	}

}
