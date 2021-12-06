<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>

<dwarf:layout pageName="players">
    <h2>Players</h2>

    <table id="playersTable" class="table table-striped">
        <thead>
        <tr>
       		 <th style="width: 150px;">Profile Picture</th>
        	<th style="width: 150px;">ID</th>
            <th style="width: 150px;">Name</th>
             <th style="width: 150px;">Username</th>
             <th style="width: 150px;">E-Mail</th>
             <th style="width: 150px;">Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${selections}" var="player">
            <tr>
              	<td>
                   <img height="50rem" src="<c:out value="${player.avatarUrl}"/>"/></td>
            	<td>
                    <c:out value="${player.id}"/>
            	</td>
            
                <td>
                    <spring:url value="/players/{playerid}" var="playerUrl">
                        <spring:param name="playerid" value="${player.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(playerUrl)}"><c:out value="${player.firstName} ${player.lastName}"/></a>
                </td>   
                
             <td>
              <c:out value="${player.username}"/>
             </td> 

             <td>
                <c:out value="${player.email}"/>
               </td> 
             
    			<td> 
                    <spring:url value="/players/{playerId2}/delete" var="playerUrl">
                        <spring:param name="playerId2" value="${player.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(playerUrl)}"><span class=" glyphicon glyphicon-trash" aria-hidden="true"></span>
                    <span>Delete</span></a>
                    
                    <spring:url value="/players/{playerId}/edit" var="editUrl">
       					 <spring:param name="playerId" value="${player.id}"/>
    				</spring:url>
    				 <a href="${fn:escapeXml(editUrl)}"><span class=" glyphicon glyphicon-edit" aria-hidden="true"></span>
							<span>Update</span></a>
                 </td>   
				</tr>       
            </tr>
        </c:forEach>
        </tbody>
    </table>
       <sec:authorize access="hasAuthority('admin')">
		<a class="btn btn-default" href='<spring:url value="/players/new" htmlEscape="true"/>'>Add Player</a>
	</sec:authorize>
	
	       <sec:authorize access="hasAuthority('admin')">
		<a class="btn btn-default" href='<spring:url value="/players/find" htmlEscape="true"/>'>Find Player</a>
	</sec:authorize>
	
      <sec:authorize access="hasAuthority('admin')">
		<a class="btn btn-default" href='<spring:url value="/players/0" htmlEscape="true"/>'>Show Player0</a>
	</sec:authorize>
</dwarf:layout>
