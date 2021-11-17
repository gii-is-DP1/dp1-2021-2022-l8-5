<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="board">

    <div class="row">
        <div class="col-md-12">
            <petclinic:board board="${board}"/>
            <c:forEach items="${board.mountainDeck.mountainCards}" var="mountainCard">
            	<petclinic:mountainCard size="160" mountainCard="${mountainCard}"/>
            	
            </c:forEach> 
        </div>
    </div>
</petclinic:layout>