<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>

<dwarf:layout pageName="error403">

    <spring:url value="/resources/images/epic_dwarf.png" var="dwarfImage"/>
    <img src="${dwarfImage}"/>

    <h2>Oops! Something went wrong with our servers. Sorry!</h2>

    <p>${exception.message}</p>

</dwarf:layout>
