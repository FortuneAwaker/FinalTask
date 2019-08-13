<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<u:html title="Редактировать расценку"
        message="${message}">
    <h3 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">Редактирование
        расценки
    </h3>
    <form action="/admin/editPrice.html?priceId=${idOfPriceToEdit}"
          method="post">
        <label for="numberOfVisits">Number of visits</label>
        <input type="text" id="numberOfVisits" name="numberOfVisits"
               required
               pattern="[0-9]+"
               title="Only numbers, maximum 3"
               maxlength="3" minlength="1">
        <label for="numberOfDays">Number of days</label>
        <input type="text" id="numberOfDays" name="numberOfDays"
               required pattern="[0-9]+"
               title="Only numbers, maximum 3"
               maxlength="3" minlength="1">
        <label for="money">Amount of money</label>
        <input type="text" id="money" name="money"
               required pattern="[0-9]+"
               title="Only numbers, maximum 3"
               maxlength="3" minlength="1">
        <BUTTON type="submit">Редактировать цену!</BUTTON>
    </form>
</u:html>