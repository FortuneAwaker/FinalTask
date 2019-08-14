<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<fmt:message key="number_pattern_title" var="number_pattern"/>
<fmt:message key="number_of_days" var="number_of_days"/>
<fmt:message key="number_of_visits" var="number_of_visits"/>
<fmt:message key="amount_of_money" var="money"/>
<fmt:message key="add_price" var="add_price"/>
<fmt:message key="adding_new_price" var="new_price"/>

<u:html title="${new_price}" message="${message}">
    <h2 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">${new_price}
    </h2>
    <form action="/admin/addPrice.html" method="post">
        <c:forEach var="exercise" items="${exercisesToChoose}">
            <label for="chosenExercise" style="background: yellow;
text-align: center">${exercise.typeOfExercises}</label>
            <input type="radio" name="chosenExercise" align="left"
                   id="chosenExercise" value="${exercise.identity}" required>
        </c:forEach>
        <label for="numberOfVisits">${number_of_visits}</label>
        <input type="text" id="numberOfVisits" name="numberOfVisits"
               required
               pattern="[0-9]+"
               title="${number_pattern} 3"
               maxlength="3" minlength="1">
        <label for="numberOfDays">${number_of_days}</label>
        <input type="text" id="numberOfDays" name="numberOfDays"
               required
               pattern="[0-9]+"
               title="${number_pattern} 3"
               maxlength="3" minlength="1">
        <label for="money">${money}</label>
        <input type="text" id="money" name="money"
               required
               pattern="[0-9]+"
               title="${number_pattern} 6"
               maxlength="6" minlength="1">
        <BUTTON type="submit">${add_price}!</BUTTON>
    </form>
</u:html>