<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Meal</title>
</head>
<body>
<h2>Add Meal</h2>
<form method="post" action="meals">
    Date and Time:
    <input type="datetime-local" name="dateTime" required><br/><br/>

    Meal description:
    <input type="text" name="description" maxlength="64" required><br/><br/>

    Calories:
    <input type="number" name="calories" min="0" required><br/><br/>

    <input type="submit" value="Add">
</form>
</body>
</html>
