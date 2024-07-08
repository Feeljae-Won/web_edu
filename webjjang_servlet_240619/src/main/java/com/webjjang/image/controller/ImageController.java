package com.webjjang.image.controller;


import java.io.File;

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
				System.out.println("2.이미지 게시판 상세보기");
				// 1. 조회수 1증가(글보기), 2. 일반게시판 글보기 데이터 가져오기 : 글보기, 수정할 때
				// 넘어 오는 글 번호와 조회수 수집 한다. (데이터는 request 안에 들어 있다.)
				no = Long.parseLong(request.getParameter("no"));
				Long inc = Long.parseLong(request.getParameter("inc"));
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
				// file 객체 업로드 시 input의 name이 같으면 한개만 처리 가능
				// 	name을 다르게 해서 올려야 한다.
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
				System.out.println("4-1. 이미지 게시판 수정 폼으로 이동");
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
				System.out.println("4-2. 이미지 게시판 수정 처리");
				
				multi = new MultipartRequest(request, realSavePath, sizeLimit, "utf-8", new DefaultFileRenamePolicy());
				
				// 수정할 글 번호를 받는다. - 데이터 수집
				no = Long.parseLong(multi.getParameter("no"));
				
				title = multi.getParameter("title");
				content = multi.getParameter("content");
				
				// 변수 - vo 저장하고 Service
				vo = new ImageVO();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setId(id);
				
				// [ImageController] - ImageWriteService - ImageDAO.writer(vo)
				result = Execute.execute(Init.get(uri), vo);
				
				// 페이지 정보 받기 & uri에 붙이기
				pageObject = PageObject.getInstance(request);
				System.out.println(pageObject);
				// 글보기로 자동 이동하기
				jsp = "redirect:view.do?no=" + no + "&inc=0"
						+ "&" + pageObject.getPageQuery(); 
				
				session.setAttribute("msg", "이미지 내용 수정이 완료 되었습니다.");
				
				break;
				
				
			case "/image/delete.do":
				System.out.println("5. 이미지 게시판 글 삭제");
				
				// 데이터 수집 - DB에서 실행에 필요한 데이터 - 글 번호, 아이디(session) - ImageVO
				ImageVO deleteVO = new ImageVO();
				no = Long.parseLong(request.getParameter("no"));
				
				
				deleteVO.setNo(no);
				deleteVO.setId(id);
				
				// DB 처리
				result = (int) Execute.execute(Init.get(uri),deleteVO);
				
				if((int) result == 1) {
					session.setAttribute("msg", "글 삭제가 성공적으로 처리 되었습니다.");
				} else {
					session.setAttribute("msg", "글 삭제가 처리되지 않았습니다. [비밀번호가 틀립니다.]");
				}
				
//				// 페이지 정보 받기 & uri에 붙이기
				jsp = "redirect:list.do" + "?perPageNum=" + request.getParameter("perPageNum");
				
				// 파일 삭제
				// 삭제할 파일 이름
				String deleteFileName = request.getParameter("deleteFileName");
				File deleteFile = new File(request.getServletContext().getRealPath(deleteFileName));
				
				// 파일 삭제 처리
				if(deleteFile.exists()) deleteFile.delete();
				
				break;
				
				
			case "/image/changeImage.do":
				System.out.println("6. 이미지 변경 처리");
				
				// 파일 업로드 cos 라이브러리 - MultipartRequest
				// 이미지 업로드 처리
				// new MultipartRequest(request, 실제 저장위치, 사이즈 제한, 인코딩 타입, 중복처리 객체-파일 이름 뒤에 cnt 붙힘);
				// file 객체 업로드 시 input의 name이 같으면 한개만 처리 가능
				// 	name을 다르게 해서 올려야 한다.
				multi = new MultipartRequest(request, realSavePath, sizeLimit, "utf-8", new DefaultFileRenamePolicy());
				
				// 수정할 글 번호를 받는다. - 데이터 수집
				no = Long.parseLong(multi.getParameter("no"));
				fileName = multi.getFilesystemName("imageFile");
				
				deleteFileName = multi.getParameter("deleteFileName");
				System.out.println("ImageController.changeImage().changeImage() : " + deleteFileName);
				
				// 변수 - vo 저장하고 Service : DB에 처리할 데이터만.
				vo = new ImageVO();
				vo.setNo(no);
				vo.setFileName(savePath + "/" + fileName);
				
				// [ImageController] - ImageChangeService - ImageDAO.changeImage(vo)
				Execute.execute(Init.get(uri), vo);
				
				// 지난 이미지 파일은 존재하면 지운다.
				deleteFile = new File(request.getServletContext().getRealPath(deleteFileName));
				if (deleteFile.exists()) deleteFile.delete();
				
				// 페이지 정보 받기 & uri에 붙이기
				pageObject = PageObject.getInstance(request);
				System.out.println(pageObject);
				
				// 글보기로 자동 이동하기
				jsp = "redirect:view.do?no=" + no + "&inc=0" + "&" + pageObject.getPageQuery(); 
				
				session.setAttribute("msg", "이미지 변경이 완료 되었습니다.");
				
				break;
				
				
			default:
				System.out.println("####################################");;
				System.out.println("##         잘못된 접근 입니다.        ##");;
				System.out.println("####################################");;
				break;
			} // end of switch
		} catch (Exception e) {
				e.printStackTrace();
			System.out.println();
			System.out.println("$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@");
			System.out.println("$%@            << 오류 출력 >>              $%@");
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
