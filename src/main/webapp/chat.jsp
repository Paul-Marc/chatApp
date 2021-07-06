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
    <% User user = (User) session.getAttribute("user");%>
    <% List<Message> messages = user.getMessages();%>
    <% List<Contact> contacts = user.getContacts();%>
</head>
<body>

<div class="addContactBox">
    <form action="servlets/AddContactServlet">
        <input type="text" name="userName">
        <input type="submit" value="Add to Contacts">
    </form>
</div>

<div id="content_box">
<div class="chatBox">
   <h1>Chatte mit ${user.getCurrentContact().getUserName()}</h1>
    <table class="messageTable" border="1">
        <% if (messages != null) {%>
        <% for (int i = 0; i < messages.size(); i++) { %>
        <tr>
            <td class="other">
                <% if (!messages.get(i).getOwner().equals(user.getUserName())) {%>
                <%out.print(messages.get(i).getOwner() + ": "); %>
                <%out.print(messages.get(i).getMessage());%>
                <%}%>
            </td>
            <td class="my">
                <% if (messages.get(i).getOwner().equals(user.getUserName())) {%>
                <%out.print(user.getUserName() + ": "); %>
                <%out.print(messages.get(i).getMessage());%>
                <%}%>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>

    <form id="send_massage" action="servlets/NewMessageServelet" method="post">
        <input id="enter_a_new_massage" type="text" name="newMessage">
        <input id="send_a_new_massage" type="submit" value="Abschicken">
    </form>
</div>

<div class="contactBox">
    <% if (contacts != null) {%>
    <% for (int i = 0; i < contacts.size(); i++) {%>

    <table class="contactTable" border="1">
        <tr>
            <td>
                <form action="servlets/LoadContactServlet" method="post">
                    <input type="hidden" value="<%out.print(contacts.get(i).getRoomID());%>" name="roomid">
                    <input type="submit" class="contact_button" value="<%out.print(contacts.get(i).getUserName());%>">
                </form>
            </td>
        </tr>
    </table>
    <%}%>
    <%}%>
</div>
</div>



</body>
</html>