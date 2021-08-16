<!-- Zuordnung zur Person: Marc Palfner & Paul Conrad -->
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>QuickChat Registrieren</title>
<link rel="stylesheet" href="css/style_register.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>

	<div id="register_form_container">
		<form id="register_form" action="servlets/RegisterServlet"
			method="post">
			<div id="username_box">
				<p id="username_text">User Name:</p>
				<input type="text" id="username_input" name="userName">
			</div>
			<br>
			<div id="password_box">
				<p id="password_text">Password:</p>
				<input type="password" id="password_input" name="password">
			</div>
			<br>
			<div id="repeat_password_box">
				<p id="repeat_password_text">Repeat Password:</p>
				<input type="password" id="repeat_password_input"
					name="repeatPassword">
			</div>
			<br> <input id="register_button" type="submit"
				value="Registrieren">
			<p id="login_text">
				Du hast bereits einen Account? <a href="index.jsp">Hier Anmelden</a>
			</p>
			<p style="color: darkred">${ registererror }</p>
		</form>


	</div>

</body>
</html>
