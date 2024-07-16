
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${headTitle }</title>
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
		<a href="list.do?${pageObject.pageQuery}" class="btn btn-secondary float-right">
				<b>리스트</b>
		</a>
		<b> Q. 질문 글</b>
		<hr>
		<c:if test="${!empty vo }">
		<div class="card text-dark">
			<div class="card-header">
				<span class="float-right">${vo.writeDate }</span>
				<b><i class="fa fa-question-circle"></i> ${vo.no }.</b> ${vo.title }
			</div>
			<div class="card-body">
				<pre>${vo.content }</pre>
			</div>
			<div class="card-footer">
				<c:if test="${!empty login && vo.id != login.id }">
				<a href="answerForm.do?no=${vo.no }&perPageNum=${param.perPageNum}"
					class="btn btn-light float-right"
				>
					<b>답변하기</b>
				</a>
				</c:if>
				 <b>작성자 : </b>${vo.name }(${vo.id })
			</div>
		</div>		
		</c:if>
	</div>
	<div class="container p-3 my-3 bg-dark text-white"
		style="border-radius: 10px 10px 10px 10px; hieght: 200px;">
		<form action="write.do" method="post" id="writeForm">
		<b>A. ${headTitle }</b><p>
		
		<!-- 페이지 정보 넘기기 -->
		<input name="perPageNum" value= "${param.perPageNum} " type="hidden">
		<!-- 질문답변 운영 정보 -->
		<input name="refNo" value= "${vo.refNo}" type="hidden">
		<input name="ordNo" value= "${vo.ordNo +1}" type="hidden">
		<input name="levNo" value= "${(empty vo)?0:vo.levNo +1}" type="hidden">
		<input name="parentNo" value= "${vo.no}" type="hidden">

			<div class="form-group">
				<label for="title"><b><i class="fa fa-caret-right"></i> 제목</b></label> <input type="text" required
					class="form-control" placeholder="제목 입력" id="title" name="title"
					value="${(empty vo)?'':('[답변] ' += vo.title) }">
			</div>
			<div class="form-group">
				<label for="content"><b><i class="fa fa-caret-right"></i> 내용</b></label>
				<c:if test="${empty vo }">
					<textarea class="form-control" rows="12" id="content" name="content" required
						placeholder="내용 입력"></textarea>
				</c:if>
				<c:if test="${!empty vo }">
					<textarea class="form-control" rows="12" id="content" name="content" required
						placeholder="내용 입력">



------------------------ 질문 글 ---------------------------
> 작성자 : ${vo.name }(${vo.id })      > 작성일 : ${vo.writeDate}
-----------------------------------------------------------
${vo.content }
</textarea>
				</c:if>
			</div>
			<hr>
			<div>
				<button type="submit" class="btn btn-light">
					<b>답변하기</b>
				</button>
				<button type="reset" class="btn btn-secondary">
					<b>초기화</b>
				</button>
				<button type="button" class="btn btn-danger cancelBtn">
					<b>취소</b>
				</button>
			</div>
		</form>
	</div>
</body>
</html>