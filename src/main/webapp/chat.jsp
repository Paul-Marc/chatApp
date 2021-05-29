<%@ page import="objects.Message" %>
<%@ page import="java.util.List" %>
<%@ page import="objects.User" %>
<%@ page import="objects.Contact" %>
<%@ page import="javax.xml.transform.Source" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <link rel="stylesheet" href="css/style.css">
    <% User user = (User) session.getAttribute("user");%>
    <% List<Message> messages = user.getMessages();%>
    <% List<Contact> contacts = user.getContacts();%>
</head>
<body>
<h1><%= "Messages" %>
</h1>
<div class="chatBox">
    <table class="messageTable" border="1">
        <% if (messages != null) {%>
        <% for (int i = 0; i < messages.size(); i++) { %>
        <tr>
            <td class="other">
                <% if (messages.get(i).getOwner() != user.getUserName()) {%>
                <%out.print(messages.get(i).getMessage());%>
                <%}%>
            </td>
            <td class="my">
                <% if (messages.get(i).getOwner() == user.getUserName()) {%>
                <%out.print(messages.get(i).getMessage());%>
                <%}%>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>

    <form action="servlets/NewMessageServelet" method="post">
        <input type="text" name="newMessage">
        <input type="submit" value="Abschicken">
    </form>
</div>

<div class="contactBox">
    <% if (contacts != null) {%>
    <% for (int i = 0; i < contacts.size(); i++) {%>

    <table class="contactTable" border="1">
        <tr>
            <td>
                <form action="servlets/LoadContactServlet" method="post">
                    <%out.print(contacts.get(i).getUserName());%>
                    <input type="hidden" value="<%out.print(contacts.get(i).getRoomID());%>" name="roomid">
                    <input type="submit" value="Start Chating">
                </form>
            </td>
        </tr>
    </table>
    <%}%>
    <%}%>
</div>

<div class="addContactBox">
    <form action="servlets/AddContactServlet">
        <input type="text" name="userName">
        <input type="submit" value="Add to Contacts">
    </form>

</div>


</body>
</html>