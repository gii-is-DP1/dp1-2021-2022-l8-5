<%@ attribute name="index" required="true" rtexprvalue="true"
	description="Index of the board cell from board" %>
<%@ attribute name="xposition" required="true" rtexprvalue="true" 
	description="X position of the board cell" %>
<%@ attribute name="yposition" required="true" rtexprvalue="true" 
	description="Y position of the board cell" %>
<%@ attribute name="isCellOccupied" required="true" rtexprvalue="true" 
	description="Indicates if the cell is occupied" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<c:choose>
	<c:when test="${isCellOccupied}">
		<button disabled class="btn btn-default" type="submit" name="pos" value="${xposition},${yposition}">
			<c:out value="${index+1}"/>
		</button>
	</c:when>
	<c:otherwise>
		<button class="btn btn-default" type="submit" name="pos" value="${xposition},${yposition}">
			<c:out value="${index+1}"/>
		</button>
	</c:otherwise>
</c:choose>