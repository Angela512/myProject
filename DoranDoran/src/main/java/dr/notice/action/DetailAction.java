package dr.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dr.controller.Action;
import dr.notice.dao.NoticeDAO;
import dr.notice.vo.NoticeVO;
import dr.util.StringUtil;

public class DetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//글번호 반환
		int notice_num = Integer.parseInt(request.getParameter("notice_num"));
		NoticeDAO dao = NoticeDAO.getInstance();
		//조회수 증가
		dao.updateReadcount(notice_num);
		//글상세 정보 반환
		NoticeVO notice = dao.getNotice(notice_num);
		
		//HTML를 허용하지 않음
		notice.setNotice_title(StringUtil.useNoHtml(notice.getNotice_title()));
		//HTML를 허용하지 않으면서 줄바꿈 처리
		notice.setNotice_content(StringUtil.useBrNoHtml(notice.getNotice_content()));
		
		request.setAttribute("notice", notice);
		
		return "/WEB-INF/views/notice/detail.jsp";
	}

}





