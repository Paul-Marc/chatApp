<%--
  Created by IntelliJ IDEA.
  User: Paul
  Date: 28.06.2021
  Time: 07:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create A New Group</title>
</head>
<body>

<form action="servlets/NewGroupToDBServlet" method="post">
    <div><p id="group_name">Gruppenname</p> <input type="text" name="group_name_input"></div> <br>
    <div><p id="group_members">Mitgliedernamen Eingeben (Komma getrennt) </p> <br> <input type="text" name="group_members_input"></div> <br>
    <input id="new_group" type="submit" value="Gruppe erstellen">

</form>

</body>
</html>
