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
	<h2><c:out value="Ronda actual: ${game.currentRound}"/></h2>
	
    <div class="row">    
        <div class="col-md-9">
        
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
	    <c:when test="${myplayer == game.currentPlayer && myworker != null && phaseName == 'ACTION_SELECTION'}">
			
			

		    <form:form class="form-horizontal" id="add-player-form">
				<div class="form-group text-center">
				
					<h2>Select the tile where you'll place your worker</h2>
					
					<div class="row-md-3">
					
					<c:choose>
						<c:when test="${board.boardCells.get(0).isCellOccupied()}">
							<button disabled class="btn btn-default" type="submit" onclick="check()" name="pos" value="1,0">1</button>
						</c:when>
						<c:otherwise>
							<button class="btn btn-default" type="submit" onclick="check()" name="pos" value="1,0">1</button>
						</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${board.boardCells.get(1).isCellOccupied()}">
							<button disabled class="btn btn-default" type="submit" onclick="check()" name="pos" value="2,0">2</button>
						</c:when>
						<c:otherwise>
							<button class="btn btn-default" type="submit" onclick="check()" name="pos" value="2,0">2</button>
						</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${board.boardCells.get(2).isCellOccupied()}">
							<button disabled class="btn btn-default" type="submit" onclick="check()" name="pos" value="3,0">3</button>
						</c:when>
						<c:otherwise>
							<button class="btn btn-default" type="submit" onclick="check()" name="pos" value="3,0">3</button>
						</c:otherwise>
					</c:choose>
					
					</div>
					<div class="row-md-3">
					
					<c:choose>
						<c:when test="${board.boardCells.get(3).isCellOccupied()}">
							<button disabled class="btn btn-default" type="submit" onclick="check()" name="pos" value="1,1">4</button>
						</c:when>
						<c:otherwise>
							<button class="btn btn-default" type="submit" onclick="check()" name="pos" value="1,1">4</button>
						</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${board.boardCells.get(4).isCellOccupied()}">
							<button disabled class="btn btn-default" type="submit" onclick="check()" name="pos" value="2,1">5</button>
						</c:when>
						<c:otherwise>
							<button class="btn btn-default" type="submit" onclick="check()" name="pos" value="2,1">5</button>
						</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${board.boardCells.get(5).isCellOccupied()}">
							<button disabled class="btn btn-default" type="submit" onclick="check()" name="pos" value="3,1">6</button>
						</c:when>
						<c:otherwise>
							<button class="btn btn-default" type="submit" onclick="check()" name="pos" value="3,1">6</button>
						</c:otherwise>
					</c:choose>
					
					</div>
					<div class="row-md-3">
					
					<c:choose>
						<c:when test="${board.boardCells.get(6).isCellOccupied()}">
							<button disabled class="btn btn-default" type="submit" onclick="check()" name="pos" value="1,2">7</button>
						</c:when>
						<c:otherwise>
							<button class="btn btn-default" type="submit" onclick="check()" name="pos" value="1,2">7</button>
						</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${board.boardCells.get(7).isCellOccupied()}">
							<button disabled class="btn btn-default" type="submit" onclick="check()" name="pos" value="2,2">8</button>
						</c:when>
						<c:otherwise>
							<button class="btn btn-default" type="submit" onclick="check()" name="pos" value="2,2">8</button>
						</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${board.boardCells.get(8).isCellOccupied()}">
							<button disabled class="btn btn-default" type="submit" onclick="check()" name="pos" value="3,2">9</button>
						</c:when>
						<c:otherwise>
							<button class="btn btn-default" type="submit" onclick="check()" name="pos" value="3,2">9</button>
						</c:otherwise>
					</c:choose>
					
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


