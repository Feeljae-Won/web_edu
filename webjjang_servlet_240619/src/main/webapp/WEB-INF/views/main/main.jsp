<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main</title>
<link rel="stylesheet" href="/css/main.css">
<script type="text/javascript" src="/js/main.js"></script>
</head>
<body>
<div class="container bg-light">
	<div class="row">
		<!-- 반응형 웹 : 한줄을 유지 되는 것 정의 : col-해상도-6 / 해상도 : md-중간,  lg-큰, xl-제일 큰  -->
		<div class="col-lg-6 module">
			<jsp:include page="noticeList.jsp"/>
		</div>
		<div class="col-lg-6 module">
			<jsp:include page="boardList.jsp"/>
		</div>
	</div>
	<hr>
	<div class="row">
		<div class="col-md-12 module">
			<jsp:include page="imageList.jsp"/>
		</div>
	</div>
	<hr>
	<div class="row">
		<div class="col-md-12 module">
			<jsp:include page="qnaList.jsp"/>
		</div>
	</div>
</div>
<p>
</body>
</html>