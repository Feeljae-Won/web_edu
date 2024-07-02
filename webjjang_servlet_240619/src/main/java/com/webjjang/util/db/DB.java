package com.webjjang.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {
	
	
	// DB 연결 관련 정보
	private static final String DRIVER = "oracle.jdbc.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String ID = "java";
	private static final String PW = "java";
	
	static {
		try {
			Class.forName(DRIVER);
			System.out.println("1. 드라이버 확인 완료");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("** 드라이버가 없으므로 프로그램을 종료 합니다.");
//			System.exit(1);
		}
	}
	
	public static final Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, ID, PW);
	}
	
	// 사용한 객체 닫기(자원을 반환)
	public static final void close(Connection con, PreparedStatement pstmt) throws SQLException {
		if(con != null) con.close();
		if(pstmt != null) pstmt.close();
	}
	
	public static final void close(Connection con, PreparedStatement pstmt, ResultSet rs) throws SQLException {
		close(con, pstmt);
		if(rs != null )rs.close();
	}
	
} // end of class
