<%@ attribute name="player" required="true" rtexprvalue="true" type="org.springframework.dwarf.player.Player"
 description="Player of the game" %>
 <%@ attribute name="playerNumber" required="true" rtexprvalue="true" 
 description="Player of the game" %>
 <%@ attribute name="resources" required="true" rtexprvalue="true" type="org.springframework.dwarf.resources.Resources"
 description="Resources of the player" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<li> <c:out value="Player ${playerNumber}: ${player.username}"/>
	<ul>
		<li>
			<c:out value="Iron: ${resources.iron}"/>
		</li>
		<li>
			<c:out value="Gold: ${resources.gold}"/>
		</li>
		<li>
			<c:out value="Steel: ${resources.steel}"/>
		</li>
		<li>
			<c:out value="Badges: ${resources.badges}"/>
		</li>
		<li>
			<c:out value="Items: ${resources.items}"/>
		</li>
	</ul>
</li>