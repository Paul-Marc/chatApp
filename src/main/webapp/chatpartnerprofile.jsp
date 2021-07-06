<%--
  Created by IntelliJ IDEA.
  User: Paul
  Date: 21.06.2021
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profil von ${user.getCurrentContact().getUserName()}</title>
</head>
<body>


<ul class="header_menu">

    <li class="menu_item"><div class="addContactBox">
        <form action="servlets/AddContactServlet">
            <input type="text" name="userName">
            <input type="submit" value="Kontakt Hinzufügen">
        </form>
    </div></li>
    <li class="menu_item"><a href="chat.jsp">Zurück Zu Den Chats</a></li>
    <li class="menu_item">Kein aktueller Chatpartner</li>
    <li class="menu_item">Ausloggen</li>

</ul>


<h2>${chatpartner.getUserName()}</h2>
<p>Biographie: ${chatpartner.getBiography()}</p>
<p>Hobbies: ${chatpartner.getHobbies()}</p>
<p>Gender: ${chatpartner.getGender()}</p>
<p>Geburtsdatum: ${chatpartner.getDateOfBirth()}</p>

</body>
</html>
