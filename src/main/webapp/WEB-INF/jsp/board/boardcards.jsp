<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<dwarf:layout pageName="boardcards">
	
	<c:set var="i" value="1" scope="page" />
	
	<c:forEach items="${boardCells}" var="boardCell">
		&nbsp;
		<div class="text-center">
		<h2><c:out value="Cards of the cell: ${i}"></c:out></h2>
		</div>
		<c:set var="pos" value="1" scope="page"/>
		<c:forEach items="${boardCell.mountaincards}" var="card">
			<div>
				<dwarf:cardInfo card="${card}" position="${pos}"></dwarf:cardInfo>
				<c:set var="pos" value="${pos+1}" scope="page" />
			</div>
		</c:forEach>
		<c:set var="i" value="${i+1}" scope="page" />
	</c:forEach>

</dwarf:layout>