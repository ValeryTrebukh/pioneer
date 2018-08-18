<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language!=null ? sessionScope.language : pageContext.request.locale}"/>
<fmt:setBundle basename="messages.app"/>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/navMenu.jsp"/>
<br>
<div><a href="movies?action=create"><fmt:message key="entity.add"/></a></div>

<div id="shownPage">
    <div>
        <c:if test="${page > 1}"><a href="movies?page=${page > 1 ? page - 1 : 1}">< < <</a></c:if>
        <c:if test="${page <= 1}">< < <</c:if>
    </div>
    <div>Page&nbsp;${page}&nbsp;of&nbsp;${pagesCount}</div>
    <div>
        <c:if test="${page < pagesCount}"><a href="movies?page=${page < pagesCount ? page + 1 : pagesCount}">> > ></a></c:if>
        <c:if test="${page >= pagesCount}">> > ></c:if>
    </div>
</div>
<table>
    <thead>
    <tr>
        <th><fmt:message key="entity.name"/></th>
        <th><fmt:message key="movie.genre"/></th>
        <th><fmt:message key="movie.duration"/></th>
        <th><fmt:message key="movie.year"/></th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <c:forEach items="${movies}" var="movie">
        <jsp:useBean id="movie" type="com.elesson.pioneer.model.Movie"/>
        <tr>
            <td><c:out value="${movie.name}"/></td>
            <td>${movie.genre}</td>
            <td>${movie.duration}</td>
            <td>${movie.year}</td>
            <td><a href="movies?action=edit&movieid=${movie.id}"><fmt:message key="entity.edit"/></a></td>
            <td><a href="movies?action=delete&movieid=${movie.id}"><fmt:message key="entity.delete"/></a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
