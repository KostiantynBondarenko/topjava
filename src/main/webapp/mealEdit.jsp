<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <dl>
        <dt>Date-time</dt>
        <dd>
            <input type="datetime-local" value="${meal.dateTime}" name="date">
        </dd>
    </dl>
    <dl>
        <dt>description</dt>
        <dd>
            <input type="text" value="${meal.description}" name="description">
        </dd>
    </dl>
    <dl>
        <dt>calories</dt>
        <dd>
            <input type="number" value="${meal.calories}" name="calories">
        </dd>
    </dl>
    <dl>
        <input type="submit" value="Сохранить">
    </dl>
</form>
</body>
</html>