package com.webjjang.util.io;

import java.util.List;
import com.webjjang.board.vo.BoardVO;

public class BoardPrint {
	
	// 1. 게시판 LIST 출력 메소드
	public void print(List<BoardVO> list) {
		System.out.println();
		System.out.println("<<<--------- 일반 게시판 리스트 ----------->>");
		System.out.println("+-----------------------------------------------------+");
		System.out.println("글 번호 /          제목            /  작성자  / 작성일  / 조회수");
		System.out.println("+-----------------------------------------------------+");
		if(list == null) { // 데이터가 없으면 null 이 된다.
			System.out.println("**** 데이터가 존재하지 않습니다. ****");
			
		}	else {
			for(BoardVO vo : list) {
				System.out.print(" " + vo.getNo());
				System.out.print(" / " + vo.getTitle());
				System.out.print(" / " + vo.getWriter());
				System.out.print(" / " + vo.getWriteDate());
				System.out.print(" / " + vo.getHit());
				System.out.println();
			} // end of for
		} // end of if
	}
	
	// 2. 게시판 VIEW 출력 메소드
	public void print(BoardVO vo) {
		System.out.println();
		System.out.println("<<<--------- 일반 게시판 글 상세보기 ----------->>");
		System.out.println("+------------------------------------------+");
		System.out.println("글 번호 : " + vo.getNo());
		System.out.println("글 제목 : " + vo.getTitle());
		System.out.println("글 내용 : " + vo.getContent());
		System.out.println("작성자 : " + vo.getWriter());
		System.out.println("작성일 : " + vo.getWriteDate());
		System.out.println("조회수 : " + vo.getHit());
		System.out.println("+------------------------------------------+");
		
	}

}
