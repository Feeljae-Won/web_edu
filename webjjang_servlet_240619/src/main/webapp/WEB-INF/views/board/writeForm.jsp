
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일반 게시판 글등록</title>
<!-- 라이브러리 등록 -->
<!-- 라이브러리 필요하다. 웹라이브러리(js 라이브러리)
	1. 다운로드 : jquery.com : 내 서버에 파일을 둔다.
	2. CDN(Content Delivery Network) - 배달 받는 방식 사용.(*) -->
<!-- Boostrap(디자인의 표준화) : jquery(동작의 표준화) 포함. -->


<script type="text/javascript">
	$(function() { // == $(document).ready(function(){~});

		// 1. click 이벤트 - writeBtn
		// 	$("#writeBtn").click(function(){

		// 2. submit 이벤트 - writeForm
		$("#writeForm").submit(function() {

			// 			alert("writeForm submit");

			// 필수 항목 체크
			// 제목 체크 - 제목란의 값을 가져와서 좌우 공백을 제거한다.
			let title = $("#title").val().trim();
			// 공백을 제거한 데이터를 제목 입력란에 다시 넣는다.
			$("#title").val(title);
			if (title == "") {
				alert("제목은 반드시 입력 하셔야 합니다."); // 경고
				$("#title").focus(); // 커서 위치
				return false; // 페이지 이동을 막는다.
			} // 제목 체크 끝

// 			alert("제목 체크 완료 ----");

			// 내용 필수 항목 체크 
			if (isEmpty("#content", "내용", 1))
				return false;

			// 작성자 필수 항목 체크 
			if (isEmpty("#writer", "작성자", 1))
				return false;

			// 비밀번호 필수 항목 체크 - $("pw").val() == ""  -> false
			if (isEmpty("#pw", "비밀번호 확인", 0))
				return false;

			// 비밀번호 확인 필수 항목 체크 
			if (isEmpty("#pw2", "비밀번호 확인", 0))
				return false;

			// 글자 길이 체크
			if (lengthCheck("#title", "제목", 3, 20, 1))
				return false;
			if (lengthCheck("#content", "내용", 3, 500, 1))
				return false;
			if (lengthCheck("#writer", "작성자", 2, 10, 1))
				return false;
			if (lengthCheck("#pw", "비밀번호", 4, 20, 0))
				return false;
			if (lengthCheck("#pw2", "비밀번호 확인", 4, 20, 0))
				return false;

			// 비밀번호와 비밀번호 확인 일치 체크
			let pw = $("#pw").val();
			let pw2 = $("#pw2").val();
			if (pw != pw2) {
				alert("비밀번호가 일치 하지 않습니다. 다시 입력해 주세요."); // 경고
				// 비밀번호와 비밀번호 확인의 데이터를 지운다.
				$("#pw, #pw2").val("");
				$("#pw").focus();
				return false;
			}

			// 페이지 이동 시키지 않는다. - 최초 이벤트 처리되는 함수에 작성한다
			// 			return false;

		}); // end of #writeBtn

	}); // end of function
</script>
</head>
<body>
	<div class="container p-3 my-3 bg-dark text-white"
		style="border-radius: 10px 10px 10px 10px; hieght: 200px;">
			<i class="fa fa-caret-right"></i> 일반 게시판 글 등록
		<hr>
		<form action="write.do" method="post" id="writeForm">
		<input name="perPageNum" value= "${param.perPageNum} " type="hidden">
		
		<input type="hidden" name="perPageNum" value="${param.perPageNum }">

			<div class="form-group">
				<label for="title"><b>제목</b></label> <input type="text"
					class="form-control" placeholder="제목 입력" id="title" name="title">
			</div>
			<div class="form-group">
				<label for="content"><b>내용</b></label>
				<textarea class="form-control" rows="7" id="content" name="content"
					placeholder="내용 입력"></textarea>
			</div>
			<div class="form-group">
				<label for="writer"><b>작성자</b></label> <input type="text"
					class="form-control" placeholder="작성자 입력" id="writer" name="writer">
			</div>
			<div class="form-inline">
				<input type="password" class="form-control" placeholder="비밀번호 입력"
					id="pw" name="pw"> <input type="password"
					class="form-control" placeholder="비밀번호 확인 입력" id="pw2"
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
				<a href="list.do?page=${param.page}&perPageNum=${para.perPageNum}&key=${param.key}&word=${param.word}">
				<button type="button" class="btn btn-danger cancelBtn">
					<b>Cancel</b>
				</button></a>
			</div>
		</form>
	</div>
</body>
</html>