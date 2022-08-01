package dr.notice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dr.notice.vo.NoticeVO;
import dr.util.DBUtil;
import dr.util.StringUtil;
import dr.notice.vo.NoticeFavVO;

public class NoticeDAO {
	//싱글턴 패턴
	private static NoticeDAO instance = new NoticeDAO();
	
	public static NoticeDAO getInstance() {
		return instance;
	}
	private NoticeDAO() {}
	
	//글 등록
	public void insertNotice(NoticeVO notice) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			//****************file이 세개면..?
			sql = "INSERT INTO notice(notice_num,notice_title,notice_content,notice_file1,notice_file2,notice_file3,mem_num,notice_head) VALUES(notice_seq.nextval,?,?,?,?,?,?,?)";
			
			//JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, notice.getNotice_title());
			pstmt.setString(2, notice.getNotice_content());
			pstmt.setString(3, notice.getNotice_file1());
			pstmt.setString(4, notice.getNotice_file2());
			pstmt.setString(5, notice.getNotice_file3());
			pstmt.setInt(6, notice.getMem_num());
			pstmt.setString(7, notice.getNotice_head());
			
			//JDBC 수행 4단계 : SQL문 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//총 레코드 수(검색 레코드 수)
	public int getNoticeCount(String keyfield, String keyword, String head)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		
		try {
			//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword) && (head == null || "".equals(head))) {
				if(keyfield.equals("1")) sub_sql = "WHERE n.notice_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql = "WHERE d.mem_name LIKE ?";
				else if(keyfield.equals("3")) sub_sql = "WHERE n.notice_content LIKE ?";
			}else if((keyword==null || "".equals(keyword)) && (head != null && !"".equals(head))) {
				sub_sql = "WHERE n.notice_head = ?";
			}else if((keyword!=null && !"".equals(keyword)) && (head != null && !"".equals(head))) {
				if(keyfield.equals("1")) sub_sql = "WHERE notice_title LIKE ? AND notice_head = ?";
				else if(keyfield.equals("2")) sub_sql = "WHERE mem_name LIKE ? AND notice_head = ?";
				else if(keyfield.equals("3")) sub_sql = "WHERE notice_content LIKE ? AND notice_head = ?";
			}
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM notice n JOIN member m USING(mem_num) JOIN member_detail d USING(mem_num) " + sub_sql;
			
			//JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword) && (head == null || "".equals(head))) {
				pstmt.setString(1, "%" + keyword + "%");
			}else if((keyword==null || "".equals(keyword)) && (head != null && !"".equals(head))) {
				pstmt.setString(1, head);
			}else if((keyword!=null && !"".equals(keyword)) && (head != null && !"".equals(head))) {
				pstmt.setString(1, "%"+keyword+"%");
				pstmt.setString(2, head);
			}
			
			//JDBC 수행 4단계
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	//글 목록(검색글 목록)
	public List<NoticeVO> getListNotice(int start, int end, String keyfield, String keyword, String head) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<NoticeVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if((keyword != null && !"".equals(keyword)) && (head == null || "".equals(head))) {
				if(keyfield.equals("1")) sub_sql = "WHERE n.notice_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql = "WHERE d.mem_name LIKE ?";
				else if(keyfield.equals("3")) sub_sql = "WHERE n.notice_content LIKE ?";
			}else if((keyword == null || "".equals(keyword)) && (head!=null && !"".equals(head))) {
				sub_sql = "WHERE n.notice_head = ?";
			}else if((keyword != null && !"".equals(keyword)) && (head != null && !"".equals(head))) {
				if(keyfield.equals("1")) sub_sql = "WHERE n.notice_title LIKE ? AND n.notice_head = ?";
				else if(keyfield.equals("2")) sub_sql = "WHERE d.mem_name LIKE ? AND n.notice_head = ?";
				else if(keyfield.equals("3")) sub_sql = "WHERE n.notice_content LIKE ? AND n.notice_head = ?";
			}
			//SQL문 작성
			sql = "SELECT * FROM (SELECT a.*, rownum rnum "
			+ "FROM (SELECT * FROM notice n JOIN member_detail d USING (mem_num) JOIN member m USING(mem_num) " + sub_sql 
			+ " ORDER BY n.notice_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
			
			//JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			if((keyword != null && !"".equals(keyword)) && (head == null || "".equals(head))) {
				pstmt.setString(++cnt, "%"+keyword+"%");
			}else if((keyword == null || "".equals(keyword)) && (head != null && !"".equals(head))) {
				pstmt.setString(++cnt, head);
			}else if((keyword != null && !"".equals(keyword)) && (head != null && !"".equals(head))) {
				pstmt.setString(++cnt, "%"+keyword+"%");
				pstmt.setString(++cnt, head);
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			//JDBC 수행 4단계
			rs = pstmt.executeQuery();
			list = new ArrayList<NoticeVO>();
			while(rs.next()) {
				NoticeVO notice = new NoticeVO();
				notice.setNotice_num(rs.getInt("notice_num"));
				notice.setNotice_title(StringUtil.useNoHtml(rs.getString("notice_title")));
				notice.setNotice_count(rs.getInt("notice_count"));
				notice.setNotice_date(rs.getDate("notice_date"));
				notice.setNotice_modify_date(rs.getDate("notice_modify_date"));
				notice.setNotice_content(rs.getString("notice_content"));
				notice.setNotice_file1(rs.getString("notice_file1"));
				notice.setNotice_file2(rs.getString("notice_file2"));
				notice.setNotice_file3(rs.getString("notice_file3"));
				notice.setMem_num(rs.getInt("mem_num"));
				notice.setMem_name(rs.getString("mem_name"));
				notice.setNotice_head(rs.getString("notice_head"));
				list.add(notice);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return list;
	}
	
	//글 상세
	public NoticeVO getNotice(int notice_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		NoticeVO notice = null;
		String sql = null;
		
		try {
			//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM notice n JOIN member m USING(mem_num) "
					+ "JOIN member_detail d USING(mem_num) WHERE n.notice_num=?";
			//JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, notice_num);
			
			//JDBC 수행 4단계
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				notice = new NoticeVO();
				notice.setNotice_num(rs.getInt("notice_num"));
				notice.setNotice_title(rs.getString("notice_title"));
				notice.setNotice_content(rs.getString("notice_content"));
				notice.setNotice_count(rs.getInt("notice_count"));
				notice.setNotice_date(rs.getDate("notice_date"));
				notice.setNotice_modify_date(rs.getDate("notice_modify_date"));
				notice.setNotice_file1(rs.getString("notice_file1"));
				notice.setNotice_file2(rs.getString("notice_file2"));
				notice.setNotice_file3(rs.getString("notice_file3"));
				notice.setMem_num(rs.getInt("mem_num"));
				notice.setMem_name(rs.getString("mem_name"));
				notice.setNotice_head(rs.getString("notice_head"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return notice;
	}
	
	//조회수 증가
	public void updateReadcount(int notice_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE notice SET notice_count=notice_count+1 WHERE notice_num=?";
			
			//JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, notice_num);
			
			//JDBC 수행 4단계
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//파일 삭제
	public void deleteFile(int notice_num, String notice_image) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE notice SET " + notice_image + "='' WHERE notice_num=?";
			//filename=''은 기존 파일명을 지우겠다는 의미
			//파일만 없애는거니까 레코드를 다 지워버리면 안됨
			
			//JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, notice_num);
			
			//JDBC 수행 4단계 : sql문 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//글 수정
	public void updateNotice(NoticeVO notice) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if(notice.getNotice_file1() != null) {
				//업로드한 파일이 있는 경우
				sub_sql += ", notice_file1=?";
			}
			if(notice.getNotice_file2() != null) {
				//업로드한 파일이 있는 경우
				sub_sql += ", notice_file2=?";
			}
			if(notice.getNotice_file3() != null) {
				//업로드한 파일이 있는 경우
				sub_sql += ", notice_file3=?";
			}
			
			//SQL문 작성
			sql = "UPDATE notice SET notice_title=?, notice_content=?, notice_head=?, notice_modify_date=SYSDATE" + sub_sql + " WHERE notice_num=?";
			
			//JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(++cnt, notice.getNotice_title());
			pstmt.setString(++cnt, notice.getNotice_content());
			pstmt.setString(++cnt, notice.getNotice_head());
			if(notice.getNotice_file1() != null) {
				pstmt.setString(++cnt, notice.getNotice_file1());
			}
			if(notice.getNotice_file2() != null) {
				pstmt.setString(++cnt, notice.getNotice_file2());
			}
			if(notice.getNotice_file3() != null) {
				pstmt.setString(++cnt, notice.getNotice_file3());
			}
			pstmt.setInt(++cnt, notice.getNotice_num());
			
			//JDBC 수행 4단계 : sql문 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	
	//글삭제
		public void deleteNotice(int notice_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			PreparedStatement pstmt2 = null;
			PreparedStatement pstmt3 = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//오토커밋 해제
				conn.setAutoCommit(false);
				
				//댓글 삭제
				
				//좋아요 삭제
				/*
				 * 
				 
				sql = "DELETE FROM notice_fav WHERE notice_num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, notice_num);
				pstmt.executeUpdate();
				*/
				
				//부모글 삭제
				sql = "DELETE FROM notice WHERE notice_num=?";
				pstmt3 = conn.prepareStatement(sql);
				pstmt3.setInt(1, notice_num);
				pstmt3.executeUpdate();
				
				//예외 발생이 없이 정상적으로 SQL문 실행
				conn.commit();
			}catch(Exception e) {
				//예외 발생
				conn.rollback();
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(null, pstmt3, null);
				DBUtil.executeClose(null, pstmt2, null);
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
//*****좋아요는 없어서 안함
		//좋아요 등록
		public void insertFav(int notice_num, int mem_num)
		                                    throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "INSERT INTO notice_fav (fav_num,notice_num,"
					+ "mem_num) VALUES (noticefav_seq.nextval,?,?)";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, notice_num);
				pstmt.setInt(2, mem_num);
				//SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		
		//좋아요 개수
		public int selectFavCount(int board_num)
				throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			int count = 0;

			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT COUNT(*) FROM zboard_fav WHERE board_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, board_num);
				//SQL문 실행
				rs = pstmt.executeQuery();
				if(rs.next()) {
					count = rs.getInt(1);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return count;
		}
		
		//회원번호와 게시물 번호를 이용한 좋아요 정보
		public NoticeFavVO selectFav(int notice_num, int mem_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			NoticeFavVO fav = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT * FROM zboard_fav WHERE board_num=? AND mem_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터를 바인딩
				pstmt.setInt(1, notice_num);
				pstmt.setInt(2, mem_num);
				//SQL문 실행
				rs = pstmt.executeQuery();
				if(rs.next()) {
					fav = new NoticeFavVO();
					fav.setFav_num(rs.getInt("fav_num"));
					fav.setBoard_num(rs.getInt("board_num"));
					fav.setMem_num(rs.getInt("mem_num"));
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return fav;
		}
		//좋아요 삭제
		public void deleteFav(int fav_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "DELETE FROM zboard_fav WHERE fav_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, fav_num);
				//SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		//내가 선택한 좋아요 목록
		public List<NoticeVO> getListBoardFav(int start,int end, int mem_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<NoticeVO> list = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT * FROM (SELECT a.*, rownum rnum "
				    + "FROM (SELECT * FROM notice n JOIN "
				    + "member m USING(mem_num) JOIN notice_fav f "
				    + "USING(notice_num) WHERE f.mem_num=? "
				    + "ORDER BY notice_num DESC)a) "
				    + "WHERE rnum >= ? AND rnum<=?";
				
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, mem_num);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				
				//SQL문 실행
				rs = pstmt.executeQuery();
				list = new ArrayList<NoticeVO>();
				while(rs.next()) {
					NoticeVO notice = new NoticeVO();
					notice.setNotice_num(rs.getInt("notice_num"));
					notice.setNotice_title(StringUtil.useNoHtml(
							           rs.getString("title")));
					notice.setNotice_date(rs.getDate("notice_date"));
					notice.setMem_name(rs.getString("mem_name"));
					
					list.add(notice);
				}
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return list;
		}
		
		//관리자 페이지에서 작성자가 쓴 글 목록보기
		public List<NoticeVO> getAllList(int start,int end, int mem_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<NoticeVO> list = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT * FROM (SELECT a.*, rownum rnum "
						+ "FROM (SELECT * FROM notice n JOIN trade t USING (mem_num) "
						+ "JOIN member_detail d USING(mem_num) WHERE mem_num=?)a) "
						+ "WHERE rnum >=? AND rnum<=?";
				
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, mem_num);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				
				//SQL문 실행
				rs = pstmt.executeQuery();
				list = new ArrayList<NoticeVO>();
				while(rs.next()) {
					NoticeVO notice = new NoticeVO();
					notice.setNotice_num(rs.getInt("notice_num"));
					notice.setNotice_title(StringUtil.useNoHtml(rs.getString("notice_title")));
					notice.setNotice_date(rs.getDate("notice_date"));
					notice.setMem_name(rs.getString("mem_name"));
					
					list.add(notice);
				}
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return list;
		}
		
	 
}
