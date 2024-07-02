<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 여기는 자바입니다.
// 한글 처리
// request.setCharacterEncoding("utf-8");
// System.out.println("일반 게시판 글수정 처리 : update.jsp");
// // 데이터 수집
// String noStr = request.getParameter("no");
// String title = request.getParameter("title");
// String content = request.getParameter("content");
// String writer = request.getParameter("writer");
// String pw = request.getParameter("pw");
// // 넘어온 데이터 확인
// System.out.println("no = " + noStr);
// System.out.println("title = " + title);
// System.out.println("content = " + content);
// System.out.println("writer = " + writer);
// System.out.println("pw = " + pw);

// // DB에서 데이터 가져오기 - BoardViewService

// 자동으로 글보기로 이동시킨다.
response.sendRedirect("view.do?no=" + request.getParameter("no") + "&inc=0");
%>