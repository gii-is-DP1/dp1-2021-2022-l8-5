<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>

<dwarf:layout pageName="players">

    <h2>Player Information</h2>
 <div class="row">
	 <div class="col-md-4">
	  <img height="120rem" src="<c:out value="${player.avatarUrl}"/>"/>
	 </div>



	<div class="col-md-6">
	<h3>Profile</h3> 
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
    <sec:authorize access="hasAuthority('admin')">
	   <spring:url value="{playerId}/edit" var="editUrl">
	       <spring:param name="playerId" value="${player.id}"/>
	   </spring:url>
	   <a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit Profile</a>
   </sec:authorize>
 	<br/>
    <br/>
    <c:if test="${!finishedGames.isEmpty()}">
	    <table class="table table-striped">
	   	<h3>Finished Playing</h3>
	    <thead>
	        <tr>
	        	<th style="width: 150px;">ID</th>
	             <th style="width: 150px;">Player 1</th>
	             <th style="width: 150px;">Player 2</th>
	             <th style="width: 150px;">Player 3</th>
	             <th style="width: 150px;">Start Date</th>
	             <th style="width: 150px;">End Date</th>
	        </tr>
	        </thead>
	        <tbody>
	        <c:forEach items="${finishedGames}" var="game">
	            <tr>
	            	<td>
	                    <c:out value="${game.id}"/>
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
	                    <c:out value="${game.startDate}"/>
	            	</td>
	              <td>
	                    <c:out value="${game.finishDate}"/>
	            	</td>
	            	  
					</tr>       
	            </tr>
	        </c:forEach>
	        </tbody>
	    </table>
    </c:if>
    <c:if test="${currentGame != null}">
	    <table class="table table-striped">
	   	<h3>Currently Playing</h3>
	    <thead>
	        <tr>
	        	<th style="width: 150px;">ID</th>
	             <th style="width: 150px;">Player 1</th>
	             <th style="width: 150px;">Player 2</th>
	             <th style="width: 150px;">Player 3</th>
	             <th style="width: 150px;">Start Date</th>
	        </tr>
	        </thead>
	        
	        <tbody>
	            <tr>
	            	<td>
	                    <c:out value="${currentGame.id}"/>
	            	</td>
	                 <td>
	                    <c:out value="${currentGame.firstPlayer.username}"/>
	            	</td>
	                
	                 <td>
	                    <c:out value="${currentGame.secondPlayer.username}"/>
	            	</td>
	                 <td>
	                    <c:out value="${currentGame.thirdPlayer.username}"/>
	            	</td>
	     	         <td>
	                    <c:out value="${currentGame.startDate}"/>
	            	</td>
	            	  
					</tr>       
	            </tr>
	        </tbody>
	    </table>
    </c:if>
    </div>
</div>

</dwarf:layout>
