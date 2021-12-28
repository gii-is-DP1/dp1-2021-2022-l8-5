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
    .colorletter{
    color:"white";
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
	.background{
	background-image: url("/resources/images/oro_erebor.jpg");
	background-repeat:no-repeat;
	background-size: 100%;
	}
    
    /* Clearfix (clear floats) */
    .row::after {
      content: "";
      clear: both;
      display: table;
    }
    </style>
<!--style="background: url(/resources/images/oro_erebor.jpg)"-->
<dwarf:layout pageName="home">
<body class="background">
    <h2 style="color: silver;"><fmt:message key="welcome"/></h2>
    <div class="row">
     <h2 style="color: silver;"><!-- <img class="img-responsive"  src="/resources/images/5d75429b60bb3.jpeg" width="30" height="30"/>-->Proyect ${title}</h2>
    <p><h2 style="color: silver;"> Group ${group}</h2></p>
    <p>
    <ul>
    <c:forEach items="${persons}" var="person">
    	<li style="color: silver;"> ${person.firstName} ${person.lastName} </li>
    	</c:forEach>
    </ul>
    </p>

    <spring:url value="/resources/images/epic_dwarf_gifito.gif" var="dwarfGif"/>
    <spring:url value="/resources/images/portal2.gif" var="portal"/>

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
    </body>
</dwarf:layout>
