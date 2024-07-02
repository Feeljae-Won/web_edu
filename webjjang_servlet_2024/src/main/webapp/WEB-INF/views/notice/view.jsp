<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
			if(isEmpty("#pw","비밀번호",0)) return false;
			// 길이 체크
			if(lengthCheck("#pw","비밀번호", 4, 20, 0)) return false;
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
		style="border-radius: 10px 10px 10px 10px; hieght: 200px;">
		<h2><i class="material-icons">chrome_reader_mode</i> Board View</h2>
		<p><i class="fa fa-caret-right"></i> 일반 게시판 상세보기</p>
		<hr>
		<table class="table table-hover"
			style="text-align: center; border-radius: 10px; border-style: hidden;">
			<thead class="thead-dark">
				<tr>
					<th class="thead-dark" style="border-radius: 10px 0px 0px 0px;">번호</th>
					<td class="dataRow table-light">${vo.no }</td>
					<th class="thead-dark" >제목</th>
					<td class="dataRow table-light" colspan="10" 
						style="border-radius: 0px 10px 0px 0px;">${vo.title }</td>
				</tr>
			<thead class="thead-dark">
				<tr>
					<th class="thead-dark">작성자</th>
					<td class="dataRow table-light" colspan="6">${vo.writer }</td>
					<th class="thead-dark">작성일</th>
					<td class="dataRow table-light">${vo.writeDate }</td>
					<th class="thead-dark">조회수</th>
					<td class="dataRow table-light">${vo.hit }</td>
				</tr>
			<thead class="thead-dark">
				<tr>
					<td colspan="12" class="dataRow table-light"
						style="height: 500px; text-align: left; 
							border-radius: 0px 0px 10px 10px;">${vo.content}</td>
				</tr>
			</thead>
		</table>
		<hr>
		<div>
			<a href="/board/updateForm.do?no=${vo.no}&title=${vo.title}&content=${vo.content}&writer=${vo.writer}" 
			class="btn btn-light" ><b>Update</b></a>
<!-- 			<button class="btn btn-danger" id="deleteBtn"><b>Delete</b></button> -->
			<!-- 삭제 버튼 Modal 적용 -->
			<button type="button" class="btn btn-danger" id="deleteBtn">
		    <b>Delete</b>
		 	</button>

			<a href="list.do" class="btn btn-secondary cancelBtn"><b>list</b></a>

<!-- 			<div id="deleteDiv"> -->
<!-- 				<form action="delete.jsp" method="post" id="deleteForm"> -->
<!-- 					<input type="hidden" name="no" value="10">  -->
<!-- 					<input name="pw" type="password" required maxlength="20" pattern="^.{3,20}$" id="pw" -->
<!-- 						title="3~20자 입력 가능" placeholder="본인 확인용 비밀번호" style="padding:5px; border-radius: 5px;"> -->
<!-- 					<button class="btn btn-danger"><b>삭제</b></button> -->
<!-- 					<button type="button" class="btn btn-secondary"	id="deleteCancelBtn"> -->
<!-- 						<b>취소</b> -->
<!-- 					</button> -->
<!-- 				</form> -->
<!-- 			</div> -->
		</div>

	</div>
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
          정말 삭제하시겠습니까?<br>
          삭제하려면 비밀번호를 입력하세요.
        </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
	        <form action="delete.do" method="post" id="deleteForm">
				<input type="hidden" name="no" value="${vo.no }"> 
				<input name="pw" type="password" required maxlength="20" pattern="^.{3,20}$" id="pw"
					title="3~20자 입력 가능" placeholder="본인 확인용 비밀번호" style="padding:5px; border-radius: 5px;">
	          <button class="btn btn-danger" id="lastDelete">Delete</button>
	          <button type="button" class="btn btn-secondary" data-dismiss="modal" id="deleteCancelBtn">Close</button>
			</form>
        </div>
        
      </div>
    </div>
  </div>
	
</body>
</html>