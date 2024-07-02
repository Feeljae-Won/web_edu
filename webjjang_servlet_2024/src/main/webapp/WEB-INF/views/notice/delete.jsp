<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 여기는 자바입니다.

// 데이터 수집
String noStr = request.getParameter("no");
String pw = request.getParameter("pw");

// 넘어오는 데이터 확인
System.out.println("no = " + noStr + ", pw = " + pw);

// 글 삭제 처리
System.out.println("일반 게시판 글삭제 처리 : delete.jsp");

// 자동으로 리스트로 이동시킨다.
response.sendRedirect("list.do");
%>