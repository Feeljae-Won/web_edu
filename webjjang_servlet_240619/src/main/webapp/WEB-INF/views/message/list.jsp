<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="pageNav" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Message List</title>
<script type="text/javascript">
	$(function() {
		$(".dataRow").click(function(){
			let no = $(this).find(".no").data("data");
			let accept = $(this).data("accept"); // data-accept=value
			console.log("no = " + no);
			location = "view.do?no=" + no 
					+ "&mode=${pageObject.acceptMode}&${pageObject.pageQuery}"
					+ "&accept=" + accept;
		});
	});
</script>
<style type="text/css">
.dataRow:hover {
	background: #4d4d4d;
	cursor: pointer;
}
</style>
</head>
<body>
	<div class="container">
		<h3>${(pageObject.acceptMode == 1)?"받은":(pageObject.acceptMode == 2 )?"보낸":"모든"}
			메세지 리스트</h3>
		<div>
			<span class="float-right"> <!-- Button to Open the Modal -->
				<button type="button" class="btn btn-primary" data-toggle="modal"
					data-target="#sendModal"
				>메세지 보내기</button>
			</span>
			<a href="list.do?mode=1&${pageObject.pageQuery}"
				class="btn btn-light msgModeBtn ${(pageObject.acceptMode == 1 || pageObject.acceptMode == '')?'active':'' }"
			>
				<b>받은 메세지</b>
			</a>
			<a href="list.do?mode=2&${pageObject.pageQuery}"
				class="btn btn-light msgModeBtn ${(pageObject.acceptMode == 2 )?'active':'' }"
			>
				<b>보낸 메세지</b>
			</a>
			<a href="list.do?mode=3&${pageObject.pageQuery}"
				class="btn btn-light msgModeBtn ${(pageObject.acceptMode == 3 )?'active':'' }"
			>
				<b>모든 메세지</b>
			</a>
		</div>
		<hr>
		<div class="bg-dark text-white p-3" style="border-radius:8px;">
			<c:if test="${ empty list }">메세지가 존재하지 않습니다.</c:if>
			<c:if test="${ !empty list }">
				<c:forEach items="${list }" var="vo">
					<div
						class="media p-1 dataRow mg-0 " data-accept="${(vo.senderId == login.id)?0:1 }"
						style="border-radius: 5px; width: 100%;"
					>
						<c:if test="${vo.senderId == login.id }">
							<!-- 내가 보낸 사람이다. 받는 사람의 정보만 표시한다. 오른쪽 정렬 -->
							<div
								class="media-body text-right mt-1 mb-1 
								${(empty vo.acceptDate)?'font-weight-bold':'' }" 
							>
								<b>${vo.accepterName } </b><small><i>(${vo.accepterId })
								</i>/ 보낸 날짜 : ${vo.sendDate } / 읽은 날짜 : ${(empty vo.acceptDate)?"읽지 않음":vo.acceptDate }
								<span class="no" data-data="${vo.no}"></span>
								</small>
								<p class="mb-1">${vo.content }</p>
							</div>
							<img src="${vo.accepterPhoto }" alt="/upload/image/noImage.png"
								class="ml-3 mt-1 rounded-circle" style="width: 30px;"
							>
						</c:if>

						<c:if test="${vo.senderId != login.id }">
							<!-- 내가 받는 사람이다. 보낸 사람의 정보만 표시한다. -->
							<img src="${vo.senderPhoto }" alt="/upload/image/noImage.png"
								class="mr-3 mt-1 rounded-circle" style="width: 30px;"
							>
							<div
								class="media-body mt-1 mb-1 
								${(empty vo.acceptDate)?'font-weight-bold':'' } no" data-data="${vo.no}"
							>
								<b>${vo.senderName } </b> <small><i>(${vo.senderId })
								<span class="no" data-data="${vo.no}"></span>
								</i> / 보낸 날짜 : ${vo.sendDate } / 읽은 날짜 : ${(empty vo.acceptDate)?"읽지 않음":vo.acceptDate }
								<span class="no" data-data="${vo.no}"></span>
								</small>
								<p class="mb-1">${vo.content }</p>
							</div>
						</c:if>
					</div>
				</c:forEach>
			</c:if>
		</div>
		<hr>
	</div>

	<!-- The Modal -->
	<div class="modal" id="sendModal">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">Modal Heading</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>

				<form action="write.do" method="post">
					<input type="hidden" name="perPageNum"
						value="${pageObject.perPageNum }"
					>
					<!-- Modal body -->
					<div class="modal-body">
						<div class="form-group">
							<label>받는 사람 ID</label>
							<input name="accepterId" required class="form-control">
						</div>
						<div class="form-group">
							<label>보낼 메세지</label>
							<textarea name="content" required class="form-control"></textarea>
						</div>

					</div>

					<!-- Modal footer -->
					<div class="modal-footer">
						<button class="btn btn-primary">
							<b>Send</b>
						</button>
						<button type="button" class="btn btn-danger" data-dismiss="modal">
							<b>Close</b>
						</button>
					</div>
				</form>

			</div>
		</div>
	</div>

</body>
</html>