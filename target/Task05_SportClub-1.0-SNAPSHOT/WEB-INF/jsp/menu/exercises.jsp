    <%@page language="java" contentType="text/html; charset=UTF-8"
            pageEncoding="UTF-8" %>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
        <fmt:setBundle basename="properties.club"/>

        <fmt:message key="our_exercises" var="our_exercises"/>
        <fmt:message key="exercises_word" var="exercises_word"/>
        <fmt:message key="to_groups" var="go_to_groups"/>
        <fmt:message key="to_see_login" var="to_see_login"/>
        <fmt:message key="add_exercise" var="add_exercise"/>
        <fmt:message key="edit_exercise" var="edit_exercise"/>
        <fmt:message key="delete_exercise" var="delete_exercise"/>

        <u:html title="${exercises_word}">
            <h2 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">${our_exercises}</h2>
            <c:choose>
                <c:when test="${sessionScope.authorizedUser.role.identity == 0}">
                    <h3 align="center" style="margin-top: 20px">
                    <a href="/admin/addExercise.html?todo=false">${add_exercise}</a>
                    </h3>
                </c:when>
            </c:choose>
            <c:forEach items="${listOfExercises}" var="execise">
                <div class="post_ex">
                <h2>${execise.typeOfExercises}</h2>
                <c:choose>
                    <c:when test="${sessionScope.authorizedUser.role.identity != 0}">
                        <div class="post_reference">
                        <a
                        href="/authorized_user/groupsByExercise.html?exercise=${execise.typeOfExercises}
                        "
                        >${go_to_groups}</a>
                        </div>
                    </c:when>
                    <c:when test="${sessionScope.authorizedUser.role.identity == 0}">
                        <div class="post_reference">
                        <a
                        href="/authorized_user/groupsByExercise.html?exercise=${execise.typeOfExercises}
                        ">${go_to_groups}</a>
                        </div>
                        <div class="post_reference">
                        <a
                        href="/admin/editExercise.html?exerciseId=${execise.identity}
                        &todo=false">
                        ${edit_exercise}.
                        </a>
                        </div>
                        <div class="post_reference">
                        <a
                        href="/admin/deleteExercise.html?exerciseId=${execise.identity}
                        ">
                        ${delete_exercise}.
                        </a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <h5 class="post_reference">${to_see_login}</h5>
                    </c:otherwise>
                </c:choose>
                </div>
            </c:forEach>
        </u:html>