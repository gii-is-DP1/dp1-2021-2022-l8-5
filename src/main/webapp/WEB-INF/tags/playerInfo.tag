<%@ attribute name="player" required="true" rtexprvalue="true" type="org.springframework.dwarf.player.Player"
description="Player of the game" %>
<%@ attribute name="playerNumber" required="true" rtexprvalue="true" 
description="Player of the game" %>
<%@ attribute name="resources" required="true" rtexprvalue="true" type="org.springframework.dwarf.resources.Resources"
description="Resources of the player" %>
<%@ attribute name="workers" required="false" rtexprvalue="true" 
description="workers that the player hasn't used yet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:out  value="Player ${playerNumber}: ${player.username}"/>

<br><br>
<ul>
	&nbsp; &nbsp; &nbsp; 
	<c:out value="Items: ${resources.items}"/><br>&nbsp; &nbsp; &nbsp; 

	<c:out value="Gold: ${resources.gold}"/><br>&nbsp; &nbsp; &nbsp; 

	<c:out value="Steel: ${resources.steel}"/><br>&nbsp; &nbsp; &nbsp; 

	<c:out value="Iron: ${resources.iron}"/><br>&nbsp; &nbsp; &nbsp; 

	<c:out value="Badges: ${resources.badges}"/><br>&nbsp; &nbsp; &nbsp;
	
	<c:if test="${workers != null}"><c:out value="Workers left: ${workers}"/><br></c:if>

</ul>


