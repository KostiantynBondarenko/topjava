<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://topjava.ru/functions" prefix="f" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <a href="meals?action=create">create new</a>

    <table border="1" cellpadding="7" cellspacing="0">
        <caption><h2>Meals</h2></caption>
        <tr>
            <th>Date and Time</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>

        <c:set var="meals" value="${requestScope.mealsWithExceed}" />

        <c:forEach items="${meals}" var="MealWithExceed">
            <jsp:useBean id="MealWithExceed" type="ru.javawebinar.topjava.model.MealWithExceed"/>
            <tr style="color:${MealWithExceed.exceed ? 'red' : 'green'}">
                <td>${f:formatLocalDateTime(MealWithExceed.dateTime,"yyyy-MM-dd HH:mm")}</td>
                <td><c:out value="${MealWithExceed.description}"/></td>
                <td><c:out value="${MealWithExceed.calories}"/></td>
                <td><a href="meals?action=edit&id=${MealWithExceed.id}">edit</a> </td>
                <td><a href="meals?action=delete&id=${MealWithExceed.id}">delete</a> </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>