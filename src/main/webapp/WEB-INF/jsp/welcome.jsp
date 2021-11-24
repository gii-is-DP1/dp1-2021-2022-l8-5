<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<dwarf:layout pageName="home">
    <h2><fmt:message key="welcome"/></h2>
    <div class="row">
     <h2><!-- <img class="img-responsive"  src="/resources/images/5d75429b60bb3.jpeg" width="30" height="30"/>-->Proyect ${title}</h2>
    <p><h2> Group ${group}</h2></p>
    <p>
    <ul>
    <c:forEach items="${persons}" var="person">
    	<li> ${person.firstName} ${person.lastName} </li>
    	</c:forEach>
    </ul>
    </p>

    <marquee  behavior="scroll" direction="right">
        <spring:url value="/resources/images/epic_dwarf_gifito.gif" var="dwarfGif"/>
        <img src="${dwarfGif}"/>
  </marquee>

    </div>
</dwarf:layout>
