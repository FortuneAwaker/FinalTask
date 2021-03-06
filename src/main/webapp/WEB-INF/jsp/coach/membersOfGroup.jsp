<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<fmt:message key="list_is_empty" var="list_is_empty"/>
<fmt:message key="subscription_word" var="subscribtion_word"/>
<fmt:message key="mark_visit" var="mark_visit"/>
<fmt:message key="delete_subscription" var="delete_subscription"/>


<u:html title="${subscribtion_word}">
    <h2 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">${subscribtion_word}</h2>
    <c:choose>
        <c:when test="${subscriptionsList == null}">
            <h3 align="center">${list_is_empty}!</h3>
        </c:when>
        <c:otherwise>
            <c:forEach var="sub" items="${subscriptionsList}">
                <div class="post_ex">
                    <h3 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">${sub.typeOfExercise}
                    </h3>
                    <div class="post_reference">
                        <fmt:message key="name_word"/>: ${sub.userInfo.name}
                    </div>
                    <div class="post_reference">
                        <fmt:message
                                key="surname_word"/>: ${sub.userInfo.surname}
                    </div>
                    <div class="post_reference">
                        <fmt:message
                                key="patronymic_word"/>:${sub.userInfo.patronymic}
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
                        <a href="/coach/notifyVisit.html?subId=${sub.identity}">
                            ${mark_visit}.
                        </a>
                    </div>
                    <div class="post_reference">
                        <a href="/coach/deleteSubscribe.html?subId=${sub.identity}">
                            ${delete_subscription}.
                        </a>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</u:html>
