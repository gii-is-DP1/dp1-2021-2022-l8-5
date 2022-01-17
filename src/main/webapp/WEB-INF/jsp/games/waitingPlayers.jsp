<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>

<dwarf:layout pageName="play">
	<p>PARTIDA CREANDOSE ...</p>
	<lu>
		<li>First player: ${game.firstPlayer.username}</li>
		
		<li>
			<c:choose>
				<c:when test="${empty game.secondPlayer}">
					<c:out value="Second player: waiting for player ..."/>
				</c:when>
				<c:otherwise>
					<c:out value="Second player: ${game.secondPlayer.username}"/>
				</c:otherwise>
			</c:choose>
		</li>
		
		<li>
			<c:choose>
				<c:when test="${empty game.thirdPlayer}">
					<c:out value="Third player: waiting for player ..."/>
				</c:when>
				<c:otherwise>
					<c:out value="Third player: ${game.thirdPlayer.username}"/>
				</c:otherwise>
			</c:choose>
		</li>
	</lu>
	
    <c:choose>
        <c:when test="${game.getPlayersList().size() <= 1}">
			<spring:url value="/games/{gameId}/delete" var="gameUrl">
		        <spring:param name="gameId" value="${game.id}"/>
	        </spring:url>
	        <a class="btn btn-default" href="${fn:escapeXml(gameUrl)}">Exit and delete</a>
        </c:when>
		<c:otherwise>
            <spring:url value="/games/{gameId}/exit" var="gameUrl">
		        <spring:param name="gameId" value="${game.id}"/>
	        </spring:url>
	        <a class="btn btn-default" href="${fn:escapeXml(gameUrl)}">Exit game</a>
        </c:otherwise>
    </c:choose>
     
    <c:if test="${game.allPlayersSet()}">
    	<c:if test="${loggedPlayer.getId() == game.firstPlayer.getId()}">
    		<spring:url value="/boards/game/{gameId}" var="boardGameUrl">
		        <spring:param name="gameId" value="${game.id}"/>
	        </spring:url>
	        <a class="btn btn-default" href="${fn:escapeXml(boardGameUrl)}">Start game !</a>
    	</c:if>
    </c:if>
	
</dwarf:layout>
