<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이미지 게시판 수정</title>

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

			// 글자 길이 체크
			if (lengthCheck("#title", "제목", 3, 100, 1))
				return false;
			if (lengthCheck("#content", "내용", 3, 500, 1))
				return false;

			return false;

		}); // end of submit()

	}) // end of function();
</script>
</head>
<body>

	<br>
	<div class="container p-3 my-3 bg-primary text-white"
		style="border-radius: 10px 10px 10px 10px; hieght: 200px;">
		<h2><i class="fa fa-pencil-square-o"></i> Image Update Form</h2>
		<p><i class="fa fa-caret-right"></i> 이미지 게시글 수정</p>
		<div class="alert alert-danger">
   			 <strong>이미지를 제외한 나머지 텍스트 데이터를 수정합니다.</strong><br>
   			 이미지 변경은 이미지 상세보기 화면에서 이미지 안에 상단 "Change" 버튼을 사용하세요.
  		</div>
		<hr>
		<form action="update.do" method="post" id="updateForm" enctype="multipart/form-data">
			<input type="hidden" name="page" value="${param.page }">
			<input type="hidden" name="perPageNum" value="${param.perPageNum }">
			<input type="hidden" name="key" value="${param.key }">
			<input type="hidden" name="word" value="${param.word }">
			<div class="form-group">
				<label for="no"><b>번호</b></label> <input type="text"
					class="form-control" id="no" name="no"
					value=${vo.no } readonly>
			</div>
			<div class="form-group">
				<label for="title"><b>제목</b></label> <input type="text"
					maxlength="100" class="form-control" placeholder="제목 입력" id="title" required
					name="title" value=${vo.title }>
			</div>
			<div class="form-group">
				<label for="content"><b>내용</b></label>
				<textarea class="form-control" rows="7" id="content" name="content" required
					rows="7" maxlength="500" placeholder="내용 입력">${vo.content }</textarea>
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