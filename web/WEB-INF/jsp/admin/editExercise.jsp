<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<u:html title="Редактировать расценку" validator="validator-of-registration.js"
        message="${message}">
    <h3 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">Редактирование упражнения
    </h3>
    <form action="/admin/editExercise.html?exerciseId=${idOfExerciseToEdit}" method="post"
          onsubmit="return validateRegistrationInfo(this)">
        <label for="nameOfExercise">Name of exercise</label>
        <input type="text" id="nameOfExercise" name="nameOfExercise"
               required
               title="Only Latin letters, minimum 3 maximum 25"
               pattern="[A-Z][a-z]+" maxlength="25" minlength="3">
        <BUTTON type="submit">Редактировать упражнение!</BUTTON>
    </form>
</u:html>