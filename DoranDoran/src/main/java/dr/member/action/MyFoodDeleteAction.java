package dr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dr.controller.Action;
import dr.food.dao.FoodDAO;
import dr.food.vo.FoodVO;
import dr.util.FileUtil;

public class MyFoodDeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session=request.getSession();
		Integer user_num=(Integer)session.getAttribute("user_num");
		
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		request.setCharacterEncoding("utf-8");
		
		String[] xx_num=request.getParameterValues("food_num");
		int[] food_nums=new int[xx_num.length];
		
		FoodDAO dao=FoodDAO.getInstance();
		
		
		for(int i=0;i<food_nums.length;i++) {
			food_nums[i]=Integer.parseInt(xx_num[i]);
			FoodVO db_food = dao.getFood(food_nums[i]);
			
			if(user_num!=db_food.getMem_num()) {
				//로그인한 회원번호와 작성자 회원번호가 불일치
				return "/WEB-INF/views/common/notice.jsp";
			}
			
			//로그인한 회원번호와 작성자 회원번호가 일치
			dao.deleteFood(food_nums[i]);
			
			//파일삭제
			FileUtil.removeFile(request, db_food.getFood_image1());
			FileUtil.removeFile(request, db_food.getFood_image2());
			FileUtil.removeFile(request, db_food.getFood_image3());
		}
				
				
		
		return "redirect:/member/food.do";
	}

}
