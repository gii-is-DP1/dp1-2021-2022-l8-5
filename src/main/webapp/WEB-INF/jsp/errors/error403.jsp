<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>

<dwarf:layout pageName="error403">

    <spring:url value="/resources/images/epic_dwarf.png" var="dwarfImage"/>
    <img src="${dwarfImage}"/>

    <h2>Hey, where are you going!? You don't have the permission to access this page!</h2>
    
<!--  
    <spring:url value="/resources/images/xijingpin.jpg" var="coolImage"/>
    <h2>Atencion ciudadano de china! No deberias estar aqui. Hemos detectado que estas haciendo trampas. Me temo que te vamos a substraer 10000 creditos sociales.</h2>

	<audio src="/resources/images/CCH2.mp3" autoplay>
	<p>If you are reading this, it is because your browser does not support the audio element.</p>
	</audio>
-->

    <p>${exception.message}</p>
</dwarf:layout>
