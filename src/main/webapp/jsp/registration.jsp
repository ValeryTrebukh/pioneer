<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language!=null ? sessionScope.language : pageContext.request.locale}"/>
<fmt:setBundle basename="messages.app"/>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/navMenu.jsp"/>
<div id="registrationForm">
    <form method="post" action="registration">
        <div><label><fmt:message key="entity.name"/></label></div>
        <div><input type="text" name="regName" placeholder="<fmt:message key="user.name.example"/>" required></div> <br>
        <c:if test="${errName}">
            <div class="error"><fmt:message key="err.name"/></div>
        </c:if>

        <div><label><fmt:message key="user.email"/></label></div>
        <div><input type="text" name="regEmail" required></div> <br>
        <c:if test="${errEmail}">
            <div class="error"><fmt:message key="err.email"/></div>
        </c:if>
        <c:if test="${duplicate}">
            <div class="error"><fmt:message key="err.duplicate"/></div>
        </c:if>

        <div><label><fmt:message key="user.password"/></label></div>
        <div><input type="password" name="regPass" required></div> <br>
        <c:if test="${errPassLen}">
            <div class="error"><fmt:message key="err.password"/></div>
        </c:if>

        <div><label><fmt:message key="user.password2"/></label></div>
        <div><input type="password" name="confPass" required></div> <br>
        <c:if test="${errPassDif}">
            <div class="error"><fmt:message key="err.password2"/></div>
        </c:if>

        <button type="submit"><fmt:message key="app.register"/></button>
    </form>
</div>
</body>
</html>

