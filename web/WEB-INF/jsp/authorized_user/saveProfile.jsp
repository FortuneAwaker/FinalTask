<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<fmt:message key="edit_profile" var="edit_profile"/>
<fmt:message key="number_pattern_title" var="number_pattern"/>
<fmt:message key="string_pattern_title" var="string_pattern"/>
<fmt:message key="edit_word" var="edit"/>
<fmt:message key="phone_pattern" var="phone_pattern"/>

<u:html title="${edit_profile}" message="${message}">
    <h2 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive">${edit_profile}
    </h2>
    <c:url value="/authorized_user/saveProfile.html" var="saveUrl"/>
    <form action="${saveUrl}" method="post" enctype="multipart/form-data">
        <label for="name"><fmt:message key="name_word"/>:</label>
        <input type="text" id="name" name="name" required
               title="${string_pattern}"
               pattern="[A-Z][a-z]+" maxlength="15" minlength="4"
               value="${userInfo.name}">
        <label for="surname"><fmt:message key="surname_word"/>:</label>
        <input type="text" id="surname" name="surname" required
               title="${string_pattern}"
               pattern="[A-Z][a-z]+" maxlength="15" minlength="4"
               value="${userInfo.surname}">
        <label for="patro"><fmt:message key="patronymic_word"/>:</label>
        <input type="text" id="patro" name="patro" required
               title="${string_pattern}"
               pattern="[A-Z][a-z]+" maxlength="15" minlength="4"
               value="${userInfo.patronymic}">
        <label for="phone"><fmt:message key="phone_word"/>:</label>
        <input type="text" id="phone" name="phone" required pattern="[0-9]+"
               title="O${phone_pattern}"
               maxlength="12" minlength="7"
               value="${userInfo.phone}">
        <label for="avatar"><fmt:message key="avatar_word"/>:</label>
        <input type="file" size="400" id="avatar" name="avatar">
        <BUTTON type="submit">${edit}!</BUTTON>
    </form>
</u:html>