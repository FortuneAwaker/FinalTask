<%@tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="properties.club"/>

<fmt:message key="hello_word" var="hello"/>
<fmt:message key="exit_word" var="LogOut"/>
<fmt:message key="login_word" var="LogIn"/>
<fmt:message key="register_word" var="Register"/>
<fmt:message key="exercises_word" var="Exercises"/>
<fmt:message key="coaches_word" var="Coaches"/>
<fmt:message key="prices_word" var="Prices"/>
<fmt:message key="to_groups" var="to_groups"/>


<body>
<header class="stripe">
    <div id="left-container">
        <a id="logo-name" style="text-decoration: none" href="/index.jsp"><p>
            SportClub</p></a>
        <img id="logo-img" src="../img/logo.png"
             alt="logo">
        <p class="language"><fmt:message key="language_word"/></p>
        <a class="language" href="/changeLanguage.html?lang=ru">RU</a>
        <a class="language" href="/changeLanguage.html?lang=be">BY</a>
        <a class="language" href="/changeLanguage.html?lang=en">EN</a>
    </div>
    <div id="right-container">
        <div class="log-in-out">
            <c:choose>
                <c:when test="${sessionScope.authorizedUser == null}">
                    <a href="/login.html?todo=false" id="log">${LogIn}</a>
                    <a href="/register.html?todo=false" id="log">${Register}</a>
                </c:when>
                <c:otherwise>
                    <c:out value="${hello}, ${userInfo.name}"/>
                    <A href="/logout.html" style="padding-left: 10px">${LogOut}</A>
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
    <A class="menu-member" href="/menu/exercises.html">${Exercises}</A>
    <A class="menu-member" href="/menu/coaches.html">${Coaches}</A>
    <A class="menu-member" href="/menu/tickets.html">${Prices}</A>
</div>
</body>
