<%@page isErrorPage="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<u:html title="Ошибка">
    <c:choose>
        <c:when test="${not empty error}">
            <H2>${error}</H2>
        </c:when>
        <c:when test="${not empty pageContext.errorData.requestURI}">
            <H2>Запрошенная страница ${pageContext.errorData.requestURI} не найдена на сервере</H2>
        </c:when>
        <c:otherwise>Непредвиденная ошибка приложения</c:otherwise>
    </c:choose>
    <c:url value="/index.html" var="mainUrl"/>
    <A href="${mainUrl}">На главную</A>
</u:html>