package com.webjjang.notice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.webjjang.main.controller.Init;
import com.webjjang.member.vo.LoginVO;
import com.webjjang.notice.vo.NoticeVO;
import com.webjjang.util.exe.Execute;
import com.webjjang.util.page.PageObject;

// Notice Module 에 맞는 메뉴 선택 , 데이터 수집(기능별), 예외 처리
public class NoticeController {

	public String execute(HttpServletRequest request) {
		
		// session을 request에서 부터 꺼낸다.
		// 처리 결과 화면에 표시하기 위한 session
		HttpSession session = request.getSession();
		
		// 로그인 정보 확인해서 계정에 gradeNo를 저장한다.
		int gradeNo = 0;
		LoginVO login = (LoginVO) session.getAttribute("login");
		if(login != null) {
			gradeNo = login.getGradeNo();
		}
		
		String jsp = null;
		String uri = request.getRequestURI();
		Object result = null;
		
		// 입력 받는 데이터 선언
		Long no = 0L;
		
		try { // 정상 처리
		
			// 메뉴 처리 : CRUD DB 처리 - Controller - Service - DAO
			switch (uri) {
			case "/notice/list.do":
				// [BoardController] - (Execute) - BoardListService - BoardDAO.list()
				System.out.println("1. 공지사항 리스트");
				// 페이지 처리를 위한 객체
				// getInstance - 기본 값이 있고 넘어오는 페이지와 검색 정보를 세팅 처리
				PageObject pageObject = PageObject.getInstance(request);
				
				String period = request.getParameter("period");
				
				if (gradeNo == 9) {
					if (period == null || period == "") {
						pageObject.setPeriod("all");
					} else {
						pageObject.setPeriod(period);
					}
				} else {
					pageObject.setPeriod("pre");
				}
				
				// DB 에서 데이터 가져오기 - 가져온 데이터는 LIST<BoardVO>
				result = Execute.execute(Init.get(uri), pageObject);
				
				// pageObject 데이터 확인
				System.out.println("NoticeController.execute().pageObject : " + pageObject);
				
				// 가져온 데이터를 request에 저장 -> jsp 까지 전달 된다.
				request.setAttribute("list", result);
				// pageObject 담기
				request.setAttribute("pageObject", pageObject);
				// /WEB-INF/views/  + board/list  +  .jsp
				jsp = "notice/list";
				break;
				
				
			case "/notice/view.do":
				System.out.println("2.공지사항 글보기");
				// 1. 조회수 1증가(글보기), 2. 일반게시판 글보기 데이터 가져오기 : 글보기, 수정할 때
				// 넘어 오는 글 번호와 조회수 수집 한다. (데이터는 request 안에 들어 있다.)
				no = Long.parseLong(request.getParameter("no"));
				// 전달 데이터 - 글번호, 조회수 증가 여부(1 : 증가, 0: 증가 안함) : 배열 또는 Map
				result = Execute.execute(Init.get(uri), no);
				
				// 가져온 데이터를 JSP로 보내기 위해서 request에 담는다.
				request.setAttribute("vo", result);
				
				// 댓글 페이지 객체
				// 데이터 전달 - page / perPageNum / no / replyPage / replayPerPageNum
//				ReplyPageObject replyPageObject = ReplyPageObject.getInstance(request);
				
				// 가져온 댓글 데이터 request에 담기
//				request.setAttribute("replyList", Execute.execute(Init.get("/boardreply/list.do"), replyPageObject));
				// 댓글 페이지 객체 담기
//				request.setAttribute("replyPageObject", replyPageObject);
				
				jsp = "notice/view";
				break;
				
				
			case "/notice/writeForm.do":
				System.out.println("3-1. 공지사항 등록 폼으로 이동");
				jsp = "notice/writeForm";
				break;
				
			case "/notice/write.do":
				System.out.println("3-2.공지사항 글 등록 처리");
				
				// 데이터 수집 - 사용자 -> 서버 : form - input - name
				String title = request.getParameter("title");
				String content = request.getParameter("content");
				String startDate = request.getParameter("startDate");
				String endDate = request.getParameter("endDate");
				
				// 변수 - vo 저장하고 Service
				NoticeVO vo = new NoticeVO();
				vo.setTitle(title);
				vo.setContent(content);
				vo.setStartDate(startDate);
				vo.setEndDate(endDate);
				
				// [noticeController] - NoticeWriteService - NoticeDAO.writer(vo)
				result = Execute.execute(Init.get(uri), vo);
				
				// 페이지 정보 받기 & uri에 붙이기
				pageObject = PageObject.getInstance(request);
				System.out.println(pageObject);
				
				// jsp 정보 앞에 "redirect:"가 붙어 있으면 redirect를 
				// 아니면 jsp로 forword를 시킨다.
				jsp = "redirect:list.do?perPageNum=" + request.getParameter("perPageNum");
				
				// 처리 메시지
				session.setAttribute("msg", "공지사항이 등록 되었습니다.");
				
				break;
				
				
			case "/notice/updateForm.do":
				System.out.println("4-1. 공지사항 수정 폼으로 이동");
				// 사용자가 -> 서버 : 글번호 데이터 수집
				no = Long.parseLong(request.getParameter("no"));
				
				// no에 맞는 데이터 가져오기 - DB - BoardViewSerivce
				result = Execute.execute(Init.get("/notice/view.do"), no);
				
				// 가져온 데이터를 JSP로 보내기 위해서 request에 담는다.
				request.setAttribute("vo", result);
				
				// jsp 정보
				jsp = "notice/updateForm";
				break;
				
			case "/notice/update.do":
				System.out.println("4-2. 게시판 글수정 처리");
				
				// 수정할 글 번호를 받는다. - 데이터 수집
				no = Long.parseLong(request.getParameter("no"));
				
				title = (String) request.getParameter("title");
				content = (String) request.getParameter("content");
				startDate = (String) request.getParameter("startDate");
				endDate = (String) request.getParameter("endDate");
				
				// 변수 - vo 저장하고 Service
				vo = new NoticeVO();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setStartDate(startDate);
				vo.setEndDate(endDate);
				
				// [NoticeController] - NoticeWriteService - BoardNoticeDAO.update(vo)
				Execute.execute(Init.get(uri), vo);
				
				// 페이지 정보 받기 & uri에 붙이기
				pageObject = PageObject.getInstance(request);
				System.out.println(pageObject);
				// 글보기로 자동 이동하기
				jsp = "redirect:view.do?no=" + no + "&" + pageObject.getPageQuery(); 
				
				session.setAttribute("msg", "공지사항이 수정 되었습니다.");
				
				break;
				
				
			case "/notice/delete.do":
				System.out.println("5.일반게시판 글삭제");
				
				// 데이터 수집 - DB에서 실행에 필요한 데이터 - 글 번호, 비밀번호 - NoticeVO
				NoticeVO deleteVO = new NoticeVO();
				no = Long.parseLong(request.getParameter("no"));
				deleteVO.setNo(no);
				
				// DB 처리
				int result1 = (int) Execute.execute(Init.get(uri),deleteVO);
				
//				// 페이지 정보 받기 & uri에 붙이기
//				pageObject = PageObject.getInstance(request);
//				System.out.println(pageObject);
				
				jsp = "redirect:list.do" + "?perPageNum=" + request.getParameter("perPageNum");
				
				if(result1 == 1) {
					session.setAttribute("msg", "공지사항이 삭제되었습니다.");
				} else {
					session.setAttribute("msg", "글 삭제가 처리되지 않았습니다. 글 번호가 맞지 않습니다.");
				}
				break;
				
			default:
				jsp = "error/404";
				break;
			} // end of switch
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			// 예외 객체 jsp에서 사용하기 위해 request에 담는다.
			request.setAttribute("e", e); // e(예외)를 jsp에 보내서 표시한다.
			
			jsp = "error/500";
		} // end of try~catch
		
		return jsp;
	} // end of main()
	
} // end of class
