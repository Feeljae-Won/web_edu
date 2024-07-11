package com.webjjang.main.controller;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webjjang.ajax.controller.AjaxController;
import com.webjjang.board.controller.BoardController;
import com.webjjang.boardreply.controller.BoardReplyController;
import com.webjjang.image.controller.ImageController;
import com.webjjang.member.controller.MemberController;
import com.webjjang.notice.controller.NoticeController;

/**
 * Servlet implementation class DispatcherServlet
 */
// WebServer와 연결하기 위해서 Servlet으로 등록되어 있어야 한다.
// 1. @WebServlet 을 쓰는 이유 : 프로그램 수정 가능, 2. web.xml 에 등록 사용 - 프로그램 수정 불가능.
// @WebServlet(urlPatterns = "*.do", loadOnStartup = 1)
// Servlet을 상속 - 기능 : URL 연결 - 서버에서 동작 되는 프로그램 - 한번만 생성된다.(싱글톤 프로그램)
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Controller 생성
	private MainController mainController= new MainController();
	private BoardController boardController = new BoardController();
	private NoticeController noticeController = new NoticeController();
	private BoardReplyController boardReplyController = new BoardReplyController();
	private MemberController memberController = new MemberController();
	private ImageController imageController = new ImageController();
	private AjaxController	ajaxController = new AjaxController();
	
	
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		// 드라이버 확인 / 객체 생성 처리 - Class.forName(class명)
		// 서버가 실행될 때 먼저 실행되어야만 한다.
		System.out.println("DispatcherServlet.init ---------- 초기화 진행 -----------");
		try {
			// 객체에 대한 생성과 초기화 + 조립
			Class.forName("com.webjjang.main.controller.Init");
			// 오라클 드라이버 확인 + 로딩
			Class.forName("com.webjjang.util.db.DB");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("DispatcherServlet.service() -----------------------------------");
		
		// 모듈 - 일반게시판 - "/board" / 공지사항 - "/notice"
		String uri = request.getRequestURI();
		
		// main 처리 - localhost -> localhost/main.do
		if(uri.equals("/") || uri.equals("main.do")) {
			response.sendRedirect("/main/main.do");
			return;
		}
		
		// 2번째 / 위치
		int pos = uri.indexOf("/", 1);
		
		// 2번째 /가 없으면 (-1) == 404.error
		// 모듈 자체가 없는 경우 404.jsp로 보낸다.
		if(pos == -1) {
			request.setAttribute("uri", request.getRequestURI());
			request.getRequestDispatcher("/WEB-INF/views/error/noModule_404.jsp").forward(request, response);
			return;
		}
		
		// 일반게시판 - "/board"
		String module = uri.substring(0, pos);
		System.out.println("module = " + module);
		
		// request에 module을 담아서 어떤 메뉴가 선택되었는지 처리 : default_decorator.jsp
		request.setAttribute("module", module);
		
		String jsp = null;
		
		switch (module) {
		case "/main":
			jsp = mainController.execute(request);
			
			break;
		case "/board":
			jsp = boardController.execute(request);
			
			break;
		case "/boardreply":
			jsp = boardReplyController.execute(request);
			
			break;
		case "/notice":
			jsp = noticeController.execute(request);
			
			break;
		case "/member":
			jsp = memberController.execute(request);
			
			break;
		case "/image":
			jsp = imageController.execute(request);
			
			break;
		case "/ajax":
			jsp = ajaxController.execute(request);
			
			break;

		default:
			request.setAttribute("uri", request.getRequestURI());
			request.getRequestDispatcher("/WEB-INF/views/error/noModule_404.jsp").forward(request, response);
			return;
		} // end of switch
		// jsp 정보 앞에 "redirect:"이 붙어 있으면 redirect 시킨다. (페이지 자동 이동)
		// jsp 정보 앞에 "redirect:"이 붙어 있지 않으면 jsp로 forward 시킨다. (페이지 자동 이동)
		if (jsp.indexOf("redirect:") == 0) {
			// redirect:list.do -> uri로 사용하기 위해 redirect:은 잘라 버린다.
			response.sendRedirect(jsp.substring("redirect:".length()));
		} else {
			// jsp로 forward 한다.
			request.getRequestDispatcher("/WEB-INF/views/" + jsp + ".jsp").forward(request, response);
			// request.getSession().removeAttribute("msg");
		} // end of if
	} // end of service

} // end of DispatcherServlet();
