<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 글 수정</title>
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.13.3/themes/base/jquery-ui.css"
>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script src="https://code.jquery.com/ui/1.13.3/jquery-ui.js"></script>

<script type="text/javascript">
	$(function() {

		// datepicker 설정
		$(".datepicker")
				.datepicker(
						{
							// 입력란의 데이터 포맷
							dateFormat : "yy-mm-dd",
							// 연 선택 입력 추가
							changeYear : true,
							// 월 선택 입력 추가
							changeMonth : true,
							// 요일 선택할 때 이름 (기본은 영어)
							dayNamesMin : [ "일", "월", "화", "수", "목",
									"금", "토" ],
							// 월 선택할 때 이름 (기본은 영어)
							monthNamesShort : [ "1월", "2월", "3월", "4월",
									"5월", "6월", "7월", "8월", "9월",
									"10월", "11월", "12월" ],
						});
		$("#startDate").datepicker(
				"option",
				{
					// 			"minDate" : new Date(),
					onClose : function(selectedDate) {
						if ($(this).val() != "")
							$("#endDate").datepicker("option",
									"minDate", selectedDate);
					}
				});

		$("#endDate").datepicker(
				"option",
				{
					"minDate" : new Date(),
					onClose : function(selectedDate) {
						if ($(this).val() != "")
							$("#startDate").datepicker("option",
									"maxDate", selectedDate);
					}
				});

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
	<div class="container p-3 my-3 bg-dark text-white"
		style="border-radius: 10px 10px 10px 10px; hieght: 200px;"
	>
		<p>
			<i class="fa fa-caret-right"></i> <b>${vo.no }. </b> ${vo.title }
		</p>
		<hr>
		<form action="update.do" method="post" id="updateForm">
			<input type="hidden" name="page" value="${param.page }">
			<input type="hidden" name="perPageNum" value="${param.perPageNum }">
			<input type="hidden" name="key" value="${param.key }">
			<input type="hidden" name="word" value="${param.word }">
			<div class="form-group">
				<label for="no"><b>번호</b></label>
				<input type="text" maxlength="100" class="form-control"
					placeholder="제목 입력" id="no" name="no" value=${vo.no } readonly
				>
			</div>
			<div class="form-group">
				<label for="title"><b>제목</b></label>
				<input type="text" maxlength="20" class="form-control"
					placeholder="제목 입력" id="title" name="title" value=${vo.title }
				>
			</div>
			<div class="form-group">
				<label for="content"><b>내용</b></label>
				<textarea class="form-control" rows="7" id="content" name="content"
					rows="7" maxlength="500" placeholder="내용 입력"
				>${vo.content }</textarea>
			</div>
			<div class="form-group">
				<label for="startDate"><b>공지 시작일</b></label>
				<input type="text" id="startDate" name="startDate"
					value="${vo.startDate }" class="datepicker" required
					autocomplete="off"
				>
				<br> <label for="endDate"><b>공지 종료일</b></label>
				<input type="text" id="endDate" name="endDate"
					value="${vo.endDate }" class="datepicker" required
					autocomplete="off"
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
				<button type="button" class="btn btn-danger cancelBtn"
					onclick="history.back()"
				>
					<b>Cancel</b>
				</button>
			</div>
		</form>
	</div>
</body>
</html>