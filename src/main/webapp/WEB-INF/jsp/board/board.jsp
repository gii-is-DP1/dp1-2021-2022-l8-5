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
            <!-- No seé que hacer con esto pero taria bien verlo -->
            <div class="tooltip">
            	<span class="tooltiptext">CumSum</span>
            	<dwarf:mountainCard padding="20" xsize="130" ysize="180" mountainCard="${boardCell.mountaincards.get(0)}"/>
            </div>
            </c:forEach>
            
            <c:forEach items="${workers}" var="worker">
	            <c:if test="${worker.xposition != null && worker.yposition != null}">
	           		<dwarf:worker padding="20" xsize="130" ysize="180" worker="${worker}"/>
	            </c:if>
            </c:forEach>
            
            <dwarf:mountainDeck padding="20" xsize="130" ysize="180" mountainDeck="${board.mountainDeck}"/> 
            
        </div>
         <div class="col-md-3" style="font-size:16px">
         		<!-- CAMBIAR DINÁMICAMENTRE LAS IMAGENES DE LOS JUGADORES -->
				<img src="/resources/images/epicworker1.png"  width="60" height="60" style="float:left" id="player1IMG"><br>
				&nbsp; <dwarf:playerInfo player="${player1}" playerNumber="${1}" resources="${resourcesPlayer1}"/><br>
				<img src="/resources/images/epicworker2.png"  width="60" height="60" style="float:left" id="player2IMG"><br>
				&nbsp; 	<dwarf:playerInfo player="${player2}" playerNumber="${2}" resources="${resourcesPlayer2}"/><br>
				<img src="/resources/images/epicworker3.png"  width="60" height="60" style="float:left" id="player3IMG"><br>
				&nbsp; <dwarf:playerInfo player="${player3}" playerNumber="${3}" resources="${resourcesPlayer3}"/><br>
        	
         </div>
    </div>
    

	<!-- aÃ±adir al when que la fase sea la de seleccion de acciones -->
	<div class="row">	
		<div class="col-md-9">
			<c:if test="${myplayer == game.currentPlayer && myworker != null && phaseName != 'MINERAL_EXTRACTION'}">
				<form:form class="form-horizontal" id="add-player-form">
					<div class="form-group text-center">
					
						<c:if test="${phaseName == 'ACTION_SELECTION' || hasAidWorkers}">
							<h2>Select the tile where you'll place your worker</h2>
							<c:set var="index" value="${0}"/>
							<c:forEach items="${ypos}" var="y">
								<div class="row-md-3">
									<c:forEach items="${xpos}" var="x">
										<dwarf:choosePanel index="${index}" yposition="${y}" xposition="${x}" isCellOccupied="${board.boardCells.get(index).isCellOccupied()}"/>
										<c:set var="index" value="${index+1}"/>
									</c:forEach>
								</div>
							</c:forEach>
						</c:if>
						
						<c:if test="${phaseName == 'ACTION_SELECTION'}">
							&nbsp;
							<h2>Select the special action to perform</h2>
							<p>You'll use your 2 workers for this turn, but the action will be performed immediatly</p>
							
							<div>
								<c:choose>
									<c:when test="${!canPay}">
										<label><input disabled="disabled" type="checkbox" name="pay" value="yes"> Perform action with only 1 worker and pay 4 badges?</label>
									</c:when>
									<c:otherwise>
										<label><input type="checkbox" name="pay" value="yes"> Perform action with only 1 worker and pay 4 badges?</label>
									</c:otherwise>
								</c:choose>
							</div>
			
							<div class="row-md-3">
								<button class="btn btn-default" type="submit" onclick="check()" name="pos" value="0,0">Special 1</button>
								<button class="btn btn-default" type="submit" onclick="check()" name="pos" value="0,1">Special 2</button>
								<button class="btn btn-default" type="submit" onclick="check()" name="pos" value="0,2">Special 3</button>
							</div>
						</c:if>
						
					</div>
			    </form:form>
			</c:if>
		</div>
		<img src="/resources/images/dwarfenenao_preview_rev_1.png"  width="250" height="250" style="float:right" id="miImagen">
	</div>
</dwarf:layout>


