<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<u:html title="Добавить группу" validator="validator-of-registration.js"
        message="${message}">
    <h3 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">Создание новой
        группы
    </h3>
    <form action="/coach/addGroup.html" method="post"
          onsubmit="return validateRegistrationInfo(this)">
        <c:forEach var="exercise" items="${exercisesToChoose}">
            <label for="chosenExercise" style="background: yellow;
text-align: center">${exercise.typeOfExercises}</label>
            <input type="radio" name="chosenExercise" align="left"
                   id="chosenExercise" value="${exercise.identity}" required>
        </c:forEach>
        <label for="numberOfClients">Number of clients</label>
        <input type="number" id="numberOfClients" name="numberOfClients"
               required>
        <BUTTON type="submit">Добавить группу!</BUTTON>
    </form>
</u:html>