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
<jsp:useBean id="event" scope="request" type="com.elesson.pioneer.model.Event"/>
${event.movie.name}<br>
${event.movie.genre}<br>
${event.movie.duration}<br>
${event.movie.year}<br>
${event.date}<br>
${event.seance.start}
<br><br>
select sits
<table>
    <jsp:useBean id="hall" scope="request" type="com.elesson.pioneer.model.Hall"/>
    <c:forEach items="${hall.rows}" var="row">
        <tr>
        <c:forEach items="${row.seats}" var="seat">
            <c:if test="${sessionScope.authUser==null}">
                <c:if test="${seat.userId==null}">
                    <td class="green">${seat.row}-${seat.seat}</td>
                </c:if>
                <c:if test="${seat.userId!=null}">
                    <td class="grey">${seat.row}-${seat.seat}</td>
                </c:if>
            </c:if>

            <c:if test="${sessionScope.authUser!=null}">
                <c:if test="${seat.userId==null}">
                    <td><a class="green" href="ticket?eid=${event.id}&tid=${seat.row}-${seat.seat}">${seat.row}-${seat.seat}</a></td>
                </c:if>
                <c:if test="${seat.userId==sessionScope.authUser.id}">
                    <c:if test="${seat.id==null}">
                        <td><a class="blue" href="ticket?eid=${event.id}&tid=${seat.row}-${seat.seat}">${seat.row}-${seat.seat}</a></td>
                    </c:if>
                    <c:if test="${seat.id!=null}">
                        <td class="blue">${seat.row}-${seat.seat}</td>
                    </c:if>
                </c:if>
                <c:if test="${seat.userId!=null && seat.userId!=sessionScope.authUser.id}">
                    <td class="grey">${seat.row}-${seat.seat}</td>
                </c:if>
            </c:if>
        </c:forEach>
        </tr>
    </c:forEach>
</table>


<c:forEach items="${sessionScope.tickets}" var="ticket">
    <jsp:useBean id="ticket" type="com.elesson.pioneer.model.Ticket"/>
    <c:out value="eid:${ticket.eventId}, row:${ticket.row}, seat:${ticket.seat}"/> <br>
</c:forEach>

</body>
</html>
