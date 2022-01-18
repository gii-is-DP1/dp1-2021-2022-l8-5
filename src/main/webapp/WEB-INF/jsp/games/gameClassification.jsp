<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<dwarf:layout pageName="gameClassification">
	<div class="row">
		<div class="col-md-4">
			<img src="${player1.avatarUrl}"  width="60" height="60" style="float:left" id="player1IMG"><br>
			&nbsp; <dwarf:playerInfo player="${player1}" playerNumber="${1}" resources="${resourcesPlayer1}"/><br>
		</div>
		<c:if test="${player2 != null}">
			<div class="col-md-4">
				<img src="${player2.avatarUrl}"  width="60" height="60" style="float:left" id="player2IMG"><br>
				&nbsp; 	<dwarf:playerInfo player="${player2}" playerNumber="${2}" resources="${resourcesPlayer2}"/><br>
			</div>
		</c:if>
		<c:if test="${player3 != null}">
			<div class="col-md-4">
				<img src="${player3.avatarUrl}"  width="60" height="60" style="float:left" id="player3IMG"><br>
				&nbsp; <dwarf:playerInfo player="${player3}" playerNumber="${3}" resources="${resourcesPlayer3}"/><br>
			</div>
		</c:if>
		<c:if test="${(player2 == null) && (player3 == null)}">
			<h2>Your opponents gave up, you are truly the Dwarf master! You win!</h2>
		</c:if>
	</div>
	
	<!-- incluir boton para volver al menu inicial de la pagina -->
	
</dwarf:layout>