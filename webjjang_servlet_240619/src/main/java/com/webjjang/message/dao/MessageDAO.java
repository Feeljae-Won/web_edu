package com.webjjang.message.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.webjjang.board.vo.BoardVO;
import com.webjjang.main.dao.DAO;
import com.webjjang.message.vo.MessageVO;
import com.webjjang.util.db.DB;
import com.webjjang.util.page.PageObject;

public class MessageDAO extends DAO {

	// 필요한 객체 선언 -> 상속
	// 접속 정보 -> DB 클래스 사용 - connection 을 가져오게 하는 메서드만 이용

	// 1-1. 리스트 처리
	// BoardController - (execute) - BoardListService - [BoardDAO.list()]
	public List<MessageVO> list(PageObject pageObject) throws Exception {
		// 결과를 저장할 수 있는 변수 선언.
		List<MessageVO> list = null;

		try {
			// 1. 드라이버 확인 - DB 클래스에서 확인 완료
			// 2. 오라클 접속
			con = DB.getConnection();
			// 3. sql 문 - 아래 LIST - 콘솔 확인하고 여기에 쿼리에 해당되는 LIST 출력
			System.out.println("sql : " + getListSQL(pageObject));
			// 4. 실행 객체 선언
			int idx = 0; // pstmt의 순서번호 사용. 먼저 1 증가하고 사용한다.
			pstmt = con.prepareStatement(getListSQL(pageObject));
			// 검색에 대한 데이터 세팅 - list() 만 사용
			// 메세지 본인 정보 세팅 - 모드 데이터 세팅
			pstmt.setString(++idx, pageObject.getAccepter());
			if(pageObject.getAcceptMode() == 3)
				pstmt.setString(++idx, pageObject.getAccepter());
			// 검색 데이터 세팅
			idx = setSearchData(pageObject, pstmt, idx);
			// 페이지 정보 세팅
			pstmt.setLong(++idx, pageObject.getStartRow()); // 기본값 = 1
			pstmt.setLong(++idx, pageObject.getEndRow()); // 기본값 = 10
			// 5. 실행 객체 실행
			rs = pstmt.executeQuery();
			// 6. 데이터 표시 또는 담기
			if (rs != null) {
				while (rs.next()) {
					// rs -> VO -> list
					// list 가 null 이면 생성해서 저장할 수 있게 해줘야 한다.
					if (list == null)
						list = new ArrayList<>();
					// rs -> VO
					MessageVO vo = new MessageVO();
					vo.setNo(rs.getLong("no"));
					vo.setContent(rs.getString("content"));
					vo.setSenderId(rs.getString("senderId"));
					vo.setSenderName(rs.getString("senderName"));
					vo.setSenderPhoto(rs.getString("senderPhoto"));
					vo.setSendDate(rs.getString("sendDate"));
					vo.setAccepterId(rs.getString("accepterId"));
					vo.setAccepterName(rs.getString("accepterName"));
					vo.setAccepterPhoto(rs.getString("accepterPhoto"));
					vo.setAcceptDate(rs.getString("acceptDate"));

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
	// MessageController - (execute) - MessageListService - [MessageDAO.view()]
	public Long getTotalRow(PageObject pageObject) throws Exception {
		// 결과를 저장할 수 있는 변수 선언.
		Long totalRow = null;

		try {
			// 1. 드라이버 확인 - DB 클래스에서 확인 완료
			// 2. 오라클 접속
			con = DB.getConnection();
			// 3. sql 문 - 아래 LIST
			System.out.println("sql : " + TOTALROW + getModeSql(pageObject) + getSearch(pageObject));
			// 4. 실행 객체 선언
			pstmt = con.prepareStatement(TOTALROW + getModeSql(pageObject) + getSearch(pageObject));
			int idx = 0;
			pstmt.setString(++idx, pageObject.getAccepter());
			if(pageObject.getAcceptMode() == 3)
				pstmt.setString(++idx, pageObject.getAccepter());
			idx = setSearchData(pageObject, pstmt, idx);
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
	
	// 1-3. 리스트 처리
		// BoardController - (execute) - BoardListService - [BoardDAO.list()]
		public List<BoardVO> rnumList(Long rnum) throws Exception {
			// 결과를 저장할 수 있는 변수 선언.
			List<BoardVO> list = null;

			try {
				// 1. 드라이버 확인 - DB 클래스에서 확인 완료
				// 2. 오라클 접속
				con = DB.getConnection();
				// 3. sql 문 - 아래 LIST - 콘솔 확인하고 여기에 쿼리에 해당되는 LIST 출력
				System.out.println("sql : " + RNUMLIST);
				// 4. 실행 객체 선언
//				pstmt = con.prepareStatement(LIST);
				pstmt = con.prepareStatement(RNUMLIST);
				// 검색에 대한 데이터 세팅 - list() 만 사용
				pstmt.setLong(1, rnum);
				pstmt.setLong(2, rnum);
				// 5. 실행 객체 실행
				rs = pstmt.executeQuery();
				// 6. 데이터 표시 또는 담기
				if (rs != null) {
					while (rs.next()) {
						// rs -> VO -> list
						// list 가 null 이면 생성해서 저장할 수 있게 해줘야 한다.
						if (list == null)
							list = new ArrayList<BoardVO>();
						// rs -> VO
						BoardVO rvo = new BoardVO();
						rvo.setRnum(rs.getLong("rnum"));
						rvo.setNo(rs.getLong("no"));
						rvo.setTitle(rs.getString("title"));
						rvo.setWriter(rs.getString("writer"));
						rvo.setWriteDate(rs.getString("writeDate"));
						rvo.setHit(rs.getLong("hit"));
						rvo.setRnum(rs.getLong("rnum"));

						// vo -> list
						list.add(rvo);
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

	// 2. 상세보기 처리
	// BoardController - (execute) - BoardListService - [BoardDAO.view()]
	public BoardVO view(Long no) throws Exception {
		// 결과를 저장할 수 있는 변수 선언.
		BoardVO vo = null;

		try {
			// 1. 드라이버 확인 - DB 클래스에서 확인 완료
			// 2. 오라클 접속
			con = DB.getConnection();
			// 3. sql 문 - 아래 LIST
			System.out.println("sql : " + VIEW);
			// 4. 실행 객체 선언
			pstmt = con.prepareStatement(VIEW);
			pstmt.setLong(1, no);
			// 5. 실행 객체 실행
			rs = pstmt.executeQuery();
			// 6. 데이터 표시 또는 담기
			if (rs != null && rs.next()) {
				// rs -> VO
				vo = new BoardVO();
				vo.setNo(rs.getLong("no"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setWriter(rs.getString("writer"));
				vo.setWriteDate(rs.getString("writeDate"));
				vo.setHit(rs.getLong("hit"));

			} // end of if

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("예외 발생 : 일반 게시판 글보기 DB 처리 중 오류 발생"); // 오류 나면 던진다
		} finally {
			// 7. 닫기
			DB.close(con, pstmt, rs);
		} // end of try ~ finally

		// 결과 데이터를 리턴 해 준다.
		return vo;

	} // end of view

	// 3-1. 글 등록
	// MessageController - (execute) - MessageListService - [MessageDAO.write()]
	public int write(MessageVO vo) throws Exception {
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
			pstmt.setString(1, vo.getContent());
			pstmt.setString(2, vo.getSenderId());
			pstmt.setString(3, vo.getAccepterId());
			// 5. 실행 객체 실행
			result = pstmt.executeUpdate();
			// 6. 데이터 표시 또는 담기
			System.out.println();
			System.out.println(result + "개 메세지 전송 성공");

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("예외 발생 : 메세지 전송 DB 처리 중 예외가 발생했습니다."); // 오류 나면 던진다
		} finally {
			// 7. 닫기
			DB.close(con, pstmt);
		} // end of try ~ finally

		// 결과 데이터를 리턴 해 준다.
		return result;

	} // end of write
	
	// 3-2. 새로운 메세지가 전달된 회원 테이블의 새로운 메세지 개수를 1증가 시킨다.
		// BoardController - (execute) - BoardListService - Board.DAO.increase()]
		public int increaseNewMsgCnt(String id) throws Exception {

			// 결과를 저장할 수 있는 변수 선언.
			int result = 0;

			try {
				// 1. 드라이버 확인 - DB 클래스에서 확인 완료
				// 2. 오라클 접속
				con = DB.getConnection();
				// 3. sql 문 - 아래 LIST
				System.out.println("sql : " + INCREASENEWMSGCNT);
				// 4. 실행 객체 선언 & 데이터 세팅
				pstmt = con.prepareStatement(INCREASENEWMSGCNT);
				pstmt.setString(1, id);
				// 5. 실행 객체 실행 -> executeUpdate() -> int 결과가 나옴.
				result = pstmt.executeUpdate();
				// 6. 데이터 표시 또는 담기
				if (result == 0) { // 글 번호가 존재하지 않는다. -> 예외로 처리한다.
					throw new Exception("예외 발생 : 아이디가 존재하지 않습니다. 글 번호를 확인해 주세요.");
				} // end of if

			} catch (Exception e) {
				e.printStackTrace();
				// 특별한 예외는 그냥 전달한다.
				if (e.getMessage().indexOf("예외 발생") >= 0)
					throw e; // 오류 나면 던진다
				// 그 외 처리 중 나타나는 오류에 대해서 사용자가 볼 수 잇는 예외로 만들어 전달한다.
				else
					throw new Exception("예외 발생 : 새로운 메세지 증가 DB 처리 중 예외가 발생했습니다.");
			} finally {
				// 7. 닫기
				DB.close(con, pstmt);
			} // end of try ~ finally

			// 결과 데이터를 리턴 해 준다.
			return result;
		} // end of increase()

	// 4. 글 수정 처리
	// BoardController - (execute) - BoardListService - Board.DAO.increase()]
	public int update(BoardVO vo) throws Exception {

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
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getWriter());
			pstmt.setLong(4, vo.getNo());
			pstmt.setString(5, vo.getPw());
			// 5. 실행 객체 실행 -> executeUpdate() -> int 결과가 나옴.
			result = pstmt.executeUpdate();
			// 6. 데이터 표시 또는 담기
			if (result == 0) { // 글 번호가 존재하지 않는다. -> 예외로 처리한다.
				throw new Exception("예외 발생 : 글 번호나 비밀번호가 맞지 않습니다. 정보를 확인해 주세요.");
			} // end of if

		} catch (Exception e) {
			e.printStackTrace();
			// 특별한 예외는 그냥 전달한다.
			if (e.getMessage().indexOf("예외 발생") >= 0)
				throw e; // 오류 나면 던진다
			// 그 외 처리 중 나타나는 오류에 대해서 사용자가 볼 수 잇는 예외로 만들어 전달한다.
			else
				throw new Exception("예외 발생 : 게시판 글 수정 DB 처리 중 예외가 발생했습니다.");
		} finally {
			// 7. 닫기
			DB.close(con, pstmt);
		} // end of try ~ finally

		// 결과 데이터를 리턴 해 준다.
		return result;
	} // end of update()

	// 5. 글 삭제
	// BoardController - (execute) - BoardListService - [BoardDAO.view()]
	public int delete(BoardVO vo) throws Exception {
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
			pstmt.setLong(1, vo.getNo());
			pstmt.setString(2, vo.getPw());
			// 5. 실행 객체 실행
			result = pstmt.executeUpdate();
			// 6. 데이터 표시 또는 담기
			System.out.println();
			System.out.println(result + "개 글 삭제 성공");

		} catch (Exception e) {
			e.printStackTrace();
			// 특별한 예외는 그냥 전달한다.
			if (result == 0)
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
	final String LIST = " "
			+ "	select rnum, no, content, senderId, senderName, senderPhoto, sendDate, accepterId, accepterName, accepterPhoto, acceptDate " 
			+ " from ( "
				+ " select rownum rnum, no, content, senderId, senderName, sendDate, senderPhoto, accepterId, accepterName, accepterPhoto, acceptDate " 
					+ " from ( "
						+ "	select m.no, m.content, m.senderId, sm.name senderName, sm.photo senderPhoto, "
						+ "	to_char(m.sendDate, 'yyyy-mm-dd') sendDate, m.accepterId, am.name accepterName, am.photo accepterPhoto, "
						+ " to_char(m.acceptDate, 'yyyy-mm-dd') acceptDate " 
						+ " from message m, member sm, member am ";
						// mode 조건 처리 + 검색 조건 처리 + 조인 조건 처리
	
	final String RNUMLIST = "select rnum, no, title, writer, writeDate, hit "
			+ " from (select rownum rnum, no, title, writer, writeDate, hit"
				+ " from (select no, title, writer, to_char(writeDate, 'yyyy-mm-dd') writeDate, hit"
					+ " from board order by no desc) ) "
			+ " where rnum = ? -1 "
				+ " or rnum = ? +1 ";
	

	// 검색이 있는 경우 TOTALROW + search
	final String TOTALROW = "select count(*) from message ";
	
	// LIST에 검색을 처리해서 만들어지는 sql문 작성 메서드
	private String getListSQL(PageObject pageObject) {
		String sql = LIST;
		
		// mode 조건 처리. 1-받은 메시지, 2-보낸 메시지, 3-모든 메시지
		sql += getModeSql(pageObject);
		// 검색 조건 처리
		sql += getSearch(pageObject);
		// 조인 조건 처리
		sql += getJoin();
		// 정렬 조건 처리
		sql += " order by no desc" + " ) "
				+ " ) where rnum between ? and ? ";
		
		return sql;
	}
	
	// 메시지 조인조건 메소드
	private String getJoin() {
		String sql = " ";
		sql += " and (  ";
		sql += " m.accepterId = am.id and m.senderId = sm.id  ";
		sql += " )  ";
		return sql;
	}
	
	// 가져온 메시지 모드에 대한 처리
	private String getModeSql(PageObject pageObject) {
		// flase 이므로 뒤에 조건에서 결정
		String sql = " where ( 1=0 ";
		// pageObject 안에 메시지 모드가 있음.
		int mode = pageObject.getAcceptMode();
		
		// 받은 메시지, 모든 메시지
		if(mode == 1 || mode == 3)
			sql += " or accepterId = ? ";
		// 보낸 메시지, 모든 메시지
		if(mode == 2 || mode == 3)
			sql += " or senderId = ? ";
		
		sql += " ) ";
		return sql;
	}
	
	// 리스트의 검색만 처리하는 쿼리 - where
	private String getSearch(PageObject pageObject) {
		// where 뒤에 false를 붙힌다. 뒤에가 true 면 true
		String sql = "";
		String key = pageObject.getKey();
		String word = pageObject.getWord();
		// key 안에 t가 포함되어 있으면 title로 검생을 한다.
		if(word != null && !word.equals("")) {
			sql += " and ( 1=0 ";
			if(key.indexOf("t") >= 0) sql += " or title like ? ";
			if(key.indexOf("c") >= 0) sql += " or content like ? ";
			if(key.indexOf("w") >= 0) sql += " or writer like ? ";
			sql += " ) ";
		}
		return sql;
	}
	
	// 검색 쿼리의 ?(물음표) 데이터를 세팅하는 메서드
	private int setSearchData(PageObject pageObject, PreparedStatement pstmt, int idx) throws SQLException {

		String key = pageObject.getKey();
		String word = pageObject.getWord();
		
		if(word != null && !word.equals("")) {
			// key 안에 t가 포함되어 있으면 title로 검생을 한다.
			// % + 데이터 + % -> like 연산자
			if(key.indexOf("t") >= 0) pstmt.setString(++idx, "%" + word + "%");;
			if(key.indexOf("c") >= 0) pstmt.setString(++idx, "%" + word + "%");;
			if(key.indexOf("w") >= 0) pstmt.setString(++idx, "%" + word + "%");;
		}
		return idx;
	}
	
	final String INCREASENEWMSGCNT = "update member set newMsgCnt = newMsgCnt + 1 " + " where id = ?";

	final String VIEW = "select no, title, content, writer, " + " to_char(writeDate, 'yyyy-mm-dd') writeDate, hit "
			+ " from board where no = ?";

	final String WRITE = "insert into message( no, content, senderId, accepterId) "
			+ " values (message_seq.nextval, ?, ?, ?) ";

	final String UPDATE = "update board set " + " title = ?, content = ?, writer = ?" + " where no = ? and pw = ? ";

	final String DELETE = "delete from board " + " where no = ? and pw = ? ";

} // end of class
