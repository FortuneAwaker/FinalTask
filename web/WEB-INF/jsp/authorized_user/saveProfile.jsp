<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<u:html title="Регистрация" message="${message}">
    <h2><fmt:message key="register_word"/></h2>
    <c:url value="/authorized_user/saveProfile.html" var="saveUrl"/>
    <form action="${saveUrl}" method="post" enctype="multipart/form-data">
        <label for="name"><fmt:message key="name_word"/>:</label>
        <input type="text" id="name" name="name" required
               title="Only Latin letters, minimum 4 maximum 15"
               pattern="[A-Z][a-z]+" maxlength="15" minlength="4"
               value="${userInfo.name}">
        <label for="surname"><fmt:message key="surname_word"/>:</label>
        <input type="text" id="surname" name="surname" required
               title="Only Latin letters, minimum 4 maximum 15"
               pattern="[A-Z][a-z]+" maxlength="15" minlength="4"
               value="${userInfo.surname}">
        <label for="patro"><fmt:message key="patronymic_word"/>:</label>
        <input type="text" id="patro" name="patro" required
               title="Only Latin letters, minimum 4 maximum 15"
               pattern="[A-Z][a-z]+" maxlength="15" minlength="4"
               value="${userInfo.patronymic}">
        <label for="phone"><fmt:message key="phone_word"/>:</label>
        <input type="text" id="phone" name="phone" required pattern="[0-9]+"
               title="Only numbers, minimum 7 maximum 12"
               maxlength="12" minlength="7"
               value="${userInfo.phone}">
        <label for="avatar"><fmt:message key="avatar_word"/>:</label>
        <input type="file" size="400" id="avatar" name="avatar">
        <BUTTON type="submit">Edit!</BUTTON>
    </form>
</u:html>