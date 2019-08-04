<%@page isErrorPage="true" language="java"
        contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<fmt:message key="requested_page" var="requested_page"/>
<fmt:message key="not_found" var="not_found"/>

<u:html title="Ошибка">
    <c:choose>
        <c:when test="${not empty error}">
            <H2>${error}</H2>
        </c:when>
        <c:when test="${not empty pageContext.errorData.requestURI}">
            <H2>${requested_page} ${pageContext.errorData.requestURI} ${not_found}</H2>
        </c:when>
        <c:otherwise><fmt:message key="unexpected_error"/></c:otherwise>
    </c:choose>
    <c:url value="/index.html" var="mainUrl"/>
    <A href="${mainUrl}"><fmt:message key="to_main_words"/></A>
</u:html>