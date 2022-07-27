package dr.job.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dr.job.dao.JobDAO;
import dr.job.vo.JobVO;
import dr.controller.Action;
import dr.util.PagingUtil;

public class ListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		JobDAO dao = JobDAO.getInstance();
		int count = dao.getJobCount(keyfield, keyword);
		
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield,keyword,
				Integer.parseInt(pageNum),count,20,10,"list.do");
		
		List<JobVO> list = null;
		if(count > 0) {
			list = dao.getListJob(page.getStartRow(),
					       page.getEndRow(), keyfield, keyword);
		}
		
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		
		return "/WEB-INF/views/job/list.jsp";
	}

}
