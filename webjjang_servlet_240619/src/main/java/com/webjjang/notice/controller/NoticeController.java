package com.webjjang.notice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.webjjang.main.controller.Init;
import com.webjjang.main.controller.Main;
import com.webjjang.notice.service.NoticeDeleteService;
import com.webjjang.notice.service.NoticeListService;
import com.webjjang.notice.service.NoticeUpdateService;
import com.webjjang.notice.service.NoticeViewService;
import com.webjjang.notice.service.NoticeWriteService;
import com.webjjang.notice.vo.NoticeVO;
import com.webjjang.util.exe.Execute;
import com.webjjang.util.io.NoticePrint;
import com.webjjang.util.page.PageObject;
import com.webjjang.util.io.In;

// Notice Module 에 맞는 메뉴 선택 , 데이터 수집(기능별), 예외 처리
public class NoticeController {

	public String execute(HttpServletRequest request) {
		
		String jsp = null;
		String uri = request.getRequestURI();
		Object result = null;
		
		// 입력 받는 데이터 선언
		Long no = 0L;
		
		try { // 정상 처리
		
			// 메뉴 처리 : CRUD DB 처리 - Controller - Service - DAO
			switch (uri) {
			case "/notice/list.do":
				// [NoticeController] - (Execute) - NoticeListService - NoticeDAO.list()
				System.out.println("1.공지사항 리스트");
				PageObject pageObject = PageObject.getInstance(request);
				
				// DB 에서 데이터 가져오기 - 가져온 데이터는 LIST<NoticeVO>
				result = Execute.execute(Init.get(uri), pageObject);
				// 가져온 데이터 출력하기
				request.setAttribute("list", result);
				jsp = "notice/list";
				break;
				
				
			case "/notice/view.do":
				System.out.println("2.공지사항 글보기");
				// 1. 조회수 1증가(글보기), 2. 공지사항 글보기 데이터 가져오기 : 글보기, 수정할 때
				// 사용자가 보고 싶은 글 번호를 입력한다. (In)
				no = In.getLong("글번호");
				// 전달 데이터 - 글번호, 조회수 증가 여부(1 : 증가, 0: 증가 안함) : 배열 또는 Map
				result = Execute.execute(new NoticeViewService(),no);
				// 게시판 글 보기 출력
				new NoticePrint().print((NoticeVO) result);
				break;
				
				
			case "/notice/write.do":
				System.out.println("3.공지사항 글등록");
				
				// 데이터 수집 - 사용자 : 제목, 내용, 작성자, 비밀전호
				String title = In.getStr("제목");
				String content = In.getStr("내용");
				String startDate = In.getStr("공지 시작일(YYYY-MM-DD)");
				String endDate = In.getStr("공지 종료일(YYYY-MM-DD)");
				
				// 변수 - vo 저장하고 Service
				NoticeVO vo = new NoticeVO();
				vo.setTitle(title);
				vo.setContent(content);
				vo.setStartDate(startDate);
				vo.setEndDate(endDate);
				
				// [NoticeController] - NoticeWriteService - NoticeDAO.writer(vo)
				result = Execute.execute(new NoticeWriteService(), vo);
				
				break;
				
				
			case "/notice/update.do":
				System.out.println("4.공지사항 글수정");
				
				// 수정할 글 번호를 받는다. - 데이터 수집
				no = In.getLong("수정할 글 번호");
				
				// 수정할 데이터 가져오기 - 글 보기 - NoticeViewService
				NoticeVO updateVO = (NoticeVO)Execute.execute(new NoticeViewService(), no);
				
				// DB 에 데이터 수정하기 - NoticeUpdateService
				break;
				
				
			case "5":
				System.out.println("5.공지사항 글삭제");
				
				// 데이터 수집 - DB에서 실행에 필요한 데이터 - 글 번호, 비밀번호 - NoticeVO
				NoticeVO deleteVO = new NoticeVO();
				deleteVO.setNo(In.getLong("삭제할 번호 입력"));
				
				// DB 처리
				Execute.execute(new NoticeDeleteService(),deleteVO);
				System.out.println("***********************************");
				System.out.println("** " + deleteVO.getNo() + "번 글이 삭제 되었습니다.");
				System.out.println("***********************************");
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
//				e.printStackTrace();
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
	} // end of main()
	
} // end of class
