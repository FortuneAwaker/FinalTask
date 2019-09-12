<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<fmt:message key="register_word" var="regiser"/>
<fmt:message key="string_pattern_title" var="string_pattern"/>
<fmt:message key="phone_pattern" var="phone_pattern"/>
<fmt:message key="login_and_password_pattern" var="pattern"/>

<u:html title="${regiser}" message="${message}">
    <h2 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">${regiser}</h2>
    <c:url value="/register.html" var="registerUrl"/>
    <form action="${registerUrl}" method="post">
        <label for="login"><fmt:message key="username_word"/>:</label>
        <input type="text" id="login" name="login" required
               maxlength="15"
               pattern="[A-Za-z0-9]+"
               title="${pattern}"
               minlength="6">
        <label for="password"><fmt:message key="password_word"/>:</label>
        <input type="password" id="password" name="password" required
               pattern="[A-Za-z0-9]+"
               title="${pattern}"
               maxlength="15" minlength="6">
        <label for="repeat"><fmt:message key="repeat_password_word"/>:</label>
        <input type="password" id="repeat" name="repeat" required
               pattern="[A-Za-z0-9]+"
               title="${pattern}"
               maxlength="15" minlength="6">
        <label for="name"><fmt:message key="name_word"/>:</label>
        <input type="text" id="name" name="name" required
               title="${string_pattern}"
               pattern="[A-Z][a-z]+" maxlength="15" minlength="4">
        <label for="surname"><fmt:message key="surname_word"/>:</label>
        <input type="text" id="surname" name="surname" required
               title="${string_pattern}"
               pattern="[A-Z][a-z]+" maxlength="15" minlength="4">
        <label for="patro"><fmt:message key="patronymic_word"/>:</label>
        <input type="text" id="patro" name="patro" required
               title="${string_pattern}"
               pattern="[A-Z][a-z]+" maxlength="15" minlength="4">
        <label for="phone"><fmt:message key="phone_word"/>:</label>
        <input type="text" id="phone" name="phone" required pattern="[0-9]+"
               title="${phone_pattern}"
               maxlength="12" minlength="7">
        <BUTTON type="submit"><fmt:message key="to_signup"/>!</BUTTON>
    </form>
</u:html>