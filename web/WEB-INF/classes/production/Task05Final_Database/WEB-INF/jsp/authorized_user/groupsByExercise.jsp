<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<u:html title="Группы">
    <h2>Группы по запросу:</h2>
    <c:forEach var="group" items="${groupsByExercise}">
        <div class="post_ex">
            <h3 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">${exerciseName}
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
</u:html>
