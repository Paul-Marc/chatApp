<!-- Zuordnung zur Person: Paul Conrad -->
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>QuickChat - Create A New Group</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/style_creategroup.css">
<link rel="stylesheet" href="css/style_navbar.css">
</head>
<body>

	<jsp:include page="navbar.jsp" />

	<div id="new_group_container">
		<form action="servlets/NewGroupToDBServlet" id="new_group_form"
			method="post">
			<div id="group_name_box">
				<p id="group_name">Gruppenname:</p>
				<input type="text" id="group_name_id" name="group_name_input">
			</div>
			<br>
			<div id="group_members_box">
				<p id="group_members">
					Mitgliedernamen Eingeben:<br> (Komma getrennt & Deinen Namen
					auch)
				</p>
				<br> <input id="group_members_id" type="text"
					name="group_members_input">
			</div>
			<br> <input id="new_group" type="submit"
				value="Gruppe erstellen">
			<p style="color: darkred">${ grouperror }</p>
		</form>
		<a href="servlets/LoadChatsServlet" id="goback_link"><Button
				id="goback_button">Zurück</Button></a>
	</div>
</body>
</html>
