<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>

<dwarf:layout pageName="listGames">
    
    <h2><c:out value="${type}"/> Games</h2>
	
	<c:if test="${type.equals('Finished')}">
		<spring:url value="/games/listGames/current" var="currentGames"/>
        <a class="btn btn-default" href="${fn:escapeXml(currentGames)}">Current Games</a>
	</c:if>
	<c:if test="${type.equals('Current')}">
		<spring:url value="/games/listGames/finished" var="finishedGames"/>
        <a class="btn btn-default mb-3" href="${fn:escapeXml(finishedGames)}">Finished Games</a>
	</c:if>
	<br>
	<br>
    <table id="gamesTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Start Date</th>
            <th style="width: 150px;">Finish Date</th>
            <th style="width: 150px;">First Player</th>
            <th style="width: 150px;">Second Player</th>
            <th style="width: 150px;">Third Player</th>
            <c:if test="${type.equals('Current')}">
            	<th style="width: 150px;">Watch game</th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${indices}" var="index">
            <tr>
             	<td>
                    <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${games.get(index).startDate}"/>
                </td>
                <c:if test="${type.equals('Finished')}">
	                <td>
	                    <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${games.get(index).finishDate}"/>
	                </td>
                </c:if>
               
                <td>
                	<c:if test="${games.get(index).firstPlayer.username == null}">
                		<p>none</p>
                	</c:if>
                    <c:out value="${games.get(index).firstPlayer.username}"/>
                </td>
                <td>
                	<c:if test="${games.get(index).secondPlayer.username == null}">
                		<p>none</p>
                	</c:if>
                    <c:out value="${games.get(index).secondPlayer.username}"/>
                </td>
                <td>
                	<c:if test="${games.get(index).thirdPlayer.username == null}">
                		<p>none</p>
                	</c:if>
                    <c:out value="${games.get(index).thirdPlayer.username}"/>
                </td>
                <c:if test="${type.equals('Current')}">
                	<td>
						<spring:url value="/boards/{boardId}/game/{gameId}" var="watchGameUrl">
							<spring:param name="gameId" value="${games.get(index).id}"/>
							<spring:param name="boardId" value="${boardsId.get(index)}"/>
						</spring:url>
						<a href="${fn:escapeXml(watchGameUrl)}"><span class="glyphicon glyphicon-play-circle" aria-hidden="true"></span>
						<span>Spectate</span></a>
					</td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>

	
</dwarf:layout>