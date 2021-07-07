<%@ page import="objects.Message" %>
<%@ page import="java.util.List" %>
<%@ page import="objects.User" %>
<%@ page import="objects.Contact" %>
<%@ page import="javax.xml.transform.Source" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>ChatApp</title>
    <link rel="stylesheet" href="css/style_chat.css">
    <link rel="stylesheet" href="css/style.css">
    <% User user = (User) session.getAttribute("user");%>
    <% List<Message> messages = user.getMessages();%>
    <% List<Contact> contacts = user.getContacts();%>

</head>
<body>

<div class="header_bar">

    <ul class="header_menu">

        <li class="menu_item"><div class="addContactBox">
            <form action="servlets/AddContactServlet">
                <input type="text" name="userName">
                <input type="submit" value="Kontakt Hinzufügen">
            </form>
        </div></li>
        <li class="menu_item"><a class="menu_link" href="createnewgroup.jsp">Gruppe Erstellen</a></li>
        <li class="menu_item">
            <form action="servlets/EditProfileServlet">
                <input type="hidden" value="${user.getBiography()}" name="biographie">
                <input type="submit" value="Mein Profil">
            </form>
        </li>
        <li class="menu_item">
            <form action="servlets/ChatPartnerProfileServlet">
                <input type="submit" value="Profil von ${user.getCurrentContact().getUserName()}">
            </form>
        </li>
        <li class="menu_item">
            <form action="servlets/LogOutServlet">
            <input type="submit" class="logout_button" value="Ausloggen">
            </form>
        </li>

    </ul>

</div>

</body>
</html>