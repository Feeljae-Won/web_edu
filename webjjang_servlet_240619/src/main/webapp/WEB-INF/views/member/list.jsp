<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="pageNav" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 리스트</title>
<script type="text/javascript">
$(function(){
	
	// 회원 한줄을 클릭하면 회원 정보보기로 이동 시키는 처리
	function dataRowClick(){
		alert("dataRow click");
	}
	
	// 이벤트처리
	$(".dataRow").on("click", function(){
		dataRowClick();
	});
	
	$(".grade, .status").parent()
	.on("mouseover", function(){
		// dataRow의 click 이벤트를 없앤다.
		$(".dataRow").off("click");
	})
	.on("mouseout", function(){
		// dataRow의 click 이벤트를 다시 설정해준다..
		$(".dataRow").on("click", function(){
			dataRowClick()
		});
	});
	
	$(".dataRow").on("change", ".grade, .status", function(){
		// alert("값이 바뀜");
		// this - select 태그 선택 .next() 다음 태그 - div 태그
		// 변경되었는지 알아내는 것 처리.
		let changeData =  $(this).val();
		let data = $(this).data("data"); // data-data 속성으로 넣어 둔다.
		console.log("원래 데이터=" + data + ", 바꿀 데이터=" + changeData);
			
		if(changeData == data)
			$(this).next().find("button").prop("disabled", true);
		else
			$(this).next().find("button").prop("disabled", false);
	});
	
	$("#gradeBtn").on("click", function(){
		let status = $(this).closest(".dataRow").find("#status").data("data");
		let gradeNo = $(this).closest(".dataRow").find(".grade").val();
// 		alert("this.gradeNo = " + gradeNo)
// 		alert("this.status = " + status);
		if(status != "정상" && gradeNo != 1) {
			alert("회원 상태가 '" + status + "'이므로 회원 등급을 변경할 수 없습니다.\n회원 등급을 변경하려면 상태를 '정상'으로 변경해 주세요.");
			return false;
		}
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
	vertical-align: baseline;
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
	<div class="jumbotron jumbotron-fluid">
		<div class="container">
			<h1>Member List</h1>
			<p>interYard Member</p>
		</div>
	</div>
	<div class="container p-3 my-3 bg-dark text-white"
		style="border-radius: 10px 10px 10px 10px; hieght: 200px;">
		<form action="list.do">
			<div class="row">
				<div class="col-sm-8">
					<div class="input-group mb-8">
						<div class="input-group-prepend">
							<select name="key" id="key" class="form-control">
								<option selected value="i">아이디</option>
								<option value="n">이름</option>
								<option value="t">연락처</option>
								<option value="int">모두</option>
							</select>
						</div>
						<input type="text" class="form-control" placeholder="검색" id="word"
							name="word" value="${pageObject.word }">
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
								<option value="20">20</option>
								<option value="30">30</option>
								<option value="40">40</option>
								<option value="50">50</option>
							</select>
						</div>
					</div>
				</div>
				<!-- col-sm-4 의 끝 : 한페이지당 표시 데이터 개수 -->
			</div>
		</form>
		<table class="table table-hover"
			style="text-align: center; border-radius: 10px; border-style: hidden;">
			<thead class="thead-dark">
				<tr>
					<th style="border-radius: 10px 0px 0px 0px;">아이디</th>
					<th >이름</th>
					<th >생년월일</th>
					<th >성별</th>
					<th >연락처</th>
					<th class="dropdown">
						<div class="dropdown-toggle" data-toggle="dropdown">
							회원등급
						</div>
						<div class="dropdown-menu">
					      <a class="dropdown-item" href="#" >일반회원</a>
					      <a class="dropdown-item" href="#" >쇼핑몰관리자</a>
					      <a class="dropdown-item" href="#" >최종관리자</a>
					    </div>
					</th>
					<th class="dropdown">
						<div class="dropdown-toggle" data-toggle="dropdown">
							상태
						</div>
						<div class="dropdown-menu">
					      <a class="dropdown-item" href="#">정상</a>
					      <a class="dropdown-item" href="#">휴면</a>
					      <a class="dropdown-item" href="#">탈퇴</a>
					      <a class="dropdown-item" href="#">강퇴</a>
					    </div>
					</th>
					<th style="border-radius: 0px 10px 0px 0px;">사진</th>
				</tr>
			</thead>
			<c:forEach items="${list }" var="vo">
				<tr class="dataRow table-light">
					<!-- td : table data - 테이블 데이터 텍스트 -->
					<td class="id">${vo.id}</td>
					<td >${vo.name}</td>
					<td>${vo.birth}</td>
					<td>${vo.gender}</td>
					<td>${vo.tel}</td>
					<td>
						<form action="changeGrade.do">
						<input name="id" value="${vo.id }" type="hidden">
						<div class="input-group mb-3">
							  <select class="form-control grade" id="gradeNo" name="gradeNo" style="width:70px;"
							  	data-data="${vo.gradeNo }"
							  >
								    <option value="1" ${(vo.gradeNo == 1)?"selected":"" }>일반회원</option>
								    <option value="2" ${(vo.gradeNo == 2)?"selected":"" }>쇼핑몰관리자</option>
								    <option value="9" ${(vo.gradeNo == 9)?"selected":"" }>최종관리자</option>
							  </select>
							  <div class="input-group-append">
							  	<button class="btn btn-success" id="gradeBtn" type="submit" disabled>변경</button>
							  </div>
						</div>
						</form>
					</td>
					<td>
						<form action="changeStatus.do">
						<input name="id" value="${vo.id }" type="hidden">
						<div class="input-group mb-3">
							  <select class="form-control status" id="status" name="status" style="width:50px;"
							  	data-data="${vo.status }">
								    <option value="정상" ${(vo.status == "정상")?"selected":"" }>정상</option>
								    <option value="탈퇴" ${(vo.status == "탈퇴")?"selected":"" }>탈퇴</option>
								    <option value="휴면" ${(vo.status == "휴면")?"selected":"" }>휴면</option>
								    <option value="강퇴" ${(vo.status == "강퇴")?"selected":"" }>강퇴</option>
							  </select>
							  <div class="input-group-append">
							  	<button class="btn btn-success" id="statusBtn" type="submit" disabled>변경</button>
							  </div>
						</div>
						</form>
					</td>
					<td>
<%-- 						<c:if test="${empty vo.photo}">X</c:if> --%>
<%-- 						<c:if test="${!empty vo.photo}"><a href="${vo.photo }">O</a></c:if> --%>
						<c:if test="${empty vo.photo}">
							<i class="fa fa-user-circle" style="font-size:50x"></i>
						</c:if>
						<c:if test="${!empty vo.photo}">
							<a href="${vo.photo }">
							<img src="${vo.photo }" style="width:30px; height:30px;">
							</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="7">
					<div>
						<a href="writeForm.do?perPageNum=${pageObject.perPageNum }"><button class="btn btn-light">
								<b>등록</b>
							</button></a>
					</div>
				</td>
			</tr>
		</table>
		<!-- 페이지 네이션 -->
		<div>
			<pageNav:pageNav listURI="list.do" pageObject="${pageObject }"></pageNav:pageNav>
		</div>
		
	</div>
</body>
</html>