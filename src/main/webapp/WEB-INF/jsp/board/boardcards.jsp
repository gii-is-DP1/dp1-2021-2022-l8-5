<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<dwarf:layout pageName="boardcards">
	
	<c:set var="i" value="1" scope="page" />
	
	<div class="row">
		<div class="col-md-6">
			<a class="btn btn-default" onclick="history.back()">
		        Return to the board
			</a>
		</div>
	</div>
	
	
	<c:forEach items="${boardCells}" var="boardCell">
		&nbsp;
		<div class="text-center">
		<h2><c:out value="Cards of the cell: ${i}"></c:out></h2>
		</div>
		<c:set var="pos" value="1" scope="page"/>
		<div class="row">
			<c:forEach items="${boardCell.mountaincards}" var="card">
				<div class="col-md-3">
					<dwarf:cardInfo card="${card}" position="${pos}"></dwarf:cardInfo>
					<c:set var="pos" value="${pos+1}" scope="page" />
				</div>
			</c:forEach>
		</div>
		<c:set var="i" value="${i+1}" scope="page" />
	</c:forEach>

</dwarf:layout>