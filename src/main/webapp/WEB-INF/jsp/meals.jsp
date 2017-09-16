<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<h3><spring:message code="meal.title"/></h3>
<form method="post" action="meals/filter">
    <dl>
        <dt><spring:message code="meal.startDate"/>:</dt>
        <dd><input type="date" id="startDate" name="startDate" value="${param.startDate}" class="input_filter"></dd>

        <dt><spring:message code="meal.endDate"/>:</dt>
        <dd><input type="date" id="endDate" name="endDate" value="${param.endDate}" class="input_filter"></dd>

        <dt><spring:message code="meal.startTime"/>:</dt>
        <dd><input type="time" id="startTime" name="startTime" value="${param.startTime}" class="input_filter"></dd>

        <dt><spring:message code="meal.endTime"/>:</dt>
        <dd><input type="time" id="endTime" name="endTime" value="${param.endTime}" class="input_filter"></dd>
    </dl>
    <button type="submit"><spring:message code="meal.filter"/></button>
    <button type="submit" onclick="myFunction()"><spring:message code="meal.reset"/></button>
</form>
<hr/>
<a href="meals/create"><spring:message code="meal.add"/></a>
<hr/>
<table border="1" cellpadding="8" cellspacing="0">
    <thead>
    <tr>
        <th><spring:message code="meal.dateTime"/></th>
        <th><spring:message code="meal.description"/></th>
        <th><spring:message code="meal.calories"/></th>
        <th colspan="2"><spring:message code="common.actions"/></th>
    </tr>
    </thead>
    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
        <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
            <td>${fn:replace(meal.dateTime, 'T', ' ')}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals/update?id=${meal.id}"><spring:message code="common.update"/></a></td>
            <td><a href="meals/delete?id=${meal.id}"><spring:message code="common.delete"/></a></td>
        </tr>
    </c:forEach>
</table>
<script>
    function myFunction() {
        document.getElementById("startDate").value = "";
        document.getElementById("endDate").value = "";
        document.getElementById("startTime").value = "";
        document.getElementById("endTime").value = "";
    }
</script>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>