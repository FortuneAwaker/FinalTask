<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<u:html title="Упражнения">
    <table border="1" width="80%" cellpadding="15">
    <thead>
    <th><c:out value="name"/> </th>
    <th><c:out value="surname"/></th>
    <th><c:out value="patronymic"/></th>
    <th><c:out value="phone"/></th>
    </thead>
          <c:forEach items="${listOfCoaches}" var="coach">
              <tr>
              <td><c:out value="${ coach.name }"/></td>
              <td><c:out value="${ coach.surname }"/></td>
              <td><c:out value="${ coach.patronymic }"/></td>
              <td><c:out value="${ coach.phone }"/></td>
              </tr>
          </c:forEach>
    </table>
</u:html>