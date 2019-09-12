    <%@page language="java" contentType="text/html; charset=UTF-8"
            pageEncoding="UTF-8" %>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
        <fmt:setBundle basename="properties.club"/>

        <fmt:message key="our_prices" var="our_prices"/>
        <fmt:message key="prices_word" var="prices_word"/>
        <fmt:message key="add_price" var="add_price"/>
        <fmt:message key="delete_word" var="delete"/>
        <fmt:message key="edit_word" var="edit"/>
        <fmt:message key="actions_prices_by_admin" var="by_admin"/>
        <fmt:message key="exercises_word" var="exercisesKey"/>
        <fmt:message key="number_of_days" var="number_of_days"/>
        <fmt:message key="number_of_visits" var="number_of_visits"/>
        <fmt:message key="price" var="priceWord"/>
        <fmt:message key="action" var="actionWord"/>
        <fmt:message key="subscribe_login" var="to_see_login"/>
        <fmt:message key="subscribe_word" var="subscribe"/>

        <u:html title="${prices_word}">
            <h2 align="center" style="margin-bottom: 30px;
            color: purple; font-family: 'Montserrat', cursive">${our_prices}
            </h2>
            <c:choose>
                <c:when test="${sessionScope.authorizedUser.role.identity == 0}">
                    <h3 align="center" style="margin-bottom: 30px;
                    color: purple; font-family: 'Montserrat', cursive"><a href="/admin/addPrice.html?todo=false">${add_price}</a></h3>
                </c:when>
            </c:choose>
            <table border="1" width="100%" cellpadding="15" class="table">
            <thead>
            <th>${exercisesKey}</th>
            <th>${number_of_days}</th>
            <th>${number_of_visits}</th>
            <th>${priceWord}</th>
            <th>${actionWord}</th>
            </thead>
            <tbody>
            <c:forEach var="price" items="${listOfPrices}">
                <tr>
                <td align="center" class="table-cell"><c:out
                    value="${ price.nameOfExercise }"/></td>
                <td align="center" class="table-cell"><c:out
                    value="${ price.numberOfDays }"/></td>
                <td align="center" class="table-cell"><c:out
                    value="${ price.numberOfVisits }"/></td>
                <td align="center" class="table-cell"><c:out
                    value="${ price.price }$"/></td>
                <td class="table-cell">
                <c:choose>
                    <c:when test="${sessionScope.authorizedUser.role.identity == 0}">
                        <a
                        href="/admin/editPrice.html?priceId=${price.identity}&todo=false">${edit}</a><br>
                        <a
                        href="/admin/deletePrice.html?priceId=${price.identity}">${delete}</a>
                    </c:when>
                    <c:when test="${sessionScope.authorizedUser.role.identity == 1}">
                        ${by_admin}.
                    </c:when>
                    <c:when test="${sessionScope.authorizedUser.role.identity == 2}">
                        <a
                        href="/authorized_user/subscribe.html?priceId=${price.identity}">${subscribe}</a>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${to_see_login}"/>
                    </c:otherwise>
                </c:choose>
                </td>
                </tr>
            </c:forEach>
            </tbody>
            </table>
        </u:html>