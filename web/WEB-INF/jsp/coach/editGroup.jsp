<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<u:html title="Редактировать группу" validator="validator-of-registration.js"
        message="${message}">
    <h3 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">Редактирование группы
    </h3>
    <form action="/coach/editGroup.html?groupId=${idOfGroupToEdit}" method="post"
          onsubmit="return validateRegistrationInfo(this)">
        <label for="numberOfClients">Number of clients</label>
        <input type="number" id="numberOfClients" name="numberOfClients"
               required>
        <BUTTON type="submit">Редактировать группу!</BUTTON>
    </form>
</u:html>