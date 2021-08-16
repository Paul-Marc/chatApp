<!-- Zuordnung zur Person: Paul Conrad -->
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>QuickChat - Profil von
	${user.getCurrentContact().getUserName()}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/style_chatpartner.css">
<link rel="stylesheet" href="css/style_navbar.css">
</head>
<body>

	<jsp:include page="navbar.jsp" />



	<div id="information_container">
		<div id="chatpartner_infos">
			<h2>${chatpartner.getUserName()}</h2>
			<p class="info_text">Nickname: ${chatpartner.getNickname() }</p>
			<p class="info_text">Biographie: ${chatpartner.getBiography()}</p>
			<p class="info_text">Hobbies: ${chatpartner.getHobbies()}</p>
			<p class="info_text">Gender: ${chatpartner.getGender()}</p>
			<p class="info_text">Geburtsdatum:
				${chatpartner.getDateOfBirth()}</p>
			<a href="servlets/LoadChatsServlet" id="goback_link"><Button
					id="goback_button">Zurück</Button></a>
		</div>
	</div>
</body>
</html>
