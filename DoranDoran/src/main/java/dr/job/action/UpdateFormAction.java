package dr.job.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dr.job.dao.JobDAO;
import dr.job.vo.JobVO;
import dr.controller.Action;

public class UpdateFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = 
				(Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않는 경우
			return "redirect:/member/loginForm.do";
		}
		
		//로그인이 된 경우
		int job_num = Integer.parseInt(
				        request.getParameter("job_num"));
		JobDAO dao = JobDAO.getInstance();
		JobVO job = dao.getJob(job_num);
		if(user_num != job.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호 불일치
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//로그인이 되어 있고 로그인한 회원번호와 작성자 회원번호 일치
		request.setAttribute("job", job);
		
		return "/WEB-INF/views/job/updateForm.jsp";
	}

}





