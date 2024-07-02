/**
 *  게시판 류의 필요한 처리 메서드
 */

 // 입력 데이터가 비어 있는 경우의 메서드
 // 비어 있다면 true를 리턴한다.
 // 결과가 true이면 페이지 이동을 막기 위해서 추후에 return false를 실행해야 한다.
 // isEmpty(객체이름-선택자, 항목이름) {}
 function isEmpty(objName, name, isTrim) {
	let str = $(objName).val().trim();
	if (isTrim == 1) str = str.trim(); 
		// 공백을 제거한 데이터를 입력 객체에 다시 넣는다.
		$(objName).val(str);
		if (str == "") {
			alert(name + "은(는) 반드시 입력 하셔야 합니다."); // 경고
			$(objName).focus(); // 커서 위치
			return true; // 비어있음(true)을 리턴한다.
		} // 체크 끝
	
 } // end of isEmpty()
 
 
 // 길이 제한
function lengthCheck(objName, name, min, max, isTrim) {
	console.log("objName = " + objName + ", name = " + name + ", Min = " + min + ", Max = " + max);
	let str = $(objName).val().trim();
	if (isTrim == 1) {
		str = str.trim(); 
		// 공백을 제거한 데이터를 입력 객체에 다시 넣는다.
		$(objName).val(str);
		} // end of trim
		
		let len = str.length;
		if (len < min || len > max) {
			alert(name + "은(는) " + min + "자 이상 " + max + "자 사이로 입력하셔야 합니다. \n현재 글자 수 : " + len + "자"); // 경고
			$(objName).focus(); // 커서 위치
			return true; // 비어있음(true)을 리턴한다.
		} // 체크 끝
		
 } // end of lengthCheck()
 
 
 // body 부분의 문서가 로딩이 끝나면 처리되는 부분
 $(function() {
	
	$(".cancelBtn").click(function(){
		history.back();
	});
 });
 
 
 