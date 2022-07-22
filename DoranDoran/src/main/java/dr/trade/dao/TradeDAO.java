package dr.trade.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dr.trade.vo.TradeVO;
import dr.util.DBUtil;

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
			sql="SELECT * FROM (SELECT a.*,rownum rnum FROM (SELECT * FROM trade t JOIN member m "
					+ "USING(mem_num) JOIN member_detail d USING(mem_num) WHERE 1=1 "+sub_sql
					+ " ORDER BY t.trade_num DESC)a) "
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
