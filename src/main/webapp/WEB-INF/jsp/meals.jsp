<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
    <title>Calories management</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <h3>Meals</h3>
    <form method="post" action="meals?action=filter">
        <dl>
            <dt>From Date:</dt>
            <dd><input type="date" id="startDate" name="startDate" value="${param.startDate}"></dd>
        </dl>
        <dl>
            <dt>To Date:</dt>
            <dd><input type="date" id="endDate" name="endDate" value="${param.endDate}"></dd>
        </dl>
        <dl>
            <dt>From Time:</dt>
            <dd><input type="time" id="startTime" name="startTime" value="${param.startTime}"></dd>
        </dl>
        <dl>
            <dt>To Time:</dt>
            <dd><input type="time" id="endTime" name="endTime" value="${param.endTime}"></dd>
        </dl>
        <button type="submit">Filter</button>
        <button type="button" onclick="myFunction()">Reset</button>
    </form>
    <hr/>
    <a href="meals?action=create">Add Meal</a>
    <hr/>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
            <tr>
                <th>Date</th>
                <th>Description</th>
                <th>Calories</th>
                <th colspan="2">Action</th>
            </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>${fn:replace(meal.dateTime, 'T', ' ')}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a> </td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a> </td>
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
</body>
</html>