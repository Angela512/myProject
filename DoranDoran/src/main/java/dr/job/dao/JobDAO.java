package dr.job.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import dr.job.vo.JobVO;
import dr.util.DBUtil;
import dr.util.StringUtil;


public class JobDAO {
	// 싱글턴 패턴
	private static JobDAO instance = new JobDAO();

	public static JobDAO getInstance() {
		return instance;
	}

	private JobDAO() {}
	
	// 글등록
	public void insertJob(JobVO job) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			// JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "INSERT INTO JOB (job_num,job_title,job_category,job_content,job_logo,job_link,job_zipcode,job_addr1,job_addr2,job_enddate,mem_num) VALUES (job_seq.nextval,?,?,?,?,?,?,?,?,?,?)";
			// JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setString(1, job.getJob_title());
			pstmt.setString(2, job.getJob_category());
			pstmt.setString(3, job.getJob_content());
			pstmt.setString(4, job.getJob_logo());
			pstmt.setString(5, job.getJob_link());
			pstmt.setString(6, job.getJob_zipcode());
			pstmt.setString(7, job.getJob_addr1());
			pstmt.setString(8, job.getJob_addr2());
			pstmt.setString(9, job.getJob_enddate());
			pstmt.setInt(10, job.getMem_num());

			// JDBC 수행 4단계 : SQL문 실행
			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 총 레코드 수(검색 레코드 수)
	public int getJobCount(String keyfield, String keyword, String job_category) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;

		try {
			// JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();

			if((keyword!=null && !"".equals(keyword)) && (job_category==null || "".equals(job_category))) {
				if(keyfield.equals("1")) sub_sql = "WHERE j.job_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql = "WHERE m.mem_num LIKE ?";
				else if(keyfield.equals("3")) sub_sql = "WHERE j.job_content LIKE ?";
			}else if((keyword==null || "".equals(keyword)) &&  (job_category!=null && !"".equals(job_category))) {
				sub_sql = "WHERE j.job_category = ?";
			}else if((keyword!=null && !"".equals(keyword)) &&  (job_category!=null && !"".equals(job_category))) {
				if(keyfield.equals("1")) sub_sql = "WHERE j.job_title LIKE ? AND j.job_category = ?";
				else if(keyfield.equals("2")) sub_sql = "WHERE m.mem_num LIKE ? AND j.job_category = ?";
				else if(keyfield.equals("3")) sub_sql = "WHERE j.job_content LIKE ? AND j.job_category = ?";
			}
			
			sql = "SELECT COUNT(*) FROM job j JOIN member m USING(mem_num) " + sub_sql;

			// JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
		
			if((keyword!=null && !"".equals(keyword)) && (job_category==null || "".equals(job_category))) {
				pstmt.setString(1, "%" + keyword + "%");
			}else if((keyword==null || "".equals(keyword)) &&  (job_category!=null && !"".equals(job_category))) {
				pstmt.setString(1, job_category);
			}else if((keyword!=null && !"".equals(keyword)) &&  (job_category!=null && !"".equals(job_category))) {
				pstmt.setString(1, "%" + keyword + "%");
				pstmt.setString(2, job_category);
			}

			// JDBC 수행 4단계
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	

	// 글목록(검색글 목록)
	public List<JobVO> getListJob(int start, int end, String keyfield, String keyword, String job_category) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<JobVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;

		try {
			// JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();

			if((keyword!=null && !"".equals(keyword)) && (job_category==null || "".equals(job_category))) {
				if(keyfield.equals("1")) sub_sql = "WHERE j.job_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql = "WHERE m.mem_num LIKE ?";
				else if(keyfield.equals("3")) sub_sql = "WHERE j.job_content LIKE ?";
			}else if((keyword==null || "".equals(keyword)) &&  (job_category!=null && !"".equals(job_category))) {
				sub_sql = "WHERE j.job_category =? ";
			}else if((keyword!=null && !"".equals(keyword)) &&  (job_category!=null && !"".equals(job_category))) {
				if(keyfield.equals("1")) sub_sql = "WHERE j.job_title LIKE ? AND j.job_category = ?";
				else if(keyfield.equals("2")) sub_sql = "WHERE m.mem_num LIKE ? AND j.job_category = ?";
				else if(keyfield.equals("3")) sub_sql = "WHERE j.job_content LIKE ? AND j.job_category = ?";
			}

			sql = "SELECT * FROM (SELECT a.*, rownum rnum " + "FROM (SELECT * FROM job j JOIN member m "
					+ "USING (mem_num) JOIN member_detail d " + "USING (mem_num) " + sub_sql
					+ " ORDER BY j.job_num DESC)a) " + "WHERE rnum >= ? AND rnum <= ?";

			// JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			if((keyword!=null && !"".equals(keyword)) && (job_category==null || "".equals(job_category))) {
				pstmt.setString(++cnt, "%" + keyword + "%");
			}else if((keyword==null || "".equals(keyword)) &&  (job_category!=null && !"".equals(job_category))) {
				pstmt.setString(++cnt, job_category);
			}else if((keyword!=null && !"".equals(keyword)) &&  (job_category!=null && !"".equals(job_category))) {
				pstmt.setString(++cnt, "%" + keyword + "%");
				pstmt.setString(++cnt, job_category);
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);

			// JDBC 수행 4단계
			rs = pstmt.executeQuery();
			list = new ArrayList<JobVO>();
			while (rs.next()) {
				JobVO Job = new JobVO();
				
				Job.setJob_num(rs.getInt("job_num"));
				Job.setJob_title(StringUtil.useNoHtml(rs.getString("job_title")));
				Job.setJob_content(rs.getString("job_content"));
				Job.setJob_date(rs.getDate("job_date"));
				Job.setJob_count(rs.getInt("job_count"));
				Job.setJob_logo(rs.getString("job_logo"));
				Job.setJob_enddate(rs.getString("job_enddate"));
				Job.setJob_addr1(rs.getString("job_addr1"));
				Job.setJob_addr2(rs.getString("job_addr2"));
				Job.setJob_category(rs.getString("job_category"));
				Job.setJob_link(rs.getString("job_link"));
				Job.setJob_zipcode(rs.getString("job_zipcode"));
				Job.setMem_num(rs.getInt("mem_num"));
				
				Job.setMem_id(rs.getString("mem_id"));
				Job.setMem_photo(rs.getString("mem_photo"));
				Job.setMem_name(rs.getString("mem_name"));


				list.add(Job);
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			// 자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	// 카테고리
		public List<JobVO> CategoryJob(int start, int end, String keyfield, String keyword) throws Exception {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<JobVO> list = null;
			String sql = null;
			String sub_sql = "";
			int cnt = 0;

			try {
				// JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();

				if (keyword != null && !"".equals(keyword)) {
					if (keyfield.equals("1"))
						sub_sql = "WHERE j.job_title LIKE ?";
					else if (keyfield.equals("2"))
						sub_sql = "WHERE m.mem_num LIKE ?";
					else if (keyfield.equals("3"))
						sub_sql = "WHERE j.job_content LIKE ?";
				}

				sql = "SELECT * FROM (SELECT a.*, rownum rnum " + "FROM (SELECT * FROM job j JOIN member m "
						+ "USING (mem_num) JOIN member_detail d " + "USING (mem_num) " + sub_sql
						+ " ORDER BY j.job_num DESC)a) " + "WHERE rnum >= ? AND rnum <= ?";

				// JDBC 수행 3단계 : PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				// ?에 데이터 바인딩
				if (keyword != null && !"".equals(keyword)) {
					pstmt.setString(++cnt, "%" + keyword + "%");
				}
				pstmt.setInt(++cnt, start);
				pstmt.setInt(++cnt, end);

				// JDBC 수행 4단계
				rs = pstmt.executeQuery();
				list = new ArrayList<JobVO>();
				while (rs.next()) {
					JobVO Job = new JobVO();
					
					Job.setJob_num(rs.getInt("job_num"));
					Job.setJob_title(StringUtil.useNoHtml(rs.getString("job_title")));
					Job.setJob_content(rs.getString("job_content"));
					Job.setJob_date(rs.getDate("job_date"));
					Job.setJob_count(rs.getInt("job_count"));
					Job.setJob_logo(rs.getString("job_logo"));
					Job.setJob_enddate(rs.getString("job_enddate"));
					Job.setJob_addr1(rs.getString("job_addr1"));
					Job.setJob_addr2(rs.getString("job_addr2"));
					Job.setJob_category(rs.getString("job_category"));
					Job.setJob_link(rs.getString("job_link"));
					Job.setJob_zipcode(rs.getString("job_zipcode"));
					Job.setMem_num(rs.getInt("mem_num"));

					list.add(Job);
				}
			} catch (Exception e) {
				throw new Exception(e);
			} finally {
				// 자원정리
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return list;
		}
	
	//글상세
		public JobVO getJob(int job_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			JobVO job = null;
			String sql = null;
			
			try {
				//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT * FROM job j JOIN member m "
					+ "USING(mem_num) JOIN member_detail d "
					+ "USING(mem_num) WHERE j.job_num=?";
				
				//JDBC 수행 3단계 : PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, job_num);
				//JDBC 수행 4단계
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					job = new JobVO();
					job.setJob_num(rs.getInt("job_num"));
					job.setJob_title(rs.getString("job_title"));
					job.setJob_category(rs.getString("job_category"));
					job.setJob_content(rs.getString("job_content"));
					job.setJob_count(rs.getInt("job_count"));
					job.setJob_link(rs.getString("job_link"));
					job.setJob_logo(rs.getString("job_logo"));
					job.setJob_zipcode(rs.getString("job_zipcode"));
					job.setJob_addr1(rs.getString("job_addr1"));
					job.setJob_addr2(rs.getString("job_addr2"));
					job.setJob_enddate(rs.getString("job_enddate"));
					job.setJob_date(rs.getDate("job_date"));


					job.setMem_num(rs.getInt("mem_num"));
					
					job.setMem_id(rs.getString("mem_id"));
					job.setMem_photo(rs.getString("mem_photo"));
					job.setMem_name(rs.getString("mem_name"));
					
					/*
					job.setReg_date(rs.getDate("reg_date"));
					job.setModify_date(rs.getDate("modify_date"));
					job.setFilename(rs.getString("filename"));
					job.setId(rs.getString("id"));
					job.setPhoto(rs.getString("photo"));
					*/
				}
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return job;
			
		}
		
		//조회수 증가
		public void updateReadcount(int job_num)
				                      throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				//JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성 
				sql = "UPDATE job SET job_count=job_count+1 WHERE job_num=?";
				
				//JDBC 수행 3단계 : PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, job_num);
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
		public void deleteFile(int job_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "UPDATE job SET job_logo='' WHERE job_num=?";
				
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, job_num);
				
				//SQL문 실행
				pstmt.executeUpdate();
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		
		
		//글 수정
		public void updateJob(JobVO job)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			String sub_sql = "";
			int cnt = 0;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				
				sql = "UPDATE job SET job_title=?,job_category=?,job_content=?,job_link=?,job_logo=?,job_zipcode=?,job_addr1=?,job_addr2=?,job_enddate=?, job_date=SYSDATE"+ sub_sql+" WHERE job_num=?";

				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setString(++cnt, job.getJob_title());
				pstmt.setString(++cnt, job.getJob_category());
				pstmt.setString(++cnt, job.getJob_content());
				pstmt.setString(++cnt, job.getJob_link());
				pstmt.setString(++cnt, job.getJob_logo());
				pstmt.setString(++cnt, job.getJob_zipcode());
				pstmt.setString(++cnt, job.getJob_addr1());
				pstmt.setString(++cnt, job.getJob_addr2());
				pstmt.setString(++cnt, job.getJob_enddate());
				pstmt.setInt(++cnt, job.getJob_num());
				
				
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
		public void deleteJob(int job_num)throws Exception{
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
				
				//부모글 삭제
				sql = "DELETE FROM job WHERE job_num=?";
				pstmt3 = conn.prepareStatement(sql);
				pstmt3.setInt(1, job_num);
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
