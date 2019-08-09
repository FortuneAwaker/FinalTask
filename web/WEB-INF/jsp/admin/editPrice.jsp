<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<u:html title="Редактировать расценку" validator="validator-of-registration.js"
        message="${message}">
    <h3 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">Редактирование
        расценки
    </h3>
    <form action="/admin/editPrice.html?priceId=${idOfPriceToEdit}" method="post"
          onsubmit="return validateRegistrationInfo(this)">
        <label for="numberOfVisits">Number of visits</label>
        <input type="number" id="numberOfVisits" name="numberOfVisits"
               required>
        <label for="numberOfDays">Number of days</label>
        <input type="number" id="numberOfDays" name="numberOfDays"
               required>
        <label for="money">Amount of money</label>
        <input type="number" id="money" name="money"
               required>
        <BUTTON type="submit">Редактировать цену!</BUTTON>
    </form>
</u:html>