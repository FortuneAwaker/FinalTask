<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<u:html title="Добавить расценку" validator="validator-of-registration.js"
        message="${message}">
    <h3 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">Создание новой
        расценки
    </h3>
    <form action="/admin/addPrice.html" method="post"
          onsubmit="return validateRegistrationInfo(this)">
        <c:forEach var="exercise" items="${exercisesToChoose}">
            <label for="chosenExercise" style="background: yellow;
text-align: center">${exercise.typeOfExercises}</label>
            <input type="radio" name="chosenExercise" align="left"
                   id="chosenExercise" value="${exercise.identity}" required>
        </c:forEach>
        <label for="numberOfVisits">Number of visits</label>
        <input type="number" id="numberOfVisits" name="numberOfVisits"
               required>
        <label for="numberOfDays">Number of days</label>
        <input type="number" id="numberOfDays" name="numberOfDays"
               required>
        <label for="money">Amount of money</label>
        <input type="number" id="money" name="money"
               required>
        <BUTTON type="submit">Добавить цену!</BUTTON>
    </form>
</u:html>