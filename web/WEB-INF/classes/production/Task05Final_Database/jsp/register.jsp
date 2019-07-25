<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<u:html title="Регистрация" message="${message}">
    <h2>Регистрация</h2>
    <c:url value="/register.html" var="registerUrl"/>
    <form action="${registerUrl}" method="post">
        <label for="login">Имя пользователя:</label>
        <input type="text" id="login" name="login" required>
        <label for="password">Пароль:</label>
        <input type="password" id="password" name="password" required>
        <label for="repeat">Повторите пароль:</label>
        <input type="password" id="repeat" name="repeat" required>
        <label for="name">Имя</label>
        <input type="text" id="name" name="name" required>
        <label for="surname">Фамилия</label>
        <input type="text" id="surname" name="surname" required>
        <label for="patro">Отчество</label>
        <input type="text" id="patro" name="patro" required>
        <label for="phone">Телефон</label>
        <input type="number" id="phone" name="phone"required>
        <label for="avatar">Аватар</label>
        <input type="image" id="avatar" name="avatar">
        <BUTTON type="submit">Зарегистрироваться</BUTTON>
    </form>
</u:html>