<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일반 게시판 글수정</title>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<script type="text/javascript">
	$(function() {

		// 2. jquery 확인
		console.log("jquery check ---------------------");

		// 5. 이벤트 처리
		$("#updateForm").submit(function() {
			console.log("data check -----------");

			// 6. 데이터 유효성 검사
			// 6-1. 필수 항목
			if (isEmpty("#title", "제목", 1))
				return false;
			if (isEmpty("#content", "내용", 1))
				return false;
			if (isEmpty("#writer", "작성자", 1))
				return false;
			if (isEmpty("#pw", "비밀번호", 0))
				return false;

			// 글자 길이 체크
			if (lengthCheck("#title", "제목", 3, 100, 1))
				return false;
			if (lengthCheck("#content", "내용", 3, 500, 1))
				return false;
			if (lengthCheck("#writer", "작성자", 2, 20, 1))
				return false;
			if (lengthCheck("#pw", "비밀번호", 4, 20, 0))
				return false;

			return false;

		}); // end of submit()

	}) // end of function();
</script>
</head>
<body>

	no=${param.no }
	<br>
	<div class="container p-3 my-3 bg-primary text-white"
		style="border-radius: 10px 10px 10px 10px; hieght: 200px;">
		<h2><i class="fa fa-pencil-square-o"></i> Board Update Form</h2>
		<p><i class="fa fa-caret-right"></i> 일반 게시판 글 수정</p>
		<hr>
		<form action="update.do" method="post" id="updateForm">
			<div class="form-group">
				<label for="no"><b>번호</b></label> <input type="text" maxlength="100"
					class="form-control" placeholder="제목 입력" id="no" name="no"
					value=${vo.no } readonly>
			</div>
			<div class="form-group">
				<label for="title"><b>제목</b></label> <input type="text"
					maxlength="20" class="form-control" placeholder="제목 입력" id="title"
					name="title" value=${vo.title }>
			</div>
			<div class="form-group">
				<label for="content"><b>내용</b></label>
				<textarea class="form-control" rows="7" id="content" name="content"
					rows="7" maxlength="500" placeholder="내용 입력">${vo.content }</textarea>
			</div>
			<div class="form-inline">
				<label for="writer"><b>작성자</b></label> <input type="text"
					class="form-control" placeholder="작성자 입력" id="writer" name="writer"
					style="margin: 0px 0px 0px 10px;" value=${vo.writer }> <label
					for="pw" style="margin: 0px 0px 0px 10px;"><b>Password:</b></label>
				<input type="password" class="form-control"
					placeholder="비밀번호 입력 (본인확인)" id="pw" name="pw"
					style="margin: 0px 0px 0px 10px;">
			</div>
			<hr>
			<div>
				<button type="submit" class="btn btn-light">
					<b>Confirm</b>
				</button>
				<button type="reset" class="btn btn-secondary">
					<b>Reset</b>
				</button>
				<button type="button" class="btn btn-danger cancelBtn" onclick="history.back()">
					<b>Cancel</b>
				</button>
			</div>
		</form>
	</div>
</body>
</html>