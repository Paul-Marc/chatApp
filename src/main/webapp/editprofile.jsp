<%--
  Created by IntelliJ IDEA.
  User: Paul
  Date: 10.06.2021
  Time: 11:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Your Profile</title>
    <link rel="stylesheet" href="css/style.css">

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
        <li class="menu_item"><a href="chat.jsp">Zurück Zu Den Chats</a></li>
        <li class="menu_item">Kein aktueller Chatpartner</li>
        <li class="menu_item">Ausloggen</li>

    </ul>





</div>

<form action="servlets/UpdateProfileServlet" method="post" id="editProfileForm">
    <h2>${ user.getUserName() }</h2>
    <div class="userinformation"><p id="username_text">Biographie:</p> <input type="text" name="user_biography" value="${ user.getBiography() }"></div> <br>
    <div class="userinformation"><p id="password_text">Geschlecht: </p>
        <select name="user_gender" id="user_gender_id" form="editProfileForm"><option selected="selected">Mann</option><option>Frau</option></select>
        <script>
            var temp = "${ user.getGender() }";
            var mySelect = document.getElementById("user_gender_id");
            for (var i, j = 0; i = mySelect.options[j]; j++) {
                if(i.value == temp) {
                    mySelect.selectedIndex = j;
                    break;
                }
            }
        </script>
    </div> <br>
    <div class="userinformation"> <p id="repeat_password_text">Geburtsdatum: </p> <input type="date" name="user_date_of_birth" value="${ user.getDateOfBirth() }"></div> <br>
    <div class="userinformation"> <p>Privates Profil:</p><input type="checkbox" id="private_profile_id" checked name="private_profile_checkbox" value="private_profile"></div> <br>
    <script>
        var privateOrNot = new String(${ user.isPrivateprofile() });
        if (privateOrNot == "false") {
            document.getElementById("private_profile_id").checked = false;
        } else if(privateOrNot == "true") {
            document.getElementById("private_profile_id").checked = true;
        }
    </script>
    <input id="editprofile_button" type="submit" value="Speichern & Weiterleiten">

</form>

<button><a href="changepw.jsp">Passwort Ändern</a></button>



</body>
</html>
