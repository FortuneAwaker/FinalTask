    <%@page language="java" contentType="text/html; charset=UTF-8"
            pageEncoding="UTF-8" %>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
        <fmt:setBundle basename="properties.club"/>

        <fmt:message key="our_exercises" var="our_exercises"/>
        <fmt:message key="to_groups" var="go_to_groups"/>
        <fmt:message key="to_see_login" var="to_see_login"/>

        <u:html title="Упражнения">
            <h1 align="center">${our_exercises}</h1>
            <c:forEach items="${listOfExercises}" var="execise">
                <div class="post_ex">
                <h2>${execise.typeOfExercises}</h2>
                <c:choose>
                    <c:when test="${sessionScope.authorizedUser != null}">
                        <a
                        href="/authorized_user/groupsByExercise.html?exercise=${execise.typeOfExercises}
                        "
                        class="post_reference">${go_to_groups}</a>
                    </c:when>
                    <c:otherwise>
                        <h5 class="post_reference">${to_see_login}</h5>
                    </c:otherwise>
                </c:choose>
                </div>
            </c:forEach>
        </u:html>