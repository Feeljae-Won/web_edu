
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>interYard - 회원 가입</title>
<!-- 라이브러리 등록 -->
<!-- 라이브러리 필요하다. 웹라이브러리(js 라이브러리)
	1. 다운로드 : jquery.com : 내 서버에 파일을 둔다.
	2. CDN(Content Delivery Network) - 배달 받는 방식 사용.(*) -->
<!-- Boostrap(디자인의 표준화) : jquery(동작의 표준화) 포함. -->
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.13.3/themes/base/jquery-ui.css"
>
<script src="https://code.jquery.com/ui/1.13.3/jquery-ui.js"></script>
<script type="text/javascript">
	$(function() { // == $(document).ready(function(){~});

		// 날짜 입력 설정 - datepicker
		// datepicker 클래스 이벤트
		let now = new Date();
		let startYear = now.getFullYear();
		let yearRange = (startYear - 100) + ":" + startYear;

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
					// 선택할 수 있는 연도의 범위 (-100년)
					yearRange : yearRange,
					// 최대로 선택할 수 있는 날짜 지정 (now : 오늘까지)
					maxDate : now,
				});

		// 이벤트 처리
		// 아이디 체크 처리 - key가 올라왔을 때 데이터를 가져온다.
		$("#id").keyup(
				function() {
					let id = $("#id").val(); // 괄호 안에 데이터가 있으면 setter, 없으면 getter
					// data 확인하기
					console.log("id = " + id);
					// 3자 미만인 경우의 처리
					if (id.length < 3) {
						// class 지정 - 디자인
						$("#checkIdDiv").removeClass(
								"alert-success alert-danger").addClass(
								"alert-danger");
						// class 지정 - 텍스트
						$("#checkIdDiv").text("아이디가 3자 미만 입니다.");
					} else if (id.length > 2) {
						// class 지정 - 디자인
						// 데이터가 3자 이상이 되면 sever에 가서 데이터를 확인하고 온다. - 결과를 JSP로 받는다.
						// $("#checkIdDiv") 안에 넣을 문구를 가져와서 넣는다.
						// ajax의 load 함수 사용 - load(url, data, function());
						$("#checkIdDiv").load(
								"/ajax/checkId.do?id=" + id,
								// 콜백 함수 - function(결과, 상태 - success/error, 통신 객체) {}
								// function(result, status, xhr){
								function(result, status, xhr) {
									if (result.indexOf("중복") >= 0) {
										$("#checkIdDiv").removeClass(
												"alert-success alert-danger")
												.addClass("alert-danger");
										// $("#confirmBtn").addClass("disabled");
										// $("#confirmBtn").attr("disabled",true);
									} else {
										$("#checkIdDiv").removeClass(
												"alert-success alert-danger")
												.addClass("alert-success");
										// $("#confirmBtn").removeClass("disabled");
										// $("#confirmBtn").removeAttr("disabled");
									}
								}); // 중복 체크 처리
					} // 아이디 유효성 검사  
					if (id.length == 0) {
						// class 지정 - 디자인
						$("#checkIdDiv").removeClass(
								"alert-success alert-danger").addClass(
								"alert-danger");
						// $("#confirmBtn").addClass("disabled");
						// $("#confirmBtn").attr("disabled",true);
						// class 지정 - 텍스트
						$("#checkIdDiv").text("아이디는 필수 입력 사항입니다.");
					} // 아이디 비어있을 때 처리
				}); // end of id.keyup()

		// 비밀번호와 비밀번호 확인의 이벤트 시작
		$("#pw, #pw2").keyup(
				function() {

					let pw = $("#pw").val();
					let pw2 = $("#pw2").val();
					// 비밀번호 길이 체크
					if (pw.length < 4) {
						// 디자인 부분 적용
						$("#pwDiv").removeClass("alert-success alert-danger")
								.addClass("alert-danger");
						// 글자 오류 적용
						$("#pwDiv").text("비밀번호는 필수 입력 입니다! 4글자 이상 입력하세요.");
					} else {
						// 디자인 부분 적용
						$("#pwDiv").removeClass("alert-success alert-danger")
								.addClass("alert-success");
						// 글자 오류 적용
						$("#pwDiv").text("정상적인 비밀번호 입니다.");
					} // 비밀 번호 체크 끝

					// 비밀번호 확인 길이 체크
					if (pw2.length < 4) {
						// 디자인 부분 적용
						$("#pw2Div").removeClass("alert-success alert-danger")
								.addClass("alert-danger");
						// 글자 오류 적용
						$("#pw2Div").text("비밀번 확인은 필수 입력 입니다! 4글자 이상 입력하세요.");
					} else {
						// 비밀번호와 비밀번호 확인이 같은지 물어봐야 한다.
						if (pw != pw2) {
							// 디자인 부분 적용
							$("#pw2Div").removeClass(
									"alert-success alert-danger").addClass(
									"alert-danger");
							// 글자 오류 적용
							$("#pw2Div").text("비밀번호와 비밀번호 확인은 같아야 합니다.");
						} else {
							// 디자인 부분 적용
							$("#pw2Div").removeClass(
									"alert-success alert-danger").addClass(
									"alert-success");
							// 글자 오류 적용
							$("#pw2Div").text("정상적인 비밀번호 입니다.");
						}
					} // 비밀번호 확인 체크 끝
				}); // end of keyup
				
		$("#name").keyup(function(){
			let name = $("#name").val();
			console.log("name = " + name.length)
			if (name.length < 3) {
				// 디자인 부분 적용
				$("#checkNameDiv").removeClass("alert-success alert-danger")
						.addClass("alert-danger");
				// 글자 오류 적용
				$("#checkNameDiv").text("이름은 필수 입력 입니다! 2글자 이상 10자 이내로 입력하세요.");
			} else {
				// 디자인 부분 적용
				$("#checkNameDiv").removeClass("alert-success alert-danger")
						.addClass("alert-success");
				// 글자 오류 적용
				$("#checkNameDiv").text("정상적인 이름 입니다.");
			}; // 이름 체크 끝
		}); // end of name.keyup();
			
	}); // end of function
</script>
</head>
<body>
	<div class="container p-3 my-3 bg-dark text-white"
		style="border-radius: 10px 10px 10px 10px; hieght: 200px;"
	>
		<form action="write.do" method="post" id="writeForm"
			enctype="multipart/form-data"
		>

			<!-- 페이지 정보 -->
			<input name="perPageNum" value="${param.perPageNum}" type="hidden">

			<!-- 데이터 수집 시작 -->
			<label for="id"><b>아이디 </b></label>
			<input type="text" required autocomplete="off" maxlength="20"
				pattern="^[a-zA-Z][a-zA-Z0-9]{2,19}$"
				title="맨 앞 글자는 영문자, 뒤에는 영숫자 입력 3~20자 이내" class="form-control"
				placeholder="아이디 입력" id="id" name="id"
			>
			<div id="checkIdDiv" class="alert alert-danger">
				<strong>아이디는 필수 입력 입니다!</strong> 3글자 이상 입력하세요.
			</div>

			<label for="pw"><b>비밀번호 </b></label>
			<input type="password" required maxlength="20" pattern="^.{4,20}$"
				title="4~20자 이내" class="form-control" placeholder="비밀번호 입력" id="pw"
				name="pw"
			>
			<div id="pwDiv" class="alert alert-danger">
				<strong>비밀번호는 필수 입력 입니다!</strong> 4글자 이상 입력하세요.
			</div>
			<label for="pw2"><b>비밀번호 확인 </b></label>
			<input type="password" required maxlength="20" pattern="^.{4,20}$"
				title="4~20자 이내" class="form-control" placeholder="비밀번호 확인" id="pw2"
			>
			<div id="pw2Div" class="alert alert-danger">
				<strong>비밀번호 확인은 필수 입력 입니다!</strong> 4글자 이상 입력하세요.
			</div>

			<label for="name"><b>이름 </b></label>
			<input type="text" required autocomplete="off" maxlength="10"
				pattern="^[가-힣]{2,10}$" title="2~10자 이내로 한글만 입력하세요."
				class="form-control" placeholder="사용자 이름" id="name" name="name"
			>
			<div id="checkNameDiv" class="alert alert-danger">
				<strong>이름은 필수 입력 입니다!</strong> 2~10자 이내로 한글만 입력하세요.
			</div>

			<label for="gender"><b>성별 </b></label>
			<div class="form-group">
				<div class="form-check-inline">
					<label class="form-check-label"> <input type="radio"
							name="gender" checked class="form-check-input" value="남자"
						>남자
					</label>
				</div>
				<div class="form-check-inline">
					<label class="form-check-label"> <input type="radio"
							name="gender" class="form-check-input" value="여자"
						>여자
					</label>
				</div>
			</div>

			<div class="form-group">
				<label for="birth"><b>생년월일</b></label><br>
				<input type="text" required class="form-control datepicker"
					id="birth" name="birth" autocomplete="off"
				>
			</div>

			<div class="form-group">
				<label for="tel"><b>연락처 </b></label>
				<input type="text" maxlength="20" class="form-control"
					placeholder="xxx-xxxx-xxxx" id="tel" name="tel"
				>
			</div>

			<div class="form-group">
				<label for="email"><b>이메일 </b></label>
				<input type="text" maxlength="20" required class="form-control"
					placeholder="user@index.com" id="email" name="email"
				>
			</div>

			<div class="form-group">
				<div class="form-group">
					<label for="photoFile"><b>사진 이미지</b></label><br>
					<input type="file" class="btn btn-light" id="photoFile"
						name="photoFile"
					>
				</div>
			</div>
			<!-- 데이터 수집 끝 -->
			<hr>
			<div>
				<button type="submit" class="btn btn-light" id="confirmBtn">
					<b>Submit</b>
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