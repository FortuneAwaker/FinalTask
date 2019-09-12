<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>


<fmt:message key="edit_word" var="edit"/>
<fmt:message key="number_pattern_title" var="number_pattern"/>
<fmt:message key="number_of_days" var="number_of_days"/>
<fmt:message key="number_of_visits" var="number_of_visits"/>
<fmt:message key="amount_of_money" var="money"/>
<fmt:message key="editing_price" var="editing"/>


<u:html title="${editing}"
        message="${message}">
    <h2 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">${editing}
    </h2>
    <form action="/admin/editPrice.html?priceId=${idOfPriceToEdit}"
          method="post">
        <label for="numberOfVisits">${number_of_visits}</label>
        <input type="text" id="numberOfVisits" name="numberOfVisits"
               required
               pattern="[0-9]+"
               title="${number_pattern} 3"
               maxlength="3" minlength="1">
        <label for="numberOfDays">${number_of_days}</label>
        <input type="text" id="numberOfDays" name="numberOfDays"
               required pattern="[0-9]+"
               title="${number_pattern} 3"
               maxlength="3" minlength="1">
        <label for="money">${money}</label>
        <input type="text" id="money" name="money"
               required pattern="[0-9]+"
               title="${number_pattern} 6"
               maxlength="6" minlength="1">
        <BUTTON type="submit">${edit}!</BUTTON>
    </form>
</u:html>