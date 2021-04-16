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
    <title>Title</title>
</head>
<body>

<form action="servlets/TestLoading" method="post">
    <input type="submit" value="load">
</form>

<form action="servlets/LoginServlet" method="post">
    Username: <input type="text" name="username"/>
    Password: <input type="password" name="password"/>
</form>
</body>
</html>
