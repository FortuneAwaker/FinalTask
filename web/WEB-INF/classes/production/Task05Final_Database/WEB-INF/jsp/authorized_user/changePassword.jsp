<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<fmt:message key="login_and_password_pattern" var="pattern"/>
<fmt:message key="changing_password" var="changing_pass"/>
<fmt:message key="change_password" var="change_pass"/>


<u:html title="${changing_pass}" message="${message}">
    <h2 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">${changing_pass}
    </h2>
    <c:url value="/authorized_user/changePassword.html" var="url"/>
    <form action="${url}" method="post">
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
        <BUTTON type="submit">${change_pass}!</BUTTON>
    </form>
</u:html>