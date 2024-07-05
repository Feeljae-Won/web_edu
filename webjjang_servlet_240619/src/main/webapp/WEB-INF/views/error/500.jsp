<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>500 Error</title>
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
<p>
	<div class="container">
		<div class="card">
			<div class="card-header bg-danger text-white">
				<i class="material-icons float-left" style="font-size:38px;">error </i>
				<h3> 처리 프로세스 오류(500)</h3>
			</div>
			<div class="card-body" id="errorDiv">
				<div class="row">
					<div class="col-md-2"><b><i class="fa fa-angle-right"></i> 오류 객체</b></div>
					<div class="col-md-10">${e.getClass().simpleName }</div>
				</div>
				<div class="row">
					<div class="col-md-2"><b><i class="fa fa-angle-right"></i> 오류 메세지</b></div>
					<div class="col-md-10">${e.message }</div>
				</div>
				<div class="row">
					<div class="col-md-2"><b><i class="fa fa-angle-right"></i> 조치 사항</b></div>
					<div class="col-md-10">
						오류로 인해서 불편을 드려 죄송합니다.<br>
						다시 한번 시도해 주세요.<br>
						오류가 계속 발생이 되면 전산 담당자에게 연락해 주세요.
					</div>
				</div>
			</div>
			<div class="card-footer">
				<a href="/board/list.do" class="btn btn-primary">일반 게시판으로 가기</a>
			</div>
		</div>

	</div>
</body>
</html>