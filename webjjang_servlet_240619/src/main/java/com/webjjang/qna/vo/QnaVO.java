package com.webjjang.qna.vo;

import lombok.Data;

@Data
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
	

}
