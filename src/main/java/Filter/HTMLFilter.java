package Filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Zuordnung zu Person: Paul Conrad
 * 
 * Zweck: Entfernt HTML tags aus übermittelten Parametern
 */
@WebFilter(urlPatterns = { "/HTMLFilter" }, servletNames = { "AddContactServlet", "AddUserToGroupServlet",
		"ChangePWServlet", "ChatPartnerProfileServlet", "EditProfileServlet", "LeaveGroupServlet", "LoadChatsServlet",
		"LoadContactServlet", "LoginServlet", "LogOutServlet", "NewGroupToDBServlet", "NewMessageServelet",
		"RegisterServlet", "UpdateProfileServlet", "UpdatePWServlet" })
public class HTMLFilter extends BaseFilter implements Filter {

	/**
	 * Hauptmethode für beginn der Filter-Chain
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HtmlRequestWrapper wrapper = new HtmlRequestWrapper((HttpServletRequest) request);
		chain.doFilter(wrapper, response);
	}

}

/**
 * Zuordnung zu Person: Paul Conrad Zweck: nimmt die "verpackte"-Anfrage und
 * filtert ggf. HTML-Tags heraus.
 *
 */
class HtmlRequestWrapper extends HttpServletRequestWrapper implements HttpServletRequest {
	public HtmlRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	/**
	 * Zweck: Methode zur Filterung
	 */
	public String getParameter(String str) {
		return super.getParameter(str) == null ? "" : super.getParameter(str).replaceAll("<(.|\n)*?>", "");
	}
}
