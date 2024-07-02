package com.webjjang.boardreply.dao;

import java.util.ArrayList;
import java.util.List;

import javax.el.ELException;

import com.webjjang.board.vo.BoardVO;
import com.webjjang.boardreply.vo.BoardReplyVO;
import com.webjjang.main.dao.DAO;
import com.webjjang.util.db.DB;
import com.webjjang.util.page.PageObject;
import com.webjjang.util.page.ReplyPageObject;

public class BoardReplyDAO extends DAO {

	// 필요한 객체 선언 -> 상속
	// 접속 정보 -> DB 클래스 사용 - connection 을 가져오게 하는 메서드만 이용

	// 1-1. 리스트 처리
	// BoardController - (execute) - BoardListService - [BoardDAO.list()]
	public List<BoardReplyVO> list(ReplyPageObject pageObject) throws Exception {
		// 결과를 저장할 수 있는 변수 선언.
		List<BoardReplyVO> list = null;

		try {
			// 1. 드라이버 확인 - DB 클래스에서 확인 완료
			// 2. 오라클 접속
			con = DB.getConnection();
			// 3. sql 문 - 아래 LIST - 콘솔 확인하고 여기에 쿼리에 해당되는 LIST 출력
			System.out.println("sql : " + LIST);
			// 4. 실행 객체 선언
			pstmt = con.prepareStatement(LIST);
			pstmt.setLong(1, pageObject.getNo()); // 게시글 번호
			pstmt.setLong(2, pageObject.getStartRow()); // 기본값 1
			pstmt.setLong(3, pageObject.getEndRow()); // 기본값 10
			// 5. 실행 객체 실행
			rs = pstmt.executeQuery();
			// 6. 데이터 표시 또는 담기
			if (rs != null) {
				while (rs.next()) {
					// rs -> VO -> list
					// list 가 null 이면 생성해서 저장할 수 있게 해줘야 한다.
					if (list == null)
						list = new ArrayList<BoardReplyVO>();
					// rs -> VO
					BoardReplyVO vo = new BoardReplyVO();
					vo.setRno(rs.getLong("rno"));
					vo.setNo(rs.getLong("no"));
					vo.setContent(rs.getString("content"));
					vo.setWriter(rs.getString("writer"));
					vo.setWriteDate(rs.getString("writeDate"));

					// vo -> list
					list.add(vo);
				} // end of while
			} // end of if

		} catch (Exception e) {
			e.printStackTrace();
			throw e; // 오류 나면 던진다
		} finally {
			// 7. 닫기
			DB.close(con, pstmt, rs);
		} // end of try ~ finally

		// 결과 데이터를 리턴 해 준다.
		return list;

	} // end of List

	// 1-2. 전체 데이터 개수 가져오기
	// BoardController - (execute) - BoardListService - [BoardDAO.view()]
	public Long getTotalRow(ReplyPageObject pageObject) throws Exception {
		// 결과를 저장할 수 있는 변수 선언.
		Long totalRow = null;

		try {
			// 1. 드라이버 확인 - DB 클래스에서 확인 완료
			// 2. 오라클 접속
			con = DB.getConnection();
			// 3. sql 문 - 아래 LIST
			System.out.println("sql : " + TOTALROW);
			// 4. 실행 객체 선언
			pstmt = con.prepareStatement(TOTALROW);
			pstmt.setLong(1, pageObject.getNo());
			// 5. 실행 객체 실행
			rs = pstmt.executeQuery();
			// 6. 데이터 표시 또는 담기
			if (rs != null && rs.next()) {
				totalRow = rs.getLong(1);
			} // end of if
		} catch (Exception e) {
			e.printStackTrace();
			throw e; // 오류 나면 던진다
		} finally {
			// 7. 닫기
			DB.close(con, pstmt, rs);
		} // end of try ~ finally
		
		// 결과 데이터를 리턴 해 준다.
		return totalRow;
		
	} // end of getTotalRow

	// 2. 댓글 상세보기는 리스트에서 내용이 다 표시되어 필요하지 않다.

	// 3. 글 등록
	// BoardReplyController - (execute) - BoardReplyWriteService - [BoardReplyDAO.view()]
	public int write(BoardReplyVO vo) throws Exception {
		// 결과를 저장할 수 있는 변수 선언.
		int result = 0;

		try {
			// 1. 드라이버 확인 - DB 클래스에서 확인 완료
			// 2. 오라클 접속
			con = DB.getConnection();
			// 3. sql 문 - 아래 LIST
			System.out.println("sql : " + WRITE);
			// 4. 실행 객체 선언
			pstmt = con.prepareStatement(WRITE);
			pstmt.setLong(1, vo.getNo());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getWriter());
			pstmt.setString(4, vo.getPw());
			// 5. 실행 객체 실행
			result = pstmt.executeUpdate();
			// 6. 데이터 표시 또는 담기
			System.out.println();
			System.out.println("--------------------------" + result + "개 댓글 등록 성공");

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("예외 발생 : 게시판 댓글 등록 DB 처리 중 예외가 발생했습니다."); // 오류 나면 던진다
		} finally {
			// 7. 닫기
			DB.close(con, pstmt);
		} // end of try ~ finally

		// 결과 데이터를 리턴 해 준다.
		return result;

	} // end of write

	// 4. 글 수정 처리
	// BoardController - (execute) - BoardListService - Board.DAO.increase()]
	public int update(BoardReplyVO vo) throws Exception {

		// 결과를 저장할 수 있는 변수 선언.
		int result = 0;

		try {
			// 1. 드라이버 확인 - DB 클래스에서 확인 완료
			// 2. 오라클 접속
			con = DB.getConnection();
			// 3. sql 문
			System.out.println("sql : " + UPDATE);
			// 4. 실행 객체 선언 & 데이터 세팅
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, vo.getContent());
			pstmt.setString(2, vo.getWriter());
			pstmt.setLong(3, vo.getRno());
			pstmt.setString(4, vo.getPw());
			// 5. 실행 객체 실행 -> executeUpdate() -> int 결과가 나옴.
			result = pstmt.executeUpdate();
			// 6. 데이터 표시 또는 담기
			if (result == 0) { // 글 번호가 존재하지 않는다. -> 예외로 처리한다.
				throw new Exception("예외 발생 : 댓글 번호나 비밀번호가 맞지 않습니다. 정보를 확인해 주세요.");
			} // end of if

		} catch (Exception e) {
			e.printStackTrace();
			// 특별한 예외는 그냥 전달한다.
			if (e.getMessage().indexOf("예외 발생") >= 0)
				throw e; // 오류 나면 던진다
			// 그 외 처리 중 나타나는 오류에 대해서 사용자가 볼 수 잇는 예외로 만들어 전달한다.
			else
				throw new Exception("예외 발생 : 게시판 댓글 수정 DB 처리 중 예외가 발생했습니다.");
		} finally {
			// 7. 닫기
			DB.close(con, pstmt);
		} // end of try ~ finally

		// 결과 데이터를 리턴 해 준다.
		return result;
	} // end of update()

	// 5. 글 삭제
	// BoardController - (execute) - BoardListService - [BoardDAO.view()]
	public int delete(BoardReplyVO vo) throws Exception {
		// 결과를 저장할 수 있는 변수 선언.
		int result = 0;

		try {
			// 1. 드라이버 확인 - DB 클래스에서 확인 완료
			// 2. 오라클 접속
			con = DB.getConnection();
			// 3. sql 문 - 아래 LIST
			System.out.println("sql : " + DELETE);
			// 4. 실행 객체 선언
			pstmt = con.prepareStatement(DELETE);
			pstmt.setLong(1, vo.getRno());
			pstmt.setString(2, vo.getPw());
			// 5. 실행 객체 실행
			result = pstmt.executeUpdate();
			// 6. 데이터 표시 또는 담기
			System.out.println();
			System.out.println(result + "개 글 삭제 성공");

		} catch (Exception e) {
			e.printStackTrace();
			// 특별한 예외는 그냥 전달한다.
			if (result == 0) throw new ELException("예외 발생 : 댓글번호나 비밀번호가 맞지 않습니다.");
				; // 오류 나면 던진다
			// 그 외 처리 중 나타나는 오류에 대해서 사용자가 볼 수 잇는 예외로 만들어 전달한다.
			throw new Exception("예외 발생 : 게시판 글 삭제 DB 처리 중 예외가 발생했습니다.");
		} finally {
			// 7. 닫기
			DB.close(con, pstmt);
		} // end of try ~ finally

		// 결과 데이터를 리턴 해 준다.
		return result;

	} // end of write

	// 실행할 쿼리를 정의해 놓은 변수 선언

	// LIST의 페이지 처리 절차 - 원본데이터(select) -> 순서 번호(select) -> 해당 페이지 데이터만 가져온다.(select)
	final String LIST = " select rno, no, content, writer, writeDate " 
			+ " from ( "
					+ " select rownum rnum, rno, no, content, writer, writeDate " 
					+ " from ("
						+ "	select rno, no, content, writer, to_char(writeDate, 'yyyy-mm-dd') writeDate " 
						+ " from board_reply "
						+ " where no = ?"
						+ " order by rno desc) "
				+ " ) where rnum between ? and ? ";
	
	// 검색이 있는 경우 TOTALROW + search
	final String TOTALROW = " select count(*) from board_reply "
			+ "	where no = ? ";
	
	final String WRITE = " insert into board_reply( "
			+ " rno, no, content, writer, pw) values (board_reply_seq.nextval, ?, ?, ?, ?) ";

	final String UPDATE = " update board_reply set " + " content = ?, writer = ?" + " where rno = ? and pw = ? ";

	final String DELETE = " delete from board_reply " + " where rno = ? and pw = ? ";

} // end of class
