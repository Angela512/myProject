package dr.job.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import dr.job.dao.JobDAO;
import dr.job.vo.JobVO;
import dr.controller.Action;
import dr.util.FileUtil;

public class DeleteFileAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> mapAjax = 
				             new HashMap<String,String>();
		
		HttpSession session = request.getSession();
		Integer user_num = 
				(Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인 된 경우
			int job_num = Integer.parseInt(
					request.getParameter("job_num"));
			JobDAO dao = JobDAO.getInstance();
			JobVO db_job = dao.getJob(job_num);
			if(user_num!=db_job.getMem_num()) {
				mapAjax.put("result", "wrongAccess");
			}else {
				dao.deleteJob(job_num);
				
				//파일 삭제
				FileUtil.removeFile(request, 
						         db_job.getJob_logo());
				mapAjax.put("result", "success");
			}
		}
		
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}



