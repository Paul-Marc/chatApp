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
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

    <form action="servlets/RegisterServlet" method="post">
        User Name: <input type="text" name="userName"> <br>
        Password: <input type="password" name="password"> <br>
        Repeat Password <input type="password" name="repeatPassword"> <br>
        <input type="submit" value="Registrieren">

    </form>

</body>
</html>
