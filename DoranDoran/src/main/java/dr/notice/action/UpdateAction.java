package dr.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import dr.controller.Action;
import dr.notice.dao.NoticeDAO;
import dr.notice.vo.NoticeVO;
import dr.util.FileUtil;

public class UpdateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		MultipartRequest multi = FileUtil.createFile(request);
		int notice_num = Integer.parseInt(multi.getParameter("notice_num"));
		String notice_file1 = multi.getFilesystemName("file1");
		String notice_file2 = multi.getFilesystemName("file2");
		String notice_file3 = multi.getFilesystemName("file3");
		
		NoticeDAO dao = NoticeDAO.getInstance();
		//수정전 데이터
		NoticeVO db_notice = dao.getNotice(notice_num);
		if(user_num != db_notice.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			
			//업로드된 파일이 있으면 파일 삭제
			FileUtil.removeFile(request, notice_file1);
			FileUtil.removeFile(request, notice_file2);
			FileUtil.removeFile(request, notice_file3);
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//로그인한 회원번호와 작성자 회원번호가 일치
		NoticeVO notice = new NoticeVO();
		notice.setNotice_num(notice_num);
		notice.setNotice_title(multi.getParameter("title"));
		notice.setNotice_content(multi.getParameter("content"));
		notice.setNotice_file1(notice_file1);
		notice.setNotice_file1(notice_file2);
		notice.setNotice_file1(notice_file3);
		
		dao.updateNotice(notice);
		
		if(notice_file1!=null) {
			//새 파일로 교체할 때 원래 파일 제거
			FileUtil.removeFile(request, db_notice.getNotice_file1());
		}
		
		return "redirect:/notice/detail.do?notice_num="+ notice_num;
	}

}




