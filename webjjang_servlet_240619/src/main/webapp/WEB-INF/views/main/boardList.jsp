<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="pageNav" tagdir="/WEB-INF/tags"%>
		<table class="table table-hover"
			style="text-align: center; border-radius: 10px; border-style: hidden;">
			<thead class="thead-dark">
				<tr>
					<th colspan="5" style="border-radius: 10px 10px 0px 0px; text-align:left;">
					<span class="float-right list"><a href="/board/list.do"><b>+</b></a>
					</span>
					Board List</th>
				</tr>
			</thead>
			<c:forEach items="${boardList }" var="vo">
				<tr class="dataRow table-light board boardLink">
					<!-- td : table data - 테이블 데이터 텍스트 -->
					<td class="no">${vo.no}</td>
					<td style="text-align:left;">${vo.title}</td>
					<td>${vo.writer}</td>
					<td>${vo.writeDate}</td>
					<td>${vo.hit}</td>
				</tr>
			</c:forEach>
		</table>
