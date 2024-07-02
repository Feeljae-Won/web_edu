package com.webjjang.image.vo;

public class ImageVO {
	
	// private 변수 -> 마우스 우클릭 -> 제너레이터
	private Long no;
	private String title;
	private String content;
	private String id;
	private String name; // member table
	private String writeDate;
	private String fileName; // 실제적으로 서버에 저장된 파일명
	private Long hit;
	
	
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getHit() {
		return hit;
	}
	public void setHit(Long hit) {
		this.hit = hit;
	}
	
	
	@Override
	public String toString() {
		return "ImageVO [no=" + no + ", title=" + title + ", content=" + content + ", id=" + id + ", name=" + name 
				+ ", writeDate=" + writeDate + ", fileName=" + fileName + ", hit=" + hit + "]";
	}
	
	

}
