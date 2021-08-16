package servlets;


import objects.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Zuordnung zu Person: Paul Conrad
 * 
 * Zweck: Die Klasse ruft die Seite zum Passwort aendern auf.
 */
@WebServlet(name = "ChangePWServlet", value = "/servlets/ChangePWServlet")
public class ChangePWServlet extends HttpServlet {

	/**
	 * Name: doPost
	 * Zweck: Leitet weiter zur Passwort-ändern Seite
	 */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    	//Direkte Weiterleitung zur Seite zum Passwort ändern
        resp.sendRedirect(req.getContextPath() +"/changepw.jsp");


    }

}
