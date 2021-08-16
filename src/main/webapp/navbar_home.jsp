<!-- Zuordnung zur Person: Paul Conrad -->
<div class="header_bar">
	<ul class="header_menu">

		<li class="menu_item"><div class="addContactBox">
				<form action="servlets/AddContactServlet">
					<input type="text" name="userName"> <input type="submit"
						class="menu_btn" value="Kontakt Hinzufuegen">
				</form>
				<div>${ addcontacterror }</div>
			</div></li>

		<li class="menu_item"><a href="createnewgroup.jsp"><button
					class="menu_btn">Gruppe Erstellen</button></a></li>

		<li class="menu_item">
			<form action="servlets/EditProfileServlet">
				<input type="hidden" value="${user.getBiography()}"
					name="biographie"> <input type="submit" class="menu_btn"
					value="Mein Profil">
			</form>
		</li>

		<li class="menu_item">
			<form action="servlets/ChatPartnerProfileServlet">
				<input type="submit" class="menu_btn"
					value="Profil von ${user.getCurrentContact().getUserName()}">
			</form>
		</li>

		<li class="menu_item">
			<form action="servlets/LogOutServlet">
				<input type="submit" class="menu_btn" class="logout_button"
					value="Ausloggen">
			</form>
		</li>

	</ul>
</div>