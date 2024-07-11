package com.webjjang.ajax.controller;


import javax.servlet.http.HttpServletRequest;

import com.webjjang.main.controller.Init;
import com.webjjang.util.exe.Execute;

// Member Module 에 맞는 메뉴 선택 , 데이터 수집(기능별), 예외 처리
public class AjaxController {

	public String execute(HttpServletRequest request) {

		// 로그인 된 정보 중에서 id를 많이 사용한다.
		String id = null;
		// return jsp 정보 저장 변수
		String jsp = null;
		// 메뉴 입력 - uri - /member/list.do
		String uri = request.getRequestURI();
		
		try { // 정상 처리
		
			// 메뉴 처리 : CRUD DB 처리 - Controller - Service - DAO
			switch (uri) {
			
			case "/ajax/checkId.do":
				System.out.println("a. 아이디 중복 체크 처리");
				
				// 데이터 수집 - 사용자 -> 서버 : form - input - name
				// JSP 에서 사용자가 입력한 아이디
				id = request.getParameter("id");
				
				// [MemberController] - MemberCheckIdService - MemberDAO.checkId("id")
				// 리턴 타입이 Object라서 String으로 캐스팅 한다.
				// DB 서버에서 가져온 아이디 - 덮어쓰기
				id = (String) Execute.execute(Init.get(uri), id);
				
				// DB 에서 받아온 데이터를 request에 담는다.
				request.setAttribute("id", id);
				
				// jsp 정보 앞에 "redirect:"가 붙어 있으면 redirect를 
				// 아니면 jsp로 forword를 시킨다.
				jsp = "/member/checkId";
				break;
				
			default:
				jsp = "error/noModule_404";
				break;
			} // end of switch
		} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("e", e);
				jsp = "error/noModule_500";
		} // end of try~catch
		return jsp;
	} // end of execute()
	
} // end of class
