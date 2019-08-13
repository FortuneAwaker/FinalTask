<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<fmt:message key="all_users_word" var="users"/>
<fmt:message key="role_word" var="role"/>
<fmt:message key="login" var="login"/>
<fmt:message key="name_word" var="name"/>
<fmt:message key="surname_word" var="surname"/>
<fmt:message key="patronymic_word" var="patro"/>
<fmt:message key="phone_word" var="phone"/>
<fmt:message key="action" var="actionWord"/>
<fmt:message key="role_word" var="role"/>

<u:html title="Пользователи">
    <h1 align="center" style="margin-bottom: 30px;
            color: purple; font-family: 'Montserrat', cursive">${users}
    </h1>
    <table border="1" width="100%" cellpadding="15" class="table">
        <thead>
        <th>${login}</th>
        <th>${role}</th>
        <th>${name}</th>
        <th>${surname}</th>
        <th>${patro}</th>
        <th>${phone}</th>
        <th>${actionWord}</th>
        </thead>
        <tbody>
        <c:forEach var="user" items="${allUsers}">
            <tr>
                <td align="center" class="table-cell" style="font-size: 12px">
                    <c:out
                            value="${ user.login }"/></td>
                <td align="center" class="table-cell" style="font-size: 12px">
                    <c:out
                            value="${ user.role }"/></td>
                <td align="center" class="table-cell" style="font-size: 12px">
                    <c:out
                            value="${ user.name }"/></td>
                <td align="center" class="table-cell" style="font-size: 12px">
                    <c:out
                            value="${ user.surname }"/></td>
                <td align="center" class="table-cell" style="font-size: 12px">
                    <c:out
                            value="${ user.patronymic }"/></td>
                <td align="center" class="table-cell" style="font-size: 12px">
                    <c:out
                            value="+${ user.phone }"/></td>
                <td class="table-cell">
                    <a style="font-size: 12px"
                            href="/admin/changeRole.html?userId=${user.identity}">Изменить
                        роль</a><br><br>
                    <a style="font-size: 12px"
                            href="/admin/deleteUser.html?userId=${user.identity}">Удалить</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</u:html>