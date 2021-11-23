<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="players">

    <h2>Player Information</h2>
 <div class="row">
	 <div class="col-md-4">
	  <img height="120rem" src="<c:out value="${player.avatarUrl}"/>"/></td>
	 </div>



	<div class="col-md-6">
    <table class="table table-striped">
        <tr>
            <th>Name</th>
            <td><b><c:out value="${player.firstName} ${player.lastName}"/></b></td>
        </tr>
        <tr>
            <th>Username</th>
            <td><b><c:out value="${player.username}"/></b></td>
        </tr>
        
         <tr>
            <th>E-Mail</th>
            <td><b><c:out value="${player.email}"/></b></td>
        </tr>
    </table>
    </div>
</div>
    <spring:url value="{playerId}/edit" var="editUrl">
        <spring:param name="playerId" value="${player.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit Player</a>

    <br/>
    <br/>
    <br/>

</petclinic:layout>
