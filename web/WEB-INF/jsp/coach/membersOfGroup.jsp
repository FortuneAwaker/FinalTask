<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<u:html title="Подписки">
    <h2 align="center">Подписки</h2>
    <c:forEach var="sub" items="${subscriptionsList}">
        <div class="post_ex">
            <h3 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">${sub.typeOfExercise}
            </h3>
            <div class="post_reference">
                <fmt:message key="name_word"/>: ${sub.userInfo.name}
            </div>
            <div class="post_reference">
                <fmt:message key="surname_word"/>: ${sub.userInfo.surname}
            </div>
            <div class="post_reference">
                <fmt:message key="patronymic_word"/>:${sub.userInfo.patronymic}
            </div>
            <div class="post_reference">
                <fmt:message key="phone_word"/>: ${sub.userInfo.phone}
            </div>
            <div class="post_reference">
                <fmt:message key="left_visits"/>: ${sub.leftVisits}
            </div>
            <div class="post_reference">
                <fmt:message key="last_day"/>: ${sub.lastDay}
            </div>
            <div class="post_reference">
                <a href="/coach/deleteSubscribe.html?subId=${sub.identity}">
                    Удалить подписку.
                </a>
            </div>
        </div>
    </c:forEach>
</u:html>
