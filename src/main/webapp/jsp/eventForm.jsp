<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language!=null ? sessionScope.language : pageContext.request.locale}"/>
<fmt:setBundle basename="messages.app"/>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<h2><fmt:message key="entity.create"/></h2>
<hr>
<jsp:useBean id="event" type="com.elesson.pioneer.model.Event" scope="request"/>
<div id="eventForm">
    <form method="post" action="event">

        <c:if test="${duplicate}">
            DUPLICATE
            </br>
        </c:if>
        <input type="hidden" name="eid" value="${event.id}">

        <div><input type="hidden" name="date" value="${event.date}"></div> <br>

        <div><label><fmt:message key="entity.name"/></label></div>
        <select name="mid">
            <c:forEach items="${movies}" var="movie">
                <jsp:useBean id="movie" type="com.elesson.pioneer.model.Movie"/>
                <option value="${movie.id}">${movie.name}</option>
            </c:forEach>
        </select>

        <div><label><fmt:message key="movie.start"/></label></div>
        <select name="sid">
            <option value="1">9:00</option>
            <option value="2">13:00</option>
            <option value="3">18:00</option>
            <option value="4">22:00</option>
        </select>

        </br>
        <button type="submit"><fmt:message key="entity.save"/></button>
        <button onclick="window.history.back()" type="button"><fmt:message key="entity.cancel"/></button>
    </form>
</div>
</body>
</html>

