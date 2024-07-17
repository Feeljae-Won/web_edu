<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="pageNav" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>QnA 게시판 리스트</title>
<script type="text/javascript">
	$(function() {
		// 2. jquery 확인
		console.log("jquery loading ---------------------");

		// 이벤트 처리
		// 한줄 데이터를 클릭하면 질문답변 보기로 이동 - 번호 필요
		$(".dataRow").click(function() {
			// 글 번호 수집
			let no = $(this).find(".no").data("data");
			console.log("no = " + no);
			location = "view.do?no=" + no + "&${pageObject.pageQuery}";
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
	background: white;
	cursor: pointer;
}

.dataRow {
	text-align: center;
}

#footer {
	background: black;
}
</style>
</head>
<body>
	<div class="container p-3 my-3 bg-dark text-white"
		style="border-radius: 10px 10px 10px 10px; hieght: 200px;"
	>
		<form action="list.do">
			<div class="row">
				<div class="col-sm-8">
					<div class="input-group mb-8">
						<div class="input-group-prepend">
							<select name="key" id="key" class="form-control">
								<option selected value="t">제목</option>
								<option value="c">내용</option>
								<option value="i">아이디</option>
								<option value="tc">제목 + 내용</option>
								<option value="ti">제목 + 아이디</option>
								<option value="ci">내용 + 아이디</option>
								<option value="tci">모두</option>
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
								<option value="10">10</option>
								<option value="15">15</option>
								<option value="20">20</option>
								<option value="25">25</option>
								<option value="30">30</option>
							</select>
						</div>
					</div>
				</div>
				<!-- col-sm-4 의 끝 : 한페이지당 표시 데이터 개수 -->
			</div>
		</form>
		<table class="table table-hover"
			style="text-align: center; border-radius: 10px; border-style: hidden;"
		>
			<thead class="thead-dark">
				<tr>
					<th style="border-radius: 10px 0px 0px 0px; width: 10%;">번호</th>
					<th style="width: 65%; text-align: left;">제목</th>
					<th style="width: 15%">작성자</th>
					<th style="border-radius: 0px 10px 0px 0px; width: 10%">작성일</th>
				</tr>
			</thead>
			<c:forEach items="${list }" var="vo">
				<tr class="dataRow table-light">
					<!-- td : table data - 테이블 데이터 텍스트 -->
					<td class="no " data-data="${vo.no}">
						<c:if test="${vo.levNo == 0}">
	            			${vo.no}
	        			</c:if> 
	        			<c:if test="${vo.levNo != 0}">
						</c:if>
					</td>
					<td style="text-align: left;"><c:if test="${ vo.levNo != 0}">
							<!-- 빈칸 넣기(들여쓰기) -->
							<c:forEach begin="1" end="${vo.levNo * 3 }">&nbsp;</c:forEach>
							<i class="material-icons">subdirectory_arrow_right</i>
						</c:if> ${vo.title}</td>
					<td>${vo.name}</td>
					<td>${vo.writeDate}</td>
				</tr>
			</c:forEach>
			<c:if test="${!empty login }">
				<tr>
					<td colspan="4">
						<div>
							<a href="questionForm.do?perPageNum=${pageObject.perPageNum }">
								<button class="btn btn-light float-left">
									<b>질문하기</b>
								</button>
							</a>
						</div>
					</td>
				</tr>
			</c:if>
		</table>
		<!-- 페이지 네이션 -->
		<div>
			<pageNav:pageNav listURI="list.do" pageObject="${pageObject }"></pageNav:pageNav>
		</div>

	</div>
</body>
</html>