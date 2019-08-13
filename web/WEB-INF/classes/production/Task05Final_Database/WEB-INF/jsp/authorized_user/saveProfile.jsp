<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<u:html title="Регистрация" message="${message}"
        validator="validator-of-registration.js">
    <h2><fmt:message key="register_word"/></h2>
    <c:url value="/authorized_user/saveProfile.html" var="saveUrl"/>
    <form action="${saveUrl}" method="post" enctype="multipart/form-data"
          onsubmit="return validateRegistrationInfo(this)">
        <label for="name"><fmt:message key="name_word"/>:</label>
        <input type="text" id="name" name="name" required>
        <label for="surname"><fmt:message key="surname_word"/>:</label>
        <input type="text" id="surname" name="surname" required>
        <label for="patro"><fmt:message key="patronymic_word"/>:</label>
        <input type="text" id="patro" name="patro" required>
        <label for="phone"><fmt:message key="phone_word"/>:</label>
        <input type="number" id="phone" name="phone" required>
        <label for="avatar"><fmt:message key="avatar_word"/>:</label>
        <input type="file" size="50" id="avatar" name="avatar">
        <BUTTON type="submit"><fmt:message key="to_signup"/>!</BUTTON>
    </form>
</u:html>