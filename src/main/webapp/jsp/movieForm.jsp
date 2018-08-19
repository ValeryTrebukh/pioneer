<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language!=null ? sessionScope.language : pageContext.request.locale}"/>
<fmt:setBundle basename="messages.app"/>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<c:if test="${param.action == 'edit'}">
    <h2><fmt:message key="entity.editdata"/></h2>
</c:if>
<c:if test="${param.action == 'create'}">
    <h2><fmt:message key="entity.create"/></h2>
</c:if>
<hr>
<jsp:useBean id="movie" type="com.elesson.pioneer.model.Movie" scope="request"/>
<div id="movieForm">
    <form method="post" action="movies">
        <input type="hidden" name="mid" value="${movie.id}">

        <div><label><fmt:message key="entity.name"/></label></div>
        <div><input type="text" value="${movie.name}" name="name" required></div> <br>
        <%--<c:if test="${errName}">--%>
            <%--<div class="error"><fmt:message key="err.name"/></div>--%>
        <%--</c:if>--%>

        <div><label><fmt:message key="movie.genre"/></label></div>
        <div><input type="text" value="${movie.genre}" name="genre" required></div> <br>
        <%--<c:if test="${errEmail}">--%>
            <%--<div class="error"><fmt:message key="err.email"/></div>--%>
        <%--</c:if>--%>
        <%--<c:if test="${duplicate}">--%>
            <%--<div class="error"><fmt:message key="err.duplicate"/></div>--%>
        <%--</c:if>--%>

        <div><label><fmt:message key="movie.duration"/></label></div>
        <div><input type="text" value="${movie.duration}" name="duration" required></div> <br>
        <%--<c:if test="${errPassLen}">--%>
            <%--<div class="error"><fmt:message key="err.password"/></div>--%>
        <%--</c:if>--%>

        <div><label><fmt:message key="movie.year"/></label></div>
        <div><input type="text" value="${movie.year}" name="year" required></div> <br>

        <div><label><fmt:message key="movie.status"/></label></div>
        <div><select name="status">
                <option value="true"><fmt:message key="movie.active"/></option>
                <option value="false"><fmt:message key="movie.archived"/></option>
            </select></div>
        </br>
        <button type="submit"><fmt:message key="entity.save"/></button>
        <button onclick="window.history.back()" type="button"><fmt:message key="entity.cancel"/></button>
    </form>
</div>
</body>
</html>
