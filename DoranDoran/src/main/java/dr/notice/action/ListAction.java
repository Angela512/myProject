package dr.notice.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dr.controller.Action;
import dr.notice.dao.NoticeDAO;
import dr.notice.vo.NoticeVO;
import dr.util.PagingUtil;

public class ListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		String head = request.getParameter("head");
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		String notice_head = null;
		
		NoticeDAO dao = NoticeDAO.getInstance();
		int count = dao.getNoticeCount(keyfield, keyword, head);//키워드가 없을때 전체 카운트 읽고, 있으면 키워드 카운트 읽음
		
		if(head != null) notice_head = "&head=" + head;
		
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield,keyword, Integer.parseInt(pageNum),count,20,10,"list.do",notice_head);
		
		List<NoticeVO> list = null;
		if(count > 0) {
			list = dao.getListNotice(page.getStartRow(), page.getEndRow(), keyfield, keyword, head);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/notice/list.jsp";
	}

}
