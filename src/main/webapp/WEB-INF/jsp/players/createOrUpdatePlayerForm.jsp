<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>

<dwarf:layout pageName="players">
    <h2>
        <c:if test="${player['new']}">New </c:if> Player
    </h2>
    <form:form modelAttribute="player" class="form-horizontal" id="add-player-form">
        <div class="form-group has-feedback">
            <dwarf:inputField label="First Name" name="firstName"/>
            <dwarf:inputField label="Last Name" name="lastName"/>
             <c:choose>
                    <c:when test="${player['new']}">
                       <dwarf:inputField label="Username" name="user.username" />
                        <span class="help-inline"><form:errors path="username"/></span>
                    </c:when>
                     <c:otherwise>
                     
                    </c:otherwise>
               </c:choose>
              <dwarf:inputField label="E-Mail" name="user.email"/> 
               <span class="help-inline"><form:errors path="email"/></span>
               <dwarf:inputField label="AvatarUrl" name="avatarUrl"/>
            <dwarf:inputField label="Password" name="user.password"/>
            <span class="help-inline"><form:errors path="password"/></span>
            
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${player['new']}">
                        <button class="btn btn-default" type="submit">Add Player</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Player</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
    
</dwarf:layout>
