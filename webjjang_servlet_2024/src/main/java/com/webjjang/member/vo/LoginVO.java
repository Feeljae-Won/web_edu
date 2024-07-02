package com.webjjang.member.vo;

public class LoginVO {
	
	private String id;
	private String pw;
	private String name;
	private String photo;
	private int gradeNo;
	private String gradeName;
	private Long newMsgCnt;
	

	private String birth;
	private String tel;
	private String status;
	
	private String regDate;
	private String conDate;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public int getGradeNo() {
		return gradeNo;
	}
	public void setGradeNo(int gradeNo) {
		this.gradeNo = gradeNo;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public Long getNewMsgCnt() {
		return newMsgCnt;
	}
	public void setNewMsgCnt(Long newMsgCnt) {
		this.newMsgCnt = newMsgCnt;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getConDate() {
		return conDate;
	}
	public void setConDate(String conDate) {
		this.conDate = conDate;
	}
	@Override
	public String toString() {
		return "LoginVO [id=" + id + ", pw=" + pw + ", name=" + name + ", photo=" + photo + ", gradeNo=" + gradeNo
				+ ", gradeName=" + gradeName + ", newMsgCnt=" + newMsgCnt + ", birth=" + birth + ", tel=" + tel
				+ ", status=" + status + ", regDate=" + regDate + ", conDate=" + conDate + "]";
	}
	
	
	
	
	
	
}
