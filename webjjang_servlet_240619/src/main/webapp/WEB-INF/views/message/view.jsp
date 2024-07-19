<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Message View</title>
</head>
<body>
	<div class="container">
		<c:if test="${param.accept == 1 }">
			<h3>받은 메세지 보기</h3>
			<hr>
			<div class="card">
				<div class="card-header">
					<div class="media hidden p-1">
						<img src="${vo.senderPhoto }" alt="/upload/image/noImage.png"
							class="mr-3 mt-1 rounded-circle" style="width: 60px;"
						>
						<div class="media-body">
							<b>${vo.senderName } </b><small><i>(${vo.senderId })</i>
							/ 보낸 날짜 : ${vo.sendDate } / 읽은 날짜 : ${(empty vo.acceptDate)?"읽지 않음":vo.acceptDate }
							</small>
						</div>
					</div>
				</div>
				<div class="card-body">
					<pre>${vo.content }</pre>
				</div>
				<div class="card-footer">
					<a href="/message/list.do?mode=${param.mode }&${pageObject.pageQuery}" 
						 class="btn btn-secondary float-right"><b>리스트</b></a>
					<a href="/message/delete.do" class="btn btn-danger float-right mr-1"><b>삭제</b></a>
					<a href="/message/writeForm.do" class="btn btn-light"><b>답장하기</b></a>
				</div>
			</div>
		</c:if>
		<c:if test="${param.accept == 0 }">
			<h3>보낸 메세지 보기</h3>
			<hr>
			<div class="card">
				<div class="card-header">
					<div class="media hidden p-1">
						<img src="${vo.accepterPhoto }" alt="/upload/image/noImage.png"
							class="mr-3 mt-1 rounded-circle" style="width: 30px;"
						>
						<div class="media-body">
						<h5 class= "mt-2">
							<b>${vo.accepterName } </b><small><i>(${vo.accepterId })</i>
							/ 보낸 날짜 : ${vo.sendDate } / 읽은 날짜 : ${(empty vo.acceptDate)?"읽지 않음":vo.acceptDate }
							</small>
						</h5>
						</div>
					</div>
				</div>
				<div class="card-body">
					<pre>${vo.content }</pre>
				</div>
				<div class="card-footer">
					<a href="/message/list.do?mode=${param.mode }&${pageObject.pageQuery}" 
						class="btn btn-secondary float-right"><b>리스트</b></a>
					<!-- 상대방이 메세지를 읽지 않은 경우에만 삭제 가능하다. -->
					<c:if test="${empty vo.acceptDate}">
						<a href="/message/delete.do" class="btn btn-danger float-right mr-1"><b>전송취소</b></a>
					</c:if>
					<a href="/message/updateForm.do" class="btn btn-light
						${(empty vo.acceptDate)?'':'disabled'}"><b>수정하기</b></a>
				</div>
			</div>
		</c:if>
	</div>
</body>
</html>