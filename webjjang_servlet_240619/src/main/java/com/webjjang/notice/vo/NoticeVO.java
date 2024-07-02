package com.webjjang.notice.vo;

public class NoticeVO {
	
	// private 변수 -> 마우스 우클릭 -> 제너레이터
	private Long no;
	private String title;
	private String content;
	private String writeDate;
	private String updateDate;
	private String startDate;
	private String endDate;
	
	// setter & getter 생성
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	// toString()
	@Override
	public String toString() {
		return "NoticeVO [no=" + no + ", title=" + title + ", content=" + content + ", writeDate=" + writeDate
				+ ", updateDate=" + updateDate + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	

}
