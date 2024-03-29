package dr.food.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dr.food.vo.FoodVO;
import dr.util.DBUtil;
import dr.util.StringUtil;


public class FoodDAO {
	//싱글턴 패턴
	private static FoodDAO instance = new FoodDAO();
	
	public static FoodDAO getInstance() {
		return instance;
	}
	private FoodDAO() {}
	
	//글등록
	public void insertFood(FoodVO food)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO food (food_num,food_name,"
				+ "food_content,food_image1,mem_num,food_phone1,food_phone2,food_phone3,food_addr1,food_addr2,food_local,food_zipcode,food_timeh1,food_timem1,food_timeh2,food_timem2,food_image2,food_image3,food_link,food_menu) VALUES ("
				+ "food_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			//JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, food.getFood_name());
			pstmt.setString(2, food.getFood_content());
			pstmt.setString(3, food.getFood_image1());
		//	pstmt.setString(4, board.getIp());
			pstmt.setInt(4, food.getMem_num());
			pstmt.setString(5, food.getFood_phone1());
			pstmt.setString(6, food.getFood_phone2());
			pstmt.setString(7, food.getFood_phone3());
			pstmt.setString(8, food.getFood_addr1());
			pstmt.setString(9, food.getFood_addr2());
			pstmt.setString(10, food.getFood_local());
			pstmt.setString(11, food.getFood_zipcode());
			
			pstmt.setString(12, food.getFood_timeh1());
			pstmt.setString(13, food.getFood_timem1());
			pstmt.setString(14, food.getFood_timeh2());
			pstmt.setString(15, food.getFood_timem2());
			
			pstmt.setString(16, food.getFood_image2());
			pstmt.setString(17, food.getFood_image3());
			
			pstmt.setString(18, food.getFood_link());
			
			pstmt.setString(19, food.getFood_menu());

			//JDBC 수행 4단계 : SQL문 실행
			pstmt.executeUpdate();
			
		}catch(Exception e){
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	
	//총 레코드 수(검색 레코드 수)
	public int getFoodCount(String keyfield,String keyword, String food_local)
	                               throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		
		try {
			//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if((keyword!=null && !"".equals(keyword)) && (food_local==null || "".equals(food_local))) {
				if(keyfield.equals("1")) sub_sql = "WHERE b.food_name LIKE ?";
				else if(keyfield.equals("2")) sub_sql = "WHERE m.mem_id LIKE ?";
				else if(keyfield.equals("3")) sub_sql = "WHERE b.food_content LIKE ?";
			}else if((keyword==null || "".equals(keyword)) && (food_local!=null && !"".equals(food_local))) {
				sub_sql = "WHERE b.food_local = ?";
			}else if((keyword!=null && !"".equals(keyword)) && (food_local!=null && !"".equals(food_local))) {
				if(keyfield.equals("1")) sub_sql = "WHERE b.food_name LIKE ? AND b.food_local = ?";
				else if(keyfield.equals("2")) sub_sql = "WHERE m.mem_id LIKE ? AND b.food_local = ?";
				else if(keyfield.equals("3")) sub_sql = "WHERE b.food_content LIKE ? AND b.food_local = ?";
			}
			
			sql = "SELECT COUNT(*) FROM food b JOIN member m USING(mem_num) " + sub_sql;
			
			//JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			if((keyword!=null && !"".equals(keyword)) && (food_local==null || "".equals(food_local))) {
				pstmt.setString(1, "%"+keyword+"%");
			}else if((keyword==null || "".equals(keyword)) && (food_local!=null && !"".equals(food_local))) {
				pstmt.setString(1, food_local);
			}else if((keyword!=null && !"".equals(keyword)) && (food_local!=null && !"".equals(food_local))) {
				pstmt.setString(1, "%"+keyword+"%");
				pstmt.setString(2, food_local);
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
	public List<FoodVO> getListFood(int start, int end,
			          String keyfield,String keyword,String food_local)
	                                   throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<FoodVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if((keyword!=null && !"".equals(keyword)) && (food_local==null || "".equals(food_local))) {
				if(keyfield.equals("1")) sub_sql = "WHERE b.food_name LIKE ?";
				else if(keyfield.equals("2")) sub_sql = "WHERE m.mem_id LIKE ?";
				else if(keyfield.equals("3")) sub_sql = "WHERE b.food_content LIKE ?";
			}else if((keyword==null || "".equals(keyword)) && (food_local!=null && !"".equals(food_local))) {
				sub_sql = "WHERE b.food_local = ?";
			}else if((keyword!=null && !"".equals(keyword)) && (food_local!=null && !"".equals(food_local))) {
				if(keyfield.equals("1")) sub_sql = "WHERE b.food_name LIKE ? AND b.food_local = ?";
				else if(keyfield.equals("2")) sub_sql = "WHERE m.mem_id LIKE ? AND b.food_local = ?";
				else if(keyfield.equals("3")) sub_sql = "WHERE b.food_content LIKE ? AND b.food_local = ?";
			}
			
			sql = "SELECT * FROM (SELECT a.*, rownum rnum "
				+ "FROM (SELECT * FROM food b JOIN member m "
				+ "USING (mem_num) JOIN member_detail d "
				+ "USING (mem_num) "+ sub_sql
				+ " ORDER BY b.food_num DESC)a) "
				+ "WHERE rnum >= ? AND rnum <= ?";
			
			//JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			if((keyword!=null && !"".equals(keyword)) && (food_local==null || "".equals(food_local))) {
				pstmt.setString(++cnt, "%"+keyword+"%");
			}else if((keyword==null || "".equals(keyword)) && (food_local!=null && !"".equals(food_local))) {
				pstmt.setString(++cnt, food_local);
			}else if((keyword!=null && !"".equals(keyword)) && (food_local!=null && !"".equals(food_local))) {
				pstmt.setString(++cnt, "%"+keyword+"%");
				pstmt.setString(++cnt, food_local);
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			//JDBC 수행 4단계
			rs = pstmt.executeQuery();
			list = new ArrayList<FoodVO>();
			while(rs.next()) {
				FoodVO food = new FoodVO();
				food.setFood_num(rs.getInt("food_num"));
				food.setMem_num(rs.getInt("mem_num"));
				food.setFood_name(StringUtil.useNoHtml(rs.getString("food_name")));
				food.setFood_phone1(rs.getString("food_phone1"));
				food.setFood_phone2(rs.getString("food_phone2"));
				food.setFood_phone3(rs.getString("food_phone3"));
				
				food.setFood_timeh1(rs.getString("food_timeh1"));
				food.setFood_timem1(rs.getString("food_timem1"));
				food.setFood_timeh2(rs.getString("food_timeh2"));
				food.setFood_timem2(rs.getString("food_timem2"));
				
				food.setFood_menu(rs.getString("food_menu"));
				food.setFood_link(rs.getString("food_link"));
				food.setFood_zipcode(rs.getString("food_zipcode"));
				food.setFood_addr1(rs.getString("food_addr1"));
				food.setFood_addr2(rs.getString("food_addr2"));

				food.setFood_image1(rs.getString("food_image1"));
				food.setFood_image2(rs.getString("food_image2"));
				food.setFood_image3(rs.getString("food_image3"));
				food.setFood_count(rs.getInt("food_count"));		
				food.setFood_content(rs.getString("food_content"));
				food.setFood_date(rs.getDate("food_date"));
				food.setFood_date_modi(rs.getDate("food_date_modi"));
				
				food.setMem_id(rs.getString("mem_id"));
				food.setMem_name(rs.getString("mem_name"));
				food.setMem_photo(rs.getString("mem_photo"));
				
				food.setFood_local(rs.getString("food_local"));
				
				list.add(food);				
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
	public FoodVO getFood(int food_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FoodVO food= null;
		String sql = null;
		
		try {
			//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM food b JOIN member m "
				+ "USING(mem_num) JOIN member_detail d "
				+ "USING(mem_num) WHERE b.food_num=?";
			
			//JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, food_num);
			//JDBC 수행 4단계
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				food = new FoodVO();
				food.setFood_num(rs.getInt("food_num"));
				food.setMem_num(rs.getInt("mem_num"));
				food.setFood_name(StringUtil.useNoHtml(rs.getString("food_name")));
				food.setFood_phone1(rs.getString("food_phone1"));
				food.setFood_phone2(rs.getString("food_phone2"));
				food.setFood_phone3(rs.getString("food_phone3"));
				
				food.setFood_timeh1(rs.getString("food_timeh1"));
				food.setFood_timem1(rs.getString("food_timem1"));
				food.setFood_timeh2(rs.getString("food_timeh2"));
				food.setFood_timem2(rs.getString("food_timem2"));
				
	
				food.setFood_menu(rs.getString("food_menu"));
				food.setFood_link(rs.getString("food_link"));
				food.setFood_zipcode(rs.getString("food_zipcode"));
				food.setFood_addr1(rs.getString("food_addr1"));
				food.setFood_addr2(rs.getString("food_addr2"));

				food.setFood_image1(rs.getString("food_image1"));
				food.setFood_image2(rs.getString("food_image2"));
				food.setFood_image3(rs.getString("food_image3"));
				food.setFood_count(rs.getInt("food_count"));		
				food.setFood_content(rs.getString("food_content"));
				food.setFood_date(rs.getDate("food_date"));
				food.setFood_date_modi(rs.getDate("food_date_modi"));
				
				food.setMem_id(rs.getString("mem_id"));
				food.setMem_name(rs.getString("mem_name"));
				food.setMem_photo(rs.getString("mem_photo"));
				food.setFood_local(rs.getString("food_local"));
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return food;
		
	}
	//조회수 증가
	public void updateReadcount(int food_num)
			                      throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성 
			sql = "UPDATE food SET food_count=food_count+1 WHERE food_num=?";
			
			//JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, food_num);
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
	public void deleteFile(int food_num,String food_image)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE food SET "+food_image+"='' WHERE food_num=?";
			
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, food_num);
			
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
	public void updateFood(FoodVO food)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if(food.getFood_image1()!=null) {
				//업로드한 파일이 있는 경우
				sub_sql += ",food_image1=?";
			}
			if(food.getFood_image2()!=null) {
				//업로드한 파일이 있는 경우
				sub_sql += ",food_image2=?";
			}
			if(food.getFood_image3()!=null) {
				//업로드한 파일이 있는 경우
				sub_sql += ",food_image3=?";
			}
			
			sql = "UPDATE food SET food_name=?,food_content=?,"
				+ "food_phone1=?,food_phone2=?,food_phone3=?,food_local=?,food_addr1=?,food_addr2=?,food_zipcode=?,food_timeh1=?,food_timem1=?,food_timeh2=?,food_timem2=?,food_link=?,food_menu=?,"
				+ "food_date_modi=SYSDATE " + sub_sql 
				+ "WHERE food_num=?";
				//+ ",ip=? WHERE board_num=?";
			
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(++cnt, food.getFood_name());
			pstmt.setString(++cnt, food.getFood_content());
			
			pstmt.setString(++cnt, food.getFood_phone1());
			pstmt.setString(++cnt, food.getFood_phone2());
			pstmt.setString(++cnt, food.getFood_phone3());
			pstmt.setString(++cnt, food.getFood_local());
			
			pstmt.setString(++cnt, food.getFood_addr1());
			pstmt.setString(++cnt, food.getFood_addr2());
			pstmt.setString(++cnt, food.getFood_zipcode());
			
			pstmt.setString(++cnt, food.getFood_timeh1());
			pstmt.setString(++cnt, food.getFood_timem1());
			pstmt.setString(++cnt, food.getFood_timeh2());
			pstmt.setString(++cnt, food.getFood_timem2());
			
			pstmt.setString(++cnt, food.getFood_link());
			
			pstmt.setString(++cnt, food.getFood_menu());
			
			if(food.getFood_image1()!=null) {
				pstmt.setString(++cnt, food.getFood_image1());
			}
			if(food.getFood_image2()!=null) {
				pstmt.setString(++cnt, food.getFood_image2());
			}
			if(food.getFood_image3()!=null) {
				pstmt.setString(++cnt, food.getFood_image3());
			}
			//pstmt.setString(++cnt, food.getIp());
			pstmt.setInt(++cnt, food.getFood_num());
			
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}
	
//글삭제
	public void deleteFood(int food_num)throws Exception{
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
			
			//부모글 삭제
			sql = "DELETE FROM food WHERE food_num=?";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, food_num);
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

}





