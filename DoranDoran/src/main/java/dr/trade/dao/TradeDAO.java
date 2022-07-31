package dr.trade.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dr.trade.vo.TradeLikeVO;
import dr.trade.vo.TradeVO;
import dr.util.DBUtil;
import dr.util.StringUtil;

public class TradeDAO {
	//싱글턴 패턴
	private static TradeDAO instance = new TradeDAO();
		
	public static TradeDAO getInstance() {
		return instance;
	}
	private TradeDAO() {}
		
	//글등록
	public void insertTrade(TradeVO trade)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		String sql=null;
		
		try {
			conn=DBUtil.getConnection();
			
			sql="INSERT INTO trade (trade_num,mem_num,trade_head,trade_category,"
					+ "trade_title,trade_content,trade_price,trade_image1,trade_image2,trade_image3,trade_phone) VALUES "
					+ "(trade_seq.nextval,?,?,?,?,?,?,?,?,?,?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, trade.getMem_num());
			pstmt.setInt(2, trade.getTrade_head());
			pstmt.setString(3, trade.getTrade_category());
			pstmt.setString(4, trade.getTrade_title());
			pstmt.setString(5, trade.getTrade_content());
			pstmt.setInt(6, trade.getTrade_price());
			pstmt.setString(7, trade.getTrade_image1());
			pstmt.setString(8, trade.getTrade_image2());
			pstmt.setString(9, trade.getTrade_image3());
			pstmt.setString(10, trade.getTrade_phone());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//총 레코드 수(검색 레코드 수)
	public int getTradeCount(String trade_head,String trade_category,String keyfield,String keyword)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql=null;
		String sub_sql="";
		int count=0;
		
		try {
			//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션을 할당
			conn=DBUtil.getConnection();
			
			if(trade_head!=null && !"".equals(trade_head)) {
				 sub_sql+="AND trade_head="+trade_head+" ";
			}
			if(trade_category!=null && !"".equals(trade_category)) {
				sub_sql+="AND trade_category="+trade_category+" ";
			}
			if(keyword!=null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql+="AND t.trade_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql+="AND m.mem_id LIKE ?";
				else if(keyfield.equals("3")) sub_sql+="AND t.trade_content LIKE ?";
			}
			
			
			sql="SELECT COUNT(*) FROM trade t JOIN member m USING(mem_num) WHERE 1=1 "+sub_sql;
			
			pstmt=conn.prepareStatement(sql);
			
			if(keyword!=null && !"".equals(keyword)) {
				pstmt.setString(1, "%"+keyword+"%");
			}
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				count=rs.getInt(1);
			}
			
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return count;
	}
	
	//글목록(검색글 목록)
	public List<TradeVO> getListTrade(int start,int end,String trade_head,String trade_category,String keyfield,String keyword)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<TradeVO> list = null;
		String sql=null;
		String sub_sql="";
		int cnt=0;
		
		try {
			//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션을 할당
			conn=DBUtil.getConnection();
			
			if(trade_head!=null && !"".equals(trade_head)) {
				 sub_sql+="AND trade_head="+trade_head+" ";
			}
			if(trade_category!=null && !"".equals(trade_category)) {
				sub_sql+="AND trade_category="+trade_category+" ";
			}
			if(keyword!=null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql+="AND t.trade_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql+="AND m.mem_id LIKE ?";
				else if(keyfield.equals("3")) sub_sql+="AND t.trade_content LIKE ?";
			}
			
			//SQL문 작성
			sql="SELECT a.*,NVL((SELECT COUNT(trade_num) FROM trade_like t "
					+ "WHERE t.trade_num = a.trade_num GROUP BY trade_num),0) "
					+ "AS like_count FROM (SELECT aa.*,rownum rnum FROM "
					+ "(SELECT * FROM trade t JOIN member m "
					+ "USING(mem_num) JOIN member_detail d USING(mem_num) "
					+ "WHERE 1=1 "+sub_sql
					+ " ORDER BY t.trade_num DESC)aa) A "
					+ "WHERE rnum>=? AND rnum<=?";
			
			//JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt=conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			if(keyword!=null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%"+keyword+"%");
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			//JDBC 수행 4단계
			rs=pstmt.executeQuery();
			
			list=new ArrayList<TradeVO>();
			
			while(rs.next()) {
				TradeVO trade = new TradeVO();
				trade.setTrade_num(rs.getInt("trade_num"));
				trade.setMem_num(rs.getInt("mem_num"));
				trade.setTrade_head(rs.getInt("trade_head"));
				trade.setTrade_category(rs.getString("trade_category"));
				trade.setTrade_title(rs.getString("trade_title"));
				trade.setTrade_date(rs.getDate("trade_date"));
				trade.setTrade_content(rs.getString("trade_content"));
				trade.setTrade_price(rs.getInt("trade_price"));
				trade.setTrade_image1(rs.getString("trade_image1"));
				trade.setTrade_image2(rs.getString("trade_image2"));
				trade.setTrade_image3(rs.getString("trade_image3"));
				trade.setTrade_count(rs.getInt("trade_count"));
				trade.setTrade_phone(rs.getString("trade_phone"));
				trade.setMem_id(rs.getString("mem_id"));
				trade.setMem_photo(rs.getString("mem_photo"));
				trade.setMem_name(rs.getString("mem_name"));
				trade.setLike_count(rs.getString("like_count"));
				
				list.add(trade);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	//글상세
	public TradeVO getTrade(int trade_num)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		TradeVO trade = null;
		String sql=null;
		
		try {
			//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션을 할당
			conn=DBUtil.getConnection();
			//SQL문 작성
			sql="SELECT * FROM trade t JOIN member m USING(mem_num) "
					+ "JOIN member_detail d USING(mem_num) "
					+ "WHERE t.trade_num=?";
			//JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt=conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, trade_num);
			//JDBC 수행 4단계
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				trade= new TradeVO();
				trade.setTrade_num(rs.getInt("trade_num"));
				trade.setMem_num(rs.getInt("mem_num"));
				trade.setTrade_head(rs.getInt("trade_head"));
				trade.setTrade_category(rs.getString("trade_category"));
				trade.setTrade_title(rs.getString("trade_title"));
				trade.setTrade_date(rs.getDate("trade_date"));
				trade.setTrade_content(rs.getString("trade_content"));
				trade.setTrade_price(rs.getInt("trade_price"));
				trade.setTrade_image1(rs.getString("trade_image1"));
				trade.setTrade_image2(rs.getString("trade_image2"));
				trade.setTrade_image3(rs.getString("trade_image3"));
				trade.setTrade_count(rs.getInt("trade_count"));
				trade.setTrade_phone(rs.getString("trade_phone"));
				trade.setMem_id(rs.getString("mem_id"));
				trade.setMem_name(rs.getString("mem_name"));
				trade.setMem_photo(rs.getString("mem_photo"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return trade;
	}
	//이전글 가져오기
	public int getPrev(int trade_num)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql=null;
		int prev=0;
		
		try {
			conn=DBUtil.getConnection();
			sql="SELECT t.* FROM (SELECT trade_num,LAG(trade_num) OVER(ORDER BY trade_num) AS prev FROM trade)t WHERE t.trade_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, trade_num);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				prev=rs.getInt("prev");
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return prev;
	}

	//다음글 가져오기
	public int getNext(int trade_num)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql=null;
		int next=0;
		
		try {
			conn=DBUtil.getConnection();
			sql="SELECT t.* FROM (SELECT trade_num,LEAD(trade_num) OVER(ORDER BY trade_num) AS next FROM trade)t WHERE t.trade_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, trade_num);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				next=rs.getInt("next");
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return next;
	}
	
	//조회수 증가
	public void updateReadcount(int trade_num)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		String sql=null;
		
		try {
			conn=DBUtil.getConnection();
			sql="UPDATE trade SET trade_count=trade_count+1 WHERE trade_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, trade_num);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}
	
	//파일 삭제
	public void deleteFile(int trade_num,String trade_image)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		String sql=null;
		
		try {
			conn=DBUtil.getConnection();
			
			sql="UPDATE trade SET "+trade_image+"='' WHERE trade_num=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, trade_num);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//글 수정
	public void updateTrade(TradeVO trade)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		String sql=null;
		String sub_sql="";
		int cnt=0;
		
		try {
			conn=DBUtil.getConnection();
			
			if(trade.getTrade_image1()!=null) {
				sub_sql+=",trade_image1=?";
			}
			if(trade.getTrade_image2()!=null) {
				sub_sql+=",trade_image2=?";
			}
			if(trade.getTrade_image3()!=null) {
				sub_sql+=",trade_image3=?";
			}
			
			sql="UPDATE trade SET trade_head=?,trade_category=?,trade_title=?,"
					+ "trade_content=?,trade_price=?"+sub_sql+",trade_phone=? "
							+ "WHERE trade_num=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(++cnt, trade.getTrade_head());
			pstmt.setString(++cnt, trade.getTrade_category());
			pstmt.setString(++cnt, trade.getTrade_title());
			pstmt.setString(++cnt, trade.getTrade_content());
			pstmt.setInt(++cnt, trade.getTrade_price());
			if(trade.getTrade_image1()!=null) {
				pstmt.setString(++cnt, trade.getTrade_image1());
			}
			if(trade.getTrade_image2()!=null) {
				pstmt.setString(++cnt, trade.getTrade_image2());
			}
			if(trade.getTrade_image3()!=null) {
				pstmt.setString(++cnt, trade.getTrade_image3());
			}
			pstmt.setString(++cnt, trade.getTrade_phone());
			pstmt.setInt(++cnt, trade.getTrade_num());
			
			pstmt.executeUpdate();
			
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}
	
	//글삭제
	public void deleteTrade(int trade_num)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		PreparedStatement pstmt2=null;
		String sql=null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn=DBUtil.getConnection();
			//오토커밋 해제
			conn.setAutoCommit(false);
			
			//좋아요 삭제
			sql="DELETE FROM trade_like WHERE trade_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, trade_num);
			pstmt.executeUpdate();
			
			//부모글 삭제
			sql="DELETE FROM trade WHERE trade_num=?";
			pstmt2=conn.prepareStatement(sql);
			pstmt2.setInt(1, trade_num);
			pstmt2.executeUpdate();
			
			//예외 발생이 없이 정상적으로 SQL문 실행
			conn.commit();
		}catch(Exception e) {
			//예외발생
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//찜하기 등록
	public void insertLike(int trade_num,int mem_num)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		String sql=null;
		
		try {
			conn=DBUtil.getConnection();
			
			sql="INSERT INTO trade_like (like_num,trade_num,mem_num) VALUES "
					+ "(trade_like_seq.nextval,?,?)";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, trade_num);
			pstmt.setInt(2, mem_num);
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//찜하기 개수
	public int selectLikeCount(int trade_num)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql=null;
		int count=0;
		
		try {
			conn=DBUtil.getConnection();
			
			sql="SELECT COUNT(*) FROM trade_like WHERE trade_num=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, trade_num);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				count=rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return count;
	}
	
	//회원번호와 게시물 번호를 이용한 찜하기 정보
	public TradeLikeVO selectLike(int trade_num,int mem_num)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		TradeLikeVO like=null;
		String sql=null;
		
		try {
			conn=DBUtil.getConnection();
			
			sql="SELECT * FROM trade_like WHERE trade_num=? AND mem_num=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, trade_num);
			pstmt.setInt(2, mem_num);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				like=new TradeLikeVO();
				like.setLike_num(rs.getInt("like_num"));
				like.setTrade_num(rs.getInt("trade_num"));
				like.setMem_num(rs.getInt("mem_num"));
			}
			
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return like;
	}
	
	//좋아요 삭제
	public void deleteLike(int like_num)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		String sql=null;
		
		try {
			conn=DBUtil.getConnection();
			
			sql="DELETE FROM trade_like WHERE like_num=?";
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, like_num);
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//내가 선택한 좋아요 목록
	public List<TradeVO> getListTradeLike(int start,int end,int mem_num)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<TradeVO> list=null;
		String sql=null;
		
		try {
			conn=DBUtil.getConnection();
			
			sql="SELECT * FROM (SELECT a.*,rownum rnum FROM (SELECT * FROM trade t JOIN "
					+ "member m USING(mem_num) JOIN trade_like l USING(trade_num) WHERE "
					+ "l.mem_num=? ORDER BY trade_num DESC)a) "
					+ "WHERE rnum>=? AND rnum<=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			
			rs=pstmt.executeQuery();
			
			list=new ArrayList<TradeVO>();
			
			while(rs.next()) {
				TradeVO trade=new TradeVO();
				trade.setTrade_num(rs.getInt("trade_num"));
				trade.setTrade_title(StringUtil.useNoHtml(rs.getString("trade_title")));
				trade.setTrade_date(rs.getDate("trade_date"));
				trade.setMem_id(rs.getString("mem_id"));
				
				list.add(trade);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
		
}
