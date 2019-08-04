    <%@page language="java" contentType="text/html; charset=UTF-8"
            pageEncoding="UTF-8" %>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
        <fmt:setBundle basename="properties.club"/>

        <tag:html title="Тренеры">
            <c:forEach items="${listOfCoaches}" var="coach">
                <div class="post">
                <div class="post_img">
                <img alt="Coach avatar"
                style="object-position: 50% 0;"
                src="${coach.avatar}">
                </div>
                <div class="post_description">
                <span>${coach.name}<br>${ coach.surname }<br>
                ${ coach.patronymic }<br>${ coach.phone }<br>
                </span>
                <a href="/coach/groups.jsp?coachId=${coach.identity}">
                <fmt:message key="to_groups"/></a>
                </div>
                </div>
            </c:forEach>
        </tag:html>