<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="players">
    <h2>Players</h2>

    <table id="playersTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Username</th>
            <th style="width: 150px;">Puntuación total</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${players}" var="player">
            <tr>
                <td>
                    <c:out value="${player.username}"/>
                </td>
                
                 <td>
                    <c:out value="${player.totalPoints}"/>
                </td>
                
            </tr>
            
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>