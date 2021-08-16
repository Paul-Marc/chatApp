<!-- Zuordnung zur Person: Paul Conrad -->
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>QuickChat - Passwort Aendern</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/style_changepw.css">
<link rel="stylesheet" href="css/style_navbar.css">
</head>
<body>

	<jsp:include page="navbar.jsp" />

	<div id="change_pw_container">
		<form action="servlets/UpdatePWServlet" id="change_pw_form"
			method="post">
			<div id="old_pw_box">
				<p id="old_pw">Dein Altes Passwort:</p>
				<input id="old_pw_input" type="password" name="old_pw_input">
			</div>
			<br>
			<div id="new_pw_box">
				<p id="new_pw">Neues Passwort:</p>
				<input type="password" id="new_pw_input" name="new_pw_input">
			</div>
			<br>
			<div id="repeat_new_pw_box">
				<p id="repeat_new_pw">Neues Passwort Wiederholen:</p>
				<input type="password" id="repeat_new_pw_input"
					name="new_pw_repeat_input">
			</div>
			<br> <input id="update_pw_button" type="submit"
				value="Passwort Ändern">
			<p style="color: darkred">${ newpwerror }</p>
			<p style="color: green">${ newpwsuccess }</p>
		</form>
		<a href="editprofile.jsp" id="goback_link"><Button
				id="goback_button">Zurueck</Button></a>
	</div>
</body>
</html>
