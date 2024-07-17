package com.webjjang.main.controller;

import java.util.HashMap;
import java.util.Map;

import com.webjjang.board.dao.BoardDAO;
import com.webjjang.board.service.BoardDeleteService;
import com.webjjang.board.service.BoardListService;
import com.webjjang.board.service.BoardRnumListService;
import com.webjjang.board.service.BoardUpdateService;
import com.webjjang.board.service.BoardViewService;
import com.webjjang.board.service.BoardWriteService;
import com.webjjang.boardreply.dao.BoardReplyDAO;
import com.webjjang.boardreply.service.BoardReplyDeleteService;
import com.webjjang.boardreply.service.BoardReplyListService;
import com.webjjang.boardreply.service.BoardReplyUpdateService;
import com.webjjang.boardreply.service.BoardReplyWriteService;
import com.webjjang.image.dao.ImageDAO;
import com.webjjang.image.service.ImageChangeService;
import com.webjjang.image.service.ImageDeleteService;
import com.webjjang.image.service.ImageListService;
import com.webjjang.image.service.ImageUpdateService;
import com.webjjang.image.service.ImageViewService;
import com.webjjang.image.service.ImageWriteService;
import com.webjjang.main.dao.DAO;
import com.webjjang.main.service.Service;
import com.webjjang.member.dao.MemberDAO;
import com.webjjang.member.service.MemberChangeGradeService;
import com.webjjang.member.service.MemberChangeStatusService;
import com.webjjang.member.service.MemberCheckIdService;
import com.webjjang.member.service.MemberConUpdateService;
import com.webjjang.member.service.MemberListService;
import com.webjjang.member.service.MemberLoginService;
import com.webjjang.member.service.MemberWriteService;
import com.webjjang.message.dao.MessageDAO;
import com.webjjang.message.service.MessageListService;
import com.webjjang.message.service.MessageWriteService;
import com.webjjang.notice.dao.NoticeDAO;
import com.webjjang.notice.service.NoticeDeleteService;
import com.webjjang.notice.service.NoticeListService;
import com.webjjang.notice.service.NoticeUpdateService;
import com.webjjang.notice.service.NoticeViewService;
import com.webjjang.notice.service.NoticeWriteService;
import com.webjjang.qna.dao.QnaDAO;
import com.webjjang.qna.service.QnaDeleteService;
import com.webjjang.qna.service.QnaListService;
import com.webjjang.qna.service.QnaUpdateService;
import com.webjjang.qna.service.QnaViewService;
import com.webjjang.qna.service.QnaWriteService;

public class Init {

	// Map <key, value>
	// Key에는 URL이 들어온다.
	// HashMap 안에 < > 안에 값을 지우면 앞에 있는 Map < > 속성을 따라가겠다.

	// service 생성해서 저장하는 객체 - <URI, service>
	private static Map<String, Service> serviceMap = new HashMap<>();
	// dao 생성해서 저장하는 객체 - <className, dao>
	private static Map<String, DAO> daoMap = new HashMap<>();

	// static 변수에 데이터를 초기화 시키는 1번만 실행되는 블록 - class가 불려지면 자동 실행
	static {
		// ------------[일반 게시판 객체 생성과 조립] ------------------
		// dao 생성
		daoMap.put("boardDAO", new BoardDAO());
		
		// service 생성
		serviceMap.put("/board/list.do", new BoardListService());
		serviceMap.put("/board/rnumList.do", new BoardRnumListService());
		serviceMap.put("/board/view.do", new BoardViewService());
		serviceMap.put("/board/write.do", new BoardWriteService());
		serviceMap.put("/board/update.do", new BoardUpdateService());
		serviceMap.put("/board/delete.do", new BoardDeleteService());

		// 조립 dao -> service
		serviceMap.get("/board/list.do").setDAO(daoMap.get("boardDAO"));
		serviceMap.get("/board/rnumList.do").setDAO(daoMap.get("boardDAO"));
		serviceMap.get("/board/view.do").setDAO(daoMap.get("boardDAO"));
		serviceMap.get("/board/write.do").setDAO(daoMap.get("boardDAO"));
		serviceMap.get("/board/update.do").setDAO(daoMap.get("boardDAO"));
		serviceMap.get("/board/delete.do").setDAO(daoMap.get("boardDAO"));

		// ------------[공지 게시판 객체 생성과 조립] ------------------
		// dao 생성
		daoMap.put("noticeDAO", new NoticeDAO());
		
		// service 생성
		serviceMap.put("/notice/list.do", new NoticeListService());
		serviceMap.put("/notice/view.do", new NoticeViewService());
		serviceMap.put("/notice/write.do", new NoticeWriteService());
		serviceMap.put("/notice/update.do", new NoticeUpdateService());
		serviceMap.put("/notice/delete.do", new NoticeDeleteService());

		// 조립 dao -> service
		serviceMap.get("/notice/list.do").setDAO(daoMap.get("noticeDAO"));
		serviceMap.get("/notice/view.do").setDAO(daoMap.get("noticeDAO"));
		serviceMap.get("/notice/write.do").setDAO(daoMap.get("noticeDAO"));
		serviceMap.get("/notice/update.do").setDAO(daoMap.get("noticeDAO"));
		serviceMap.get("/notice/delete.do").setDAO(daoMap.get("noticeDAO"));

		// ------------[일반게시판 댓글 객체 생성과 조립] ------------------
		// dao 생성
		daoMap.put("boardReplyDAO", new BoardReplyDAO());
		
		// service 생성
		serviceMap.put("/boardreply/list.do", new BoardReplyListService());
		serviceMap.put("/boardreply/write.do", new BoardReplyWriteService());
		serviceMap.put("/boardreply/update.do", new BoardReplyUpdateService());
		serviceMap.put("/boardreply/delete.do", new BoardReplyDeleteService());
		
		// 조립
		serviceMap.get("/boardreply/list.do").setDAO(daoMap.get("boardReplyDAO"));
		serviceMap.get("/boardreply/write.do").setDAO(daoMap.get("boardReplyDAO"));
		serviceMap.get("/boardreply/update.do").setDAO(daoMap.get("boardReplyDAO"));
		serviceMap.get("/boardreply/delete.do").setDAO(daoMap.get("boardReplyDAO"));
		
		// ------------[Member 객체 생성과 조립] ------------------
		// dao 생성
		daoMap.put("memberDAO", new MemberDAO());
		
		// service 생성
		serviceMap.put("/member/login.do", new MemberLoginService());
		serviceMap.put("/ajax/checkId.do", new MemberCheckIdService());
		serviceMap.put("/member/write.do", new MemberWriteService());
		serviceMap.put("/member/list.do", new MemberListService());
		serviceMap.put("/member/changeGrade.do", new MemberChangeGradeService());
		serviceMap.put("/member/changeStatus.do", new MemberChangeStatusService());
		// MemberController에서 들어오지 않고 필터에서 들어온다. (ConDateUpdateFilter)
		serviceMap.put("/member/conUpdate.do", new MemberConUpdateService());
		
		// 조립
		serviceMap.get("/member/login.do").setDAO(daoMap.get("memberDAO"));
		serviceMap.get("/ajax/checkId.do").setDAO(daoMap.get("memberDAO"));
		serviceMap.get("/member/write.do").setDAO(daoMap.get("memberDAO"));
		serviceMap.get("/member/list.do").setDAO(daoMap.get("memberDAO"));
		serviceMap.get("/member/changeGrade.do").setDAO(daoMap.get("memberDAO"));
		serviceMap.get("/member/changeStatus.do").setDAO(daoMap.get("memberDAO"));
		serviceMap.get("/member/conUpdate.do").setDAO(daoMap.get("memberDAO"));
		
		// ------------[Member 객체 생성과 조립] ------------------
		// dao 생성
		daoMap.put("imageDAO", new ImageDAO());
		
		// service 생성
		serviceMap.put("/image/list.do", new ImageListService());
		serviceMap.put("/image/view.do", new ImageViewService());
		serviceMap.put("/image/write.do", new ImageWriteService());
		serviceMap.put("/image/update.do", new ImageUpdateService());
		serviceMap.put("/image/delete.do", new ImageDeleteService());
		serviceMap.put("/image/changeImage.do", new ImageChangeService());
		
		// 조립
		serviceMap.get("/image/list.do").setDAO(daoMap.get("imageDAO"));
		serviceMap.get("/image/view.do").setDAO(daoMap.get("imageDAO"));
		serviceMap.get("/image/write.do").setDAO(daoMap.get("imageDAO"));
		serviceMap.get("/image/update.do").setDAO(daoMap.get("imageDAO"));
		serviceMap.get("/image/delete.do").setDAO(daoMap.get("imageDAO"));
		serviceMap.get("/image/changeImage.do").setDAO(daoMap.get("imageDAO"));
		
		// ------------[질문답변 객체 생성과 조립] ------------------
		// dao 생성
		daoMap.put("qnaDAO", new QnaDAO());
		
		// service 생성
		serviceMap.put("/qna/list.do", new QnaListService());
		serviceMap.put("/qna/view.do", new QnaViewService());
		serviceMap.put("/qna/write.do", new QnaWriteService());

		// 조립 dao -> service
		serviceMap.get("/qna/list.do").setDAO(daoMap.get("qnaDAO"));
		serviceMap.get("/qna/view.do").setDAO(daoMap.get("qnaDAO"));
		serviceMap.get("/qna/write.do").setDAO(daoMap.get("qnaDAO"));
		
		// ------------[메세지 객체 생성과 조립] ------------------
		// dao 생성
		daoMap.put("MessageDAO", new MessageDAO());
		
		// service 생성
		serviceMap.put("/message/list.do", new MessageListService());
		serviceMap.put("/message/write.do", new MessageWriteService());

		// 조립 dao -> service
		serviceMap.get("/message/list.do").setDAO(daoMap.get("MessageDAO"));
		serviceMap.get("/message/write.do").setDAO(daoMap.get("MessageDAO"));

		
		System.out.println("Init.static 초기화 블록 ------------- 객체 생성과 로딩");

	}

	public static Service get(String uri) {

		return serviceMap.get(uri);
	}

}
