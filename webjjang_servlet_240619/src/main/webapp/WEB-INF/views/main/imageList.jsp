<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="pageNav" tagdir="/WEB-INF/tags"%>
<script type="text/javascript">
	$(function() {

		// 이미지 사이즈 조정 4:3
		let imgWidth = $(".imageDiv:first").width();
		let imgHeight = $(".imageDiv:first").height();

		console.log("img width : " + imgWidth);
		console.log("img height : " + imgHeight);
		
		// 높이 계산 - 너비는 동일하다. : 이미지와 이미지를 감싸고 있는 div 태그의 높이로 사용
		let height = imgWidth / 4 * 3;
		
		// 전체 imageDiv의 높이를 조정한다.
		$(".imageDiv").height(height);
		// imageDiv의 배경을 검정색으로 변경
		$(".imageDiv").css("background","black");
		
		// 이미지 배열로 처리하면 안된다. foreach 사용 - jQuery 사용할 때는 each() / - javascript = foreach
		$(".imageDiv > img").each(function(idx, image) {
// 			alert(image);
			// 이미지가 계산된 높이 보다 크면 줄인다.
			if($(image).height() > height) {
				let image_width = $(image).width();
				let image_height = $(image).height();
				let width = height / $(image).height() * image_width;
				
				console.log("changed image width : " + width);
				
				// 이미지 높이 줄이기
				$(image).height(height);
				// 이미지 너비 줄이기
				$(image).width(width);
			}
		});
		

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

<script type="text/javascript">
	$(function() {

		// 2. jquery 확인
		console.log("jquery loading ---------------------");

	});
</script>

<span class="float-right list"><a href="/image/list.do"><b>+ 더보기</b></a>
</span>
<h4>Gallery</h4>
		<c:if test="${empty imageList }">
			<div class="jumbotron jumbotron-fluid">
				<div class="container">
					<h4>데이터가 존재하지 않습니다.</h4>
					<p>이미지를 등록해 주세요.</p>
				</div>
			</div>
		</c:if>
		<c:if test="${!empty imageList }">
			<div class="row">
				<!-- 이미지의 데이터가 있는 만큼 반복해서 표시하는 처리 시작 -->
				<c:forEach items="${imageList }" var="vo" varStatus="vs">
					<!-- 줄바꿈처리 - 찍는 인덱스 번호가 3의 배수이면 줄바꿈을 한다. -->
					<c:if test="${(vs.index != 0) && (vs.index % 3 == 0) }">
						${ "</div>" }
						<p>${ "<div class='row'>" }
					</c:if>
					<!-- 데이터 표시 시작 -->
					<div class="col-md-4 dataRow image imageLink">
						<div class="card text-dark" style="width: 100%;">
							<div class="imageDiv text-center align-content-center" style="border-radius:10px;">
								<img class="card-img-top" src="${vo.fileName }" alt="image"
									style="width: 100%"
								>
							</div>
							<div class="card-body text-truncate title">
								<span class="float-right">${vo.hit }</span> <strong
									class="card-title"
								> <span class="no">${vo.no }</span>. <span>${vo.title }</span>
								</strong>
								<p>
								<p class="card-text">
									<span class="float-right">${vo.writeDate }</span> ${vo.name }(${vo.id })
								</p>
							</div>

						</div>
					</div>
					<!-- 데이퍼 표시 끝 -->
				</c:forEach>
				<!-- 이미지의 데이터가 있는 만큼 반복해서 표시하는 처리 끝 -->
			</div>
		</c:if>
