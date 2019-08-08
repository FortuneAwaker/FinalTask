<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<fmt:message key="subscribe_login" var="to_see_login"/>
<fmt:message key="subscribe_word" var="subscribe"/>

<u:html title="Группы">
    <h2 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">Мои группы
    </h2>
    <c:choose>
        <c:when test="${coachIdFromRequest != null}">
            <c:forEach var="group" items="${listOfGroups}">
                <div class="post_ex">
                    <h3 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">${group.typeOfExercises}
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
                        <c:choose>
                            <c:when test="${sessionScope.authorizedUser != null}">
                                <a href="/authorized_user/subscribe.html?groupId=${group.identity}">${subscribe}</a>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${to_see_login}"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <h2 align="center"><a href="/coach/addGroup.html?todo=false">Добавить группу</a></h2>
            <c:forEach var="group" items="${listOfGroups}">
                <div class="post_ex">
                    <h3 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">${group.typeOfExercises}
                    </h3>
                    <div class="post_reference">
                        <fmt:message key="current_and_maximum_numbers"/> -
                            ${group.currentClients}/${group.maxClients}
                    </div>
                    <div class="post_reference">
                        <a href="/coach/membersOfGroup.html?groupId=${group.identity}">
                            Перейти к участникам группы
                        </a>
                    </div>
                    <div class="post_reference">
                        <a href="/coach/deleteGroup.html?groupId=${group.identity}">
                            Удалить группу
                        </a>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>

</u:html>
