package dr.job.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dr.job.dao.JobDAO;
import dr.job.vo.JobVO;
import dr.controller.Action;
import dr.util.StringUtil;

public class DetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//글번호 반환
		int job_num = Integer.parseInt(
				        request.getParameter("job_num"));
		
		JobDAO dao = JobDAO.getInstance();
		//조회수 증가
		dao.updateReadcount(job_num);
		//글상세 정보 반환
		JobVO job = dao.getJob(job_num);
		
		//HTML를 허용하지 않음
		job.setJob_title(StringUtil.useNoHtml(
				                    job.getJob_title()));
		//HTML를 허용하지 않으면서 줄바꿈 처리
		job.setJob_content(StringUtil.useBrNoHtml(
				                   job.getJob_content()));
		
		request.setAttribute("job", job);
		
		return "/WEB-INF/views/job/detail.jsp";
	}

}





