<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<u:html title="Смена пароля" message="${message}"
        validator="validator-of-registration.js">
    <h2><fmt:message key="register_word"/></h2>
    <c:url value="/authorized_user/changePassword.html" var="url"/>
    <form action="${url}" method="post">
        <label for="password"><fmt:message key="password_word"/>:</label>
        <input type="password" id="password" name="password" required
               pattern="[A-Za-z0-9]+"
               title="Only Latin letters and numbers, minimum 6 maximum 15"
               maxlength="15" minlength="6">
        <label for="repeat"><fmt:message key="repeat_password_word"/>:</label>
        <input type="password" id="repeat" name="repeat" required
               pattern="[A-Za-z0-9]+"
               title="Only Latin letters and numbers, minimum 6 maximum 15"
               maxlength="15" minlength="6">
        <BUTTON type="submit"><fmt:message key="to_signup"/>!</BUTTON>
    </form>
</u:html>