package com.webjjang.image.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.webjjang.image.vo.ImageVO;
import com.webjjang.main.controller.Init;
import com.webjjang.member.vo.LoginVO;
import com.webjjang.util.exe.Execute;
import com.webjjang.util.page.PageObject;
import com.webjjang.util.page.ReplyPageObject;

// Image Module 에 맞는 메뉴 선택 , 데이터 수집(기능별), 예외 처리
public class ImageController {

	public String execute(HttpServletRequest request) {

		// session을 request에서 부터 꺼낸다.
		HttpSession session = request.getSession();
		
		// return jsp 정보 저장 변수
		String jsp = null;
		// 메뉴 입력 - uri - /image/list.do
		String uri = request.getRequestURI();
		
		// 로그인 아이디 꺼내기 ------------------------------------
		HttpSession httpSession = request.getSession();
		LoginVO loginVO = (LoginVO) session.getAttribute("login");
		
		String id = null;
		if (loginVO != null) id = loginVO.getId();
		
		// 파일 업로드 설정 --------------------------------------
		// 파일의 상대적인 저장 위치
		String savePath = "/upload/image";
		// 파일 시스템에서는 절대 저장위치가 필요하다. - 파일 업로드 시 필요
		String realSavePath = request.getServletContext().getRealPath(savePath);
		
		// 업로드 파일 용량 제한(100mb)
		int sizeLimit = 100 * 1024 * 1024;
		
		Object result = null;
		
		// 입력 받는 데이터 선언
		Long no = 0L;
		
		try { // 정상 처리
		
			// 메뉴 처리 : CRUD DB 처리 - Controller - Service - DAO
			switch (uri) {
			case "/image/list.do":
				// [ImageController] - (Execute) - ImageListService - ImageDAO.list()
				System.out.println("1.이미지 게시판 리스트");
				// 페이지 처리를 위한 객체
				// getInstance - 기본 값이 있고 넘어오는 페이지와 검색 정보를 세팅 처리
				PageObject pageObject = PageObject.getInstance(request);
				
				// 이미지 게시판의 한페이지 이미지의 개수의 기본 값을 6으로 처리하자 
				// - 중복 처리 (앞에 데이터는 지워지고 뒤에 설정한 데이터가 저장됨.)
				String strPerPageNum = request.getParameter("perPageNum");
				if(strPerPageNum == null) pageObject.setPerPageNum(6);
				
				// DB 에서 데이터 가져오기 - 가져온 데이터는 LIST<ImageVO>
				result = Execute.execute(Init.get(uri), pageObject);
				
				// pageObject 데이터 확인
				System.out.println("ImageController.execute().pageObject : " + pageObject);
				
				// 가져온 데이터를 request에 저장 -> jsp 까지 전달 된다.
				request.setAttribute("list", result);
				// pageObject 담기
				request.setAttribute("pageObject", pageObject);
				// /WEB-INF/views/  + image/list  +  .jsp
				jsp = "image/list";
				break;
				
				
			case "/image/view.do":
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
				request.setAttribute("replyList", Execute.execute(Init.get("/boardreply/list.do"), replyPageObject));
				// 댓글 페이지 객체 담기
				request.setAttribute("replyPageObject", replyPageObject);
				
				jsp = "image/view";
				break;
			
			case "/image/writeForm.do":
				System.out.println("3-1. 글등록 폼으로 이동");
				jsp = "image/writeForm";
				break;
				
			case "/image/write.do":
				System.out.println("3-2.글등록 처리");
				
				// 이미지 업로드 처리
				// new MultipartRequest(request, 실제 저장위치, 사이즈 제한, 인코딩 타입, 중복처리 객체-파일 이름 뒤에 cnt 붙힘);
				MultipartRequest multi = 
						new MultipartRequest(request, realSavePath, sizeLimit, "utf-8", new DefaultFileRenamePolicy());
				
				// 일반 데이터 수집 - 사용자 -> 서버 : form - input - name : multi에서
				String title = multi.getParameter("title");
				String content = multi.getParameter("content");
				String fileName = multi.getFilesystemName("imageFile");
				// id는 session에서 받아야 한다. -> 위에서 처리함
						
				// 변수 - vo 저장하고 Service
				ImageVO vo = new ImageVO();
				vo.setTitle(title);
				vo.setContent(content);
				vo.setId(id);
				// fileName 은 위치 정보(상대위치) + 파일명
				vo.setFileName(savePath + "/" + fileName);
				
				// [ImageController] - ImageWriteService - ImageDAO.writer(vo)
				result = Execute.execute(Init.get(uri), vo);
				
//				// 페이지 정보 받기 & uri에 붙이기
//				pageObject = PageObject.getInstance(request);
//				System.out.println(pageObject);
				
				// jsp 정보 앞에 "redirect:"가 붙어 있으면 redirect를 
				// 아니면 jsp로 forword를 시킨다.
				jsp = "redirect:list.do?perPageNum=" + multi.getParameter("perPageNum");
				
				// 처리 메시지
				session.setAttribute("msg", "이미지 등록 되었습니다.");
				
				break;
			
			case "/image/updateForm.do":
				System.out.println("4-1. 글수정 폼으로 이동");
				// 사용자가 -> 서버 : 글번호 데이터 수집
				no = Long.parseLong(request.getParameter("no"));
				
				// no에 맞는 데이터 가져오기 - DB - ImageViewSerivce
				result = Execute.execute(Init.get("/image/view.do"), new Long[] {no, 0L});
				
				// 가져온 데이터를 JSP로 보내기 위해서 request에 담는다.
				request.setAttribute("vo", result);
				
				// jsp 정보
				jsp = "image/updateForm";
				break;
				
			case "/image/update.do":
				System.out.println("4-2. 게시판 글수정 처리");
				
				// 수정할 글 번호를 받는다. - 데이터 수집
				strNo = request.getParameter("no");
				no = Long.parseLong(strNo);
				
				title = (String) request.getParameter("title");
				content = (String) request.getParameter("content");
				
				// 변수 - vo 저장하고 Service
				vo = new ImageVO();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContent(content);
				
				// [ImageController] - ImageWriteService - ImageDAO.writer(vo)
				result = Execute.execute(Init.get(uri), vo);
				
				// 페이지 정보 받기 & uri에 붙이기
				pageObject = PageObject.getInstance(request);
				System.out.println(pageObject);
				// 글보기로 자동 이동하기
				jsp = "redirect:view.do?no=" + no + "&inc=0"
						+ "&" + pageObject.getPageQuery(); 
				
				session.setAttribute("msg", "글 수정이 성공적으로 처리 되었습니다.");
				
				break;
				
				
			case "/image/delete.do":
				System.out.println("5.일반게시판 글삭제");
				
				// 데이터 수집 - DB에서 실행에 필요한 데이터 - 글 번호, 비밀번호 - ImageVO
				ImageVO deleteVO = new ImageVO();
				strNo = request.getParameter("no");
				no = Long.parseLong(strNo);
				deleteVO.setNo(no);
				
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