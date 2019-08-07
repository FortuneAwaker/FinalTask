<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<fmt:message key="our_prices" var="our_prices"/>
<fmt:message key="exercises_word" var="exercisesKey"/>
<fmt:message key="number_of_days" var="number_of_days"/>
<fmt:message key="number_of_visits" var="number_of_visits"/>
<fmt:message key="price" var="priceWord"/>
<fmt:message key="action" var="actionWord"/>
<fmt:message key="subscribe_login" var="to_see_login"/>
<fmt:message key="subscribe_word" var="subscribe"/>

<u:html title="Подписка">
    <c:choose>
        <c:when test="${sessionScope.pricesOfGroup != null}">
            <h1 align="center">Вы выбрали группу с типом
                упражнения: ${wantedExercise.typeOfExercises}. Выберите цену,
                чтобы оформить подписку</h1>
            <table border="1" width="100%" cellpadding="15" class="table">
                <thead>
                <th>${exercisesKey}</th>
                <th>${number_of_days}</th>
                <th>${number_of_visits}</th>
                <th>${priceWord}</th>
                <th>${actionWord}</th>
                </thead>
                <tbody>
                <c:forEach var="price" items="${pricesOfGroup}">
                    <tr>
                        <td align="center" class="table-cell"><c:out
                                value="${ wantedExercise.typeOfExercises }"/></td>
                        <td align="center" class="table-cell"><c:out
                                value="${ price.numberOfDays }"/></td>
                        <td align="center" class="table-cell"><c:out
                                value="${ price.numberOfVisits }"/></td>
                        <td align="center" class="table-cell"><c:out
                                value="${ price.price }$"/></td>
                        <td class="table-cell">
                            <c:choose>
                                <c:when test="${sessionScope.authorizedUser != null}">
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
        </c:when>
        <c:when test="${sessionScope.groupsOfPrices != null}">
            <h1 align="center">Выберите группу для указанной цены.</h1>
            <c:forEach var="group" items="${groupsOfPrices}">
                <div class="post_ex">
                    <h3 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">${wantedExercise.typeOfExercises}
                    </h3>
                    <div class="post_reference">
                        <a href="/menu/coaches.html?coachId=${group.coachID}">
                            <fmt:message key="coach_information"/>
                        </a>
                    </div>
                    <div class="post_reference">
                        <fmt:message key="current_and_maximum_numbers"/> -
                            ${group.currentClients}/${group.maxClients}
                    </div>
                    <div class="post_reference">
                        <a href="/authorized_user/subscribe.html?groupId=${group.identity}">
                            <fmt:message key="subscribe_word"/>
                        </a>
                    </div>
                </div>
            </c:forEach>
        </c:when>
    </c:choose>
</u:html>