<!-- Zuordnung zur Person: Paul Conrad -->
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Edit Your Profile</title>
<link rel="stylesheet" href="css/style_editprofile.css">
<link rel="stylesheet" href="css/style_navbar.css">
<meta name="viewport" content="width=device-width, initial-scale=1">

</head>
<body>

	<jsp:include page="navbar.jsp" />

	<div id="editprofile_container">
		<form action="servlets/UpdateProfileServlet" method="post"
			id="editProfileForm">
			<h2>Eigenes Profil Bearbeiten</h2>
			<div class="userinformation">
				<p id="nickname_text">Spitzname:</p>
				<input type="text" id="nickname_input" name="user_nickname"
					value="${ user.getNickname() }">
			</div>
			<br>
			<div class="userinformation">
				<p id="username_text">Biographie:</p>
				<input type="text" id="bio_input" name="user_biography"
					value="${ user.getBiography() }">
			</div>
			<br>
			<div class="userinformation">
				<p id="hobbies_text">Hobbies:</p>
				<input type="text" id="hobbie_input" name="user_hobbies"
					value="${ user.getHobbies() }">
			</div>
			<br>
			<div class="userinformation">
				<p id="gender_text">Geschlecht:</p>
				<select name="user_gender" id="gender_input" form="editProfileForm"><option
						selected="selected">Mann</option>
					<option>Frau</option></select>
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
			</div>
			<br>
			<div class="userinformation">
				<p id="dateOfBirth_text">Geburtsdatum:</p>
				<input type="date" id="birthday_input" name="user_date_of_birth"
					value="${ user.getDateOfBirth() }">
			</div>
			<br>
			<div class="userinformation">
				<p id="privateprofile_text">Privates Profil:</p>
				<input type="checkbox" id="privateprofile_input" checked
					name="private_profile_checkbox" value="private_profile">
			</div>
			<br>
			<script>
        var privateOrNot = new String(${ user.isPrivateprofile() });
        if (privateOrNot == "false") {
            document.getElementById("private_profile_id").checked = false;
        } else if(privateOrNot == "true") {
            document.getElementById("private_profile_id").checked = true;
        }
    </script>
			<input id="editprofile_button" type="submit"
				value="Speichern & Weiterleiten">
			<p style="color: darkred">${ editprofileerror }</p>

		</form>

		<a href="changepw.jsp" id="changepw_link"><button
				id="changepw_button">Passwort Aendern</button></a> <a
			href="servlets/LoadChatsServlet" id="goback_link"><Button
				id="goback_button">Zurück</Button></a>
	</div>



</body>
</html>
