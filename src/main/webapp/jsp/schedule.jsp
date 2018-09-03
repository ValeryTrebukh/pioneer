<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language!=null ? sessionScope.language : pageContext.request.locale}"/>
<fmt:setBundle basename="messages.app"/>
<html>
    <jsp:include page="fragments/headTag.jsp"/>
    <body>
    <jsp:include page="fragments/navMenu.jsp"/>
    <div class="week">
        <c:forEach items="${nextWeek}" var="day">
            <jsp:useBean id="day" type="java.time.LocalDate"/>
            <div><a href="schedule?date=${day}"><fmt:message key="sch.${day.dayOfWeek}"/></a></div>
        </c:forEach>
    </div>
    <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
        <div><a href="event?action=create&date=${date}"><fmt:message key="entity.add"/></a></div>
    </c:if>
    <br>
    <div id="chosenDay">
        <div><fmt:message key="sch.seances"/>${date}</div>
    </div>
    <table>
        <thead>
        <tr>
            <th><fmt:message key="entity.name"/></th>
            <th><fmt:message key="movie.start"/></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${events}" var="event">
            <jsp:useBean id="event" type="com.elesson.pioneer.model.Event"/>
            <tr>
                <td><a href="event?action=view&eid=${event.id}"><c:out value="${event.movie.name}"/></a></td>
                <td>${event.seance.start}</td>
                <c:if test="${sessionScope.authUser.role == 'ADMIN'}">
                    <td><a href="event?action=delete&eid=${event.id}"><fmt:message key="entity.delete"/></a></td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
    </body>
</html>
