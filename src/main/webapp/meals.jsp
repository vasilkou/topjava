<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://topjava.javawebinar.tu/functions" %>
<jsp:useBean id="meals" scope="request" type="java.util.List"/>
<html>
<head>
    <title>Meal list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meal list</h2>
<table border="1" cellpadding="5">
    <tr>
        <th>Id</th>
        <th>Date Time</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan="2">Actions</th>
    </tr>
    <c:forEach var="meal" items="${meals}">
        <tr style="color:${meal.exceed ? 'red' : 'green'}">
            <td>${meal.id}</td>
            <td>${f:formatLocalDateTime(meal.dateTime, "dd.MM.yyyy HH:mm")}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>
                <a href="meals?action=edit&id=${meal.id}">Edit</a>
            </td>
            <td>
                <a href="meals?action=delete&id=${meal.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<p>
    <a href="meals?action=add">Add meal</a>
</p>
</body>
</html>
