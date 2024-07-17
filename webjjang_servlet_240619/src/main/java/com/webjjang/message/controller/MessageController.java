package com.webjjang.message.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.webjjang.board.vo.BoardVO;
import com.webjjang.main.controller.Init;
import com.webjjang.member.vo.LoginVO;
import com.webjjang.message.vo.MessageVO;
import com.webjjang.util.exe.Execute;
import com.webjjang.util.page.PageObject;
import com.webjjang.util.page.ReplyPageObject;

// Board Module 에 맞는 메뉴 선택 , 데이터 수집(기능별), 예외 처리
public class MessageController {

	public String execute(HttpServletRequest request) {

		// session을 request에서 부터 꺼낸다.
		HttpSession session = request.getSession();
		LoginVO loginVO = (LoginVO) session.getAttribute("login");
		
		// 로그인이 되어 있어야 메세지 메뉴가 보이고 처리가 가능하다.
		String id = loginVO.getId();
		
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
			case "/message/list.do":
				// [BoardController] - (Execute) - BoardListService - BoardDAO.list()
				System.out.println("1.메세지 리스트");
				// 페이지 처리를 위한 객체
				// getInstance - 기본 값이 있고 넘어오는 페이지와 검색 정보를 세팅 처리
				PageObject pageObject = PageObject.getInstance(request);
				
				// message mode에 대한 정보 담기
				// 1 - 받은 메세지, 2 - 보낸 메세지, 3 - 전체 메세지
				String strMode = request.getParameter("mode");
				// 데이터가 넘어오는 경우의 처리. 기본은 1
				if(strMode != null && !strMode.equals(""))
					pageObject.setAcceptMode(Integer.parseInt(strMode));
				
				// message의 ID 세팅
				pageObject.setAccepter(id);
				
				// DB 에서 데이터 가져오기 - 가져온 데이터는 LIST<MessageVO>
				result = Execute.execute(Init.get(uri), pageObject);
				
				// pageObject 데이터 확인
				System.out.println("MessageController.execute().pageObject : " + pageObject);
				
				// 가져온 데이터를 request에 저장 -> jsp 까지 전달 된다.
				request.setAttribute("list", result);
				// pageObject 담기
				request.setAttribute("pageObject", pageObject);
				// /WEB-INF/views/  + board/list  +  .jsp
				jsp = "message/list";
				break;
				
				
			case "/board/view.do":
				System.out.println("2.일반게시판 글보기");
				// 1. 조회수 1증가(글보기), 2. 일반게시판 글보기 데이터 가져오기 : 글보기, 수정할 때
				// 넘어 오는 글 번호와 조회수 수집 한다. (데이터는 request 안에 들어 있다.)
				String strNo = request.getParameter("no");
				String strInc = request.getParameter("inc");
				String strRnum = request.getParameter("rnum");
				System.out.println("BoardController.view() rnum = " + strRnum);
				no = Long.parseLong(strNo);
				Long inc = Long.parseLong(strInc);
				Long rnum = Long.parseLong(strRnum);
				// 전달 데이터 - 글번호, 조회수 증가 여부(1 : 증가, 0: 증가 안함) : 배열 또는 Map
				result = Execute.execute(Init.get(uri), new Long[] {no, inc});
				
				// 가져온 데이터를 JSP로 보내기 위해서 request에 담는다.
				request.setAttribute("vo", result);
				
				// DB 에서 데이터 가져오기 - 가져온 데이터는 LIST<BoardVO>
				Object rnumResult = Execute.execute(Init.get("/board/rnumList.do"), rnum);
				request.setAttribute("rnumList", rnumResult);
				
				// 댓글 페이지 객체
				// 데이터 전달 - page / perPageNum / no / replyPage / replayPerPageNum
				ReplyPageObject replyPageObject = ReplyPageObject.getInstance(request);
				
				// 가져온 댓글 데이터 request에 담기
				request.setAttribute("replyList", Execute.execute(Init.get("/boardreply/list.do"), replyPageObject));
				// 댓글 페이지 객체 담기
				request.setAttribute("replyPageObject", replyPageObject);
				
				jsp = "board/view";
				break;
			
			// writeForm 은 리스트의 모달 창으로 대신 작성했다.
			case "/message/write.do":
				System.out.println("3-2. 메세지 등록 처리");
				
				// 데이터 수집 - 사용자 -> 서버 : form - input - name
				String accepterId = request.getParameter("accepterId");
				String content = request.getParameter("content");
				
				// 변수 - vo 저장하고 Service
				MessageVO vo = new MessageVO();
				vo.setAccepterId(accepterId);
				vo.setContent(content);
				vo.setSenderId(id); // 보내는 사람은 본인이다.
				
				// [BoardController] - BoardWriteService - BoardDAO.writer(vo)
				result = Execute.execute(Init.get(uri), vo);
				
				// jsp 정보 앞에 "redirect:"가 붙어 있으면 redirect를 
				// 아니면 jsp로 forword를 시킨다.
				jsp = "redirect:list.do?perPageNum=" + request.getParameter("perPageNum");
				
				// 처리 메시지
				session.setAttribute("msg", "메세지가 [" + accepterId + "] 님에게 전송되었습니다.");
				
				break;
			
			case "/board/updateForm.do":
				System.out.println("4-1. 글수정 폼으로 이동");
				// 사용자가 -> 서버 : 글번호 데이터 수집
				no = Long.parseLong(request.getParameter("no"));
				
				// no에 맞는 데이터 가져오기 - DB - BoardViewSerivce
				result = Execute.execute(Init.get("/board/view.do"), new Long[] {no, 0L});
				
				// 가져온 데이터를 JSP로 보내기 위해서 request에 담는다.
				request.setAttribute("vo", result);
				
				// jsp 정보
				jsp = "board/updateForm";
				break;
				
			case "/board/update.do":
				System.out.println("4-2. 게시판 글수정 처리");
				
				// 수정할 글 번호를 받는다. - 데이터 수집
				strNo = request.getParameter("no");
				no = Long.parseLong(strNo);
				
				content = (String) request.getParameter("content");
				
				// 변수 - vo 저장하고 Service
				vo = new MessageVO();
				vo.setNo(no);
				vo.setContent(content);
				
				// [BoardController] - BoardWriteService - BoardDAO.writer(vo)
				result = Execute.execute(Init.get(uri), vo);
				
				// 페이지 정보 받기 & uri에 붙이기
				pageObject = PageObject.getInstance(request);
				System.out.println(pageObject);
				// 글보기로 자동 이동하기
				jsp = "redirect:view.do?no=" + no + "&inc=0"
						+ "&" + pageObject.getPageQuery(); 
				
				session.setAttribute("msg", "글 수정이 성공적으로 처리 되었습니다.");
				
				break;
				
				
			case "/board/delete.do":
				System.out.println("5.일반게시판 글삭제");
				
				// 데이터 수집 - DB에서 실행에 필요한 데이터 - 글 번호, 비밀번호 - BoardVO
				BoardVO deleteVO = new BoardVO();
				strNo = request.getParameter("no");
				no = Long.parseLong(strNo);
				deleteVO.setNo(no);
				deleteVO.setPw(request.getParameter("pw"));
				
				// DB 처리
				int result1 = (int) Execute.execute(Init.get(uri),deleteVO);
				
//				// 페이지 정보 받기 & uri에 붙이기
//				pageObject = PageObject.getInstance(request);
//				System.out.println(pageObject);
				
				jsp = "redirect:list.do" + "?perPageNum=" + request.getParameter("perPageNum");
				
				if(result1 == 1) {
					session.setAttribute("msg", "글 삭제가 성공적으로 처리 되었습니다.");
				} else {
					session.setAttribute("msg", "글 삭제가 처리되지 않았습니다. [비밀번호가 틀립니다.]");
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
			request.setAttribute("e", e);
			
			jsp = "error/500";
		} // end of try~catch
		return jsp;
	} // end of execute()
	
} // end of class
