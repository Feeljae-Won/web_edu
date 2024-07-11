package com.webjjang.boardreply.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.webjjang.boardreply.vo.BoardReplyVO;
import com.webjjang.main.controller.Init;
import com.webjjang.util.exe.Execute;
import com.webjjang.util.page.ReplyPageObject;

// Board Module 에 맞는 메뉴 선택 , 데이터 수집(기능별), 예외 처리
public class BoardReplyController {

	public String execute(HttpServletRequest request) {
		
		// session을 request에서 부터 꺼낸다.
		HttpSession session = request.getSession();

		// return jsp 정보 저장 변수
		String jsp = null;
		// 메뉴 입력 - uri - /board/list.do
		String uri = request.getRequestURI();
		
		
		try { // 정상 처리
			
			// 페이지 정보 받기 & uri에 붙이기
			ReplyPageObject pageObject = ReplyPageObject.getInstance(request);
		
			// 메뉴 처리 : CRUD DB 처리 - Controller - Service - DAO
			switch (uri) {
			case "/boardreply/write.do":
				System.out.println("1.댓글 등록 처리");
				
				// 데이터 수집 - 사용자 -> 서버 : form - input - name
				// no 는 pageObject에 있다.
				String content = request.getParameter("content");
				String writer = request.getParameter("writer");
				String pw = request.getParameter("pw");
				
				// 변수 - vo 저장하고 Service
				BoardReplyVO vo = new BoardReplyVO();
				vo.setNo(pageObject.getNo());
				vo.setContent(content);
				vo.setWriter(writer);
				vo.setPw(pw);
				
				// [BoardController] - BoardWriteService - BoardDAO.writer(vo)
				Execute.execute(Init.get(uri), vo);
				
				
				// jsp 정보 앞에 "redirect:"가 붙어 있으면 redirect를 
				// 아니면 jsp로 forword를 시킨다.
				jsp = "redirect:/board/view.do?no="+ pageObject.getNo() 
					+ "&inc=0" 
					// 일반게시판의 페이지 & 검색 정보 붙히기
					+ "&" + pageObject.getPageObject().getPageQuery()
					+ "&" + pageObject.getPageQuery();
				
				session.setAttribute("msg", "댓글 등록이 성공적으로 처리 되었습니다.");
				
				break;
			
			case "/boardreply/update.do":
				System.out.println("2. 댓글 수정 처리");
				
				// 수정할 글 번호를 받는다. - 데이터 수집
				String strNo = request.getParameter("rno");
				Long rno = Long.parseLong(strNo);
				
				content = (String) request.getParameter("content");
				writer = (String) request.getParameter("writer");
				pw = (String) request.getParameter("pw");
				
				// 변수 - vo 저장하고 Service
				vo = new BoardReplyVO();
				vo.setRno(rno);
				vo.setContent(content);
				vo.setWriter(writer);
				vo.setPw(pw);
				
				// [BoardController] - BoardWriteService - BoardDAO.writer(vo)
				Execute.execute(Init.get(uri), vo);
				
				// 페이지 정보 받기 & uri에 붙이기
//				pageObject = PageObject.getInstance(request);
				System.out.println(pageObject);
				// 글보기로 자동 이동하기
				jsp = "redirect:/board/view.do?no=" + pageObject.getNo() + "&inc=0"
						+ "&" + pageObject.getPageObject().getPageQuery()
						+ "&" + pageObject.getPageQuery();
				// DB 에 데이터 수정하기 - BoardUpdateService
				
				session.setAttribute("msg", "댓글 수정이 성공적으로 되었습니다.");
				break;
				
				
			case "/boardreply/delete.do":
				System.out.println("3. 댓글 삭제 처리");
				
				// 데이터 수집 - DB에서 실행에 필요한 데이터 - 글 번호, 비밀번호 - BoardVO
				BoardReplyVO deleteVO = new BoardReplyVO();
				strNo = request.getParameter("rno");
				rno = Long.parseLong(strNo);
				deleteVO.setRno(rno);
				deleteVO.setPw(request.getParameter("pw"));
				
				// DB 처리
				Execute.execute(Init.get(uri),deleteVO);
				
//				// 페이지 정보 받기 & uri에 붙이기
//				pageObject = PageObject.getInstance(request);
//				System.out.println(pageObject);
				
				jsp = "redirect:/board/view.do?no="+ pageObject.getNo() 
				+ "&inc=0" 
				// 일반게시판의 페이지 & 검색 정보 붙히기
				+ "&" + pageObject.getPageObject().getPageQuery()
				+ "&" + pageObject.getPageQuery();
				System.out.println("페이지 정보 확인 : " + pageObject.getPageObject());
				
				session.setAttribute("msg", "댓글 삭제 되었습니다.");
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
