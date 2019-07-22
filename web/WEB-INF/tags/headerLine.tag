<%@tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<body>
<header class="stripe">
    <div id="left-container">
        <a id="logo-name" style="text-decoration: none" href="/index.jsp"><p>
            SportClub</p></a>
        <%--<img id="logo-img" src="../img/logo.png"--%>
        <%--alt="logo">--%>
    </div>
    <div id="right-container">
        <div class="log-in-out">
            <c:choose>
                <c:when test="${sessionScope.authorizedUser == null}">
                    <a href="../jsp/login.jsp" id="log">LogIn</a>
                    <a href="../jsp/register.jsp" id="log">SighUp</a>
                </c:when>
                <c:otherwise>
                    <c:out value="Hello, ${userInfo.name}"/>
                    <c:url value="/logout.html" var="logoutUrl"/>
                    <LI><A href="${logoutUrl}">выход</A></LI>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>
<div id="menu-items">
    <c:forEach items="${menu}" var="item">
        <c:url value="${item.url}" var="itemUrl"/>
        <A class="menu-member" href="${itemUrl}">${item.name}</A>
    </c:forEach>
    <A class="menu-member" href="/menu/exercises.html">Exercises</A>
    <A class="menu-member" href="/menu/coaches.html">Coaches</A>
    <A class="menu-member" href="/menu/tickets">Tickets</A>
</div>
</body>
