<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 02.09.2022
  Time: 0:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        Add event
    </title>
</head>
<body>
Please input event's data
<form action="/events/add" method="post">
    <input type="text" name="name" placeholder="please input name"/> <br>
    <input type="text" name="place" placeholder="please input place"/> <br>
    Is Online? <br>
    Yes <input type="radio" name="isOnline" value="TRUE"/>
    No <input type="radio" name="isOnline" value="FALSE"/>
    <br>
    <input type="number" name="price" placeholder="Please input price">
    <br>
    Please input event's data
    <input type="date" name="eventDate"> <br>
    Event Type
    <select name="eventType">
        <option value="CHESS_TOURNAMENT">CHESS_TOURNAMENT</option>
        <option value="CONCERT">CONCERT</option>
        <option value="PERFORMANCE">PERFORMANCE</option>
    </select>
    <br>
    <input type="submit" value="Add">
</form>
</body>
</html>
