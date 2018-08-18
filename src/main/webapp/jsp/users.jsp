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
    <div><a href="users?action=create"><fmt:message key="entity.add"/></a></div>

    <div id="shownPage">
        <div>
            <c:if test="${page > 1}"><a href="users?page=${page > 1 ? page - 1 : 1}">< < <</a></c:if>
            <c:if test="${page <= 1}">< < <</c:if>
        </div>
        <div>Page&nbsp;${page}&nbsp;of&nbsp;${pagesCount}</div>
        <div>
            <c:if test="${page < pagesCount}"><a href="users?page=${page < pagesCount ? page + 1 : pagesCount}">> > ></a></c:if>
            <c:if test="${page >= pagesCount}">> > ></c:if>
        </div>
    </div>
    <table>
        <thead>
        <tr>
            <th><fmt:message key="entity.name"/></th>
            <th><fmt:message key="user.email"/></th>
            <th><fmt:message key="user.roles"/></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${users}" var="user">
            <jsp:useBean id="user" scope="page" type="com.elesson.pioneer.model.User"/>
            <tr>
                <td><c:out value="${user.name}"/></td>
                <td><a href="mailto:${user.email}">${user.email}</a></td>
                <td>${user.role}</td>
                <td><a href="users?action=edit&userid=${user.id}"><fmt:message key="entity.edit"/></a></td>
                <td><a href="users?action=delete&userid=${user.id}"><fmt:message key="entity.delete"/></a></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
