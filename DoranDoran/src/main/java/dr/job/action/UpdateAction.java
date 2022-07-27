package dr.job.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import dr.job.dao.JobDAO;
import dr.job.vo.JobVO;
import dr.controller.Action;
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
		int job_num = Integer.parseInt(
				multi.getParameter("job_num"));
		
		JobDAO dao = JobDAO.getInstance();
		//수정전 데이터
		JobVO db_job = dao.getJob(job_num);
		if(user_num != db_job.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호가 불일치
		
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//로그인한 회원번호와 작성자 회원번호가 일치
		JobVO job = new JobVO();
		job.setJob_num(job_num);
		job.setJob_title(multi.getParameter("job_title"));
		job.setJob_category(multi.getParameter("job_category"));
		job.setJob_content(multi.getParameter("job_content"));
		job.setJob_logo(multi.getFilesystemName("job_logo"));
		job.setJob_link(multi.getParameter("job_link"));
		job.setJob_zipcode(multi.getParameter("job_zipcode"));
		job.setJob_addr1(multi.getParameter("job_addr1"));
		job.setJob_addr2(multi.getParameter("job_addr2"));
		job.setJob_enddate(multi.getParameter("job_enddate"));

		
		dao.updateJob(job);
		
		
		return "redirect:/job/detail.do?job_num="+job_num;
	}

}




