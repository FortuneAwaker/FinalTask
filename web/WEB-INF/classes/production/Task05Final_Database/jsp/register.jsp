<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<u:html title="Регистрация" message="${message}"
        validator="validator-of-registration.js">
    <h2><fmt:message key="register_word"/></h2>
    <c:url value="/register.html" var="registerUrl"/>
    <form action="${registerUrl}" method="post"
          onsubmit="return validateRegistrationInfo(this)">
        <label for="login"><fmt:message key="username_word"/>:</label>
        <input type="text" id="login" name="login" required>
        <label for="password"><fmt:message key="password_word"/>:</label>
        <input type="password" id="password" name="password" required>
        <label for="repeat"><fmt:message key="repeat_password_word"/>:</label>
        <input type="password" id="repeat" name="repeat" required>
        <label for="name"><fmt:message key="name_word"/>:</label>
        <input type="text" id="name" name="name" required>
        <label for="surname"><fmt:message key="surname_word"/>:</label>
        <input type="text" id="surname" name="surname" required>
        <label for="patro"><fmt:message key="patronymic_word"/>:</label>
        <input type="text" id="patro" name="patro" required>
        <label for="phone"><fmt:message key="phone_word"/>:</label>
        <input type="number" id="phone" name="phone" required>
        <label for="avatar"><fmt:message key="avatar_word"/>:</label>
        <input type="image" id="avatar" name="avatar">
        <BUTTON type="submit"><fmt:message key="to_signup"/>!</BUTTON>
    </form>
</u:html>