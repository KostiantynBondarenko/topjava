<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
<h3><spring:message code="${meal.isNew() ? 'meal.add' : 'meal.edit'}"/></h3>
<hr/>
<form method="post" action="meals">
    <input type="hidden" name="id" value="${meal.id}">
    <dl>
        <dt><spring:message code="meal.dateTime"/>:</dt>
        <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime"></dd>
    </dl>
    <dl>
        <dt><spring:message code="meal.description"/>:</dt>
        <dd><input type="text" placeholder="Enter description.." value="${meal.description}" size=25 maxlength="25" name="description"></dd>
    </dl>
    <dl>
        <dt><spring:message code="meal.calories"/>:</dt>
        <dd><input type="number" value="${meal.calories}" name="calories"></dd>
    </dl>
    <button type="submit"><spring:message code="common.save"/></button>
    <button onclick="window.history.back()" type="button"><spring:message code="common.cancel"/></button>
</form>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>