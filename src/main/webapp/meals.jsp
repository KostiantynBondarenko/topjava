<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://topjava.ru/functions" prefix="f" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>

    <table border="1" cellpadding="7" cellspacing="0">
        <caption><h2>Meals</h2></caption>
        <tr>
            <th>Date and Time</th>
            <th>Description</th>
            <th>Calories</th>
        </tr>

        <c:set var="meals" value="${requestScope.mealWithExceed}" />

        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealWithExceed"/>
            <tr style="color:${meal.exceed ? 'red' : 'green'}">
                <td>${f:formatLocalDateTime(meal.dateTime,"yyyy-MM-dd HH:mm")}</td>
                <td><c:out value="${meal.description}"/></td>
                <td><c:out value="${meal.calories}"/></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>



