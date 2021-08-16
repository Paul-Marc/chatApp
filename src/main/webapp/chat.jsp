<!-- Zuordnung zur Person: Paul Conrad & Marc Palfner -->
<%@ page import="java.util.List"%>
<%@ page import="objects.*"%>
<%@ page import="javax.xml.transform.Source"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>QuickChat</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/style_chat.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/style_navbar.css">




</head>
<body>


	<jsp:include page="navbar_home.jsp" />



	<div id="content_box">
		<div class="chatBox">
			<h1>Chatte mit ${user.getCurrentContact().getUserName()}</h1>
			<h3>${ groupMembers }</h3>




			<table class="messageTable" border="1">
				<!-- ================================================================= -->
				<div>${ chatverlauf }</div>
				<!-- ================================================================= -->
			</table>




			<form id="send_massage" action="servlets/NewMessageServelet"
				method="post">
				<input id="enter_a_new_massage" type="text" name="newMessage">
				<input id="send_a_new_massage" type="submit" value="Abschicken">
			</form>

			<!-- ==========================Gruppenfunktionen===================== -->
			<div id="groupManagement">
				<div>${ leaveGroup }</div>
				<div>${ addToGroup }</div>
			</div>



		</div>






		<div class="contactBox">
			<b style="margin: 20px; font-size: 26pt;">${ user.getUserName() }</b>
			<!-- ================================================================= -->
			<div id="contact_list">${ kontaktliste }</div>
			<!-- ================================================================= -->
		</div>
</body>
</html>