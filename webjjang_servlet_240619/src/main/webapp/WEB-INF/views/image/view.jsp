<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이미지 게시판 글보기</title>
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
			// js 경고창 - alert : 일반 경고 / confirm : 확인, 취소 / prompt : 키인
			// 확인 창이 나타나는데 취소 버트느을 누르면 삭제 페이지 이동을 취소시킨다.
			if(!confirm("정말 삭제하시겠습니까? 삭제하면 복구할 수 없습니다.")) return false;
		});
		
		// updateBtn 처리
		$('[data-toggle="tooltip"]').tooltip();   

	});
</script>

</head>
<body>
	<%-- 	글 번호 : ${param.no } 조회수 : ${param.inc } --%>
	<div class="container p-3 my-3 bg-dark text-white"
		style="border-radius: 10px 10px 10px 10px; hieght: 200px;"
	>
		<div class="float-right">
			<c:if test="${ !empty login && login.id == vo.id }">
				<a
					href="/image/updateForm.do?no=${vo.no}&title=${vo.title}&content=${vo.content}&id=${vo.id}&fileName=${vo.fileName }
				&page=${param.page}&perPageNum=${param.perPageNum}&key=${param.key}&word=${param.word}"
					class="btn btn-light" data-toggle="tooltip" data-placement="top"
					title="이미지를 제외한 다른 정보만 수정 가능합니다." id="updateBtn" name="updateBtn"
				>
					<b>Update</b>
				</a>
				<a href="delete.do?no=${vo.no }&deleteFileName=${vo.fileName}&perPageNum=${param.perPageNum}" 
					class="btn btn-danger" id="deleteBtn">
					<b>Delete</b>
				</a>
			</c:if>

			<a
				href="list.do?page=${param.page}&perPageNum=${param.perPageNum}&key=${param.key}&word=${param.word}"
			>
				<button type="button" class="btn btn-secondary cancelBtn">
					<b>list</b>
				</button>
			</a>
		</div>
		<i class="fa fa-caret-right"></i> <b>${vo.no }. </b> ${vo.title }
		<p>
		<hr>
		<div class="card text-dark">
			<div class="card-header">
				<span class="float-right"><b>조회수 : </b> ${vo.hit }</span> <b>${vo.no }.
					${vo.title } </b>
			</div>
			<div class="card-body">
				<div class="card" style="width: 100%">
					<img class="card-img-top" src="${vo.fileName }" alt="image">
					<div class="card-img-overlay">
						<c:if test="${login.id == vo.id }">
							<button type="button" class="btn btn-primary" data-toggle="modal"
								data-target="#changeImageModal"
							>Change</button>
						</c:if>
						<a href="${vo.fileName }" class="btn btn-success" download>Download</a>
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
					<!-- 페이지 정보도 넘긴다. -->
					<input name="page" value="${param.page }" type="hidden">
					<input name="pePageNum" value="${param.perPageNum }" type="hidden">
					<input name="key" value="${param.key }" type="hidden">
					<input name="word" value="${param.word }" type="hidden">

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