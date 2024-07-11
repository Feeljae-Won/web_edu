<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error - 404</title>
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