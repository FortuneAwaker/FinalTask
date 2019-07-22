<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>SportClub</title>
</head>
<body>
<header class="header">
    <div class="logo">
        <ul>
            <li>
                <p>SportClub</p>
            </li>
            <li>
                <img src="../img/logo.png"
                     alt="logo">
            </li>
            <li>
                <img src="../img/logo.png"
                     alt="logo2">
            </li>
        </ul>
    </div>
    <div class="log_in_out">
        <c:choose>
            <c:when test="${sessionScope.user == null}">
                <a href="${pageContext.request.contextPath}/jsp/">LogIn</a>
                <a href="${pageContext.request.contextPath}/jsp/register.jsp">SighUp</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/controller?command=logout">LogOut</a>
            </c:otherwise>
        </c:choose>
    </div>
</header>
<div>
    <ul>
        <c:forEach items="${menu}" var="item">
            <c:url value="${item.url}" var="itemUrl"/>
            <li class="item"><A href="${itemUrl}">${item.name}</A></li>
        </c:forEach>
    </ul>
</div>
<!--tag:headerLine/-->
<h1>Приветствуем вас!</h1>
<footer class="footer">
    Alexander Popovich
</footer>
</body>
</html>
