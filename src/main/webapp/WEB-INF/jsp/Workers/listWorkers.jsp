<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="workers">
    <h2>workers</h2>

    <table id="workersTable" class="table table-striped">
        <thead>
        <tr>
         	<th style="width: 150px;">ID</th>
            <th style="width: 150px;">position</th>
            <th style="width: 150px;">status</th>
            <th style="width: 150px;">user</th>
            <th style="width: 150px;">game</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${workers}" var="worker">
            <tr>
             	<td>
                    <c:out value="${worker.id}"/>
                </td>
                <td>
                    <c:out value="${worker.position}"/>
                </td>
                <td>
                    <c:out value="${worker.status}"/>
                </td>
                <td>
                    <c:out value="${worker.player.username}"/>
                </td>					                    
                    <spring:url value="/workers/update/{workerId}" var="editUrl">
       					 <spring:param name="workerId" value="${worker.id}"/>
    				</spring:url>
    				 <a href="${fn:escapeXml(editUrl)}"><span class=" glyphicon glyphicon-edit" aria-hidden="true"></span>
							<span>Update</span></a>
                    
				</tr>
               
            </tr>
            
        </c:forEach>
        </tbody>
    </table>

	
</petclinic:layout>