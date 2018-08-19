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
${event.movie.year}
<br><br>
select sits
<table>
    <jsp:useBean id="hall" scope="request" type="com.elesson.pioneer.model.Hall"/>
    <c:forEach items="${hall.rows}" var="row">
        <tr>
        <c:forEach items="${row.seats}" var="seat">
            <td><c:out value="${seat}"/></td>
        </c:forEach>
        </tr>
    </c:forEach>
</table>


</body>
</html>
