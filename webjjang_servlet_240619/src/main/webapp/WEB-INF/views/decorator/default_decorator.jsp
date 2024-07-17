<!-- sitemesh 사용을 위한 설정 파일 -->
<!-- 작성자 : 원필재 -->
<!-- 작성일 : 2024-06-28 -->
<!-- 최종수정일 : 2024-06-28 -->


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 개발자 작성한 title을 가져 다 사용 -->
<title>interYard:<decorator:title />
</title>
<!-- Bootstrap 4 + jquery 라이브러리 등록 - CDN 방식 -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"
>
<script
	src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.min.js"
></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"
></script>

<!-- icon 라이브러리 등록 - Font Awesome 4 / google -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet"
>

<style type="text/css">
.container {
	padding: 25px 5px;
}

pre {
	background: white;
	border: 0px;
}

/* Remove the navbar's default margin-bottom and rounded borders */
.navbar {
	margin-bottom: 0;
	border-radius: 0;
}

/* Add a gray background color and some padding to the footer */
footer {
	background-color: #f2f2f2;
	padding: 25px;
}

.carousel-inner img {
	width: 100%; /* Set width to 100% */
	margin: auto;
	min-height: 200px;
}

/* Hide the carousel text when the screen is less than 600 pixels wide */
@media ( max-width : 600px) {
	.carousel-caption {
		display: none;
	}
}

article {
	min-height: 795px;
}

#welcome {
	color: grey;
	margin: 0 auto;
}

.icon-with-badge {
	position: relative;
	display: inline-block;
}

.icon-with-badge .badge {
	position: absolute;
	top: -1px;
	right: -5px;
	padding: 0.25em 0.5em;
	font-size: 0.65rem;
}
</style>
<script type="text/javascript">
	$(function() {
		// 취소 버튼 이벤트
		$(".cancelBtn").click(function() {
			history.back();
		});
	});
</script>
<script type="text/javascript">
	$(document).ready(function() {
	});
</script>
<decorator:head />
</head>
<body>
	<header>
		<%-- 현재 요청 URI 가져오기 --%>
		<c:set var="requestURI" value="${pageContext.request.requestURI}" />

		<%-- 특정 URI 패턴에 따라 Jumbotron 적용 --%>
		<c:if test="${fn:contains(requestURI, '/main.do')}">
			<div class="jumbotron text-white"
				style="margin-bottom: 0; background-image: url('/upload/image/DSCF9544.JPG'); background-size: cover; background-position: center;"
			>
				<h1 style="text-shadow: 3px 3px 4px black; text-align: center;">Welcome
					to our site!</h1>
				<p>
				<h6 style="text-shadow: 1px 1px 2px black; text-align: center;">This
					is the home page.</h6>
			</div>
		</c:if>
		<nav class="navbar navbar-expand-lg bg-dark navbar-dark">
			<a class="navbar-brand active" href="/main/main.do">
				<i><b>i</b>nter<b>Yard</b></i>
			</a>

			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#collapsibleNavbar"
			>
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="collapsibleNavbar">
				<ul class="navbar-nav mr-auto">
					<li class="nav-item ${( module == '/notice')?'active' : ''}">
						<a class="nav-link" href="/notice/list.do">Notice</a>
					</li>
					<li class="nav-item ${( module == '/shop')?'active' : ''}">
						<a class="nav-link" href="/shop/list.do">Shop</a>
					</li>
					<li class="nav-item ${( module == '/board')?'active' : ''}">
						<a class="nav-link" href="/board/list.do">Board</a>
					</li>
					<li class="nav-item ${( module == '/image')?'active' : ''}">
						<a class="nav-link" href="/image/list.do">Gallery</a>
					</li>
					<li class="nav-item ${( module == '/qna')?'active' : ''}">
						<a class="nav-link" href="/qna/list.do">QnA</a>
					</li>
					<c:if test="${!empty login && login.gradeNo == 9 }">
						<li class="nav-item ${( module == '/member')?'active' : ''}">
							<a class="nav-link" href="/member/list.do">Member</a>
						</li>
					</c:if>
				</ul>
				<!-- 이곳에서 부터 오른쪽 정렬 -->
				<ul class="navbar-nav ml-auto d-flex">
					<c:if test="${ empty login }">
						<!-- 로그인 안했을 때 -->
						<li class="nav-item ">
							<a class="nav-link" href="/member/loginForm.do">
								<i class="fa fa-user"></i> LOGIN
							</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="/member/writeForm.do">
								<i class="fa fa-user-plus"></i> SIGNUP
							</a>
						</li>
					</c:if>
					<c:if test="${ !empty login }">
						<!-- 로그인 했을 때 -->
						<!-- 새로운 메세지 -->
						<li class="nav-item ">
							<div class="icon-with-badge">
								<a class="nav-link" href="/message/list.do">
									<i class="fa fa-bell"></i> 
									<c:if test="${login.newMsgCnt != 0}">
										<span
											class="badge badge-pill badge-danger"
										> ${(login.newMsgCnt == 0)?"0":login.newMsgCnt } </span>
									</c:if>
								</a>
							</div>
						</li>

						<!-- 사진 보여주기 처리 -->
						<li class="nav-link ">
							<c:if test="${empty login.photo }">
								<i class="fa fa-user-circle"></i>
							</c:if>
							<c:if test="${!empty login.photo }">
								<img src="${login.photo }" class="rounded-circle"
									style="height: 25px;"
								>
							</c:if>
							<i class="material-icons"></i> <b> ${login.name }(${login.gradeName })</b>님
						</li>
						<li class="nav-item">
							<a class="nav-link" href="/member/logout.do">
								<i class="fa fa-user-times"></i> 로그아웃
							</a>
						</li>
						<div class="dropdown">
							<button type="button" class="btn btn-light dropdown-toggle"
								data-toggle="dropdown"
							>
								<i class="fa fa-info-circle"></i> 마이페이지
							</button>
							<div class="dropdown-menu">
								<a class="dropdown-item" href="/member/view.do">
									<i class="fa fa-drivers-license-o"></i> 내 정보 보기
								</a>
								<a class="dropdown-item" href="/cart/list.do">
									<i class="fa fa-shopping-cart"></i> 장바구니
								</a>
								<a class="dropdown-item" href="/order/list.do">
									<i class="fa fa-truck"></i> 주문상세
								</a>
								<a class="dropdown-item" href="/member/delete.do">
									<i class="fa fa-user-times"></i> 회원탈퇴
								</a>
							</div>
						</div>
					</c:if>
				</ul>
			</div>
		</nav>
		<c:if test="${fn:contains(requestURI, '/board')}">
			<div class="jumbotron text-white"
				style="margin-bottom: 0; background-image: url('/upload/image/office.jpg'); background-size: cover; background-position: center;"
			>
				<h1 style="text-shadow: 3px 3px 4px black; text-align: center;">Board</h1>
				<p>
				<h6 style="text-shadow: 1px 1px 2px black; text-align: center;">Board
					List &amp; Write Your Story</h6>
			</div>
		</c:if>
		<c:if test="${fn:contains(requestURI, '/image')}">
			<div class="jumbotron text-white"
				style="margin-bottom: 0; background-image: url('/upload/image/gallery-bw.jpg'); background-size: cover; background-position: center;"
			>
				<h1 style="text-shadow: 3px 3px 4px black; text-align: center;">Gallery</h1>
				<p>
				<h6 style="text-shadow: 1px 1px 2px black; text-align: center;">Show
					your Experience</h6>
			</div>
		</c:if>
		<c:if test="${fn:contains(requestURI, '/member')}">
			<div class="jumbotron text-white"
				style="margin-bottom: 0; background-image: url('/upload/image/croud-bw.jpg'); background-size: cover; background-position: center;"
			>
				<h1 style="text-shadow: 3px 3px 4px black; text-align: center;">Login
					&amp; Sign Up!</h1>
				<p>
				<h6 style="text-shadow: 1px 1px 2px black; text-align: center;">Access
					for interYard !</h6>
			</div>
		</c:if>
		<c:if test="${fn:contains(requestURI, '/notice')}">
			<div class="jumbotron text-white"
				style="margin-bottom: 0; background-image: url('/upload/image/notice-bw.jpg'); background-size: cover; background-position: center;"
			>
				<h1 style="text-shadow: 3px 3px 4px black; text-align: center;">Notice</h1>
				<p>
				<h6 style="text-shadow: 1px 1px 2px black; text-align: center;">Click
					to Notice!</h6>
			</div>
		</c:if>
		<c:if test="${fn:contains(requestURI, '/qna')}">
			<div class="jumbotron text-white"
				style="margin-bottom: 0; background-image: url('/upload/image/qna-color.jpg'); background-size: cover; background-position: center;"
			>
				<h1 style="text-shadow: 3px 3px 4px black; text-align: center;">Q
					n A</h1>
				<p>
				<h6 style="text-shadow: 1px 1px 2px black; text-align: center;">Question
					&amp; Answer</h6>
			</div>
		</c:if>
	</header>
	<article>
		<!-- 여기에 게발자가 작성한 body 태그 안에 내용이 들어온다. -->
		<decorator:body />
	</article>
	<footer class="container-fluid text-center">
		<p>이 홈페이지의 저작권은 원필재에게 있습니다.</p>
	</footer>

	<c:if test="${ !empty msg }">
		<!-- msg를 표시할 모달 창 -->
		<!-- The Modal -->
		<div class="modal fade" id="msgModal">
			<div class="modal-dialog">
				<div class="modal-content">

					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title">처리 결과</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>

					<!-- Modal body -->
					<div class="modal-body">${msg }</div>

					<!-- Modal footer -->
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal"
						>닫기</button>
					</div>

				</div>
			</div>
		</div>
		<!-- 모달을 보이게 하는 스크립트 -->
		<script type="text/javascript">
			$(function() {
				$("#msgModal").modal("show");
			});
		</script>
	</c:if>
</body>
</html>

<%
session.removeAttribute("msg");
%>
