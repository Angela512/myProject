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
				multi.getFilesystemName("filename1"));
		food.setFood_image2(
				multi.getFilesystemName("filename2"));
		food.setFood_image3(
				multi.getFilesystemName("filename3"));
		food.setMem_num(user_num);
		
		food.setFood_addr1(multi.getParameter("address1"));
		food.setFood_addr2(multi.getParameter("address2"));
		
		food.setFood_phone1(multi.getParameter("phone1"));
		food.setFood_phone2(multi.getParameter("phone2"));
		food.setFood_phone3(multi.getParameter("phone3"));
		
		food.setFood_local(multi.getParameter("local"));
		food.setFood_zipcode(multi.getParameter("zipcode"));
		
		food.setFood_timeh1(multi.getParameter("hour1"));
		food.setFood_timem1(multi.getParameter("min1"));
		food.setFood_timeh2(multi.getParameter("hour2"));
		food.setFood_timem2(multi.getParameter("min2"));
		
		food.setFood_link(multi.getParameter("link"));
		
		food.setFood_menu(multi.getParameter("menu"));

		FoodDAO dao = FoodDAO.getInstance();
		dao.insertFood(food);
		
		
		return "/WEB-INF/views/food/write.jsp";
	}

}
