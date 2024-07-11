
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>공지사항 글등록</title>
<!-- 라이브러리 등록 -->
<!-- 라이브러리 필요하다. 웹라이브러리(js 라이브러리)
	1. 다운로드 : jquery.com : 내 서버에 파일을 둔다.
	2. CDN(Content Delivery Network) - 배달 받는 방식 사용.(*) -->
<!-- Boostrap(디자인의 표준화) : jquery(동작의 표준화) 포함. -->
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.13.3/themes/base/jquery-ui.css"
>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script src="https://code.jquery.com/ui/1.13.3/jquery-ui.js"></script>

<script type="text/javascript">
	$(function() { // == $(document).ready(function(){~});

		// datepicker 설정
		$(".datepicker").datepicker(
				{
					// 입력란의 데이터 포맷
					dateFormat : "yy-mm-dd",
					// 연 선택 입력 추가
					changeYear : true,
					// 월 선택 입력 추가
					changeMonth : true,
					// 요일 선택할 때 이름 (기본은 영어)
					dayNamesMin : [ "일", "월", "화", "수", "목", "금", "토" ],
					// 월 선택할 때 이름 (기본은 영어)
					monthNamesShort : [ "1월", "2월", "3월", "4월", "5월", "6월",
							"7월", "8월", "9월", "10월", "11월", "12월" ],
				});
		$("#startDate").datepicker("option", {
// 			"minDate" : new Date(),
			onClose : function(selectedDate) {
				if ($(this).val() != "")
					$("#endDate").datepicker("option", "minDate", selectedDate);
			}
		});

		$("#endDate").datepicker("option", {
			"minDate" : new Date(),
			onClose : function(selectedDate) {
				if ($(this).val() != "")
				$("#startDate").datepicker("option", "maxDate", selectedDate);
			}
		});

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

			// 글자 길이 체크
			if (lengthCheck("#title", "제목", 3, 20, 1))
				return false;
			if (lengthCheck("#content", "내용", 3, 500, 1))
				return false;

		}); // end of #writeBtn

	}); // end of function
</script>
</head>
<body>

	<div class="container p-3 my-3 bg-dark text-white"
		style="border-radius: 10px 10px 10px 10px; hieght: 200px;"
	>
		<i class="fa fa-caret-right"></i> <b>공지사항 글 등록</b>
		<hr>
		<form action="write.do" method="post" id="writeForm">
			<input name="perPageNum" value="${param.perPageNum} " type="hidden">

			<div class="form-group">
				<label for="title"><b>제목</b></label>
				<input type="text" class="form-control" placeholder="제목 입력"
					id="title" name="title"
				>
			</div>
			<div class="form-group">
				<label for="content"><b>내용</b></label>
				<textarea class="form-control" rows="7" id="content" name="content"
					placeholder="내용 입력"
				></textarea>
			</div>
			<div class="form-group">
				<label for="startDate"><b>공지 기간</b></label><br>
				<input type="text" id="startDate" name="startDate"
					class="datepicker" required autocomplete="off"
				>
				<label for="endDate"><b>~</b></label>
				<input type="text" id="endDate" name="endDate" class="datepicker"
					required autocomplete="off"
				>
			</div>
			<hr>
			<div>
				<button type="submit" class="btn btn-light">
					<b>Confirm</b>
				</button>
				<button type="reset" class="btn btn-secondary">
					<b>Reset</b>
				</button>
				<a
					href="list.do?page=${param.page}&perPageNum=${para.perPageNum}&key=${param.key}&word=${param.word}"
				>
					<button type="button" class="btn btn-danger cancelBtn">
						<b>Cancel</b>
					</button>
				</a>
			</div>
		</form>
	</div>
</body>
</html>