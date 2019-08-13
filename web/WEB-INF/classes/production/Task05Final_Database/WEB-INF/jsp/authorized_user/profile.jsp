<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="properties.club"/>

<u:html title="Подписки">
    <h2 align="center">Профиль</h2>
    <c:choose>
        <c:when test="${sessionScope.authorizedUser != null}">
            <div class="post_ex">
                <div class="post_img">
                    <img width="150" height="250" src="data:image/jpeg;base64,${image}"  alt="avatar">
                </div>
                <div class="post_reference">
                    <fmt:message key="name_word"/>: ${userInfo.name}
                </div>
                <div class="post_reference">
                    <fmt:message key="surname_word"/>: ${userInfo.surname}
                </div>
                <div class="post_reference">
                    <fmt:message
                            key="patronymic_word"/>:${userInfo.patronymic}
                </div>
                <div class="post_reference">
                    <fmt:message key="phone_word"/>: ${userInfo.phone}
                </div>
                <div class="post_reference">
                    Role: ${authorizedUser.role.name}
                </div>
                <div class="post_reference">
                    <a href="/authorized_user/saveProfile.html?todo=false">
                        Редактировать профиль.
                    </a>
                </div>
                <div class="post_reference">
                    <a href="/authorized_user/changePassword.html?todo=false">
                        Изменить пароль.
                    </a>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <h3 align="center">Профиль не найден!</h3>
        </c:otherwise>
    </c:choose>
</u:html>
