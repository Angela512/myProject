package dr.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dr.member.vo.MemberVO;
import dr.trade.vo.TradeVO;
import dr.util.DBUtil;

public class MemberDAO {
	//싱글턴 패턴
	private static MemberDAO instance = new MemberDAO();
	
	public static MemberDAO getInstance() {
		return instance;
	}
	private MemberDAO() {}
	
	//회원가입
	public void insertMember(MemberVO member)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		PreparedStatement pstmt2=null;
		PreparedStatement pstmt3=null;
		ResultSet rs = null;
		String sql=null;
		int num=0; //시퀀스 번호 저장
		
		try {
			//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션을 할당
			conn=DBUtil.getConnection();
			//오토 커밋 해제
			conn.setAutoCommit(false);
			
			//회원번호(mem_num) 생성
			sql="SELECT member_seq.nextval FROM dual";
			//JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt=conn.prepareStatement(sql);
			//JDBC 수행 4단계 : 
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				num=rs.getInt(1);
			}
			
			
			//zmember에 데이터 저장
			sql="INSERT INTO member (mem_num,mem_id) VALUES(?,?)";
			//JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt2=conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt2.setInt(1, num);
			pstmt2.setString(2, member.getMem_id());
			//JDBC 수행 4단계 : 
			pstmt2.executeUpdate();
			
			//zmember_detail에 데이터 저장
			sql="INSERT INTO member_detail (mem_num,mem_name,mem_pw,mem_phone,mem_email,mem_zipcode,mem_addr1,mem_addr2) "
					+ "VALUES (?,?,?,?,?,?,?,?)";
			//JDBC 수행 3단계 : 
			pstmt3=conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt3.setInt(1, num);
			pstmt3.setString(2, member.getMem_name());
			pstmt3.setString(3, member.getMem_pw());
			pstmt3.setString(4, member.getMem_phone());
			pstmt3.setString(5, member.getMem_email());
			pstmt3.setString(6, member.getMem_zipcode());
			pstmt3.setString(7, member.getMem_addr1());
			pstmt3.setString(8, member.getMem_addr2());
			//JDBC 수행 4단계 : 
			pstmt3.executeUpdate();
			
			//SQL실행시 모두 성공하면 commit
			conn.commit();
		}catch(Exception e) {
			//SQL문이 하나라도 실패하면 rollback
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	
	
	//ID 중복 체크 및 로그인 처리
	public MemberVO checkMember(String id)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		MemberVO member=null;
		String sql=null;
		
		try {
			//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션을 할당
			conn=DBUtil.getConnection();
			//SQL문작성
			sql="SELECT * FROM member m LEFT JOIN member_detail d ON m.mem_num = d.mem_num "
					+ "WHERE m.mem_id=?";
			//JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt=conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, id);
			//JDBC 수행 4단계
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				member=new MemberVO();
				member.setMem_num(rs.getInt("mem_num"));
				member.setMem_id(rs.getString("mem_id"));
				member.setAuth(rs.getInt("auth"));
				member.setMem_pw(rs.getString("mem_pw"));
				member.setMem_photo(rs.getString("mem_photo"));;
				member.setMem_email(rs.getString("mem_email"));
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return member;
	}
	
	//회원상세 정보
	public MemberVO getMember(int mem_num)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		MemberVO member=null;
		String sql=null;
		
		try {
			//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션 할당
			conn=DBUtil.getConnection();
			//SQL문 작성
			sql="SELECT * FROM member m JOIN member_detail d "
					+ "ON m.mem_num=d.mem_num WHERE m.mem_num=?";
			//JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt=conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, mem_num);
			//JDBC 수행 4단계
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				member=new MemberVO();
				member.setMem_num(rs.getInt("mem_num"));
				member.setMem_id(rs.getString("mem_id"));
				member.setAuth(rs.getInt("auth"));
				member.setMem_pw(rs.getString("mem_pw"));
				member.setMem_name(rs.getString("mem_name"));
				member.setMem_phone(rs.getString("mem_phone"));
				
				
				member.setMem_email(rs.getString("mem_email"));
				member.setMem_zipcode(rs.getString("mem_zipcode"));
				member.setMem_addr1(rs.getString("mem_addr1"));
				member.setMem_addr2(rs.getString("mem_addr2"));
				member.setMem_photo(rs.getString("mem_photo"));
				member.setMem_date(rs.getDate("mem_date"));
				member.setMem_modify_date(rs.getDate("mem_modify_date"));
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return member;
	}
	
	//회원정보 수정
	public void updateMember(MemberVO member)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		String sql=null;

		try {
			//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션 할당
			conn=DBUtil.getConnection();
			//SQL문 작성
			sql="UPDATE member_detail SET mem_name=?,mem_phone=?,mem_email=?,mem_zipcode=?,mem_addr1=?,"
					+ "mem_addr2=?,mem_modify_date=SYSDATE WHERE mem_num=?";
			//JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt=conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, member.getMem_name());
			pstmt.setString(2, member.getMem_phone());
			pstmt.setString(3, member.getMem_email());
			pstmt.setString(4, member.getMem_zipcode());
			pstmt.setString(5, member.getMem_addr1());
			pstmt.setString(6, member.getMem_addr2());
			pstmt.setInt(7, member.getMem_num());
			//JDBC 수행 4단계 : 
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//비밀번호 수정
	public void updatePassword(String mem_pw,int mem_num)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		String sql=null;
		
		try {
			//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션을 할당
			conn=DBUtil.getConnection();
			//SQL문 작성
			sql="UPDATE member_detail SET mem_pw=? WHERE mem_num=?";
			//JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt=conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, mem_pw);
			pstmt.setInt(2, mem_num);
			//JDBC 수행 4단계
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//프로필 사진 수정
public void updateMyPhoto(String mem_photo,int mem_num)throws Exception{
	      Connection conn=null;
	      PreparedStatement pstmt=null;
	      String sql=null;
	      
	      try {
	         //JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션을 할당
	         conn=DBUtil.getConnection();
	         //SQL문 작성
	         sql="UPDATE member_detail SET mem_photo=? WHERE mem_num=?";
	         //JDBC 수행 3단계 : PreparedStatement 객체 생성
	         pstmt=conn.prepareStatement(sql);
	         //?에 데이터 바인딩
	         pstmt.setString(1, mem_photo);
	         pstmt.setInt(2, mem_num);
	         //JDBC 수행 4단계
	         pstmt.executeUpdate();
	         
	      }catch(Exception e) {
	         throw new Exception(e);
	      }finally {
	         //자원정리
	         DBUtil.executeClose(null, pstmt, conn);
	      }
	   }

	
	//회원탈퇴(회원정보 삭제)
	public void deleteMember(int mem_num)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		PreparedStatement pstmt2=null;
		String sql=null;
		
		try {
			//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션을 할당
			conn=DBUtil.getConnection();
			
			//auto commit 해제
			conn.setAutoCommit(false);
			
			//member의 auth 값 변경
			//SQL문 작성
			sql="UPDATE member SET auth=0 WHERE mem_num=?";
			//JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt=conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, mem_num);
			//JDBC 수행 4단계
			pstmt.executeUpdate();
					
			//member_detail의 레코드 삭제
			//SQL문 작성
			sql="DELETE FROM member_detail WHERE mem_num=?";
			//JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt2=conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt2.setInt(1, mem_num);
			//JDBC 수행 4단계
			pstmt2.executeUpdate();
			
			//모든 SQL문의 실행이 성공하면 커밋
			conn.commit();
		}catch(Exception e) {
			//SQL문이 하나라도 실패하면 롤백
			conn.rollback();
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//관리자
	//전체글 개수(검색글 개수)
	public int getMemberCountByAdmin(String keyfield,String keyword)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql=null;
		String sub_sql="";
		int count=0;

		try {
			//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션을 할당
			conn=DBUtil.getConnection();

			if(keyword!=null && !"".equals(keyword)) {
				//검색 처리
				if(keyfield.equals("1")) sub_sql="WHERE mem_id LIKE ?";
				else if(keyfield.equals("2")) sub_sql="WHERE mem_name LIKE ?";
				else if(keyfield.equals("3")) sub_sql="WHERE mem_email LIKE ?";
			}

			sql="SELECT COUNT(*) FROM member LEFT JOIN member_detail USING (mem_num) "+sub_sql;

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
			//자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return count;
	}
	
	//목록(검색글 목록)
		public List<MemberVO> getListMemberByAdmin(int start,int end,String keyfield,String keyword)throws Exception{
			Connection conn=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			List<MemberVO> list=null;
			String sql=null;
			String sub_sql="";
			int cnt=0;
			
			try {
				//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션을 할당
				conn=DBUtil.getConnection();
				
				if(keyword!=null && !"".equals(keyword)) {
					//검색 처리
					if(keyfield.equals("1")) sub_sql="WHERE mem_id LIKE ?";
					else if(keyfield.equals("2")) sub_sql="WHERE mem_name LIKE ?";
					else if(keyfield.equals("3")) sub_sql="WHERE mem_email LIKE ?";
				}
				
				sql="SELECT * FROM (SELECT a.*, rownum rnum FROM "
						+ "(SELECT * FROM member m LEFT JOIN member_detail d "
						+ "USING(mem_num) "+sub_sql
						+" ORDER BY mem_num DESC NULLS LAST)a) "
						+ "WHERE rnum>=? AND rnum<=?";
				
				pstmt=conn.prepareStatement(sql);
				
				if(keyword!=null && !"".equals(keyword)) {
					pstmt.setString(++cnt, "%"+keyword+"%");
				}
				pstmt.setInt(++cnt, start);
				pstmt.setInt(++cnt, end);
				
				rs=pstmt.executeQuery();
				
				list=new ArrayList<MemberVO>();
				
				while(rs.next()) {
					MemberVO member = new MemberVO();
					member.setMem_num(rs.getInt("mem_num"));
					member.setMem_id(rs.getString("mem_id"));
					member.setAuth(rs.getInt("auth"));
					member.setMem_pw(rs.getString("mem_pw"));
					member.setMem_name(rs.getString("mem_name"));
					member.setMem_phone(rs.getString("mem_phone"));
					member.setMem_email(rs.getString("mem_email"));
					member.setMem_zipcode(rs.getString("mem_zipcode"));
					member.setMem_addr1(rs.getString("mem_addr1"));
					member.setMem_addr2(rs.getString("mem_addr2"));
					member.setMem_photo(rs.getString("mem_photo"));
					member.setMem_date(rs.getDate("mem_date"));
					member.setMem_modify_date(rs.getDate("mem_modify_date"));
					
					list.add(member);
				}
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return list;
		}
	
	//회원정보(등급) 수정
	public void updateMemberByAdmin(int auth,int mem_num)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		String sql=null;
		
		try {
			//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션을 할당
			conn=DBUtil.getConnection();
			//SQL문 작성
			sql="UPDATE member SET auth=? WHERE mem_num=?";
			//JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt=conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, auth);
			pstmt.setInt(2, mem_num);
			//JDBC 수행 4단계
			pstmt.executeUpdate();
			
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//내가 쓴 글 중고거래 게시물 수
	public int getMyTradeCount(String trade_head,int mem_num)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql=null;
		String sub_sql="";
		int count=0;
		
		try {
			conn=DBUtil.getConnection();
			
			if(trade_head!=null && !"".equals(trade_head)) {
				 sub_sql+="AND trade_head="+trade_head;
			}
			
			sql="SELECT COUNT(*) FROM trade WHERE mem_num=? "+sub_sql;
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			
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
	
	//내가 쓴 글 중고거래 게시물 목록
	public List<TradeVO> getMyListTrade(int start,int end,String trade_head,int mem_num)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<TradeVO> list=null;
		String sql=null;
		String sub_sql="";
		
		try {
			conn=DBUtil.getConnection();
			
			if(trade_head!=null && !"".equals(trade_head)) {
				 sub_sql+="AND trade_head="+trade_head;
			}
			
			sql="SELECT a.*,NVL((SELECT COUNT(trade_num) FROM trade_like t "
					+ "WHERE t.trade_num = a.trade_num GROUP BY trade_num),0) "
					+ "AS like_count FROM (SELECT aa.*,rownum rnum FROM "
					+ "(SELECT * FROM trade t JOIN member m "
					+ "USING(mem_num) JOIN member_detail d USING(mem_num) "
					+ "WHERE mem_num=? "+sub_sql
					+ " ORDER BY t.trade_num DESC)aa) A "
					+ "WHERE rnum>=? AND rnum<=?";
			
			pstmt=conn.prepareStatement(sql);
			
			
			//?에 데이터 바인딩
			pstmt.setInt(1, mem_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			
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
	
	//내 찜 목록 게시물 수
	public int getMyLikeTradeCount(String trade_head,int mem_num)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		String sql=null;
		String sub_sql="";
		int count=0;
		
		try {
			conn=DBUtil.getConnection();
			
			if(trade_head!=null && !"".equals(trade_head)) {
				 sub_sql+="AND trade_head="+trade_head;
			}
			
			sql="SELECT COUNT(*) FROM trade_like l JOIN trade t USING(trade_num) WHERE l.mem_num=? "+sub_sql;
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			
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
	
	//내 찜 목록 게시물 목록
	public List<TradeVO> getMyLikeList(int start,int end,String trade_head,int mem_num)throws Exception{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<TradeVO> list=null;
		String sql=null;
		String sub_sql="";
		
		try {
			conn=DBUtil.getConnection();
			
			if(trade_head!=null && !"".equals(trade_head)) {
				 sub_sql+="AND trade_head="+trade_head;
			}
			
			sql="SELECT a.*,NVL((SELECT COUNT(trade_num) FROM trade_like t "
					+ "WHERE t.trade_num = a.trade_num GROUP BY trade_num),0) AS like_count "
					+ "FROM (SELECT aa.*,rownum rnum FROM (SELECT * FROM "
					+ "trade t JOIN member m USING(mem_num) "
					+ "JOIN member_detail d USING(mem_num) "
					+ "JOIN trade_like l USING(trade_num) WHERE l.mem_num=? "+sub_sql
					+ " ORDER BY trade_num DESC)aa) A "
					+ "WHERE rnum>=? AND rnum<=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			
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
	
}
