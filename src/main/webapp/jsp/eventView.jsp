<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language!=null ? sessionScope.language : pageContext.request.locale}"/>
<fmt:setBundle basename="messages.app"/>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/navMenu.jsp"/>
<jsp:useBean id="event" scope="request" type="com.elesson.pioneer.model.Event"/>

    <div class="info-film">
        <img src="${pageContext.request.contextPath}/img/BigBrother.png"/>
        <div class="info-film-content">
            <h2 class="title-page">${event.movie.name}</h2>
            <p class="info">${event.movie.genre}, ${event.movie.year}</p>
            <ul class="data-film">
                <li>
                    <p class="title-data-film"><fmt:message key="movie.duration"/>:</p>
                    <p>${event.movie.duration} <fmt:message key="movie.min"/></p>
                </li>
                <li>
                    <p class="title-data-film"><fmt:message key="entity.date"/>:</p>
                    <p>${event.date}</p>
                </li>
                <li>
                    <p class="title-data-film"><fmt:message key="movie.start"/>:</p>
                    <p>${event.seance.start}</p>
                </li>
            </ul>
        </div>
    </div>
    <br>

    <div class="seats">
        <h4>select sits</h4>
        <div></div>
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

        <c:if test="${sessionScope.tickets!=null && not empty(sessionScope.tickets)}">
            <form method="post" action="ticket">
                <input type="hidden" name="eid" value="${event.id}">

                <c:forEach items="${sessionScope.tickets}" var="ticket">
                    <jsp:useBean id="ticket" type="com.elesson.pioneer.model.Ticket"/>
                    <c:out value="eid:${ticket.eventId}, row:${ticket.row}, seat:${ticket.seat}"/> <br>
                </c:forEach>

                <button type="submit"><fmt:message key="ticket.buy"/></button>
            </form>
        </c:if>
    </div>


</body>
</html>
