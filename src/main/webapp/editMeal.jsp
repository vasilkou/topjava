<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="f" uri="http://topjava.javawebinar.tu/functions" %>
<jsp:useBean id="mealToEdit" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<html>
<head>
    <title>Edit Meal</title>
</head>
<body>
<h2>Edit Meal</h2>
<form method="post" action="meals">
    Id:
    <input type="text" name="id" readonly value="${mealToEdit.id}"><br/><br/>

    Date and Time:
    <input type="datetime-local" name="dateTime" value="${f:formatLocalDateTime(mealToEdit.dateTime, "yyyy-MM-dd'T'HH:mm")}" required><br/><br/>

    Meal description:
    <input type="text" name="description" maxlength="64" value="${mealToEdit.description}" required><br/><br/>

    Calories:
    <input type="number" name="calories" min="0" value="${mealToEdit.calories}" required><br/><br/>

    <input type="submit" value="Edit">
</form>
</body>
</html>
