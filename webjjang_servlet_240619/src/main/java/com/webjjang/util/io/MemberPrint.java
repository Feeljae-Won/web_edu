package com.webjjang.util.io;

import java.util.List;

import com.webjjang.main.controller.Main;
import com.webjjang.member.vo.MemberVO;

public class MemberPrint {
	
	// 1. 회원 LIST 출력 메소드
	public void print(List<MemberVO> list) {
		System.out.println();
		System.out.println("<<<------------------- 회원 리스트 ---------------------->>");
		System.out.println("+-----------------------------------------------------+");
		System.out.println(" ID  /  Name  /  Birth  /     Tel     /  Grade Name  /  Status");
		System.out.println("+-----------------------------------------------------+");
		if(list == null) { // 데이터가 없으면 null 이 된다.
			System.out.println("**** 데이터가 존재하지 않습니다. ****");
			
		}	else {
			for(MemberVO vo : list) {
				System.out.print(" " + vo.getId());
				System.out.print(" / " + vo.getName());
				System.out.print(" / " + vo.getBirth());
				System.out.print(" / " + vo.getTel());
				System.out.print(" / " + vo.getGradeName());
				System.out.print(" / " + vo.getStatus());
				System.out.println();
			} // end of for
		} // end of if
	}
	
	// 2. 회원 정보 상세보기 출력 메소드
	public void print(MemberVO vo) {
		System.out.println();
		System.out.println("<<<------------------- "
				+ ((Main.login.getGradeNo() == 9) ? "회원" : "내") + " 정보 ---------------------->>");
		System.out.println("+-----------------------------------------------------+");
		System.out.println("ID : " + vo.getId());
		System.out.println("PW : " + vo.getPw());
		System.out.println("Name : " + vo.getName());
		System.out.println("Birth : " + vo.getBirth());
		System.out.println("Tel : " + vo.getTel());
		System.out.println("E-mail : " + vo.getEmail());
		System.out.println("Grade Name : " + vo.getGradeName());
		System.out.println("Status : " + vo.getStatus());
		System.out.println("Registration : " + vo.getRegDate());
		System.out.println("Last Login : " + vo.getConDate());
		System.out.println("Photo : " + vo.getPhoto());
		System.out.println("+-----------------------------------------------------+");
		
	}

}
