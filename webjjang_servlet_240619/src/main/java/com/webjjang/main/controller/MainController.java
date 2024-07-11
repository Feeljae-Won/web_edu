package com.webjjang.main.controller;


import javax.servlet.http.HttpServletRequest;

import com.webjjang.util.exe.Execute;
import com.webjjang.util.page.PageObject;

// Board Module 에 맞는 메뉴 선택 , 데이터 수집(기능별), 예외 처리
public class MainController {

	public String execute(HttpServletRequest request) {

		// session을 request에서 부터 꺼낸다.
		
		// return jsp 정보 저장 변수
		String jsp = null;
		// 메뉴 입력 - uri - /board/list.do
		String uri = request.getRequestURI();
		
		Object result = null;
		
		try { // 정상 처리
		
			// 메뉴 처리 : CRUD DB 처리 - Controller - Service - DAO
			switch (uri) {
			case "/main/main.do":
				System.out.println("1. 메인 처리");
				
				
				// 페이지 처리를 위한 객체 생성
				PageObject pageObject = new PageObject();
				
				// 메인에 표시할 데이터 - 일반 게시판 / 이미지
				// DB 에서 데이터 가져오기
				// 일반 게시판의 데이터 가져오기
				pageObject.setPerPageNum(5);
				// [MainController] - (Execute) - BoardListService - BoardDAO.list()
				result = Execute.execute(Init.get("/board/list.do"), pageObject);
				// 가져온 데이터를 request에 저장 -> jsp 까지 전달 된다.
				request.setAttribute("boardList", result);
				
				// 공지사항 게시판의 데이터 가져오기
				pageObject.setPerPageNum(5);
				// [MainController] - (Execute) - BoardListService - BoardDAO.list()
				result = Execute.execute(Init.get("/notice/list.do"), pageObject);
				// 가져온 데이터를 request에 저장 -> jsp 까지 전달 된다.
				request.setAttribute("noticeList", result);
				
				// 이미지 게시판의 데이터 가져오기
				pageObject.setPerPageNum(6);
				// [MainController] - (Execute) - ImageListService - ImageDAO.list()
				result = Execute.execute(Init.get("/image/list.do"), pageObject);
				// 가져온 데이터를 request에 저장 -> jsp 까지 전달 된다.
				request.setAttribute("imageList", result);
				
				// /WEB-INF/views/  + main/main  +  .jsp
				jsp = "main/main";
				break;
				
			default:
				jsp = "error/404";
				break;
			} // end of switch
		} catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();
			
			// 예외 객체 jsp에서 사용하기 위해 request에 담는다.
			request.setAttribute("e", e);
			
			jsp = "error/500";
		} // end of try~catch
		return jsp;
	} // end of execute()
	
} // end of class
