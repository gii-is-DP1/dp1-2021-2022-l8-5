<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="games">
    <h2>Games</h2>

    <table id="gamesTable" class="table table-striped">
        <thead>
        <tr>
         	<th style="width: 150px;">ID</th>
            <th style="width: 150px;">Start Date</th>
            <th style="width: 150px;">Finish Date</th>
            <th style="width: 150px;">First Player</th>
            <th style="width: 150px;">Second Player</th>
            <th style="width: 150px;">Third Player</th>
            <th style="width: 150px;">Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${games}" var="game">
            <tr>
             	<td>
                    <c:out value="${game.id}"/>
                </td>
                <td>
                    <c:out value="${game.startDate}"/>
                </td>
                <td>
                    <c:out value="${game.finishDate}"/>
                </td>
                <td>
                    <c:out value="${game.firstPlayer.username}"/>
                </td>
                <td>
                    <c:out value="${game.secondPlayer.username}"/>
                </td>
                <td>
                    <c:out value="${game.thirdPlayer.username}"/>
                </td>
                 
				<td> 
					
                    <spring:url value="/games/delete/{gameId}" var="gameUrl">
                        <spring:param name="gameId" value="${game.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(gameUrl)}"><span class=" glyphicon glyphicon-trash" aria-hidden="true"></span>
                    <span>Delete</span></a>
                    
                    <spring:url value="/games/update/{playerId}" var="editUrl">
       					 <spring:param name="gameId" value="${player.id}"/>
    				</spring:url>
    				 <a href="${fn:escapeXml(editUrl)}"><span class=" glyphicon glyphicon-edit" aria-hidden="true"></span>
							<span>Update</span></a>
                    
				</tr>
               
            </tr>
            
        </c:forEach>
        </tbody>
    </table>

	
</petclinic:layout>