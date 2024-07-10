package com.webjjang.member.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.webjjang.board.vo.BoardVO;
import com.webjjang.main.controller.Init;
import com.webjjang.member.vo.LoginVO;
import com.webjjang.member.vo.MemberVO;
import com.webjjang.util.exe.Execute;
import com.webjjang.util.page.PageObject;

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
		
		// 파일 업로드 설정 --------------------------------------
		// 파일의 상대적인 저장 위치
		String savePath = "/upload/member";
		// 파일 시스템에서는 절대 저장위치가 필요하다. - 파일 업로드 시 필요
		String realSavePath = request.getServletContext().getRealPath(savePath);
		// 업로드 파일 용량 제한(100mb)
		int sizeLimit = 100 * 1024 * 1024;
		
		// realSavePath 존재하지 않으면 만들자
		File realSavePathFile = new File(realSavePath);
		if (!realSavePathFile.exists()) realSavePathFile.mkdirs();

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
				jsp = "redirect:/main/main.do";
				
				// 로그인 완료 메시지 처리
				session.setAttribute("msg", "로그인이 정상 처리 되었습니다.");
				
				break;
				
			case "/member/logout.do":
				System.out.println("b. 로그 아웃 처리");
				// session의 로그인 내용 지우기 - 로그 아웃 처리
				session.removeAttribute("login");
				
				session.setAttribute("msg", "로그아웃이 정상 처리 되었습니다.");
				
				jsp = "redirect:/main/main.do";
				break;
			
			
			
				
			case "/member/view.do":
				System.out.println("2. 회원정보 보기 글보기");
				
				// 넘어 오는 글 번호와 조회수 수집 한다. (데이터는 request 안에 들어 있다.)
				id = login.getId();
				// 전달 데이터 - id
				result = Execute.execute(Init.get(uri), id);
				
				// 가져온 데이터를 JSP로 보내기 위해서 request에 담는다.
				request.setAttribute("vo", result);
				
				jsp = "member/view";
				break;
				
			case "/member/writeForm.do":
				System.out.println("c. 회원가입 폼 폼으로 이동");
				jsp = "member/writeForm";
				break;
				
			case "/member/write.do":
				System.out.println("c-1. 회원 가입 처리");
				
				// 파일이 존재한다.
				MultipartRequest multi = 
						new MultipartRequest(request, realSavePath, sizeLimit, "utf-8", new DefaultFileRenamePolicy());
				
				// 데이터 수집 - 사용자 -> 서버 : form - input - name
				id = multi.getParameter("id");
				pw = multi.getParameter("pw");
				String name = multi.getParameter("name");
				String gender = multi.getParameter("gender");
				String birth = multi.getParameter("birth");
				String tel = multi.getParameter("tel");
				String email = multi.getParameter("email");
				String photo = multi.getFilesystemName("photoFile");
				
				// 변수 - vo 저장하고 Service
				MemberVO vo = new MemberVO();
				vo.setId(id);
				vo.setPw(pw);
				vo.setName(name);
				vo.setGender(gender);
				vo.setBirth(birth);
				vo.setTel(tel);
				vo.setEmail(email);
				// null 인 경우 처리
				// 이미지가 있으면 세팅한다. 없으면 세팅하지 않는다.
				if (!(photo == null || photo.equals("")))
					vo.setPhoto(savePath + "/" + photo);
				
				// [MemberController] - MemberWriteService - MemberDAO.writer(vo)
				Execute.execute(Init.get(uri), vo);
				
				// jsp 정보 앞에 "redirect:"가 붙어 있으면 redirect를 
				// 아니면 jsp로 forword를 시킨다.
				jsp = "redirect:/board/list.do";
				
				// 처리 메시지
				session.setAttribute("msg", "회원 가입이 정상 처리 되었습니다.");
				
				break;
			
			case "/member/checkId.do":
				System.out.println("d. 아이디 중복 체크 처리");
				
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
				
				// 처리 메시지
				session.setAttribute("msg", "글 등록이 성공적으로 처리 되었습니다.");
				
				break;
				
			case "/member/list.do": // 관리자만 가능하다.
				// [MemberController] - (Execute) - MemberListService - MemberDAO.list()
				System.out.println("e. 회원 리스트");
				// 페이지 처리를 위한 객체
				// getInstance - 기본 값이 있고 넘어오는 페이지와 검색 정보를 세팅 처리
				PageObject pageObject = PageObject.getInstance(request);
				
				// 아이디 세팅 - 관리자 계쩡은 제외시키기 위해서 => accepter
				pageObject.setAccepter(id);
				
				// DB 에서 데이터 가져오기 - 가져온 데이터는 LIST<BoardVO>
				result = Execute.execute(Init.get(uri), pageObject);
				
				// 가져온 데이터를 request에 저장 -> jsp 까지 전달 된다.
				request.setAttribute("list", result);
				// pageObject 담기
				request.setAttribute("pageObject", pageObject);
				// /WEB-INF/views/  + member/list  +  .jsp
				jsp = "member/list";
				break;
				
			case "/member/changeGrade.do":
				System.out.println("d-3. 회원 등급 변경 처리");
				
				// 데이터 수집 - 사용자 -> 서버 : form - input - name
				id = request.getParameter("id");
				int gradeNo = Integer.parseInt(request.getParameter("gradeNo"));
				
				// 변수 - vo 저장하고 Service
				vo = new MemberVO();
				vo.setId(id);
				vo.setGradeNo(gradeNo);
				
				// [MemberController] - MemberWriteService - MemberDAO.writer(vo)
				Execute.execute(Init.get(uri), vo);
				
				// jsp 정보 앞에 "redirect:"가 붙어 있으면 redirect를 
				// 아니면 jsp로 forword를 시킨다.
				jsp = "redirect:/member/list.do";
				
				// 처리 메시지
				session.setAttribute("msg", "회원 [ " + id + " ] 님의 등급이 " 
						+ " 변경이 되었습니다.");
				
				break;
				
			case "/member/changeStatus.do":
				System.out.println("d-4. 회원 상태 변경 처리");
				
				// 데이터 수집 - 사용자 -> 서버 : form - input - name
				id = request.getParameter("id");
				String status = request.getParameter("status");
				
				// 변수 - vo 저장하고 Service
				vo = new MemberVO();
				vo.setId(id);
				vo.setStatus(status);
				
				// [MemberController] - MemberWriteService - MemberDAO.writer(vo)
				Execute.execute(Init.get(uri), vo);
				
				// jsp 정보 앞에 "redirect:"가 붙어 있으면 redirect를 
				// 아니면 jsp로 forword를 시킨다.
				jsp = "redirect:/member/list.do";
				
				// 처리 메시지
				session.setAttribute("msg", "회원 [ " + id + " ] 님의 상태가 " 
						+ " 변경이 되었습니다.");
				
				break;
				
				
			case "/member/updateForm.do":
				System.out.println("4-1. 회원 정보 수정 폼으로 이동");
				// 사용자가 -> 서버 : 글번호 데이터 수집
				id = request.getParameter("id");
				
				// no에 맞는 데이터 가져오기 - DB - MemberViewSerivce
				Execute.execute(Init.get("/member/view.do"), id);
				
				// 가져온 데이터를 JSP로 보내기 위해서 request에 담는다.
				request.setAttribute("vo", result);
				
				// jsp 정보
				jsp = "member/updateForm";
				break;
				
			case "/member/update.do":
				System.out.println("4-2. 회원 정보 수정 처리");
				
				// 수정할 글 번호를 받는다. - 데이터 수집
//				strNo = request.getParameter("no");
//				no = Long.parseLong(strNo);
				
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
//				strNo = request.getParameter("no");
//				no = Long.parseLong(strNo);
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
