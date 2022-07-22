package dr.board.vo;

import java.sql.Date;

public class BoardVO {

	private int mem_num;
	private int board_num;
	private String board_head;
	private String board_title;
	private String board_content;
	private Date board_date;
	private int board_count;
	private String board_image1;
	private String board_image2;
	private String board_image3;
	
	private String mem_id;
	private String mem_name;
	private String mem_photo;
	private Date mem_modify_date;
	
	public Date getMem_modify_date() {
		return mem_modify_date;
	}
	public void setMem_modify_date(Date mem_modify_date) {
		this.mem_modify_date = mem_modify_date;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public int getBoard_num() {
		return board_num;
	}
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	public String getBoard_head() {
		return board_head;
	}
	public void setBoard_head(String board_head) {
		this.board_head = board_head;
	}
	public String getBoard_title() {
		return board_title;
	}
	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}
	public String getBoard_content() {
		return board_content;
	}
	public void setBoard_content(String board_content) {
		this.board_content = board_content;
	}
	public Date getBoard_date() {
		return board_date;
	}
	public void setBoard_date(Date board_date) {
		this.board_date = board_date;
	}
	public int getBoard_count() {
		return board_count;
	}
	public void setBoard_count(int board_count) {
		this.board_count = board_count;
	}
	public String getBoard_image1() {
		return board_image1;
	}
	public void setBoard_image1(String board_image1) {
		this.board_image1 = board_image1;
	}
	public String getBoard_image2() {
		return board_image2;
	}
	public void setBoard_image2(String board_image2) {
		this.board_image2 = board_image2;
	}
	public String getBoard_image3() {
		return board_image3;
	}
	public void setBoard_image3(String board_image3) {
		this.board_image3 = board_image3;
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

}