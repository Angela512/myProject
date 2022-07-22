package dr.job.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dr.job.vo.JobVO;
import dr.util.DBUtil;
import dr.util.StringUtil;


public class JobDAO {
	// 싱글턴 패턴
	private static JobDAO instance = new JobDAO();

	public static JobDAO getInstance() {
		return instance;
	}

	private JobDAO() {
	}

	// 글등록
	public void insertJob(JobVO job) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			// JDBC 수행 1,2단계 : 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "INSERT INTO JOB (job_num,job_title,job_content,mem_num) VALUES (job_seq.nextval,?,?,?)";
			// JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setString(1, job.getJob_title());
			pstmt.setString(2, job.getJob_content());
			pstmt.setInt(3, job.getMem_num());

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
	public int getJobCount(String keyfield, String keyword) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;

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

			sql = "SELECT COUNT(*) FROM job j JOIN member m USING(mem_num) " + sub_sql;

			// JDBC 수행 3단계 : PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			if (keyword != null && !"".equals(keyword)) {
				pstmt.setString(1, "%" + keyword + "%");
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
	public List<JobVO> getListJob(int start, int end, String keyfield, String keyword) throws Exception {
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
					+ " ORDER BY job_num DESC)a) " + "WHERE rnum >= ? AND rnum <= ?";

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

				Job.setJob_date(rs.getDate("job_date"));

				Job.setJob_count(rs.getInt("job_count"));
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
					job.setJob_content(rs.getString("job_content"));
					job.setJob_count(rs.getInt("job_count"));
					job.setMem_num(rs.getInt("mem_num"));
					
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
	 
}
