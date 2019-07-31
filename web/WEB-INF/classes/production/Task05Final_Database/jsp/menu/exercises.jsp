    <%@page language="java" contentType="text/html; charset=UTF-8"
            pageEncoding="UTF-8" %>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
        <u:html title="Упражнения">
            <h1 align="center">Наши упражнения!</h1>
            <c:forEach items="${listOfExercises}" var="execise">
                <div class="post_ex">
                <h2>${execise.typeOfExercises}</h2>
                <c:choose>
                    <c:when test="${sessionScope.authorizedUser != null}">
                        <a
                        href="/authorized_user/groupsByExercise.html?exercise=${execise.typeOfExercises}
                        "
                        class="post_reference">Перейти к группам</a>
                    </c:when>
                    <c:otherwise>
                        <h5 class="post_reference">Чтобы просмотреть группы с
                        упражнениями, войдите в
                        аккаунт.</h5>
                    </c:otherwise>
                </c:choose>
                </div>
            </c:forEach>
        </u:html>