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
        <div class="col-md-9">
            <dwarf:board board="${board}"/>
            <c:forEach items="${board.specialDecks}" var="specialDeck">
            	<dwarf:specialDeck padding="20" xsize="130" ysize="180" specialDeck="${specialDeck}"/>
            </c:forEach>

            <c:forEach items="${board.boardCells}" var="boardCell">
            	<dwarf:mountainCard padding="20" xsize="130" ysize="180" mountainCard="${boardCell.mountaincards.get(0)}"/>
            </c:forEach>
            
            <dwarf:mountainDeck padding="20" xsize="130" ysize="180" mountainDeck="${board.mountainDeck}"/> 
            
        </div>
         <div class="col-md-3">
        	<ul>
				<dwarf:playerInfo player="${player1}" playerNumber="${1}" resources="${resourcesPlayer1}"/>
				<dwarf:playerInfo player="${player2}" playerNumber="${2}" resources="${resourcesPlayer2}"/>
				<dwarf:playerInfo player="${player3}" playerNumber="${3}" resources="${resourcesPlayer3}"/>
        	</ul>
         </div>
         <img src="/resources/images/dwarfenenao_preview_rev_1.png"  width="250" height="250" style="float:right" id="miImagen">
         
    </div>
    

<!-- añadir al when que la fase sea la de seleccion de acciones -->
    
    <c:choose>
	    <c:when test="${myplayer == game.currentPlayer}">
	    
			<form:form modelAttribute="myworker1" class="form-horizontal" id="add-player-form">
			
		        <div class="form-group has-feedback col-md-5">
		            <dwarf:inputField label="Horizontal tile" name="xposition"/>
		            <dwarf:inputField label="Vertical tile" name="yposition"/>  
		        </div>
		        
		        <div class="form-group">
		            <div class="col-sm-offset-2 col-sm-10">
		            	<button class="btn btn-default" type="submit">Confirm action</button>
		            </div>
		        </div>
		        
		    </form:form>
	    </c:when>
	    <c:otherwise>
	    
	    </c:otherwise>
    </c:choose>
    
</dwarf:layout>