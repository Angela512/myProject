package dr.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dr.board.vo.BoardVO;
import dr.util.DBUtil;
import dr.util.StringUtil;
import dr.util.DurationFromNow;
import dr.board.vo.BoardReplyVO;

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
				//SQL문 작성================pk(자동으로 들어가는 항목?) 제외하고 필요한 컬럼 인서트 하는것이 맞는지?
				sql = "INSERT INTO board (board_num,board_head,board_title,board_content,"
						+ "board_image1,board_image2,board_image3,mem_num) "
						+ "VALUES (board_seq.nextval,?,?,?,?,?,?,?)";
				//JDBC 수행 3단계 : PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
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
		public int getBoardCount(String keyfield,String keyword, String head)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			String sub_sql = "";
			int count = 0;
			
			try {
				//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				
				if((keyword!=null && !"".equals(keyword)) && (head==null || "".equals(head))) {
					if(keyfield.equals("1")) sub_sql = "WHERE b.board_title LIKE ?";
					else if(keyfield.equals("2")) sub_sql = "WHERE d.mem_name LIKE ?";
					else if(keyfield.equals("3")) sub_sql = "WHERE b.board_content LIKE ?";
				}else if((keyword==null || "".equals(keyword)) && (head!=null && !"".equals(head))) {
					sub_sql = "WHERE b.board_head = ?";
				}else if((keyword!=null && !"".equals(keyword)) && (head!=null && !"".equals(head))) {
					if(keyfield.equals("1")) sub_sql = "WHERE b.board_title LIKE ? AND b.board_head = ?";
					else if(keyfield.equals("2")) sub_sql = "WHERE d.mem_name LIKE ? AND b.board_head = ?";
					else if(keyfield.equals("3")) sub_sql = "WHERE b.board_content LIKE ? AND b.board_head = ?";
				}
				
				sql = "SELECT COUNT(*) FROM board b JOIN member m USING (mem_num) "
						+ "JOIN member_detail d USING (mem_num) " + sub_sql;
				
				//JDBC 수행 3단계 : PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				if((keyword!=null && !"".equals(keyword)) && (head==null || "".equals(head))) {
					pstmt.setString(1, "%"+keyword+"%");
				}else if((keyword==null || "".equals(keyword)) && (head!=null && !"".equals(head))) {
					pstmt.setString(1, head);
				}else if((keyword!=null && !"".equals(keyword)) && (head!=null && !"".equals(head))) {
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
				//자원정리
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return count;
		}
		//글목록(검색글 목록)
		public List<BoardVO> getListBoard(int start,int end,String keyfield,String keyword,String head)
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
				
				if((keyword!=null && !"".equals(keyword)) && (head==null || "".equals(head))) {
					if(keyfield.equals("1")) sub_sql = "WHERE b.board_title LIKE ?";
					else if(keyfield.equals("2")) sub_sql = "WHERE d.mem_name LIKE ?";
					else if(keyfield.equals("3")) sub_sql = "WHERE b.board_content LIKE ?";
				}else if((keyword==null || "".equals(keyword)) && (head!=null && !"".equals(head))) {
					sub_sql = "WHERE b.board_head = ?";
				}else if((keyword!=null && !"".equals(keyword)) && (head!=null && !"".equals(head))) {
					if(keyfield.equals("1")) sub_sql = "WHERE b.board_title LIKE ? AND b.board_head = ?";
					else if(keyfield.equals("2")) sub_sql = "WHERE d.mem_name LIKE ? AND b.board_head = ?";
					else if(keyfield.equals("3")) sub_sql = "WHERE b.board_content LIKE ? AND b.board_head = ?";
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
				if((keyword!=null && !"".equals(keyword)) && (head==null || "".equals(head))) {
					pstmt.setString(++cnt, "%"+keyword+"%");
				}else if((keyword==null || "".equals(keyword)) && (head!=null && !"".equals(head))) {
					pstmt.setString(++cnt, head);
				}else if((keyword!=null && !"".equals(keyword)) && (head!=null && !"".equals(head))) {
					pstmt.setString(++cnt, "%"+keyword+"%");
					pstmt.setString(++cnt, head);
				}
				pstmt.setInt(++cnt, start);
				pstmt.setInt(++cnt, end);
				
				//JDBC 수행 4단계===========데이터 전부 다 써도 되고, 필요한것만 써도 되고. vo에 있는것 모두
				rs = pstmt.executeQuery();
				list = new ArrayList<BoardVO>();
				while(rs.next()) {
					BoardVO board = new BoardVO();
					board.setBoard_num(rs.getInt("board_num"));
					board.setBoard_head(rs.getString("board_head"));
					board.setBoard_title(StringUtil.useNoHtml(rs.getString("board_title")));
					board.setMem_id(rs.getString("mem_id"));
					board.setMem_name(rs.getString("mem_name"));
					board.setBoard_date(rs.getDate("board_date"));
					board.setBoard_count(rs.getInt("board_count"));
					board.setBoard_image1(rs.getString("board_image1"));
					board.setBoard_image2(rs.getString("board_image2"));
					board.setBoard_image3(rs.getString("board_image3"));
					board.setBoard_content(rs.getString("board_content"));
					board.setMem_num(rs.getInt("mem_num"));
					board.setMem_photo(rs.getString("mem_photo"));
					
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
					board.setMem_id(rs.getString("mem_id"));
					board.setMem_name(rs.getString("mem_name"));
					board.setBoard_date(rs.getDate("board_date"));
					board.setBoard_modifydate(rs.getDate("board_modifydate"));
					board.setBoard_count(rs.getInt("board_count"));
					board.setBoard_image1(rs.getString("board_image1"));
					board.setBoard_image2(rs.getString("board_image2"));
					board.setBoard_image3(rs.getString("board_image3"));
					board.setBoard_content(rs.getString("board_content"));
					board.setMem_num(rs.getInt("mem_num"));
					board.setMem_photo(rs.getString("mem_photo"));
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
				sql = "UPDATE board SET board_count=board_count+1 WHERE board_num=?";
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
		//이미지 삭제
		public void deleteImage(int board_num, String board_image)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성             빈문자열은 기존 내용 지운다는 소리
				sql = "UPDATE board SET "+board_image+"='' WHERE board_num=?";
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
					sub_sql += ",board_image1=?";
				}
				if(board.getBoard_image2()!=null) {
					//업로드한 이미지가 있는 경우
					sub_sql += ",board_image2=?";
				}
				if(board.getBoard_image3()!=null) {
					//업로드한 이미지가 있는 경우
					sub_sql += ",board_image3=?";
				}
				
				//sql문 작성
				sql = "UPDATE board SET board_title=?,board_content=?,"
						+ "board_modifydate=SYSDATE" + sub_sql   //SYSDATE 뒤에 공백 없음. ,로 구분하기 때문
						+ " WHERE board_num=?";
				
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				
				//?에 데이터 바인딩
				pstmt.setString(++cnt, board.getBoard_title());
				pstmt.setString(++cnt, board.getBoard_content());
				if(board.getBoard_image1()!=null) { //이미지가 있을 경우
					pstmt.setString(++cnt, board.getBoard_image1());
				}
				if(board.getBoard_image2()!=null) { //이미지가 있을 경우
					pstmt.setString(++cnt, board.getBoard_image2());
				}
				if(board.getBoard_image3()!=null) { //이미지가 있을 경우
					pstmt.setString(++cnt, board.getBoard_image3());
				}
				
				pstmt.setInt(++cnt, board.getBoard_num());
				
				pstmt.executeUpdate();
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		//글삭제
		public void deleteBoard(int board_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			PreparedStatement pstmt2 = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//오토커밋 해제
				conn.setAutoCommit(false);
				
				//댓글 삭제
				sql = "DELETE FROM board_reply WHERE board_num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, board_num);
				pstmt.executeUpdate();
				
				//부모글 삭제
				sql = "DELETE FROM board WHERE board_num=?";
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setInt(1, board_num);
				pstmt2.executeUpdate();
				
				//예외 발생이 없이 정상적으로 SQL문 실행
				conn.commit();
			}catch(Exception e) {
				//예외 발생
				conn.rollback();
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(null, pstmt2, null);
				DBUtil.executeClose(null, pstmt, conn);
			
			}
		}
		//댓글 등록
		public void insertReplyBoard(BoardReplyVO boardReply)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션 할당 //질문)오라클 db와 연결하는 것?
				conn = DBUtil.getConnection();
				//sql문 작성            //질문)sql문에서 pk와 date들은 ?로 데이터 바인딩 안하나요?
				sql = "INSERT INTO board_reply (reply_num,mem_num,board_num,reply_content) "
						+ "VALUES (board_reply_seq.nextval,?,?,?)";  //질문)뜻이 board_reply db에 ()안의 값을 삽입하고, 시퀀스에서는 무엇을 한다는 걸까요
				//PreparedStatement 객체 생성 //질문)이 객체를 생성한다는 것은 어떤 의미?
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, boardReply.getMem_num());
				pstmt.setInt(2, boardReply.getBoard_num());
				pstmt.setString(3, boardReply.getReply_content());
				//sql문 실행
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		//댓글 개수
		public int getReplyBoardCount(int board_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			int count = 0;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT COUNT(*) FROM board_reply b "
						+ "JOIN member m ON b.mem_num=m.mem_num "
						+ "WHERE b.board_num=?";
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
		//댓글 목록
		public List<BoardReplyVO> getListReplyBoard(int start, int end, int board_num)
																			throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<BoardReplyVO> list = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//sql문 작성
				sql = "SELECT * FROM (SELECT a.*, rownum rnum "
						+ "FROM (SELECT * FROM board_reply b "
						+ "JOIN member m USING (mem_num) "
						+ "JOIN member_detail d USING (mem_num) "
						+ "WHERE b.board_num=? ORDER BY b.reply_num "
						+ "DESC)a) WHERE rnum >= ? AND rnum <= ?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, board_num);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				//sql문 실행
				rs = pstmt.executeQuery();
				list = new ArrayList<BoardReplyVO>();
				while(rs.next()) {
					BoardReplyVO reply = new BoardReplyVO();
					reply.setReply_num(rs.getInt("reply_num"));
					//날짜 -> 1분전, 1시간전, 1일전 형식의 문자열로 변환
					reply.setReply_date(DurationFromNow.getTimeDiffLabel(rs.getString("reply_date")));
					if(rs.getString("reply_modifydate")!=null) {
						reply.setReply_modifydate(DurationFromNow.getTimeDiffLabel(
								rs.getString("reply_modifydate")));
					}
					reply.setReply_content(StringUtil.useBrNoHtml(rs.getString("reply_content")));
					reply.setBoard_num(rs.getInt("board_num"));
					reply.setMem_num(rs.getInt("mem_num"));
					reply.setMem_name(rs.getString("mem_name"));
					
					list.add(reply);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return list;
		}
		//댓글 상세(인증하기 위해서 만드는 것임)
		public BoardReplyVO getReplyBoard(int reply_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			BoardReplyVO reply = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//sql문 작성
				sql = "SELECT * FROM board_reply WHERE reply_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, reply_num);
				//sql문 실행
				rs = pstmt.executeQuery();
				if(rs.next()) {
					reply = new BoardReplyVO();
					reply.setReply_num(rs.getInt("reply_num"));
					reply.setMem_num(rs.getInt("mem_num"));
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			
			return reply;
		}
		//댓글 수정
		public void updateReplyBoard(BoardReplyVO reply)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//sql문 작성
				sql = "UPDATE board_reply SET reply_content=?,"
						+ "reply_modifydate=SYSDATE "
						+ "WHERE reply_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setString(1, reply.getReply_content());
				pstmt.setInt(2, reply.getReply_num());
				//sql문 실행
				pstmt.executeUpdate();
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		//댓글 삭제
		public void deleteReplyBoard(int reply_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션을 할당
				conn = DBUtil.getConnection();
				//sql문 작성
				sql = "DELETE FROM board_reply WHERE reply_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, reply_num);
				//sql문 실행
				pstmt.executeUpdate();
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
	}
