package dr.food.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import dr.food.dao.FoodDAO;
import dr.food.vo.FoodVO;
import dr.controller.Action;
import dr.util.StringUtil;

public class DetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//글번호 반환
		int food_num = Integer.parseInt(
				        request.getParameter("food_num"));
		FoodDAO dao = FoodDAO.getInstance();
		//조회수 증가
		dao.updateReadcount(food_num);
		//글상세 정보 반환
		FoodVO food = dao.getFood(food_num);
		
		//HTML를 허용하지 않음
		food.setFood_name(StringUtil.useNoHtml(
				                    food.getFood_name()));
		//HTML를 허용하지 않으면서 줄바꿈 처리
		food.setFood_content(StringUtil.useBrNoHtml(
				                   food.getFood_content()));
		
		request.setAttribute("food", food);
		
		return "/WEB-INF/views/food/detail.jsp";
	}

}





