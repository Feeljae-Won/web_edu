<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="pageNav" tagdir="/WEB-INF/tags"%>

<script type="text/javascript">
	$(function() {
		// 이벤트 처리
		// ----------- Modal 화면의 이벤트 처리
		// 댓글 등록 이벤트
		$("#replyWriteBtn").click(function() {
			// 제목을 댓글 등록
			$("#boardReplyModal").find(".modal-title").text("댓글 등록");

			// input / text를 선택한다.
			$("#boardReplyForm").find(".form-group").show();
			// 데이터 지우기
			$("#boardReplyForm").find(".form-group > input, .form-group > textarea").val("");

			// 버튼을 선택
			// 버튼을 다 보이게
			$("#boardReplyForm button").show();
			// 필요 없는 버튼을 안보이게 한다.
			$("#modalReplyUpdateBtn, #modalReplyDeleteBtn").hide();

			// 모달 창 보이게
			$("#boardReplyModal").modal("show");
		});

		// 댓글 수정 이벤트
		$(".replyUpdateBtn").click(function() {
			// 제목을 댓글 수정
			$("#boardReplyModal").find(".modal-title").text("댓글 수정")

			// input / text를 선택한다. - 내용 / 작성자 / 비밀번호
			$("#boardReplyForm").find(".form-group").show();
			
			// 데이터 지우기
			$("#boardReplyForm").find(".form-group > input, .form-group > textarea").val("");
			
			// 댓글번호와 내용, 작성자는 데이터를 수집해서 value값으로 세팅한다.
			let replyDataRow = $(this).closest(".replyDataRow");
			// data("rno") - tag 안에 data-rno = "값"
			let rno = replyDataRow.data("rno");
			let content = replyDataRow.find(".replyContent").text();
			let writer = replyDataRow.find(".replyWriter").text();

			// reply Modal 입력란에 데이터 세팅
			$("#rno").val(rno);
			$("#content").val(content);
			$("#writer").val(writer);

			$("#boardReplyForm").find("input, textarea").show();
			
			// 버튼을 선택
			// 버튼을 다 보이게
			$("#boardReplyForm button").show();
			// 필요 없는 버튼을 안보이게 한다.
			$("#modalReplyWriteBtn, #modalReplyDeleteBtn").hide();

			$("#boardReplyModal").modal("show");
		});

		// 댓글 삭제 이벤트
		$(".replyDeleteBtn").click(function() {
			// 제목을 댓글 삭제
			$("#boardReplyModal").find(".modal-title").text("댓글 삭제")
			
			// 데이터 지우기
			$("#boardReplyForm").find(".form-group > input, .form-group > textarea").val("");
			
			// input / text를 선택한다. - pw
			$("#boardReplyForm").find(".form-group").hide();
			$("#pwDiv").show();
			
			// data("rno") - tag 안에 data-rno = "값"
			$("#rno").val($(this).closest(".replyDataRow").data("rno"));
			
			// 버튼을 선택
			// 버튼을 다 보이게
			$("#boardReplyForm button").show();
			// 필요 없는 버튼을 안보이게 한다.
			$("#modalReplyWriteBtn, #modalReplyUpdateBtn").hide();

			$("#boardReplyModal").modal("show");
		}); 
		
		// ----------- Modal 화면 안에 처리 버튼 이벤트 처리
		$("#modalReplyWriteBtn").click(function() {
// 			alert("click - modalReplyWriteBtn");
			// 데이터를 전송해서 실행되는 uri를 지정한다.
			$("#boardReplyForm").attr("action", "/boardreply/write.do");
			// 데이터를 전송하면서 페이지 이동을 시킨다.
			$("#boardReplyForm").submit();
		});
		
		$("#modalReplyUpdateBtn").click(function() {
// 			alert("click - modalReplyUpdateBtn");
			// 데이터를 전송해서 실행되는 uri를 지정한다.
			$("#boardReplyForm").attr("action", "/boardreply/update.do")
			// 데이터를 전송하면서 페이지 이동을 시킨다.
			$("#boardReplyForm").submit();
		});
		
		$("#modalReplyDeleteBtn").click(function() {
// 			alert("click - modalReplyDeleteBtn");
			// 데이터를 전송해서 실행되는 uri를 지정한다.
			$("#boardReplyForm").attr("action", "/boardreply/delete.do")
			// 데이터 전송하면서 페이지 이동
			$("#boardReplyForm").submit();
		});

	});
</script>

<div class="card bg-secondary text-white">
	<div class="card-header">
		<h3>
			<span class="btn btn-light float-right" id="replyWriteBtn"><b>등록</b></span>
			<b>댓글 리스트</b>
		</h3>
	</div>

	<div class="card-body bg-light text-dark ">
		<c:if test="${ empty replyList }">
			<span class="bg-light text-dark" style="margin: 10px;"> <b>댓글이
					존재하지 않습니다.</b></span>
		</c:if>
		<c:if test="${ !empty replyList }">
			<!-- 데이터가 있는 만큼 반복  처리 시작 -->
			<c:forEach items="${replyList }" var="replyVO">
				<div class="replyDataRow" data-rno="${replyVO.rno }">
					<div class="card-header bg-primary text-light bg-light text-dark">
						<span class="float-right">${replyVO.writeDate }</span> <b
							class="replyWriter">${replyVO.writer }</b>
					</div>
					<div class="card-body">
						<div class="float-right">
							<button class="btn btn-basic replyUpdateBtn">
								<b>수정</b>
							</button>
							|
							<button class="btn btn-basic replyDeleteBtn">
								<b>삭제</b>
							</button>
						</div>
						<pre class="replyContent">${replyVO.content }</pre>
					</div>
				</div>
				<!-- <div class="card-footer"></div> -->
			</c:forEach>
		</c:if>
		<div class="card-footer">
			<pageNav:replayPageNav listURI="view.do" pageObject="${replyPageObject }" query="&inc=0"/>
		</div>
	</div>
	<!-- 데이터가 있는 만큼 반복 처리 끝 -->

	<!-- 댓글 등록 / 수정 / 삭제를 위한 모달 창 -->
	<!-- The Modal -->
	<div class="modal text-dark" id="boardReplyModal">
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<!-- 버튼에 따라서 제목을 수정해서 사용 - .text(제목) -->
					<h4 class="modal-title">댓글 등록</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>

				<!-- 데이터를 넘기는 form 태그 -->
				<form id="boardReplyForm" method="post">
					<input type="hidden" name="rno" id="rno"> 
					<input type="hidden" name="no" value="${param.no }">
					<!-- 페이지 정보 보내기 -->
					<input type="hidden" name="page" value="${param.page }"> 
					<input type="hidden" name="perPageNum" value="${param.perPageNum }"> 
					<input type="hidden" name="key" value="${param.key }"> 
					<input type="hidden" name="word" value="${param.word }"> 
					<!-- Modal body -->
					<div class="modal-body">
						<!-- 내용 / 작성자 / 비밀번호 -->
						<div class="form-group" id="contentDiv">
							<label for="content">내용</label>
							<textarea class="form-control" rows="3" id="content" placeholder="댓글을 입력하세요."
								name="content"></textarea>
						</div>
						<div class="form-group" id="writerDiv">
							<label for="writer">작성자</label> <input type="text"
								class="form-control" id="writer" name="writer" placeholder="작성자를 입력하세요.">
						</div>
						<div class="form-group" id="pwDiv">
							<label for="pw">비밀번호</label> <input type="password"
								class="form-control" id="pw" name="pw" placeholder="비밀번호 입력">
						</div>

					</div>

					<!-- Modal footer -->
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" id="modalReplyWriteBtn">Submit</button>
						<button type="button" class="btn btn-info" id="modalReplyUpdateBtn">Update</button>
						<button type="button" class="btn btn-danger" id="modalReplyDeleteBtn">Delete</button>
						<button type="button" class="btn btn-secondary" data-dismiss="modal"
							id="modalReplyCloseBtn:">Close</button>
					</div>
				</form>

			</div>
		</div>
	</div>
</div>

