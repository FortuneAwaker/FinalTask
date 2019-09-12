<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>


<fmt:message key="name_of_exercise" var="name"/>
<fmt:message key="string_pattern_title" var="string_pattern"/>
<fmt:message key="edit_word" var="edit"/>
<fmt:message key="editing_exercise" var="editing"/>


<u:html title="${editing}" validator="validator-of-registration.js"
        message="${message}">
    <h2 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">${editing}
    </h2>
    <form action="/admin/editExercise.html?exerciseId=${idOfExerciseToEdit}"
          method="post">
        <label for="nameOfExercise">${name}</label>
        <input type="text" id="nameOfExercise" name="nameOfExercise"
               required
               title="${string_pattern}"
               pattern="[A-Z][a-z\s]+" maxlength="15" minlength="3">
        <BUTTON type="submit">${edit}!</BUTTON>
    </form>
</u:html>