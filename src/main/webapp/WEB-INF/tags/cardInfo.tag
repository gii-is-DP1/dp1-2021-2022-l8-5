<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="card" required="true" rtexprvalue="true" type="org.springframework.dwarf.mountainCard.MountainCard"
description="Card to be shown" %>
<%@ attribute name="position" required="true" rtexprvalue="true"
description="Position of the card in the stack" %>

	<h3><c:out value="Card position: ${position}"/></h3>
	<h3><c:out value="Card name: ${card.name}"/></h3>
	<img src="${card.image}" alt="${card.description}" title="Name: ${card.name} -> Effect: ${card.description}" width="200" height="300">
	&nbsp;
