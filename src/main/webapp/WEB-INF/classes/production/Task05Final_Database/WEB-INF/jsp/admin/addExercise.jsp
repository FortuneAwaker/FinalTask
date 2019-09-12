<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<fmt:message key="add_exercise" var="add_exercise"/>
<fmt:message key="adding_new_exercise" var="new_exercise"/>
<fmt:message key="name_of_exercise" var="name"/>
<fmt:message key="string_pattern_title" var="string_pattern"/>



<u:html title="${add_exercise}"
        message="${message}">
    <h2 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">${new_exercise}
    </h2>
    <form action="/admin/addExercise.html" method="post">
        <label for="nameOfExercise">${name}</label>
        <input type="text" id="nameOfExercise" name="nameOfExercise"
               required
               title="${string_pattern}"
               pattern="[A-Z][a-z\s]+" maxlength="15" minlength="3">
        <BUTTON type="submit">${add_exercise}!</BUTTON>
    </form>
</u:html>