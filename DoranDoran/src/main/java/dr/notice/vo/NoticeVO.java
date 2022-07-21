package dr.notice.vo;

import java.sql.Date;

public class NoticeVO {
	private int notice_num;
	private int mem_num;
	private String notice_title;
	private Date notice_date;
	private int notice_count;
	private String notice_content;
	private String notice_file1;
	private String notice_file2;
	private String notice_file3;
	
	private String mem_id;
	private String mem_name;
	private String mem_photo;
	
	
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getNotice_content() {
		return notice_content;
	}
	public void setNotice_content(String notice_content) {
		this.notice_content = notice_content;
	}	

	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public String getMem_photo() {
		return mem_photo;
	}
	public void setMem_photo(String mem_photo) {
		this.mem_photo = mem_photo;
	}
	public int getNotice_num() {
		return notice_num;
	}
	public void setNotice_num(int notice_num) {
		this.notice_num = notice_num;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public String getNotice_title() {
		return notice_title;
	}
	public void setNotice_title(String notice_title) {
		this.notice_title = notice_title;
	}
	public Date getNotice_date() {
		return notice_date;
	}
	public void setNotice_date(Date notice_date) {
		this.notice_date = notice_date;
	}
	public int getNotice_count() {
		return notice_count;
	}
	public void setNotice_count(int notice_count) {
		this.notice_count = notice_count;
	}
	
	public String getNotice_file1() {
		return notice_file1;
	}
	public void setNotice_file1(String notice_file1) {
		this.notice_file1 = notice_file1;
	}
	public String getNotice_file2() {
		return notice_file2;
	}
	public void setNotice_file2(String notice_file2) {
		this.notice_file2 = notice_file2;
	}
	public String getNotice_file3() {
		return notice_file3;
	}
	public void setNotice_file3(String notice_file3) {
		this.notice_file3 = notice_file3;
	}
}
