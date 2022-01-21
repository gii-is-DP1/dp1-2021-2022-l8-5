<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="dwarf" tagdir="/WEB-INF/tags" %>

<dwarf:layout pageName="resources">
    <h2>
        Update Resources
    </h2>
    <form:form modelAttribute="resources" class="form-horizontal" id="add-resources-form">
        <div class="form-group has-feedback">
            <dwarf:inputField label="Iron" name="iron"/>
            <dwarf:inputField label="Steel" name="steel"/>
            <dwarf:inputField label="Gold" name="gold"/>
            <dwarf:inputField label="Items" name="items"/> 
            <dwarf:inputField label="Badges" name="badges"/>
            <dwarf:inputField label="GameId" name="game"/>
            <dwarf:inputField label="PlayerId" name="player"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">   
                        <button class="btn btn-default" type="submit">Update Resources</button>   
            </div>
        </div>
    </form:form>
    
</dwarf:layout>
