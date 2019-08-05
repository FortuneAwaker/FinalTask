<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="properties.club"/>

<tag:html title="Главная" message="${message}">
    <body>
    <h1 style="text-align: center; color: purple"><fmt:message key="hello_on_main_page"/></h1>
    <div class="post">
        <div class="post_img"><img alt="Photo on main page"
                                   style="object-position: 50% 0;"
                                   src="../../img/motivation.jpg">
        </div>
        <div class="post_description">
            <h4><fmt:message key="post1_description"></fmt:message></h4>
        </div>
    </div>
    <div class="post">
        <div class="post_img">
            <img alt="Photo on main page"
                 style="object-position: 50% 0; height: 400px;"
                 src="../../img/bMipYRFqfjI.jpg">
        </div>
        <div class="post_description">
            <h4><fmt:message key="post2_description"/></h4>
        </div>
    </div>
    </body>
</tag:html>
