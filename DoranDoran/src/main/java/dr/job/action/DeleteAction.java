package dr.job.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dr.job.dao.JobDAO;
import dr.job.vo.JobVO;
import dr.controller.Action;
import dr.util.FileUtil;

public class DeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");

		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		int job_num = Integer.parseInt(
				        request.getParameter("job_num"));
		JobDAO dao = JobDAO.getInstance();
		JobVO db_job = dao.getJob(job_num);
		if(user_num != db_job.getMem_num() && user_auth != 3) {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//로그인한 회원번호와 작성자 회원번호가 일치
		dao.deleteJob(job_num);
		//파일 삭제
		FileUtil.removeFile(request, db_job.getJob_logo());
		
		return "redirect:/job/list.do";
	}

}
