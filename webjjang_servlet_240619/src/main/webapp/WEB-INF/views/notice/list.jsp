<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="pageNav" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 리스트</title>
<script type="text/javascript">
	$(function() {
		// 이벤트 처리
		$(".dataRow").click(function() {
			// 			alert("click --------");
			// 글 번호 수집
			let no = $(this).find(".no").text();
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

		// 공지사항 라디오 버튼 체인지 이벤트
		$(".noticeOption").change(function() {
			// name이 같은 것끼리 배열로 설정되어 있음 (index 번호로)
			if (this.optionList[0].checked) {
				// 				alert("현재공지");
				location = "/notice/list.do?period=pre";
			} else if (this.optionList[1].checked) {
				// 				alert("이전공지");
				location = "/notice/list.do?period=old";
			} else if (this.optionList[2].checked) {
				// 				alert("예정공지");
				location = "/notice/list.do?period=res";
			} else if (this.optionList[3].checked) {
				// 				alert("모든공지");
				location = "/notice/list.do?period=all";
			}
		});

		// radio 버튼 활성화
		$(function() {
			// 아이디로 찾아서 속석 적용
		 	$('#${pageObject.period}').prop('checked', true);
			
			// 속성으로 찾아서 속성 적용
// 			$('[value="${pageObject.period}"]').prop('checked', true);
		})

	});
</script>

<style type="text/css">
.dataRow:hover {
	background: white;
	cursor: pointer;
}

.dataRow {
	text-align: center;
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
	<div class="container p-3 my-3 bg-dark text-white"
		style="border-radius: 10px 10px 10px 10px; hieght: 200px;"
	>
		<!-- 리스트 상단 검색 및 페이지 정보 -->
		<form action="list.do">
			<div class="row">
				<!-- 검색란의 시작 -->
				<div class="col-sm-8">
					<div class="input-group mb-8">
						<div class="input-group-prepend">
							<select name="key" id="key" class="form-control">
								<option selected value="t">제목</option>
								<option value="c">내용</option>
								<option value="tc">제목 + 내용</option>
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

				<!-- 페이지 정보 시작 -->
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
		<!-- 리스트 상단 끝 -->

		<!-- 관리자만 보이는 공지 옵션 -->
		<c:if test="${!empty login && login.gradeNo == 9 }">
			<p>
			<div>
				<form class="noticeOption">
					<div class="custom-control custom-radio custom-control-inline">
						<input type="radio" class="custom-control-input" id="pre"
							name="optionList" value="pre"
						>
						<label class="custom-control-label" for="pre">현재 공지</label>
					</div>
					<div class="custom-control custom-radio custom-control-inline">
						<input type="radio" class="custom-control-input" id="old"
							name="optionList" value="old"
						>
						<label class="custom-control-label" for="old">이전 공지</label>
					</div>
					<div class="custom-control custom-radio custom-control-inline">
						<input type="radio" class="custom-control-input" id="res"
							name="optionList" value="res"
						>
						<label class="custom-control-label" for="res">예정 공지</label>
					</div>
					<div class="custom-control custom-radio custom-control-inline">
						<input type="radio" class="custom-control-input" id="all"
							name="optionList" value="all"
						>
						<label class="custom-control-label" for="all">모든 공지</label>
					</div>
				</form>
			</div>
		</c:if>
		<!-- 관리자만 보이는 공지 옵션 끝 -->

		<p>
		<table class="table table-hover"
			style="text-align: center; border-radius: 10px; border-style: hidden;"
		>
			<thead class="thead-dark">
				<tr>
					<th style="border-radius: 10px 0px 0px 0px; width: 5%;">No</th>
					<th>공지 기간</th>
					<th style="text-align: left; width: 60%;">제목</th>
					<th style="border-radius: 0px 10px 0px 0px;">최종 등록일</th>
				</tr>
			</thead>
			<c:forEach items="${list }" var="vo">
				<tr class="dataRow table-light">
					<!-- td : table data - 테이블 데이터 텍스트 -->
					<td class="no">${vo.no}</td>
					<td>${vo.startDate}~${vo.endDate}</td>
					<td style="text-align: left;"><b>${vo.title}</b></td>
					<td>${vo.updateDate}</td>
				</tr>
			</c:forEach>
		</table>
		<p>

			<!-- 공지사항 등록은 최종 관리자만 가능 -->
			<c:if test="${!empty login && login.gradeNo == 9 }">
				<div class="">
					<a href="writeForm.do?perPageNum=${pageObject.perPageNum }">
						<button class="btn btn-light float-left">
							<b>등록</b>
						</button>
					</a>
				</div>
			</c:if>

			<!-- 페이지 네이션 -->
		<div>
			<pageNav:pageNav listURI="list.do" pageObject="${pageObject }"></pageNav:pageNav>
		</div>

	</div>
</body>
</html>