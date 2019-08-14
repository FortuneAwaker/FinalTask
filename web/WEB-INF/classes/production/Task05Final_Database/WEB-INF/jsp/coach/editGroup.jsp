<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<fmt:message key="edit_group" var="edit"/>
<fmt:message key="number_of_clients" var="number_of_clients"/>
<fmt:message key="number_pattern_title" var="number_pattern"/>
<fmt:message key="editing_group" var="editing"/>


<u:html title="${editing}"
        message="${message}">
    <h2 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">${editing}
    </h2>
    <form action="/coach/editGroup.html?groupId=${idOfGroupToEdit}" method="post">
        <label for="numberOfClients">${number_of_clients}</label>
        <input type="number" id="numberOfClients" name="numberOfClients"
               required
               pattern="[0-9]+"
               title="${number_pattern} 3"
               maxlength="3" minlength="1">
        <BUTTON type="submit">${edit}!</BUTTON>
    </form>
</u:html>