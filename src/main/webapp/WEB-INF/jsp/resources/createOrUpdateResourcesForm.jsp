<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="resources">
    <h2>
        Update Resources
    </h2>
    <form:form modelAttribute="resources" class="form-horizontal" id="add-resources-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Iron" name="iron"/>
            <petclinic:inputField label="Steel" name="steel"/>
            <petclinic:inputField label="Gold" name="gold"/>
            <petclinic:inputField label="Items" name="items"/> 
            <petclinic:inputField label="Badges" name="badges"/>
            <petclinic:inputField label="GameId" name="game"/>
            <petclinic:inputField label="PlayerId" name="player"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">   
                        <button class="btn btn-default" type="submit">Update Resources</button>   
            </div>
        </div>
    </form:form>
    
</petclinic:layout>



            <!--petclinic:inputField label="Id" name="resourcesId"/-->
            <!--petclinic:inputField label="GameId" name="gameId"/-->
            <!--petclinic:inputField label="PlayerId" name="playerId"/-->