<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language!=null ? sessionScope.language : pageContext.request.locale}"/>
<fmt:setBundle basename="messages.app"/>
<html>
    <jsp:include page="fragments/headTag.jsp"/>
<body>
    <jsp:include page="fragments/navMenu.jsp"/>
    <div id="loginForm">
        <form method="post" action="login">
            <div><label><fmt:message key="user.email"/></label></div>
            <div><input type="text" name="authUserEmail" required></div> <br>
            <div><label><fmt:message key="user.password"/></label></div>
            <div><input type="password" name="authUserPass" required></div> <br>
            <c:if test="${error == 'password'}">
                <div class="error"><fmt:message key="err.login"/></div>
            </c:if>
            <button type="submit"><fmt:message key="app.login"/></button>
        </form>
    </div>
</body>
</html>
