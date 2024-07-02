<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>interYard - 로그인</title>
</head>
<body>
	<div class="container">
		<div class="card bg-dark text-white">
			<div class="card-header">
				<h4>interYard</h4>
				<b>가입하신 정보로 로그인 해 주세요.</b>
			</div>
			<form action="login.do" method="post">
				<div class="card-body bg-light text-dark">
					<div class="form-group">
						<label for="usr">ID:</label>
						<input type="text" class="form-control" id="id" name="id" placeholder="Input your ID"
							autocomplete="none">
					</div>
					<div class="form-group">
						<label for="pwd">Password:</label>
						<input type="password" class="form-control" id="pw" name="pw" placeholder="Input your Password">
					</div>
				</div>
				<div class="card-footer">
					<button class="btn btn-primary">Submit</button>
					<button class="btn btn-info float-right" type="button">Sign Up</button>
					<button class="btn btn-light float-right" type="button">Search ID &amp; PW</button>
				</div>
			</form>
		</div>

	</div>

</body>
</html>