<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="resources">
    <h2>Resources</h2>

    <table id="resourcesTable" class="table table-striped">
        <thead>
        <tr>
         	<th style="width: 150px;">ID</th>
            <th style="width: 150px;">GameID</th>
            <th style="width: 150px;">Player</th>
            <th style="width: 150px;">Iron</th>
            <th style="width: 150px;">Steel</th>
            <th style="width: 150px;">Gold</th>
            <th style="width: 150px;">Items</th>
            <th style="width: 150px;">Badges</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${resources}" var="resources">
            <tr>
             	<td>
                    <c:out value="${resources.id}"/>
                </td>
                <td>
                    <c:out value="${resources.game.id}"/>
                </td>
                <td>
                    <c:out value="${resources.player.username}"/>
                </td>
                <td>
                    <c:out value="${resources.iron}"/>
                </td>
                <td>
                    <c:out value="${resources.steel}"/>
                </td>
                <td>
                    <c:out value="${resources.gold}"/>
                </td>
                <td>
                    <c:out value="${resources.items}"/>
                </td>
                <td>
                    <c:out value="${resources.badges}"/>
                </td>
                 
            </tr>
            
        </c:forEach>
        </tbody>
    </table>

	
</petclinic:layout>