<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<fmt:message key="subscribe_login" var="to_see_login"/>
<fmt:message key="subscribe_word" var="subscribe"/>
<fmt:message key="groups_word" var="groups_word"/>
<fmt:message key="list_is_empty" var="list_is_empty"/>
<fmt:message key="add_group" var="add_group"/>
<fmt:message key="go_to_clients" var="go_to_clients"/>
<fmt:message key="edit_group" var="edit"/>
<fmt:message key="delete_group" var="delete"/>


<u:html title="${groups_word}">
    <h2 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">${groups_word}
    </h2>
    <c:choose>
        <c:when test="${!(not empty listOfGroups)}">
            <h3 align="center">${list_is_empty}!</h3>
        </c:when>
        <c:otherwise>
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
                                <fmt:message key="current_and_maximum_numbers"/>
                                -
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
                    <h2 align="center"><a
                            href="/coach/addGroup.html?todo=false">${add_group}</a></h2>
                    <c:forEach var="group" items="${listOfGroups}">
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
                                <a href="/coach/membersOfGroup.html?groupId=${group.identity}">
                                    ${go_to_clients}
                                </a>
                            </div>
                            <div class="post_reference">
                                <a href="/coach/editGroup.html?todo=false&groupId=${group.identity}">
                                    ${edit}
                                </a>
                            </div>
                            <div class="post_reference">
                                <a href="/coach/deleteGroup.html?groupId=${group.identity}">
                                    ${delete}
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>

</u:html>
