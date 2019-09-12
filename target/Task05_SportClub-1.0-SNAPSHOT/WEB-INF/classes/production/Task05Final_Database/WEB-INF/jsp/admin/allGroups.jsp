<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<fmt:message key="subscribe_login" var="to_see_login"/>
<fmt:message key="subscribe_word" var="subscribe"/>
<fmt:message key="all_groups_word" var="all_groups"/>
<fmt:message key="list_is_empty" var="list_is_empty"/>

<u:html title="${all_groups}">
    <h2 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">${all_groups}
    </h2>
    <c:choose>
        <c:when test="${!(not empty allGroups)}">
            <h3 align="center">${list_is_empty}!</h3>
        </c:when>
        <c:otherwise>
            <c:forEach var="group" items="${allGroups}">
                <div class="post_ex">
                    <h3 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">${group.typeOfExercises}
                    </h3>
                    <div class="post_reference">
                        <fmt:message key="current_and_maximum_numbers"/>
                        -
                            ${group.currentClients}/${group.maxClients}
                    </div>
                    <div class="post_reference">
                        <a href="/menu/coaches.html?coachId=${group.coachID}">
                            <fmt:message key="coach_information"/>
                        </a>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>

</u:html>
