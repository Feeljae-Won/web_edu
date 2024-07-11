<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%
	// DispatcherServlet을 거치지 않고 들어온 경우
	// uri가 없어서 request에서 꺼내서 넣어둔다.
	if(session.getAttribute("uri") == null) {
		session.setAttribute("uri", request.getRequestURI());
		
		
	}
%>
<!-- sitemesh 미적용 페이지 - 웹 라이브러리 없음. -->

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error - noModule_404</title>
<!-- Bootstrap 4 + jquery 라이브러리 등록 -CDN 방식 -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<script	src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
<script	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

<!-- 아이콘 링크 등록 font awesome4, google -->
<link rel="stylesheet"	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<style type="text/css">
	#errorDiv > .row {
		padding: 10px;
		border-top: 1px dotted #aaa;
		margin: 0 10px;
	}
	#errorDiv {
/* 		padding: 10px; */
	}
</style>
</head>
<body>
	<div class="container">
		<div class="card">
			<div class="card-header bg-danger text-white">
				<i class="material-icons float-left" style="font-size:38px;">error </i>
				<h3> 요청 자원 오류(404)</h3>
			</div>
			<div class="card-body" id="errorDiv">
				<div class="row">
					<div class="col-md-2"><b><i class="fa fa-angle-right"></i> 요청 URI</b></div>
					<div class="col-md-10">${uri }</div>
				</div>
				<div class="row">
					<div class="col-md-2"><b><i class="fa fa-angle-right"></i> 오류 메세지</b></div>
					<div class="col-md-10">
						요청하신 페이지의 주소는 존재하지 않거나 지원하지 않습니다.
					</div>
				</div>
				<div class="row">
					<div class="col-md-2"><b><i class="fa fa-angle-right"></i> 조치 사항</b></div>
					<div class="col-md-10">
						요청하신 페이지의 주소를 확인하시고 다시 시도해 주세요.<br>
						오류가 계속 발생이 되면 전산 담당자에게 연락해 주세요.
					</div>
				</div>
			</div>
			<div class="card-footer">
				<a href="/main/main.do" class="btn btn-primary">메인 페이지로 가기</a>
			</div>
		</div>
	</div>
</body>
</html>