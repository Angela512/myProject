package dr.notice.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import dr.notice.dao.NoticeDAO;
import dr.notice.vo.NoticeFavVO;
import dr.controller.Action;

public class WriteFavAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인이 된 경우
			//전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
			
			int notice_num = Integer.parseInt(request.getParameter("notice_num"));
			
			NoticeDAO dao = NoticeDAO.getInstance();
			NoticeFavVO noticeFav = dao.selectFav(notice_num, user_num);
			if(noticeFav!=null) {//좋아요 등록된 상태
				dao.deleteFav(noticeFav.getFav_num());
				
				mapAjax.put("result", "success");
				mapAjax.put("status", "noFav");
				mapAjax.put("count", dao.selectFavCount(notice_num));
			}else {//좋아요 등록되지 않은 상태
				dao.insertFav(notice_num, user_num);
				
				mapAjax.put("result", "success");
				mapAjax.put("status", "yesFav");
				mapAjax.put("count",dao.selectFavCount(notice_num));
			}
		}
		
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}






