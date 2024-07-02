<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="pageNav" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이미지 게시판</title>
<script type="text/javascript">
	$(function() {
		// 이벤트 처리
		$(".dataRow").click(
				function() {
					// 			alert("click --------");
					// 글 번호 수집
					let no = $(this).find(".no").text();
					console.log("no = " + no);
					location = "view.do?no=" + no + "&inc=1"
							+ "&${pageObject.pageQuery}";
				})
		// perPageNum 처리
		$("#perPageNum").change(function() {
			// 			 alert("change perPageNum")
			// page는 1페이지 + 검색 데이터를 전부 보낸다.
			$("#searchForm").submit();
		})

		// 검색 데이터 세팅
		$("#key").val("${(empty pageObject.key)?'t':pageObject.key}");
		$("#perPageNum").val(
				"${(empty pageObject.perPageNum)?'10':pageObject.perPageNum}");
	})
</script>

<style type="text/css">
.dataRow:hover {
	opacity:80%;
	cursor: pointer;
}

button {
	float: left;
	border-radius: 10px 10px 10px 10px;
	padding: 5px;
}

button:hover {
	cursor: pointer;
	background: #444;
	color: white;
}

#footer {
	background: black;
}
</style>
<script type="text/javascript">
	$(function() {

		// 2. jquery 확인
		console.log("jquery loading ---------------------");

	});
</script>
</head>
<body>

	<div class="container p-3 my-3 bg-primary text-white"
		style="border-radius: 10px 10px 10px 10px; hieght: 200px;"
	>
		<h2>
			<i class="fa fa-caret-right"></i> Gallery List
		</h2>
		<p>
			<i class="fa fa-caret-right"></i> 이미지 게시판 리스트
		</p>
		<form action="list.do">
			<div class="row">
				<div class="col-sm-8">
					<div class="input-group mb-8">
						<div class="input-group-prepend">
							<select name="key" id="key" class="form-control">
								<option select value="t">제목</option>
								<option value="c">내용</option>
								<option value="w">작성자</option>
								<option value="tc">제목 + 내용</option>
								<option value="tw">제목 + 작성자</option>
								<option value="cw">내용 + 작성자</option>
								<option value="tcw">모두</option>
							</select>
						</div>
						<input type="text" class="form-control" placeholder="검색" id="word"
							name="word" value="${pageObject.word }"
						>
						<div class="input-group-append">
							<button class="btn btn-dark">
								<i class="fa fa-search"></i>
							</button>
						</div>

					</div>
				</div>
				<!-- col-md-8의 끝 : 검색 -->
				<div class="col-sm-4">
					<!-- 너비 조정을 위한 div 추가. float-right : 오른쪽 정렬 -->
					<div style="width: 170px;" class="float-right">
						<div class="input-group mb-2">
							<div class="input-group-prepend">
								<span class="input-group-text">Rows/Page</span>
							</div>
							<select name="perPageNum" id="perPageNum" class="form-control">
								<option value="6">6</option>
								<option value="9">9</option>
								<option value="12">12</option>
								<option value="15">15</option>
								<option value="18">18</option>
							</select>
						</div>
					</div>
				</div>
				<!-- col-sm-4 의 끝 : 한페이지당 표시 데이터 개수 -->
			</div>
		</form>
		<hr>
		<c:if test="${empty list }">
			<div class="jumbotron jumbotron-fluid">
				<div class="container">
					<h4>데이터가 존재하지 않습니다.</h4>
					<p>이미지를 등록해 주세요.</p>
				</div>
			</div>
		</c:if>
		<c:if test="${!empty list }">
			<div class="row">
				<!-- 이미지의 데이터가 있는 만큼 반복해서 표시하는 처리 시작 -->
				<c:forEach items="${list }" var="vo" varStatus="vs">
					<!-- 줄바꿈처리 - 찍는 인덱스 번호가 3의 배수이면 줄바꿈을 한다. -->
					<c:if test="${(vs.index != 0) && (vs.index % 3 == 0) }">
						${ "</div>" }
						<p>
						${ "<div class='row'>" }
					</c:if>
					<!-- 데이터 표시 시작 -->
					<div class="col-md-4 dataRow">
						<div class="card text-dark" style="width: 100%;">
							<img class="card-img-top" src="${vo.fileName }" alt="image"
								style="width: 100%">
							<div class="card-body">
								<strong class="card-title">
									<span class="float-right">${vo.writeDate }</span>
									${vo.name }(${vo.id })
								</strong>
								<p>
								<p class="card-text">
									<span class="no">${vo.no }</span>. ${vo.title }
								</p>
							</div>
						</div>
					</div>
					<!-- 데이퍼 표시 끝 -->
				</c:forEach>
				<!-- 이미지의 데이터가 있는 만큼 반복해서 표시하는 처리 끝 -->
			</div>
		</c:if>
		<hr>
		<!-- container footer -->
		<div>
		<!-- 로그인이 되어 있으면 보이게 하자 -->
		<c:if test="${ !empty login }">
			<a href="writeForm.do?perPageNum=${pageObject.perPageNum }">
				<button class="btn btn-light float-left">
					<b>등록</b>
				</button>
			</a>
		</c:if>
		</div>

		<!-- 페이지 네이션 -->
		<div>
			<pageNav:pageNav listURI="list.do" pageObject="${pageObject }"></pageNav:pageNav>
		</div>

	</div>
</body>
</html>