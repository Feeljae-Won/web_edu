package com.webjjang.qna.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.webjjang.board.vo.BoardVO;
import com.webjjang.main.controller.Init;
import com.webjjang.member.vo.LoginVO;
import com.webjjang.qna.vo.QnaVO;
import com.webjjang.util.exe.Execute;
import com.webjjang.util.page.PageObject;

// Board Module 에 맞는 메뉴 선택 , 데이터 수집(기능별), 예외 처리
public class QnaController {

	public String execute(HttpServletRequest request) {

		// session을 request에서 부터 꺼낸다.
		HttpSession session = request.getSession();
		
		// 로그인 정보 꺼내기
		LoginVO login = (LoginVO) session.getAttribute("login");
		
		String id = null;
		if (login != null) id = login.getId();
		
		// return jsp 정보 저장 변수
		String jsp = null;
		// 메뉴 입력 - uri - /board/list.do
		String uri = request.getRequestURI();
		
		Object result = null;
		
		// 입력 받는 데이터 선언
		Long no = 0L;
		
		try { // 정상 처리
		
			// 메뉴 처리 : CRUD DB 처리 - Controller - Service - DAO
			switch (uri) {
			case "/qna/list.do":
				// [QnaController] - (Execute) - QnaListService - QnaDAO.list()
				System.out.println("1. 질문답변 리스트");
				// 페이지 처리를 위한 객체
				// getInstance - 기본 값이 있고 넘어오는 페이지와 검색 정보를 세팅 처리
				PageObject pageObject = PageObject.getInstance(request);
				// DB 에서 데이터 가져오기 - 가져온 데이터는 LIST<BoardVO>
				result = Execute.execute(Init.get(uri), pageObject);
				
				// pageObject 데이터 확인
				System.out.println("QnaController.execute().pageObject : " + pageObject);
				
				// 가져온 데이터를 request에 저장 -> jsp 까지 전달 된다.
				request.setAttribute("list", result);
				// pageObject 담기
				request.setAttribute("pageObject", pageObject);
				// /WEB-INF/views/  + board/list  +  .jsp
				jsp = "qna/list";
				break;
				
				
			case "/qna/view.do":
				System.out.println("2. 질문답변 글보기");
				// 1. 일반게시판 글보기 데이터 가져오기 : 글보기, 수정할 때
				// 넘어 오는 글 번호와 조회수 수집 한다. (데이터는 request 안에 들어 있다.)
				String strNo = request.getParameter("no");
				no = Long.parseLong(strNo);
				
				// 전달 데이터 - 글번호
				result = Execute.execute(Init.get(uri), no);
				// 가져온 데이터를 JSP로 보내기 위해서 request에 담는다.
				request.setAttribute("vo", result);
				
				// 페이지 객체 - 수정, 리스트로 가기에서 페이지 정보가 필요
				pageObject = PageObject.getInstance(request);
				request.setAttribute("pageObject", pageObject);
				
				// pageObject 데이터 확인
				System.out.println("QnaController.execute().pageObject : " + pageObject);
				
				
				jsp = "qna/view";
				break;
			
			case "/qna/questionForm.do":
				System.out.println("3-1. 질문하기 폼으로 이동");
				
				request.setAttribute("headTitle", "질문하기");
				
				jsp = "qna/writeForm";
				break;
				
			case "/qna/answerForm.do":
				System.out.println("3-2. 답변하기 폼으로 이동");
				
				request.setAttribute("headTitle", "답변하기");
				// 넘어오는 글 번호에 따른 데이터를 가져와서 request애 저장한다.
				strNo = request.getParameter("no");
				no = Long.parseLong(strNo);
				
				// 전달 데이터 - 글번호
				result = Execute.execute(Init.get("/qna/view.do"), no);
				// 가져온 데이터를 JSP로 보내기 위해서 request에 담는다.
				request.setAttribute("vo", result);
				
				jsp = "qna/writeForm";
				break;
				
			case "/qna/write.do":
				System.out.println("3-3. 질문답변 등록 처리");
				
				// 사용자 입력 데이터 수집 - 사용자 -> 서버 : form - input - name
				String title = request.getParameter("title");
				String content = request.getParameter("content");
				
				// 글 데이터 수집
				// refNo, parentNo는 데이터가 없을 때 null이 들어오므로 parseLong해서 받으면 오류 발생
				String strRefNo = request.getParameter("refNo"); // 질문인 경우 refNo가 없음.
				Long ordNo = Long.parseLong(request.getParameter("ordNo"));
				Long levNo = Long.parseLong(request.getParameter("levNo"));
				String strParentNo = request.getParameter("parentNo");
				
				// 변수 - vo 저장하고 Service
				QnaVO vo = new QnaVO();
				vo.setTitle(title);
				vo.setContent(content);
				vo.setId(id);
				vo.setOrdNo(ordNo);
				vo.setLevNo(levNo);
				
				// 답변인 경우의 처리
				if (strRefNo != null && !strRefNo.equals("")) {
					vo.setRefNo(Long.parseLong(strRefNo));
					vo.setParentNo(Long.parseLong(strParentNo));
					vo.setQuestion(false); // 답변
				} else {
					vo.setQuestion(true); // 질문
				}
				
				// [QnaController] - QnaWriteService - QnaDAO.writer(vo)
				Execute.execute(Init.get(uri), vo);
				
//				// 페이지 정보 받기 & uri에 붙이기
//				pageObject = PageObject.getInstance(request);
//				System.out.println(pageObject);
				
				// jsp 정보 앞에 "redirect:"가 붙어 있으면 redirect를 
				// 아니면 jsp로 forword를 시킨다.
				jsp = "redirect:list.do?perPageNum=" + request.getParameter("perPageNum");
				
				// 처리 메시지
				session.setAttribute("msg", "글 등록이 성공적으로 처리 되었습니다.");
				
				break;
			
			case "/qna/updateForm.do":
				System.out.println("4-1. 질문답변 수정 폼으로 이동");
				// 사용자가 -> 서버 : 글번호 데이터 수집
				no = Long.parseLong(request.getParameter("no"));
				
				// no에 맞는 데이터 가져오기 - DB - QnaViewSerivce
				result = Execute.execute(Init.get("/qna/view.do"), no);
				
				// 가져온 데이터를 JSP로 보내기 위해서 request에 담는다.
				request.setAttribute("vo", result);
				
				// jsp 정보
				jsp = "qna/updateForm";
				break;
				
			case "/qna/update.do":
				System.out.println("4-2. 게시판 글수정 처리");
				
				// 수정할 글 번호를 받는다. - 데이터 수집
				strNo = request.getParameter("no");
				no = Long.parseLong(strNo);
				
				title = (String) request.getParameter("title");
				content = (String) request.getParameter("content");
				
				// 변수 - vo 저장하고 Service
				vo = new QnaVO();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContent(content);
				
				// [QnaController] - QnaWriteService - QnaDAO.update(vo)
				result = Execute.execute(Init.get(uri), vo);
				
				// 페이지 정보 받기 & uri에 붙이기
				pageObject = PageObject.getInstance(request);
				System.out.println(pageObject);
				// 글보기로 자동 이동하기
				jsp = "redirect:view.do?no=" + no + "&" + pageObject.getPageQuery(); 
				
				session.setAttribute("msg", "질문답변 수정이 성공적으로 처리 되었습니다.");
				
				break;
				
				
			case "/qna/delete.do":
				System.out.println("5.일반게시판 글삭제");
				
				// 데이터 수집 - DB에서 실행에 필요한 데이터 - 글 번호, 비밀번호 - BoardVO
				QnaVO deleteVO = new QnaVO();
				strNo = request.getParameter("no");
				no = Long.parseLong(strNo);
				deleteVO.setNo(no);
				deleteVO.setId(id);
				
				// DB 처리
				int result1 = (int) Execute.execute(Init.get(uri),deleteVO);
				
//				// 페이지 정보 받기 & uri에 붙이기
//				pageObject = PageObject.getInstance(request);
//				System.out.println(pageObject);
				
				jsp = "redirect:list.do" + "?perPageNum=" + request.getParameter("perPageNum");
				
				if(result1 == 1) {
					session.setAttribute("msg", "질문답변 삭제가 성공적으로 처리 되었습니다.");
				} else {
					session.setAttribute("msg", "질문답변 삭제가 처리되지 않았습니다. 글 번호를 다시 확인해 주세요.");
				}
				break;
				
			default:
				jsp = "error/404";
				break;
			} // end of switch
		} catch (Exception e) {
			 e.printStackTrace();
			
			// 예외 객체 jsp에서 사용하기 위해 request에 담는다.
			request.setAttribute("e", e);
			
			jsp = "error/500";
		} // end of try~catch
		return jsp;
	} // end of execute()
	
} // end of class
