package dr.job.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import dr.job.dao.JobDAO;
import dr.job.vo.JobVO;
import dr.controller.Action;
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
		JobVO job = new JobVO();
		job.setJob_title(multi.getParameter("job_title"));
		job.setJob_category(multi.getParameter("job_category"));
		job.setJob_content(multi.getParameter("job_content"));
		job.setJob_logo(
				multi.getFilesystemName("job_logo"));
		job.setJob_link(multi.getParameter("job_link"));
		job.setJob_zipcode(multi.getParameter("job_zipcode"));
		job.setJob_addr1(multi.getParameter("job_addr1"));
		job.setJob_addr2(multi.getParameter("job_addr2"));
		job.setJob_enddate(multi.getParameter("job_enddate"));



		job.setMem_num(user_num);
		
		JobDAO dao = JobDAO.getInstance();
		dao.insertJob(job);
		
		return "/WEB-INF/views/job/write.jsp";
	}

}
