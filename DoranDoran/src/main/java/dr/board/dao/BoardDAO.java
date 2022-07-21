package dr.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dr.board.vo.BoardVO;
import dr.util.DBUtil;
import dr.util.StringUtil;

public class BoardDAO {

	//싱글턴 패턴
		private static BoardDAO instance = new BoardDAO();
		
		public static BoardDAO getInstance() {
			return instance;
		}
		private BoardDAO() {}
		
		//글등록
		public void insertBoard(BoardVO board)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성================자동으로 들어가는 항목 제외하고 sql인서트 하는것이 맞는지?
				sql = "INSERT INTO board (board_num,board_head,board_title,board_content,"
						+ "board_image1,board_image2,board_image3,mem_num) "
						+ "VALUES (board_seq.nextval,?,?,?,?,?,?,?,?)";
				//JDBC 수행 3단계 : PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩================pk는 데이터 바인딩 하지 않는 것인가?.vo에 있는 데이터 모두 바인딩 아닌가?
				pstmt.setString(1, board.getBoard_head());
				pstmt.setString(2, board.getBoard_title());
				pstmt.setString(3, board.getBoard_content());
				pstmt.setString(4, board.getBoard_image1());
				pstmt.setString(5, board.getBoard_image2());
				pstmt.setString(6, board.getBoard_image3());
				pstmt.setInt(7, board.getMem_num());
				
				//JDBC 수행 4단계: SQL문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(null, pstmt, null);
			}
		}
		//총 레코드 수(검색 레코드 수)
		public int getBoardCount(String keyfield,String keyword)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			String sub_sql = "";
			int count = 0;
			
			try {
				//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				
				if(keyword!=null && !"".equals(keyword)) {
					if(keyfield.equals("1")) sub_sql = "WHERE b.mem_title LIKE ?";
					else if(keyfield.equals("2")) sub_sql = "WHERE m.mem_id LIKE ?";
					else if(keyfield.equals("3")) sub_sql = "WHERE b.mem_content LIKE ?";
				}
				
				sql = "SELECT COUNT(*) FROM board b JOIN member m USING(mem_num) " + sub_sql;
				
				//JDBC 수행 3단계 : PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				if(keyword!=null && !"".equals(keyword)) {
					pstmt.setString(1, "%"+keyword+"%");
				}
				
				//JDBC 수행 4단계
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
		//글목록(검색글 목록)
		public List<BoardVO> getListBoard(int start,int end,String keyfield,String keyword)
										throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<BoardVO> list = null;
			String sql = null;
			String sub_sql = "";
			int cnt = 0;
			
			try {
				//JDBC 수행 1,2 단계 : 커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				
				if(keyword!=null && !"".equals(keyword)) {
					if(keyfield.equals("1")) sub_sql = "WHERE b.mem_title LIKE ?";
					else if(keyfield.equals("2")) sub_sql = "WHERE m.mem_id LIKE ?";
					else if(keyfield.equals("3")) sub_sql = "WHERE b.mem_content LIKE ?";
				}
				
				sql = "SELECT * FROM (SELECT a.*, rownum rnum "
						+ "FROM (SELECT * FROM board b JOIN member m "
						+ "USING (mem_num) JOIN member_detail d "
						+ "USING (mem_num) "+ sub_sql 
						+ " ORDER BY b.board_num DESC)a) "
						+ "WHERE rnum >= ? AND rnum <= ?";
				//JDBC 수행 3단계 : PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				
				//?에 데이터 바인딩
				if(keyword!=null && !"".equals(keyword)) {
					pstmt.setString(++cnt, "%"+keyword+"%");
				}
				pstmt.setInt(++cnt, start);
				pstmt.setInt(++cnt, end);
				
				//JDBC 수행 4단계====================필요한 데이터만 작성?
				rs = pstmt.executeQuery();
				list = new ArrayList<BoardVO>();
				while(rs.next()) {
					BoardVO board = new BoardVO();
					board.setBoard_num(rs.getInt("board_num"));
					board.setBoard_head(rs.getString("board_head"));
					board.setBoard_title(StringUtil.useNoHtml(rs.getString("board_title")));
					board.setMem_name(rs.getString("mem_name"));
					board.setBoard_date(rs.getDate("board_date"));
					board.setBoard_count(rs.getInt("board_count"));
					board.setBoard_image1(rs.getString("board_image1"));
					board.setBoard_image2(rs.getString("board_image2"));
					board.setBoard_image3(rs.getString("board_image3"));
					board.setBoard_content(rs.getString("board_content"));
					board.setMem_num(rs.getInt("mem_num"));
					/* 오류가 왜 나는지?
					board.setMem_id(rs.getString("mem_id"));
					board.setMem_Photo(rs.getString("mem_photo"));
					*/
					
					list.add(board);
				}
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return list;
		}
		//글상세
		public BoardVO getBoard(int board_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			BoardVO board = null;
			String sql = null;
			
			try {
				//JDBC 수행 1,2단계: 커넥션풀에서 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT * FROM board b JOIN member m "
						+ "USING(mem_num) JOIN member_detail d "
						+ "USING(mem_num) WHERE b.board_num=?";
				//JDBC 수행 3단계 : PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, board_num);
				//JDBC 수행 4단계
				rs = pstmt.executeQuery();
				
				if(rs.next()) { 
					board = new BoardVO();
					board.setBoard_num(rs.getInt("board_num"));
					board.setBoard_head(rs.getString("board_head"));
					board.setBoard_title(StringUtil.useNoHtml(rs.getString("board_title")));
					board.setMem_name(rs.getString("mem_name"));
					board.setBoard_date(rs.getDate("board_date"));
					board.setBoard_count(rs.getInt("board_count"));
					board.setBoard_image1(rs.getString("board_image1"));
					board.setBoard_image2(rs.getString("board_image2"));
					board.setBoard_image3(rs.getString("board_image3"));
					board.setBoard_content(rs.getString("board_content"));
					board.setMem_num(rs.getInt("mem_num"));
					/* 오류가 왜 나는지?
					board.setMem_id(rs.getString("mem_id"));
					board.setMem_Photo(rs.getString("mem_photo"));
					*/
				}
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return board;
		}
		//조회수 증가
		public void updateReadcount(int board_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				//JDBC 수행 1,2단계: 커넥션풀에서 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "UPDATE board SET count=count+1 WHERE board_num=?";
				//JDBC 수행 3단계 : PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, board_num);
				//JDBC 수행 4단계
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(null, pstmt, conn);
				
			}
		}
		//파일 삭제
		public void deleteImage(int board_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성             빈문자열은 기존 내용 지운다는 소리
				sql = "UPDATE board SET board_image1='' WHERE board_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, board_num);
				
				//SQL문 실행
				pstmt.executeUpdate();
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(null, pstmt, conn);
				
			}
		}
		//글수정
		public void updateBoard(BoardVO board)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			String sub_sql = "";
			int cnt = 0;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				
				if(board.getBoard_image1()!=null) {
					//업로드한 이미지가 있는 경우
					sub_sql = ",board_image1=?";
				}
				//sql문 작성
				sql = "UPDATE board SET title=?,content=?,"
						+ "board_date=SYSDATE" + sub_sql   //SYSDATE 뒤에 공백 없음. ,로 구분하기 때문
						+ "WHERE board_num=?";
				
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				
				//?에 데이터 바인딩
				pstmt.setString(++cnt, board.getBoard_title());
				pstmt.setString(++cnt, board.getBoard_content());
				if(board.getBoard_image1()!=null) { //이미지가 있을 경우
					pstmt.setString(++cnt, board.getBoard_image1());
				}
				pstmt.setInt(++cnt, board.getBoard_num());
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		//====
		//글삭제
		
		//좋아요 등록
		//좋아요 개수
		//회원번호와 게시물 번호를 이용한 좋아요 정보
		//좋아요 삭제
		//내가 선택한 좋아요 목록
		
		//댓글 등록
		//댓글 개수
		//댓글 목록
		//댓글 상세
		//댓글 수정
		//댓글 삭제
		
	}
