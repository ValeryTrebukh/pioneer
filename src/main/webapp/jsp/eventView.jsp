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
<br>
select sits
<br>
<c:forEach items="${nextWeek}" var="day">
    <jsp:useBean id="day" type="java.time.LocalDate"/>
    <div><a href="schedule?date=${day}"><fmt:message key="sch.${day.dayOfWeek}"/></a></div>
</c:forEach>


</body>
</html>
