<%--
  Created by IntelliJ IDEA.
  User: klausmp
  Date: 4/15/21
  Time: 9:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="css/style_register.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>

<div id="register_form">
    <form action="servlets/RegisterServlet" method="post">
        <div id="username_box"><p id="username_text">User Name:</p> <input type="text" name="userName"></div> <br>
        <div id="password_box"><p id="password_text">Password: </p>  <input type="password" name="password"></div> <br>
        <div id="repeat_password_box"> <p id="repeat_password_text">Repeat Password </p> <input type="password" name="repeatPassword"></div> <br>
        <input id="registrieren_button" type="submit" value="Registrieren">

    </form>
    <p id="login_text">Du hast bereits einen Account? <a href="index.jsp">Hier Anmelden</a></p>

</div>

</body>
</html>
