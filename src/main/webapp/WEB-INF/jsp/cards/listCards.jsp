<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="cards">
    <h2>Cards</h2>

    <table id="cardsTable" class="table table-striped">
        <thead>
        <tr>
         	<th style="width: 150px;">ID</th>
            <th style="width: 150px;">Name</th>
            <th style="width: 150px;">Description</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cards}" var="card">
            <tr>
             <td>
                    <c:out value="${card.id}"/>
                </td>
                <td>
                    <c:out value="${card.name}"/>
                </td>

                <td>
                    <c:out value="${card.description}"/>
                </td>
            </tr>
            
        </c:forEach>
        </tbody>
    </table>

	
</petclinic:layout>