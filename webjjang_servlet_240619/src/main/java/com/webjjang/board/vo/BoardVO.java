package com.webjjang.board.vo;

public class BoardVO {
	
	// private 변수 -> 마우스 우클릭 -> 제너레이터
	private Long no;
	private String title;
	private String content;
	private String writer;
	private String writeDate;
	private Long hit;
	private String pw;
	private Long rnum;
	
	// setter, getter
	public Long getNo() {return no;}
	public void setNo(Long no) {this.no = no;}
	
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}
	
	public String getContent() {return content;}
	public void setContent(String content) {this.content = content;}
	
	public String getWriter() {return writer;}
	public void setWriter(String writer) {this.writer = writer;}
	
	public String getWriteDate() {return writeDate;}
	public void setWriteDate(String writeDate) {this.writeDate = writeDate;}
	
	public Long getHit() {return hit;}
	public void setHit(Long hit) {this.hit = hit;}
	
	public String getPw() {return pw;}
	public void setPw(String pw) {this.pw = pw;}
	
	public Long getRnum() {return rnum;}
	public void setRnum(Long rnum) {this.rnum = rnum;}
	
	// toString - 데이터 확인용 -> 마우스 우클릭 -> 제너레이터
	@Override
	public String toString() {
		return "BoardVO [no=" + no + ", title=" + title + ", content=" + content + ", writer=" + writer + ", writeDate="
				+ writeDate + ", hit=" + hit + ", pw=" + pw + ", rnum=" + rnum + "]";
	}

}
