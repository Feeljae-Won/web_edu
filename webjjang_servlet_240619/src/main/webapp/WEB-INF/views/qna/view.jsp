<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>질문답변 글보기</title>
<style type="text/css">
#deleteDiv {
	display: none;
	margin: 10px 0;
}
</style>
<script type="text/javascript">
	$(function() {

		// 이벤트 처리
		$("#deleteBtn").click(function() {
			// 			$(this).attr("disabled",true);
			// 			$("#deleteDiv").show();
			// 데이터 지우기
			$("#pw").val("");
			$("#deleteModal").modal("show");
		});
		// deleteDiv의 삭제 버튼 처리
		$("#deleteForm").submit(function() {
			console.log("writeForm - submit Event ---------")
			// 비밀번호 필수 입력
			if (isEmpty("#pw", "비밀번호", 0))
				return false;
			// 길이 체크
			if (lengthCheck("#pw", "비밀번호", 4, 20, 0))
				return false;
		});
		// deleteDiv의 취소 버튼 처리
		$("#deleteCancelBtn").click(function() {
			console.log("writeForm 취소버튼 - Click Event ---------")
			$("#pw").val("");
			$("#deleteModal").modal("hide");
			$("#deleteBtn").attr("disabled", false);
		});

	});
</script>

</head>
<body>
	<div class="container p-3 my-3 bg-dark text-white"
		style="border-radius: 10px 10px 10px 10px; hieght: 200px;"
	>
		<div class="float-right">
			
			<a href="list.do?${pageObject.pageQuery}" class="btn btn-secondary">
				<b>리스트</b>
			</a>
		</div>
		<b>Q. 질문 글</b>
		<p>
		<hr>
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
		<br>


		<!-- 댓글 처리 시작 -->
		<%-- 		<jsp:include page="reply.jsp" /> --%>
		<!-- 댓글 처리 끝 -->


	</div>
	<!-- 컨테이너 끝 -->

	<!-- The Modal -->
	<div class="modal fade" id="deleteModal">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">게시판 글 삭제</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>

				<!-- Modal body -->
				<div class="modal-body">
					정말 삭제하시겠습니까?<br> 삭제하려면 비밀번호를 입력하세요.
				</div>

				<!-- Modal footer -->
				<div class="modal-footer">
					<form action="delete.do" method="post" id="deleteForm">
						<input type="hidden" name="page" value="${param.page }">
						<input type="hidden" name="perPageNum"
							value="${param.perPageNum }"
						>
						<input type="hidden" name="key" value="${param.key }">
						<input type="hidden" name="word" value="${param.word }">

						<input type="hidden" name="no" value="${vo.no }">
						<input name="pw" type="password" required maxlength="20"
							pattern="^.{3,20}$" id="pw" title="3~20자 입력 가능"
							placeholder="본인 확인용 비밀번호"
							style="padding: 5px; border-radius: 5px;"
						>
						<button class="btn btn-danger" id="lastDelete">Delete</button>
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal" id="deleteCancelBtn"
						>Close</button>
					</form>
				</div>

			</div>
		</div>
	</div>


</body>
</html>