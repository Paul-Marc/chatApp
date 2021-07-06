<%--
  Created by IntelliJ IDEA.
  User: klausmp
  Date: 4/13/21
  Time: 7:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>chatApp Login</title>
    <link rel="stylesheet" href="css/style_index.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>


<div id="login_form_container">
<form id="login_form" action="servlets/Loginservlet" method="post">
    <div id="username_box"><p id="username_text">Username:</p> <input id="username_input" type="text" name="username"/> </div><br/>
    <div id="password_box"><p id="password_text">Password:</p> <input id="password_input" type="password" name="password"/></div>
    <input id="login_button" type="submit" value="Anmelden">
    <p style="color: darkred">${ loginerror }</p>
    <p id="registrieren_text">Du hast noch keinen Account? <a href="register.jsp">Hier registrieren</a></p>
</form>


</div>
</body>
</html>
