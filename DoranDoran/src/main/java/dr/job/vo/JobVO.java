package dr.job.vo;

import java.sql.Date;

public class JobVO {
	private int job_num;
	private String job_title;
	private String job_content;
	private Date job_date;
	private int job_count;
	private String job_logo;
	private String job_enddate;
	private String job_addr1;
	private String job_addr2;
	private String job_category;
	private String job_link;
	private String job_zipcode;
	private int mem_num;
	
	private String mem_id;
	private String mem_photo;
	private String mem_name;
	
	
	public int getJob_num() {
		return job_num;
	}
	public void setJob_num(int job_num) {
		this.job_num = job_num;
	}
	public String getJob_title() {
		return job_title;
	}
	public void setJob_title(String job_title) {
		this.job_title = job_title;
	}
	public String getJob_content() {
		return job_content;
	}
	public void setJob_content(String job_content) {
		this.job_content = job_content;
	}
	public Date getJob_date() {
		return job_date;
	}
	public void setJob_date(Date job_date) {
		this.job_date = job_date;
	}
	public int getJob_count() {
		return job_count;
	}
	public void setJob_count(int job_count) {
		this.job_count = job_count;
	}
	public String getJob_logo() {
		return job_logo;
	}
	public void setJob_logo(String job_logo) {
		this.job_logo = job_logo;
	}
	public String getJob_enddate() {
		return job_enddate;
	}
	public void setJob_enddate(String job_enddate) {
		this.job_enddate = job_enddate;
	}
	public String getJob_addr1() {
		return job_addr1;
	}
	public void setJob_addr1(String job_addr1) {
		this.job_addr1 = job_addr1;
	}
	public String getJob_addr2() {
		return job_addr2;
	}
	public void setJob_addr2(String job_addr2) {
		this.job_addr2 = job_addr2;
	}
	public String getJob_category() {
		return job_category;
	}
	public void setJob_category(String job_category) {
		this.job_category = job_category;
	}
	public String getJob_link() {
		return job_link;
	}
	public void setJob_link(String job_link) {
		this.job_link = job_link;
	}
	public String getJob_zipcode() {
		return job_zipcode;
	}
	public void setJob_zipcode(String job_zipcode) {
		this.job_zipcode = job_zipcode;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getMem_photo() {
		return mem_photo;
	}
	public void setMem_photo(String mem_photo) {
		this.mem_photo = mem_photo;
	}
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}

}
