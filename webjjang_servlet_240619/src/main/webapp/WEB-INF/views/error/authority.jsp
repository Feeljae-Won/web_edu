<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>권한오류</title>
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
				<h3> 권한오류</h3>
			</div>
			<div class="card-body" id="errorDiv">
				<div class="row">
					<div class="col-md-2"><b><i class="fa fa-angle-right"></i> 요청 URI</b></div>
					<div class="col-md-10">${uri }</div>
				</div>
				<div class="row">
					<div class="col-md-2"><b><i class="fa fa-angle-right"></i> 오류 메세지</b></div>
					<div class="col-md-10">
						요청하신 페이지에 접근할 권한이 없습니다.
					</div>
				</div>
				<div class="row">
					<div class="col-md-2"><b><i class="fa fa-angle-right"></i> 조치 사항</b></div>
					<div class="col-md-10">
						로그인 정보의 등급을 확인해 주세요.<br>
						오류가 있는 경우 관리자에게 연락해 주세요.
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