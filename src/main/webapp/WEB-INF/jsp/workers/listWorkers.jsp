<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>

<dwarf:layout pageName="workers">
    <h2>Workers</h2>

    <table id="workersTable" class="table table-striped">
        <thead>
        <tr>
         	<th style="width: 150px;">ID</th>
            <th style="width: 150px;">xposition</th>
            <th style="width: 150px;">yposition</th>
            <th style="width: 150px;">status</th>
            <th style="width: 150px;">user</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${workers}" var="worker">
            <tr>
             	<td>
                    <c:out value="${worker.id}"/>
                </td>
                <td>
                    <c:out value="${worker.xposition}"/>
                </td>
                 <td>
                    <c:out value="${worker.yposition}"/>
                </td>
                
                <td>
                    <c:out value="${worker.status}"/>
                </td>
                <td>
                    <c:out value="${worker.player.username}"/>
                </td>
                				                    
                    <spring:url value="/workers/update/{WorkerId}" var="workerUrl">
       					 <spring:param name="WorkerId" value="${worker.id}"/>
    				</spring:url>
    				 <a href="${fn:escapeXml(editUrl)}"><span class=" glyphicon glyphicon-edit" aria-hidden="true"></span>
							<span>Update</span></a>
               
            </tr>
            
        </c:forEach>
        </tbody>
    </table>

	
</dwarf:layout>