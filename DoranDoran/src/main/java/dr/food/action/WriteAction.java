package dr.food.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;


import dr.controller.Action;
import dr.food.dao.FoodDAO;
import dr.food.vo.FoodVO;
import dr.util.FileUtil;

public class WriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = 
				(Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.jsp";
		}
		
		//로그인이 된 경우
		
		
		MultipartRequest multi = 
				FileUtil.createFile(request);
		FoodVO food = new FoodVO();
		food.setFood_name(multi.getParameter("title"));
		food.setFood_content(multi.getParameter("content"));
	//	food.setIp(request.getRemoteAddr());
		food.setFood_image1(
				multi.getFilesystemName("filename"));
		food.setMem_num(user_num);
		
		FoodDAO dao = FoodDAO.getInstance();
		dao.insertFood(food);
		
		
		return "/WEB-INF/views/food/write.jsp";
	}

}
