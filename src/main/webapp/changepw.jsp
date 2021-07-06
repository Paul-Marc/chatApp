<%--
  Created by IntelliJ IDEA.
  User: Paul
  Date: 20.06.2021
  Time: 09:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Passwort Ändern</title>
</head>
<body>



<form action="servlets/UpdatePWServlet" method="post">
    <div><p id="old_pw">Dein Altes Passwort:</p> <input type="password" name="old_pw_input"></div> <br>
    <div><p id="new_pw">Neues Passwort: </p>  <input type="password" name="new_pw_input"></div> <br>
    <div><p id="repeat_new_pw">Neues Passwort Wiederholen: </p> <input type="password" name="new_pw_repeat_input"></div> <br>
    <input id="update_pw_button" type="submit" value="Passwort Ändern">

</form>
<a href="editprofile.jsp">Zurück</a>
</body>
</html>
