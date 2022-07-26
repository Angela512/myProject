package dr.notice.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import dr.controller.Action;
import dr.notice.dao.NoticeDAO;
import dr.notice.vo.NoticeVO;
import dr.util.FileUtil;

public class DeleteFileAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> mapAjax = new HashMap<String,String>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인 된 경우
			int notice_num = Integer.parseInt(request.getParameter("notice_num"));
			String notice_image = request.getParameter("notice_image");
						
			NoticeDAO dao = NoticeDAO.getInstance();
			NoticeVO db_notice = dao.getNotice(notice_num);
			
			if(user_num!=db_notice.getMem_num()) {
				mapAjax.put("result", "wrongAccess");
			}else {
				dao.deleteFile(notice_num, notice_image);
				
				//파일 삭제
				if(notice_image.equals("notice_file1")) {
					FileUtil.removeFile(request, db_notice.getNotice_file1());
				}else if(notice_image.equals("notice_file2")) {
					FileUtil.removeFile(request, db_notice.getNotice_file2());
				}else if(notice_image.equals("notice_file3")) {
					FileUtil.removeFile(request, db_notice.getNotice_file3());
				}
				
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



