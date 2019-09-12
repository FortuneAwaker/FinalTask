<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<fmt:message key="add_group" var="add_group"/>
<fmt:message key="adding_group" var="adding_group"/>
<fmt:message key="number_pattern_title" var="number_pattern"/>
<fmt:message key="number_of_clients" var="number_of_clients"/>


<u:html title="${adding_group}"
        message="${message}">
    <h2 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">${adding_group}
    </h2>
    <form action="/coach/addGroup.html" method="post">
        <c:forEach var="exercise" items="${exercisesToChoose}">
            <label for="chosenExercise" style="background: yellow;
text-align: center">${exercise.typeOfExercises}</label>
            <input type="radio" name="chosenExercise" align="left"
                   id="chosenExercise" value="${exercise.identity}" required>
        </c:forEach>
        <label for="numberOfClients">${number_of_clients}</label>
        <input type="text" id="numberOfClients" name="numberOfClients"
               required
               pattern="[0-9]+"
               title="${number_pattern} 3"
               maxlength="3" minlength="1">
        <BUTTON type="submit">${add_group}!</BUTTON>
    </form>
</u:html>