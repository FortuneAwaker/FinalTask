<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<fmt:message key="my_subscriptions" var="my_subs"/>
<fmt:message key="list_is_empty" var="list_is_empty"/>


<u:html title="${my_subs}">
    <h2 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">${my_subs}
    </h2>
    <c:choose>
        <c:when test="${not empty listOfSubscriptions}">
            <c:forEach var="subscription" items="${listOfSubscriptions}">
                <div class="post_ex">
                    <h3 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">${subscription.typeOfExercise}
                    </h3>
                    <div class="post_reference">
                        <a href="/menu/coaches.html?coachId=${subscription.coachId}">
                            <fmt:message key="coach_information"/>
                        </a>
                    </div>
                    <div class="post_reference">
                        <h3>
                            <fmt:message key="left_visits"/> -
                                ${subscription.leftVisits}
                        </h3>
                    </div>
                    <div class="post_reference">
                        <h3>
                            <fmt:message key="last_day"/> -
                                ${subscription.lastDay}
                        </h3>
                    </div>
                    <div class="post_reference">
                        <h3>
                            <fmt:message key="price"/> -
                                ${subscription.payment}
                        </h3>
                    </div>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <h3>${list_is_empty}!</h3>
        </c:otherwise>
    </c:choose>


</u:html>