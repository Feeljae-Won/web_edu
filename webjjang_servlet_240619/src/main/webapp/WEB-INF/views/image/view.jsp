<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일반 게시판 글보기</title>
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

	<%-- 	글 번호 : ${param.no } 조회수 : ${param.inc } --%>
	<div class="container p-3 my-3 bg-primary text-white"
		style="border-radius: 10px 10px 10px 10px; hieght: 200px;"
	>
		<h2>
			<i class="material-icons">chrome_reader_mode</i> Image View
		</h2>
		<p>
		<div class="float-right">
			<c:if test="${login.id == vo.id }">
				<a
					href="/image/updateForm.do?no=${vo.no}&title=${vo.title}&content=${vo.content}&id=${vo.id}&fileName=${vo.fileName }
				&page=${param.page}&perPageNum=${para.perPageNum}&key=${param.key}&word=${param.word}"
					class="btn btn-light"
				>
					<b>Update</b>
				</a>
				<!-- 			<button class="btn btn-danger" id="deleteBtn"><b>Delete</b></button> -->
				<!-- 삭제 버튼 Modal 적용 -->
				<button type="button" class="btn btn-danger" id="deleteBtn">
					<b>Delete</b>
				</button>
			</c:if>

			<a
				href="list.do?page=${param.page}&perPageNum=${para.perPageNum}&key=${param.key}&word=${param.word}"
			>
				<button type="button" class="btn btn-secondary cancelBtn">
					<b>list</b>
				</button>
			</a>
		</div>
		<i class="fa fa-caret-right"></i> 이미지 게시판 상세보기
		<p>
		<hr>
		<div class="card text-dark">
			<div class="card-header">
				<b>${vo.no }. ${vo.title } </b>
			</div>
			<div class="card-body">
				<div class="card" style="width: 100%">
					<img class="card-img-top" src="${vo.fileName }" alt="image">
					<div class="card-img-overlay">
						<c:if test="${login.id == vo.id }">
							<button type="button" class="btn btn-primary" data-toggle="modal"
								data-target="#changeImageModal"
							>이미지 변경</button>
						</c:if>
						<a href="${vo.fileName }" class="btn btn-success" download>다운로드</a>
					</div>

					<div class="card-body">
						<p class="card-text">
						<pre>${vo.content }</pre>
						</p>
					</div>
				</div>
			</div>
			<div class="card-footer">
				<span class="float-right">${vo.writeDate }</span> ${vo.name }(${vo.id })
			</div>
		</div>
		<hr>


		<!-- 댓글 처리 시작 -->
		<jsp:include page="reply.jsp" />
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

	<!-- The Modal -->
	<div class="modal" id="changeImageModal">
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">바꿀 이미지 선택하기</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>

				<form action="changeImage.do" method="post"
					enctype="multipart/form-data"
				>
					<!-- 숨겨서 넘겨야할 데이터 - 이미지 번호, 현재 파일이름(삭제) -->
					<input name="no" value="${vo.no }" type="hidden">
					<input name="deleteFileName" value="${vo.fileName }" type="hidden">
					<!-- Modal body -->
					<div class="modal-body">
						<div class="form-group">
							<label for="imageFile">첨부 이미지</label>
							<input id="imageFile" name="imageFile" required
								class="form-control" type="file"
							>
						</div>
					</div>

					<!-- Modal footer -->
					<div class="modal-footer">
						<button class="btn btn-primary">바꾸기</button>
						<button type="button" class="btn btn-danger" data-dismiss="modal">취소</button>
					</div>

				</form>
			</div>
		</div>
	</div>

</body>
</html>