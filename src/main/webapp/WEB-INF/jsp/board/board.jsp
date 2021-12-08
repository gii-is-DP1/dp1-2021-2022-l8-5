<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>

<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<dwarf:layout pageName="board">
	<h1>Has entrado en partida</h1>
	
    <div class="row">
        <div class="col-md-12">
            <dwarf:board board="${board}"/>
            <c:forEach items="${board.boardCells}" var="boardCell">
            	<dwarf:mountainCard padding="20" xsize="130" ysize="180" mountainCard="${boardCell.mountaincards.get(0)}"/>
            </c:forEach>
            
        </div>
    </div>
    
</dwarf:layout>