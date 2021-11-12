<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="players2">
    <h2>Players</h2>

    <table id="players2Table" class="table table-striped">
        <thead>
        <tr>
        	<th style="width: 150px;">ID</th>
            <th style="width: 150px;">Name</th>
             <th style="width: 150px;">Username</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${selections}" var="player">
            <tr>
            	<td>
                    <c:out value="${player.id}"/>
            	</td>
            
                <td>
                    <spring:url value="/players2/{player2id}" var="player2Url">
                        <spring:param name="player2id" value="${player.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(player2Url)}"><c:out value="${player.firstName} ${player.lastName}"/></a>
                </td>   
                
             <td>
              <c:out value="${player.user.username}"/>
             </td>        
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
