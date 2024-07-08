<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${empty id }">
	<strong>사용 가능</strong>한 아이디 입니다.
</c:if>
<c:if test="${!empty id }">
	<strong>중복된 아이디</strong> 입니다. 다른 아이디를 입력해 주세요.
</c:if>
