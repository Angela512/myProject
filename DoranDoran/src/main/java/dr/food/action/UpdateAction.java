package dr.food.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import dr.controller.Action;
import dr.food.dao.FoodDAO;
import dr.food.vo.FoodVO;
import dr.util.FileUtil;

public class UpdateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = 
				(Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		MultipartRequest multi = 
				FileUtil.createFile(request);
		int food_num = Integer.parseInt(
				multi.getParameter("food_num"));
		String filename = multi.getFilesystemName("filename");
		
		FoodDAO dao = FoodDAO.getInstance();
		//수정전 데이터
		FoodVO db_food = dao.getFood(food_num);
		if(user_num != db_food.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			
			//업로드된 파일이 있으면 파일 삭제
			FileUtil.removeFile(request, filename);
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//로그인한 회원번호와 작성자 회원번호가 일치
		FoodVO food = new FoodVO();
		food.setFood_num(food_num);
		food.setFood_name(multi.getParameter("title"));
		food.setFood_content(multi.getParameter("content"));
	//	food.setIp(request.getRemoteAddr());
		food.setFood_image1(filename);
		
		food.setFood_phone1(multi.getParameter("phone1"));
		food.setFood_phone2(multi.getParameter("phone2"));
		food.setFood_phone3(multi.getParameter("phone3"));
		food.setFood_local(multi.getParameter("local"));
		
		food.setFood_addr1(multi.getParameter("address1"));
		food.setFood_addr2(multi.getParameter("address2"));
		food.setFood_zipcode(multi.getParameter("zipcode"));
		
		dao.updateFood(food);
		
		if(filename!=null) {
			//새 파일로 교체할 때 원래 파일 제거
			FileUtil.removeFile(request, 
					        db_food.getFood_image1());
		}
		
		return "redirect:/food/detail.do?food_num="+food_num;
	}

}




