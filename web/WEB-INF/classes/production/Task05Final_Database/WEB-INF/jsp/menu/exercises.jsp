<%@page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<u:html title="Упражнения">
        <ul>
        <c:forEach items="${listOfExercises}" var="execise">
             <li>${execise.typeOfExercises}</li>
        </c:forEach>
        </ul>
</u:html>