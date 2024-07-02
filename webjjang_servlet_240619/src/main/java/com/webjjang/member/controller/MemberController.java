package com.webjjang.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.webjjang.board.service.BoardDeleteService;
import com.webjjang.board.service.BoardListService;
import com.webjjang.board.service.BoardUpdateService;
import com.webjjang.board.service.BoardViewService;
import com.webjjang.board.service.BoardWriteService;
import com.webjjang.board.vo.BoardVO;
import com.webjjang.main.controller.Init;
import com.webjjang.member.vo.LoginVO;
import com.webjjang.util.exe.Execute;
import com.webjjang.util.io.BoardPrint;
import com.webjjang.util.io.In;
import com.webjjang.util.page.PageObject;
import com.webjjang.util.page.ReplyPageObject;

// Member Module 에 맞는 메뉴 선택 , 데이터 수집(기능별), 예외 처리
public class MemberController {

	public String execute(HttpServletRequest request) {

		// 로그인 처리를 session으로 한다.
		// session을 request에서 부터 꺼낸다.
		HttpSession session = request.getSession();
		
		// 로그인 된 정보 중에서 id를 많이 사용한다.
		String id = null;
		LoginVO login = (LoginVO) session.getAttribute("login");
		// 로그인이 된 경우에만 id 정보를 가져온다.
		if(login != null) id = login.getId();
		
		// return jsp 정보 저장 변수
		String jsp = null;
		// 메뉴 입력 - uri - /member/list.do
		String uri = request.getRequestURI();
		
		Object result = null;
		
		// 입력 받는 데이터 선언
		Long no = 0L;
		
		try { // 정상 처리
		
			// 메뉴 처리 : CRUD DB 처리 - Controller - Service - DAO
			switch (uri) {
			case "/member/loginForm.do":
				System.out.println("a. 로그인 폼으로 이동");
				jsp = "member/loginForm";
				break;
				
			case "/member/login.do":
				System.out.println("a-1.로그인 처리");
				
				// 데이터 수집 - 사용자 -> 서버 : form - input - name
				id = request.getParameter("id");
				String pw = request.getParameter("pw");
				
				// 변수 - vo 저장하고 Service
				LoginVO loginVO = new LoginVO();
				loginVO.setId(id);
				loginVO.setPw(pw);
				
				// [MemberController] - MemberLoginService - MemberDAO.login(vo)
				// session에 데이터를 담아서 로그인 처리 한다.
				session.setAttribute("login", Execute.execute(Init.get(uri), loginVO));
				
//				// 페이지 정보 받기 & uri에 붙이기
//				pageObject = PageObject.getInstance(request);
//				System.out.println(pageObject);
				
				// jsp 정보 앞에 "redirect:"가 붙어 있으면 redirect를 
				// 아니면 jsp로 forword를 시킨다.
				// 원래는 main이나 진행하려는 uri로 이동
				// 완성이 안되어 있어서 완성되어진 게시판 리스트로 보낸다.
				jsp = "redirect:/board/list.do";
				
				// 로그인 완료 메시지 처리
				session.setAttribute("msg", "로그인이 정상 처리 되었습니다.");
				
				break;
				
			case "/member/logout.do":
				System.out.println("b. 로그 아웃 처리");
				// session의 로그인 내용 지우기 - 로그 아웃 처리
				session.removeAttribute("login");
				
				session.setAttribute("msg", "로그아웃이 정상 처리 되었습니다.");
				
				jsp = "redirect:/board/list.do";
				break;
			
			
			case "/member/list.do":
				// [MemberController] - (Execute) - MemberListService - BoardDAO.list()
				System.out.println("1.일반게시판 리스트");
				// 페이지 처리를 위한 객체
				// getInstance - 기본 값이 있고 넘어오는 페이지와 검색 정보를 세팅 처리
				PageObject pageObject = PageObject.getInstance(request);
				// DB 에서 데이터 가져오기 - 가져온 데이터는 LIST<BoardVO>
				result = Execute.execute(Init.get(uri), pageObject);
				
				// pageObject 데이터 확인
				System.out.println("BoardController.execute().pageObject : " + pageObject);
				
				// 가져온 데이터를 request에 저장 -> jsp 까지 전달 된다.
				request.setAttribute("list", result);
				// pageObject 담기
				request.setAttribute("pageObject", pageObject);
				// /WEB-INF/views/  + board/list  +  .jsp
				jsp = "board/list";
				break;
				
				
			case "/member/view.do":
				System.out.println("2.일반게시판 글보기");
				// 1. 조회수 1증가(글보기), 2. 일반게시판 글보기 데이터 가져오기 : 글보기, 수정할 때
				// 넘어 오는 글 번호와 조회수 수집 한다. (데이터는 request 안에 들어 있다.)
				String strNo = request.getParameter("no");
				String strInc = request.getParameter("inc");
				no = Long.parseLong(strNo);
				Long inc = Long.parseLong(strInc);
				// 전달 데이터 - 글번호, 조회수 증가 여부(1 : 증가, 0: 증가 안함) : 배열 또는 Map
				result = Execute.execute(Init.get(uri), new Long[] {no, inc});
				
				// 가져온 데이터를 JSP로 보내기 위해서 request에 담는다.
				request.setAttribute("vo", result);
				
				// 댓글 페이지 객체
				// 데이터 전달 - page / perPageNum / no / replyPage / replayPerPageNum
				ReplyPageObject replyPageObject = ReplyPageObject.getInstance(request);
				
				// 가져온 댓글 데이터 request에 담기
				request.setAttribute("replyList", Execute.execute(Init.get("/memberreply/list.do"), replyPageObject));
				// 댓글 페이지 객체 담기
				request.setAttribute("replyPageObject", replyPageObject);
				
				jsp = "board/view";
				break;
			
			case "/member/updateForm.do":
				System.out.println("4-1. 글수정 폼으로 이동");
				// 사용자가 -> 서버 : 글번호 데이터 수집
				no = Long.parseLong(request.getParameter("no"));
				
				// no에 맞는 데이터 가져오기 - DB - BoardViewSerivce
				result = Execute.execute(Init.get("/member/view.do"), new Long[] {no, 0L});
				
				// 가져온 데이터를 JSP로 보내기 위해서 request에 담는다.
				request.setAttribute("vo", result);
				
				// jsp 정보
				jsp = "board/updateForm";
				break;
				
			case "/member/update.do":
				System.out.println("4-2. 게시판 글수정 처리");
				
				// 수정할 글 번호를 받는다. - 데이터 수집
				strNo = request.getParameter("no");
				no = Long.parseLong(strNo);
				
				id = (String) request.getParameter("title");
				pw = (String) request.getParameter("pw");
				
				// 변수 - vo 저장하고 Service
				loginVO = new LoginVO();
				loginVO.setId(id);
				loginVO.setPw(pw);
				
				// [BoardController] - BoardWriteService - BoardDAO.writer(vo)
				result = Execute.execute(Init.get(uri), loginVO);
				
				// 페이지 정보 받기 & uri에 붙이기
				pageObject = PageObject.getInstance(request);
				System.out.println(pageObject);
				// 글보기로 자동 이동하기
				jsp = "redirect:view.do?no=" + no + "&inc=0"
						+ "&" + pageObject.getPageQuery(); 
				
				session.setAttribute("msg", "글 수정이 성공적으로 처리 되었습니다.");
				
				break;
				
				
			case "/member/delete.do":
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
				System.out.println("####################################");;
				System.out.println("## 잘못된 메뉴를 선택하셨습니다.          ##");;
				System.out.println("## [0~5, 0] 중에서 입력하셔야 합니다.    ##");;
				System.out.println("####################################");;
				break;
			} // end of switch
		} catch (Exception e) {
			// TODO: handle exception
				e.printStackTrace();
			System.out.println();
			System.out.println("$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@");
			System.out.println("$%@ << 오류 출력 >>                         $%@");
			System.out.println("$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@");
			System.out.println("$%@ 타입 : " + e.getClass().getSimpleName());
			System.out.println("$%@ 내용 : " + e.getMessage());
			System.out.println("$%@ 조치 : 데이터를 확인 후 다시 실행해 보세요.");
			System.out.println("$%@     : 계속 오류가 나면 전산담당자에게 연락하세요.");
			System.out.println("$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@");
		} // end of try~catch
		return jsp;
	} // end of execute()
	
} // end of class
