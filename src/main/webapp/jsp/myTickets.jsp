<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language!=null ? sessionScope.language : pageContext.request.locale}"/>
<fmt:setBundle basename="messages.app"/>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/navMenu.jsp"/>

<table>
    <thead>
    <tr>
        <th><fmt:message key="entity.name"/></th>
        <th><fmt:message key="entity.date"/></th>
        <th><fmt:message key="movie.start"/></th>
        <th><fmt:message key="ticket.seat"/></th>
    </tr>
    </thead>
    <c:forEach items="${tickets}" var="ticket">
        <jsp:useBean id="ticket" type="com.elesson.pioneer.model.Ticket"/>
        <tr>
            <td><a href="event?action=view&eid=${ticket.event.id}"><c:out value="${ticket.event.movie.name}"/></a></td>
            <td>${ticket.event.date}</td>
            <td>${ticket.event.seance.start}</td>
            <td>${ticket.row} - ${ticket.seat}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
