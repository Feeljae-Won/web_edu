package com.webjjang.message.vo;

import lombok.Data;

@Data
public class MessageVO {
	
	// private 변수 -> 마우스 우클릭 -> 제너레이터
	private Long no;
	private String content;
	private String senderId;
	private String senderName;
	private String senderPhoto;
	private String sendDate;
	private String accepterId;
	private String accepterName;
	private String accepterPhoto;
	private String acceptDate;
	private int allUser; // 기본 값 : 0 (개인 메시지), 1 (전체 메시지) 
	
	

}
