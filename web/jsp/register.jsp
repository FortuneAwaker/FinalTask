<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<u:html title="Регистрация" message="${message}">
    <h2>Регистрация</h2>
    <c:url value="/register.html" var="registerUrl"/>
    <form action="${registerUrl}" method="post">
        <label for="login">Имя пользователя:</label>
        <input type="text" id="login" name="login">
        <label for="password">Пароль:</label>
        <input type="text" id="password" name="password">
        <label for="repeat">Повторите пароль:</label>
        <input type="text" id="repeat" name="repeat password">
    </form>
</u:html>