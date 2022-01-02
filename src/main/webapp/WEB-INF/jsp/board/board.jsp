<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<dwarf:layout pageName="board">
	
	<h2><c:out value="Turno para: ${game.currentPlayer.username}"/></h2>
	<h2><c:out value="Fase de la ronda: ${game.currentPhaseName.toString()}"/></h2>
	
    <div class="row">
    	<div class="col-md-1" style="height: 600px; margin-top: 30px">
    		<h3 class="text-center" style="height: 200px; padding-top: 90px">0</h3>
       		<h3 class="text-center" style="height: 200px; padding-top: 90px">1</h3>
       		<h3 class="text-center" style="height: 200px; padding-top: 90px">2</h3>
    	</div>
    
        <div class="col-md-8">
        	<div class="row">
        		<h3 class="text-center" style="width: 150px; float: left">0</h3>
        		<h3 class="text-center" style="width: 150px; float: left">1</h3>
        		<h3 class="text-center" style="width: 150px; float: left">2</h3>
        		<h3 class="text-center" style="width: 150px; float: left">3</h3>
        		<h3 class="text-center" style="width: 150px; float: left">4</h3>
        	</div>
        
            <dwarf:board board="${board}"/>
            <c:forEach items="${board.specialDecks}" var="specialDeck">
            	<dwarf:specialDeck padding="20" xsize="130" ysize="180" specialDeck="${specialDeck}"/>
            </c:forEach>

            <c:forEach items="${board.boardCells}" var="boardCell">
            	<dwarf:mountainCard padding="20" xsize="130" ysize="180" mountainCard="${boardCell.mountaincards.get(0)}"/>
            </c:forEach>
            
            <c:forEach items="${workers}" var="worker">
	            <c:if test="${worker.xposition != null && worker.yposition != null}">
	           		<dwarf:worker padding="20" xsize="130" ysize="180" worker="${worker}"/>
	            </c:if>
            </c:forEach>
            
            <dwarf:mountainDeck padding="20" xsize="130" ysize="180" mountainDeck="${board.mountainDeck}"/> 
            
        </div>
         <div class="col-md-3" style="font-size:16px">
				<img src="/resources/images/epicworker1.png"  width="60" height="60" style="float:left" id="player1IMG"><br>
				&nbsp; <dwarf:playerInfo player="${player1}" playerNumber="${1}" resources="${resourcesPlayer1}"/><br>
				<img src="/resources/images/epicworker2.png"  width="60" height="60" style="float:left" id="player2IMG"><br>
				&nbsp; 	<dwarf:playerInfo player="${player2}" playerNumber="${2}" resources="${resourcesPlayer2}"/><br>
				<img src="/resources/images/epicworker3.png"  width="60" height="60" style="float:left" id="player3IMG"><br>
				&nbsp; <dwarf:playerInfo player="${player3}" playerNumber="${3}" resources="${resourcesPlayer3}"/><br>
        	
         </div>
         
         
    </div>
    

<!-- añadir al when que la fase sea la de seleccion de acciones -->
<div class="row">	
	<div class="col-md-9">
    <c:choose>
	    <c:when test="${myplayer == game.currentPlayer}">
			
			<form:form modelAttribute="myworker" class="form-horizontal" id="add-player-form" >
				
		        <div class="form-group has-feedback col-md-5">
		          	<h2>Horizontal position</h2>
		            <form:select  path="xposition" title="Horizontal position">
					    <form:option value="NONE"> --SELECT--</form:option>
					    <form:options items="${xpos}"></form:options>
				    </form:select>
				    <br/>
			     <br/>
			     <h2>Vertical position</h2>
    	            <form:select  path="yposition" title="Vertical position">
					    <form:option value="NONE"> --SELECT--</form:option>
					    <form:options items="${ypos}"></form:options>
				    </form:select>
				    
		             <span class="help-inline"><form:errors path="*"/></span>
		        </div>
		        
		        <div class="form-group">
		            <div class="col-sm-offset-2 col-sm-10">
		            	<button class="btn btn-default" type="submit" onclick="check()">Confirm action</button>
		            </div>
		        </div>
				
		        
		    </form:form>
	    </c:when>
	    <c:otherwise>
	    
	    </c:otherwise>
    </c:choose>
</div>
	<img src="/resources/images/dwarfenenao_preview_rev_1.png"  width="250" height="250" style="float:right" id="miImagen">
</div>
</dwarf:layout>


