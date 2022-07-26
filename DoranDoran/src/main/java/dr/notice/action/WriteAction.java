package dr.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import dr.controller.Action;
import dr.notice.dao.NoticeDAO;
import dr.notice.vo.NoticeVO;
import dr.util.FileUtil;

public class WriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.jsp";
		}
		
		//로그인이 된 경우
		MultipartRequest multi = FileUtil.createFile(request);
		NoticeVO notice = new NoticeVO();
		notice.setNotice_title(multi.getParameter("title"));
		notice.setNotice_content(multi.getParameter("content"));
		notice.setNotice_file1(multi.getFilesystemName("file1"));
		notice.setNotice_file2(multi.getFilesystemName("file2"));
		notice.setNotice_file3(multi.getFilesystemName("file3"));
		notice.setNotice_head(multi.getParameter("head"));
		notice.setMem_num(user_num);
		
		NoticeDAO dao = NoticeDAO.getInstance();
		dao.insertNotice(notice);
		
		return "/WEB-INF/views/notice/write.jsp";
		
		
	}

}
