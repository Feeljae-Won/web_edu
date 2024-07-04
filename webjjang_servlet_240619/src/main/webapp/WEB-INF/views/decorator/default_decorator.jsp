<!-- sitemesh 사용을 위한 설정 파일 -->
<!-- 작성자 : 원필재 -->
<!-- 작성일 : 2024-06-28 -->
<!-- 최종수정일 : 2024-06-28 -->


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>웹짱 : <decorator:title />
</title>
<!-- Bootstrap 4 + jquery 라이브러리 등록 -CDN 방식 -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"
>
<script
	src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"
></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"
></script>

<!-- 아이콘 링크 등록 font awesome4, google -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet"
>

<style type="text/css">
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
</style>
<script type="text/javascript">
	$(document).ready(function() {
	});
</script>
<decorator:head />
</head>
<body>
	<header>
		<nav class="navbar navbar-expand-sm bg-primary navbar-dark">
			<a class="navbar-brand active" href="main.do">
				<i><b>i</b>nter<b>Yard</b></i>
			</a>

			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#collapsibleNavbar"
			>
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="collapsibleNavbar">
				<ul class="navbar-nav">
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
					<li class="nav-item" ${( module == '/qna_board')?'active' : ''}>
						<a class="nav-link" href="/qna_board/list.do">QnA</a>
					</li>
				</ul>
			</div>
			<div class="float-right">
				<ul class="navbar-nav">
					<c:if test="${ empty login }">
						<!-- 로그인 안했을 때 -->
						<li class="nav-item">
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
						<li class="nav-link ">
							<i class="material-icons"></i> <b> ${login.name }(${login.gradeName })</b>님
							안녕하세요.
						</li>
						<li class="nav-item">
							<a class="nav-link" href="/member/logout.do">
								<i class="fa fa-user-times"></i> 로그아웃
							</a>
						</li>
						<div class="dropdown">
							<button type="button" class="btn btn-primary dropdown-toggle"
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
	</header>
	<article>
		<!-- 여기에 게발자가 작성한 body 태그 안에 내용이 들어온다. -->
		<decorator:body />
	</article>
	<footer class="container-fluid text-center">
		<p>이 홈페이지의 저작권은 원필재에게 있습니다.</p>
	</footer>

	<c:if test="${ !empty msg }">
		<!-- msg를 표시할 modal 창 -->
		<div class="modal text-dark" id="msgModal">
			<div class="modal-dialog">
				<div class="modal-content">

					<!-- Modal Header -->
					<div class="modal-header">
						<!-- 버튼에 따라서 제목을 수정해서 사용 - .text(제목) -->
						<h4 class="modal-title">처리 결과 Modal</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>

					<!-- Modal body -->
					<div class="modal-body">${msg }</div>

					<!-- Modal footer -->
					<div class="modal-footer"></div>

				</div>
			</div>
		</div>
		<!-- modal을 보이게 하는 javascript -->
		<script type="text/javascript">
			$(function() {
				$("#msgModal").modal("show");
			})
		</script>
	</c:if>
</body>
</html>

<%
session.removeAttribute("msg");
%>
