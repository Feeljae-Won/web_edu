package com.webjjang.member.dao;

import java.util.ArrayList;
import java.util.List;

import com.webjjang.main.dao.DAO;
import com.webjjang.member.vo.LoginVO;
import com.webjjang.member.vo.MemberVO;
import com.webjjang.util.db.DB;

public class MemberDAO extends DAO {

	// 필요한 객체 선언 -> 상속
	// 접속 정보 -> DB 클래스 사용 - connection 을 가져오게 하는 메서드만 이용

	// 1. 회원 리스트 처리
	// MemberController - (execute) - MemberListService - [MemberDAO.list()]
	public List<MemberVO> list() throws Exception {
		// 결과를 저장할 수 있는 변수 선언.
		List<MemberVO> list = null;

		try {
			// 1. 드라이버 확인 - DB 클래스에서 확인 완료
			// 2. 오라클 접속
			con = DB.getConnection();
			// 3. sql 문 - 아래 LIST
			System.out.println("sql : " + LIST);
			// 4. 실행 객체 선언
			pstmt = con.prepareStatement(LIST);
			// 5. 실행 객체 실행
			rs = pstmt.executeQuery();
			// 6. 데이터 표시 또는 담기
			if (rs != null) {
				while (rs.next()) {
					// rs -> VO -> list
					// list 가 null 이면 생성해서 저장할 수 있게 해줘야 한다.
					if (list == null)
						list = new ArrayList<MemberVO>();
					// rs -> VO
					MemberVO vo = new MemberVO();
					vo.setId(rs.getString("id"));
					vo.setName(rs.getString("name"));
					vo.setBirth(rs.getString("birth"));
					vo.setTel(rs.getString("tel"));
					vo.setGradeNo(rs.getInt("gradeNo"));
					vo.setGradeName(rs.getString("gradeName"));
					vo.setStatus(rs.getString("status"));

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

	// 2. 회원 정보 보기 처리
	// MemberController - (execute) - MemberListService - [MemberDAO.view()]
	public MemberVO view(Object obj) throws Exception {
		// 결과를 저장할 수 있는 변수 선언.
		MemberVO vo = null;
		try {
			// 1. 드라이버 확인 - DB 클래스에서 확인 완료
			// 2. 오라클 접속
			con = DB.getConnection();
			// 3. sql 문 - 아래 LIST
			System.out.println("sql : " + VIEW);
			// 4. 실행 객체 선언
			pstmt = con.prepareStatement(VIEW);
			pstmt.setString(1, (String) obj);
			// 5. 실행 객체 실행
			rs = pstmt.executeQuery();
			// 6. 데이터 표시 또는 담기
			if (rs != null && rs.next()) {
				// rs -> VO
				vo = new MemberVO();
				vo.setId(rs.getString("id"));
				vo.setPw(rs.getString("pw"));
				vo.setName(rs.getString("name"));
				vo.setGender(rs.getString("gender"));
				vo.setBirth(rs.getString("birth"));
				vo.setTel(rs.getString("tel"));
				vo.setEmail(rs.getString("email"));
				vo.setRegDate(rs.getString("regDate"));
				vo.setConDate(rs.getString("conDate"));
				vo.setStatus(rs.getString("status"));
				vo.setPhoto(rs.getString("photo"));
				vo.setGradeName(rs.getString("gradeName"));

			} // end of if

		} catch (Exception e) {
			e.printStackTrace();
			throw e; // 오류 나면 던진다
		} finally {
			// 7. 닫기
			DB.close(con, pstmt, rs);
		} // end of try ~ finally

		// 결과 데이터를 리턴 해 준다.
		return vo;

	} // end of view

	// 3. 회원 등록 처리
	// MemberController - (execute) - MemberListService - [MemberDAO.view()]
	public int write(MemberVO vo) throws Exception {
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
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getGender());
			pstmt.setString(5, vo.getBirth());
			pstmt.setString(6, vo.getTel());
			pstmt.setString(7, vo.getEmail());
			pstmt.setString(8, vo.getPhoto());

			// 5. 실행 객체 실행
			result = pstmt.executeUpdate();
			// 6. 데이터 표시 또는 담기

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("예외 발생 : 회원 등록 DB 처리 중 예외가 발생했습니다."); // 오류 나면 던진다
		} finally {
			// 7. 닫기
			DB.close(con, pstmt);
		} // end of try ~ finally

		// 결과 데이터를 리턴 해 준다.
		return result;

	} // end of write

	// 3-1. 회원 아이디 중복 처리
	// MemberController - (execute) - MemberCheckIdService - [MemberDAO.checkId()]
	public String checkId(String id) throws Exception {
		// 결과(id)를 저장할 수 있는 변수 선언.
		String result = null;
		try {
			// 1. 드라이버 확인 - DB 클래스에서 확인 완료
			// 2. 오라클 접속
			con = DB.getConnection();
			// 3. sql 문 - 아래 LIST
			System.out.println("sql : " + CHECKID);
			// 4. 실행 객체 선언
			pstmt = con.prepareStatement(CHECKID);
			pstmt.setString(1, id);
			// 5. 실행 객체 실행
			rs = pstmt.executeQuery();
			// 6. 데이터 표시 또는 담기
			// 결과 데이터
			if (rs != null && rs.next()) {
				// result : 넘겨 줘야할 id
				result = rs.getString("id");
			} // end of if

		} catch (Exception e) {
			e.printStackTrace();
			throw e; // 오류 나면 던진다
		} finally {
			// 7. 닫기
			DB.close(con, pstmt, rs);
		} // end of try ~ finally

		// 결과 데이터 아이디를 리턴 해 준다.
		return result;

	} // end of checkId()

	// 4. 회원 정보 수정 처리
	// MemberController - (execute) - MemberListService - Member.DAO.update()]
	public int update(MemberVO vo) throws Exception {

		// 결과를 저장할 수 있는 변수 선언.
		int result = 0;

		try {
			// 1. 드라이버 확인 - DB 클래스에서 확인 완료
			// 2. 오라클 접속
			con = DB.getConnection();
			// 3. sql 문 - 아래 LIST
			System.out.println("sql : " + UPDATE);
			// 4. 실행 객체 선언 & 데이터 세팅
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getGender());
			pstmt.setString(3, vo.getBirth());
			pstmt.setString(4, vo.getTel());
			pstmt.setString(5, vo.getEmail());
			pstmt.setString(6, vo.getPhoto());
			pstmt.setString(7, vo.getId());
			pstmt.setString(8, vo.getPw());
			// 5. 실행 객체 실행 -> executeUpdate() -> int 결과가 나옴.
			result = pstmt.executeUpdate();
			// 6. 데이터 표시 또는 담기
			if (result == 0) { // 글 번호가 존재하지 않는다. -> 예외로 처리한다.
				throw new Exception("예외 발생 : 아이디나 비밀번호가 맞지 않습니다. 정보를 확인해 주세요.");
			} // end of if

		} catch (Exception e) {
			e.printStackTrace();
			// 특별한 예외는 그냥 전달한다.
			if (e.getMessage().indexOf("예외 발생") >= 0)
				throw e; // 오류 나면 던진다
			// 그 외 처리 중 나타나는 오류에 대해서 사용자가 볼 수 잇는 예외로 만들어 전달한다.
			else
				throw new Exception("예외 발생 : 회원 정보 수정 DB 처리 중 예외가 발생했습니다.");
		} finally {
			// 7. 닫기
			DB.close(con, pstmt);
		} // end of try ~ finally

		// 결과 데이터를 리턴 해 준다.
		return result;
	} // end of update()

	// 5. 회원 탈퇴 처리 : 상태 - 탈퇴로 수정
	// MemberController - (execute) - MemberDeleteService - [MemberDAO.view()]
	public int delete(MemberVO vo) throws Exception {
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
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			// 5. 실행 객체 실행
			result = pstmt.executeUpdate();
			// 6. 데이터 표시 또는 담기
			System.out.println();
			System.out.println("상태 변경 완료");

		} catch (Exception e) {
			e.printStackTrace();
			// 특별한 예외는 그냥 전달한다.
			if (result == 0)
				;
			System.out.println("예외 발생 : 아이디나 비밀번호가 맞지 않습니다. 다시 확인해 주세요. "); // 오류 나면 던진다
			// 그 외 처리 중 나타나는 오류에 대해서 사용자가 볼 수 잇는 예외로 만들어 전달한다.
			throw new Exception("예외 발생 : 회원 탈퇴 DB 처리 중 예외가 발생했습니다.");
		} finally {
			// 7. 닫기
			DB.close(con, pstmt);
		} // end of try ~ finally

		// 결과 데이터를 리턴 해 준다.
		return result;

	} // end of write

	// 6-1. 로그인 처리
	// MemberController - (execute) - MemberLoginService - [MemberDAO.login()]
	public LoginVO login(LoginVO loginvo) throws Exception {
		// 결과를 저장할 수 있는 변수 선언.
		LoginVO vo = null;

		try {
			// 1. 드라이버 확인 - DB 클래스에서 확인 완료
			// 2. 오라클 접속
			con = DB.getConnection();
			// 3. sql 문 - 아래 LIST
			System.out.println("sql : " + LOGIN);
			// 4. 실행 객체 선언
			pstmt = con.prepareStatement(LOGIN);
			pstmt.setString(1, loginvo.getId());
			pstmt.setString(2, loginvo.getPw());
			// 5. 실행 객체 실행
			rs = pstmt.executeQuery();
			// 6. 데이터 표시 또는 담기
			if (rs != null && rs.next()) {
				// rs -> VO
				vo = new LoginVO();
				vo.setId(rs.getString("id"));
				vo.setName(rs.getString("name"));
				vo.setGradeNo(rs.getInt("gradeNo"));
				vo.setGradeName(rs.getString("gradeName"));
				vo.setPhoto(rs.getString("photo"));
				vo.setNewMsgCnt(rs.getLong("newMsgCnt"));

			} // end of if
			if (vo == null) { // ID와 PW가 맞지 않는다. -> 예외로 처리
				throw new Exception("예외 발생 : ID나 PW가 맞지 않습니다. 다시 확인해 주세요.");
			} // end of if

		} catch (Exception e) {
			e.printStackTrace();
			// 특별한 예외는 그냥 전달한다.
			if (e.getMessage().indexOf("예외 발생") >= 0)
				throw e; // 오류 나면 던진다
			// 그 외 처리 중 나타나는 오류에 대해서 사용자가 볼 수 잇는 예외로 만들어 전달한다.
			else
				throw new Exception("예외 발생 : 로그인 처리 중 예외가 발생했습니다.");
		} finally {
			// 7. 닫기
			DB.close(con, pstmt, rs);
		} // end of try ~ finally

		// 결과 데이터를 리턴 해 준다.
		return vo;

	} // end of login

	// 6-2. 로그인 최근 접속일 업데이트
	// NoticeController - (execute) - NoticeListService - Notice.DAO.increase()]
	public int conUpdate(LoginVO vo) throws Exception {

		// 결과를 저장할 수 있는 변수 선언.
		int result = 0;

		try {
			// 1. 드라이버 확인 - DB 클래스에서 확인 완료
			// 2. 오라클 접속
			con = DB.getConnection();
			// 3. sql 문 - 아래 LIST
			System.out.println("sql : " + CONUPDATE);
			// 4. 실행 객체 선언 & 데이터 세팅
			pstmt = con.prepareStatement(CONUPDATE);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			// 5. 실행 객체 실행 -> executeUpdate() -> int 결과가 나옴.
			result = pstmt.executeUpdate();
			System.out.println("업데이트? : " + result);
			// 6. 데이터 표시 또는 담기
			if (result == 0) { // 로그인 정보가 존재하지 않는다. -> 예외로 처리한다.
				throw new Exception("예외 발생 : 로그인 정보가 없습니다.");
			} // end of if

		} catch (Exception e) {
			e.printStackTrace();
			// 특별한 예외는 그냥 전달한다.
			if (e.getMessage().indexOf("예외 발생") >= 0)
				throw e; // 오류 나면 던진다
			// 그 외 처리 중 나타나는 오류에 대해서 사용자가 볼 수 잇는 예외로 만들어 전달한다.
			else
				throw new Exception("예외 발생 : 최근 접속일 DB 처리 중 예외가 발생했습니다.");
		} finally {
			// 7. 닫기
			DB.close(con, pstmt);
		} // end of try ~ finally

		// 결과 데이터를 리턴 해 준다.
		return result;
	} // end of update()

	// 실행할 쿼리를 정의해 놓은 변수 선언
	final String LIST = "select m.id, m.name, to_char(m.birth, 'yyyy-mm-dd') birth, m.tel, m.gradeNo, g.gradeName, m.status "
			+ " from member m, grade g where m.gradeNo = g.gradeNo order by id desc";

	final String VIEW = "select m.id, m.pw, m.name, m.gender, to_char(m.birth, 'yyyy-mm-dd') birth, m.tel, m.email, "
			+ " m.regDate, m.conDate, m.status, m.photo, g.gradeName"
			+ " from member m, grade g where (id = ?) and (m.gradeNo = g.gradeNo)";

	final String WRITE = "insert into member(id, pw, name, gender, birth, tel, email, photo) "
			+ "values (?, ?, ?, ?, ?, ?, ?, ?)";
	
	final String CHECKID = "select id from member where id = ?";

	final String UPDATE = "update member set " + " name =  ?, gender = ?, birth = ?, tel = ?, email = ?, photo = ?"
			+ " where id = ? and pw = ?";

	final String DELETE = "update member set status = '탈퇴' where id = ? and pw = ?";

	final String LOGIN = "select m.id, m.name, m.gradeNo, g.gradeName, m.photo, m.newMsgCnt "
			+ " from member m, grade g " + " where (id = ? and pw = ? and status = '정상') and (g.gradeNo = m.gradeNo)";

	final String CONUPDATE = "update member set conDate = sysDate where id = ? and pw = ?";

} // end of class
