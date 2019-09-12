<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<fmt:message key="enter_word" var="enter"/>

<u:html title="${enter}" message="${message}">
    <H2 align="center" style="margin-bottom: 15px;
            color: purple; font-family: 'Montserrat', cursive"><fmt:message key="enter_word"/></H2>
    <c:url value="/login.html" var="loginUrl"/>
    <FORM action="${loginUrl}" method="post">
        <LABEL for="login"><fmt:message key="username_word"/>:</LABEL>
        <INPUT type="text" id="login" name="login">
        <LABEL for="password"><fmt:message key="password_word"/>:</LABEL>
        <INPUT type="password" id="password" name="password">
        <BUTTON type="submit"><fmt:message key="to_enter"/></BUTTON>
    </FORM>
</u:html>