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
    <jsp:useBean id="user" type="com.elesson.pioneer.model.User" scope="request"/>
    <div id="userForm">
        <form method="post" action="users">
            <input type="hidden" name="userid" value="${user.id}">

            <div><label><fmt:message key="entity.name"/></label></div>
            <div><input type="text" value="${user.name}" name="name" required></div> <br>
            <c:if test="${errName}">
                <div class="error"><fmt:message key="err.name"/></div>
            </c:if>

            <div><label><fmt:message key="user.email"/></label></div>
            <div><input type="text" value="${user.email}" name="email" required></div> <br>
            <c:if test="${errEmail}">
                <div class="error"><fmt:message key="err.email"/></div>
            </c:if>
            <c:if test="${duplicate}">
                <div class="error"><fmt:message key="err.duplicate"/></div>
            </c:if>

            <div><label><fmt:message key="user.password"/></label></div>
            <div><input type="text" name="password" ${param.action == 'create' ? 'required' : ''}></div> <br>
            <c:if test="${errPassLen}">
                <div class="error"><fmt:message key="err.password"/></div>
            </c:if>

            <c:if test="${param.action == 'edit'}">
                <div><label>ROLE:</label></div>
                <select name="role">
                    <option value="${user.role}" selected>${user.role}</option>
                    <c:if test="${user.role != 'CLIENT'}">
                        <option value="CLIENT">CLIENT</option>
                    </c:if>
                    <c:if test="${user.role != 'ADMIN'}">
                        <option value="ADMIN">ADMIN</option>
                    </c:if>
                </select>
            </c:if>
            </br>
            <button type="submit"><fmt:message key="entity.save"/></button>
            <button onclick="window.history.back()" type="button"><fmt:message key="entity.cancel"/></button>
        </form>
    </div>
</body>
</html>
