<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<dwarf:layout pageName="board">

	<spring:url value="/games/{gameId}/exit" var="gameUrl">
		<spring:param name="gameId" value="${game.id}"/>
	</spring:url>
	<a class="btn btn-default" href="${fn:escapeXml(gameUrl)}">Exit game</a>
    
	<c:if test="${phaseName == 'ACTION_SELECTION'}">
		<h2><c:out value="Timer: ${board.inactivityTimer}"/></h2>
		<h2><c:out value="Turno para: ${game.currentPlayer.username}"/></h2>
	</c:if>
	<h2><c:out value="Fase de la ronda: ${game.currentPhaseName.toString()}"/></h2>
	<h2><c:out value="Ronda actual: ${game.currentRound}"/></h2>
	
    <div class="row">    
        <div class="col-md-9">
        
            <dwarf:board board="${board}"/>
            <c:forEach items="${board.specialDecks}" var="specialDeck">
            	<c:if test="${specialDeck.getSpecialCard().size() != 0}">
            		<dwarf:specialDeck padding="20" xsize="130" ysize="180" specialDeck="${specialDeck}"/>
            	</c:if>
            </c:forEach>

            <c:forEach items="${board.boardCells}" var="boardCell">
            	<dwarf:mountainCard padding="20" xsize="130" ysize="180" mountainCard="${boardCell.mountaincards.get(0)}"/>
            </c:forEach>
            
            <c:forEach items="${workers}" var="worker">
	            <c:if test="${worker.xposition != null && worker.yposition != null && worker.xposition > 0}">
	           		<dwarf:worker padding="20" xsize="130" ysize="180" worker="${worker}"/>
	            </c:if>
            </c:forEach>
            
            <dwarf:mountainDeck padding="20" xsize="130" ysize="180" mountainDeck="${board.mountainDeck}"/> 
            
        </div>
         <div class="col-md-3" style="font-size:16px">
	         <c:if test="${player1 != null}">
	         	<img src="${player1worker.image}"  width="60" height="60" style="float:left" id="player1IMG"><br>
				&nbsp; <dwarf:playerInfo player="${player1}" playerNumber="${1}" resources="${resourcesPlayer1}" workers="${remainingWorkersPlayer1}"/><br>
	         </c:if>
	         <c:if test="${player2 != null}">
	         	<img src="${player2worker.image}"  width="60" height="60" style="float:left" id="player2IMG"><br>
				&nbsp; <dwarf:playerInfo player="${player2}" playerNumber="${2}" resources="${resourcesPlayer2}" workers="${remainingWorkersPlayer2}"/><br>
	         </c:if>
	         <c:if test="${player3 != null}">
	         	<img src="${player3worker.image}"  width="60" height="60" style="float:left" id="player3IMG"><br>
				&nbsp; <dwarf:playerInfo player="${player3}" playerNumber="${3}" resources="${resourcesPlayer3}" workers="${remainingWorkersPlayer3}"/><br>
	         </c:if>
         </div>
    </div>
    

	<div class="row">	
		<div class="col-md-9">
	        
			<c:if test="${myplayer == game.currentPlayer && myworker != null && phaseName != 'MINERAL_EXTRACTION'}">
				<form:form class="form-horizontal" id="add-player-form">
					<div class="form-group text-center">
					
						<c:if test="${phaseName == 'ACTION_SELECTION'}">
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
						
						<c:if test="${(phaseName == 'ACTION_SELECTION') && (hasEnoughWorkers || canPay) && (!hasAidWorkers)}">
							&nbsp;
							<h2>Select the special action to perform</h2>
							<p>You'll use your 2 workers for this turn, but the action will be performed immediatly</p>
							
							<div>
								<c:choose>
									<c:when test="${!canPay}">
										<label><input disabled="disabled" type="checkbox" name="pay" value="yes"> Perform action with only 1 worker and pay 4 badges?</label>
									</c:when>
									<c:when test="${canPay && hasOneWorker}">
										<label><input disabled="disabled" type="checkbox" name="pay" value="yes" checked="checked"> Perform action with only 1 worker and pay 4 badges?</label>
										<input type="hidden" id="hiddenInput" name="pay" value="yes" />
									</c:when>
									<c:otherwise>
										<label><input type="checkbox" name="pay" value="yes"> Perform action with only 1 worker and pay 4 badges?</label>
									</c:otherwise>
								</c:choose>
							</div>
			
							<div class="row-md-3">
							<c:choose>
								<c:when test="${board.specialDecks.get(0).getSpecialCard().size() > 0}">
									<button class="btn btn-default" type="submit" onclick="check()" name="pos" value="0,0">Special 1</button>
								</c:when>
								<c:otherwise>
									<button disabled class="btn btn-default" type="submit" onclick="check()" name="pos" value="0,0">Special 1</button>
								</c:otherwise>
							</c:choose>
							
							<c:choose>
								<c:when test="${board.specialDecks.get(1).getSpecialCard().size() > 0}">
									<button class="btn btn-default" type="submit" onclick="check()" name="pos" value="0,1">Special 2</button>
								</c:when>
								<c:otherwise>
									<button disabled class="btn btn-default" type="submit" onclick="check()" name="pos" value="0,1">Special 2</button>
								</c:otherwise>
							</c:choose>
							
							<c:choose>
								<c:when test="${board.specialDecks.get(2).getSpecialCard().size() > 0}">
									<button class="btn btn-default" type="submit" onclick="check()" name="pos" value="0,2">Special 3</button>
								</c:when>
								<c:otherwise>
									<button disabled class="btn btn-default" type="submit" onclick="check()" name="pos" value="0,2">Special 3</button>
								</c:otherwise>
							</c:choose>
								
							</div>
						</c:if>
						
					</div>
			    </form:form>
			</c:if>
			<div class="text-center">
				<spring:url value="/boards/${board.id}/boardcards" var="cardsUrl">
		        </spring:url>
		        <a class="btn btn-default" href="${fn:escapeXml(cardsUrl)}">View all cards for each tile</a>
	        </div>
		</div>
		<img src="/resources/images/dwarfenenao_preview_rev_1.png"  width="250" height="250" style="float:right" id="miImagen">
	</div>
</dwarf:layout>


