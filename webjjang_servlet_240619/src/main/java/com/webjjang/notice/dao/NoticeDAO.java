package com.webjjang.notice.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.webjjang.notice.vo.NoticeVO;
import com.webjjang.main.dao.DAO;
import com.webjjang.util.db.DB;
import com.webjjang.util.page.PageObject;

public class NoticeDAO extends DAO{

	// 필요한 객체 선언 -> 상속
	// 접속 정보 -> DB 클래스 사용 - connection 을 가져오게 하는 메서드만 이용
	
	
	// 1. 리스트 처리
	// NoticeController - (execute) - NoticeListService - [NoticeDAO.list()]
	public List<NoticeVO> list(PageObject pageObject) throws Exception {
		// 결과를 저장할 수 있는 변수 선언.
		List<NoticeVO> list = null;
		
		try {
			// 1. 드라이버 확인 - DB 클래스에서 확인 완료
			// 2. 오라클 접속
			con = DB.getConnection();
			// 3. sql 문 - 아래 LIST
			System.out.println("sql : " + getListSQL(pageObject));
			// 4. 실행 객체 선언
			pstmt = con.prepareStatement(getListSQL(pageObject));
			// 검색에 대한 데이터 세팅 - list() 만 사용
			int idx = 0; // pstmt의 순서번호 사용. 먼저 1 증가하고 사용한다.
			idx = setSearchData(pageObject, pstmt, idx);
			pstmt.setLong(++idx, pageObject.getStartRow());
			pstmt.setLong(++idx, pageObject.getEndRow());
			// 5. 실행 객체 실행
			rs = pstmt.executeQuery();
			// 6. 데이터 표시 또는 담기
			if (rs != null) {
				while (rs.next()) {
					// rs -> VO -> list
					// list 가 null 이면 생성해서 저장할 수 있게 해줘야 한다.
					if(list == null) list = new ArrayList<NoticeVO>();
					// rs -> VO
					NoticeVO vo = new NoticeVO();
					vo.setNo(rs.getLong("no"));
					vo.setTitle(rs.getString("title"));
					vo.setStartDate(rs.getString("startDate"));
					vo.setEndDate(rs.getString("endDate"));
					vo.setUpdateDate(rs.getString("updateDate"));
					
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
		// NoticeController - (execute) - NoticeListService - [NoticeDAO.view()]
		public Long getTotalRow(PageObject pageObject) throws Exception {
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
	
	
	// 2. 상세보기 처리
	// NoticeController - (execute) - NoticeListService - [NoticeDAO.view()]
	public NoticeVO view(Long no) throws Exception {
		// 결과를 저장할 수 있는 변수 선언.
		NoticeVO vo = null;
		
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
				vo = new NoticeVO();
				vo.setNo(rs.getLong("no"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setStartDate(rs.getString("startDate"));
				vo.setEndDate(rs.getString("endDate"));
				vo.setUpdateDate(rs.getString("updateDate"));
				vo.setWriteDate(rs.getString("writeDate"));
					
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
		
	
	// 3. 글 등록
	// NoticeController - (execute) - NoticeListService - [NoticeDAO.view()]
	public int write(NoticeVO vo) throws Exception {
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
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getStartDate());
			pstmt.setString(4, vo.getEndDate());
			
			// 5. 실행 객체 실행
			result = pstmt.executeUpdate();
			// 6. 데이터 표시 또는 담기
			System.out.println();
			System.out.println(result + "개 공지 등록 성공");
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("예외 발생 : 공지 등록 DB 처리 중 예외가 발생했습니다."); // 오류 나면 던진다
			} finally {
				// 7. 닫기
				DB.close(con, pstmt);
			} // end of try ~ finally
			
			// 결과 데이터를 리턴 해 준다.
			return result;
			
		} // end of write
	
	// 4. 글 수정 처리
	// NoticeController - (execute) - NoticeListService - Notice.DAO.increase()]
	public int update(NoticeVO vo) throws Exception {
		
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
				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContent());
				pstmt.setString(3, vo.getStartDate());
				pstmt.setString(4, vo.getEndDate());
				pstmt.setLong(5, vo.getNo());
				// 5. 실행 객체 실행 -> executeUpdate() -> int 결과가 나옴.
				result = pstmt.executeUpdate();
				// 6. 데이터 표시 또는 담기
				if (result == 0) { // 글 번호가 존재하지 않는다. -> 예외로 처리한다.
					throw new Exception("예외 발생 : 글 번호나 비밀번호가 맞지 않습니다. 정보를 확인해 주세요.");
				} // end of if
				
			} catch (Exception e) {
				e.printStackTrace();
				// 특별한 예외는 그냥 전달한다.
				if(e.getMessage().indexOf("예외 발생") >= 0) throw e; // 오류 나면 던진다
				// 그 외 처리 중 나타나는 오류에 대해서 사용자가 볼 수 잇는 예외로 만들어 전달한다.
				else throw new Exception("예외 발생 : 게시판 글 수정 DB 처리 중 예외가 발생했습니다.");
			} finally {
				// 7. 닫기
				DB.close(con, pstmt);
			} // end of try ~ finally
			
			// 결과 데이터를 리턴 해 준다.
			return result;
	} // end of update()
	
	
	// 5. 글 삭제
	// NoticeController - (execute) - NoticeListService - [NoticeDAO.view()]
	public int delete(NoticeVO vo) throws Exception {
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
			// 5. 실행 객체 실행
			result = pstmt.executeUpdate();
			// 6. 데이터 표시 또는 담기
			System.out.println();
			System.out.println(result + "개 글 삭제 성공");
				
			} catch (Exception e) {
				e.printStackTrace();
				// 특별한 예외는 그냥 전달한다.
				if(result == 0); // 오류 나면 던진다
				// 그 외 처리 중 나타나는 오류에 대해서 사용자가 볼 수 잇는 예외로 만들어 전달한다.
					throw new Exception("예외 발생 : 게시판 글 삭제 DB 처리 중 예외가 발생했습니다.");
			} finally {
				// 7. 닫기
				DB.close(con, pstmt);
			} // end of try ~ finally
			
			// 결과 데이터를 리턴 해 준다.
			return result;
			
		} // end of write
		
	
	
	// LIST의 페이지 처리 절차 - 원본데이터(select) -> 순서 번호(select) -> 해당 페이지 데이터만 가져온다.(select)
	final String LIST = ""
			+ " select no, title, startDate, endDate, updateDate "
			+ " from ( "
				+ " select rownum rnum, no, title, startDate, endDate, updateDate "
				+ " from ( "
					+ "select no, title, "
					+ " to_char(startDate, 'yyyy-mm-dd') startDate, "
					+ " to_char(endDate, 'yyyy-mm-dd') endDate, "
					+ " to_char(updateDate, 'yyyy-mm-dd') updateDate "
					+ " from notice "
					+ " where (1 = 1) "
					;
					//검색부분
			// LIST에 검색을 처리해서 만들어지는 sql문 작성 메서드
			private String getListSQL(PageObject pageObject) {
				String sql = LIST;
				sql += getSearch(pageObject);
				sql += getPeriod(pageObject);
				sql += " order by updateDate desc, endDate desc, no desc" + " ) "
						+ " ) where rnum between ? and ? ";
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
					sql += " and ( 1=0   ";
					if(key.indexOf("t") >= 0) sql += " or title like ? ";
					if(key.indexOf("c") >= 0) sql += " or content like ? ";
					sql += " ) ";
				}
				return sql;
			}
			
			// 리스트의 기간 검색만 처리하는 쿼리 - where
			private String getPeriod(PageObject pageObject) {
				// where 뒤에 false를 붙힌다. 뒤에가 true 면 true
				String sql = "";
				String period = pageObject.getPeriod();
				sql += " and ( 1=1   ";
				// period에 따라서 기간을 검색한다.
				// 현재공지
				if(period.equals("pre")) sql += " and trunc(sysdate) between trunc(startDate) and trunc(endDate) ";
				// 지난 공지
				else if(period.equals("old")) sql += " and trunc(sysdate) > trunc(endDate) ";
				// 예약공지	
				else if(period.equals("res")) sql += " and trunc(sysdate) between trunc(startDate) and trunc(endDate) ";
				// 모든 공지 - 조건 없음
				else sql+= "";
				sql += " ) ";
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
				}
				return idx;
			}	
			
	
	final String TOTALROW = "select count(*) from notice where ( 1=1 ) ";
	
	final String VIEW = "select no, title, content,  "
			+ " to_char(updateDate, 'yyyy-mm-dd') updateDate, " 
			+ " to_char(startDate, 'yyyy-mm-dd') startDate, " 
			+ " to_char(endDate, 'yyyy-mm-dd') endDate, "  
			+ " to_char(writeDate, 'yyyy-mm-dd') writeDate "  
			+ " from notice where no = ?";
	
	final String WRITE = "insert into notice(no, title, content, startDate, endDate) "
			+ "values (notice_seq.nextval, ?, ?, ?, ?)";
	
	final String UPDATE = "update notice set "
			+ " title = ?, content = ?, startDate = ?, endDate = ?, updateDate = sysDate "
			+ " where no = ?";
	
	final String DELETE = "delete from notice "
			+ " where no = ?";
	


} // end of class
