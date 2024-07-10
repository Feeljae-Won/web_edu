/**
 *  main에서 사용되는 js
 */

 $(function() {
	$(".dataRow").click(function() {
//		alert("클릭");
		let no = $(this).find(".no").text();
		console.log("no = "	+ no);
		// 어떤 모듈인지 확인하기
		if($(this).hasClass("board")){
//			alert("일반 게시판 클릭");
			location="/board/view.do?no=" + no + "&inc=1";
		} else if($(this).hasClass("image")){
//			alert("이미지 게시판 클릭");
			location="/image/view.do?no=" + no + "&inc=1";
		};
	});
 });