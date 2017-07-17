<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://topjava.ru/functions" prefix="f" %>
<html>
<head>
    <title>MealEdit</title>
</head>
<body>
    <a href="index.html">Home</a>
    <h1>Edit / Create</h1>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form method="post" action="meals">
        <input type="hidden" value="${meal.id}" name="id">

        <label for="datetime-local">Date-time:</label>
        <input type="datetime-local" id="datetime-local" value="${meal.dateTime}" name="date"/>
        <%--<input type="datetime-local" id="datetime-local" value="${f:formatLocalDateTime(meal.dateTime,'yyyy-MM-dd HH:mm')}" name="date"/>--%>
        <br/><br/>
        <label for="Description">Description:</label>
        <input type="text"  placeholder="Описание" value="${meal.description}" name="имя" id="Description" maxlength="25" name="description"/>
        <br/><br/>
        <label for="Сalories">Сalories:</label>
        <input type="number" value="${meal.calories}" name="имя" id="Сalories" name="calories"/>
        <br/><br/>
        <a href="meals"><input type="button" value="Отмена"/></a>
        <input type="submit" value="Сохранить">

    </form>
</body>
</html>
