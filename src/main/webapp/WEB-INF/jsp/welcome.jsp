<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  


<style>
    * {
      box-sizing: border-box;
    }
    
    .column {
      float: left;
      width: 6.33%;
      padding: 5px;
    }
    .column2 {
      border-top: 1000px;
      float: left;
      width: 81.33%;
      padding: 5px;
    }
    
    /* Clearfix (clear floats) */
    .row::after {
      content: "";
      clear: both;
      display: table;
    }
    </style>

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

    <spring:url value="/resources/images/epic_dwarf_gifito.gif" var="dwarfGif"/>
    <spring:url value="/resources/images/portal.gif" var="portal"/>

    <div class="row">
        <div class="column">
            <img src="${portal}" width="100" height="350" style="float:left"/>
        </div>
        <div class="column2">
            <marquee  behavior="scroll" direction="right"style="padding-top:50">

                <img src="${dwarfGif}"/>
            </marquee>
        </div>
        <div class="column">
            <img src="${portal}" width="100" height="350" style="float:right"/>
        </div>
      </div>
  

    </div>
</dwarf:layout>
