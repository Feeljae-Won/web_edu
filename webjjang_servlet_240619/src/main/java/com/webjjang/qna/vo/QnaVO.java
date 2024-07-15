package com.webjjang.qna.vo;

public class QnaVO {
	
	// private 변수 -> 마우스 우클릭 -> 제너레이터
	private Long no;
	private String title;
	private String content;
	private String id;
	private String name;
	private String writeDate;
	private Long refNo; // 관련 글 번호
	private Long ordNo; // 순서 번호
	private Long levNo; // 들여쓰기 글 번호
	private Long parentNo; // 부모 글 번호
	private boolean isQuestion; // 질문글 확인
	
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
		return id;
	}
	public void setName(String name) {
		this.id = name;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	public Long getRefNo() {
		return refNo;
	}
	public void setRefNo(Long refNo) {
		this.refNo = refNo;
	}
	public Long getOrdNo() {
		return ordNo;
	}
	public void setOrdNo(Long ordNo) {
		this.ordNo = ordNo;
	}
	public Long getLevNo() {
		return levNo;
	}
	public void setLevNo(Long levNo) {
		this.levNo = levNo;
	}
	public Long getParentNo() {
		return parentNo;
	}
	public void setParentNo(Long parentNo) {
		this.parentNo = parentNo;
	}
	public boolean isQuestion() { // boolean type의 getter는 앞에 get 대신 is가 붙는다.
		return isQuestion;
	}
	public void setQuestion(boolean isQuestion) {
		this.isQuestion = isQuestion;
	}
	
	
	@Override
	public String toString() {
		return "QnaVO [no=" + no + ", title=" + title + ", content=" + content + ", id=" + id + ", name=" + name
				+ ", writeDate=" + writeDate + ", refNo=" + refNo + ", ordNo=" + ordNo + ", levNo=" + levNo
				+ ", parentNo=" + parentNo + ", isQuestion=" + isQuestion + "]";
	}
	

	

}
